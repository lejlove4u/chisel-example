import chisel3._
import chisel3.util._

class Debounce(fac: Int = 100000000/100) extends Module {
 	val io = IO(new Bundle {
		val btn = Input(Bool())
		val led = Output(UInt(8.W))
	})

	// Synchronizes the signal input from the button.
	val btn = io.btn
	val btnSync = RegNext(RegNext(btn))

	// Defines the signal for sampling the signal input from the button.
	val btnDebReg = Reg(Bool())
	val btnShiftReg = RegInit(0.U(3.W))

	// Defines the signal needed to specify the sampling point.
	val cntReg = RegInit(0.U(32.W))
	val tick = cntReg === (fac-1).U

	// Increment the counter required to know the sampling time,
	// and sample the signal input from the button when the sampling time comes.
	cntReg := cntReg + 1.U
	when(tick) {
		cntReg := 0.U
		btnDebReg := btnSync
		btnShiftReg := btnShiftReg(1, 0) ## btnDebReg
	}

	// Selects the dominant value of the sampled signals.
	// (It is a majority voting)
	val btnClean = (btnShiftReg(2)&btnShiftReg(1)) |
		(btnShiftReg(2)&btnShiftReg(0)) | (btnShiftReg(1)&btnShiftReg(0))

	// Counters when the filtered value is a rising edge.
	val btnRisingEdge = btnClean & !RegNext(btnClean)

	val reg = RegInit(0.U(8.W))
	when(btnRisingEdge) {
		reg := reg + 1.U
	}

	// Assign a count value to the output signal.
	io.led := reg
}

class DebounceFunc(fac: Int = 100000000/100) extends Module {
	val io = IO(new Bundle {
		val btn = Input(Bool())
		val led = Output(UInt(8.W))
	})

	def sync(v: Bool) = RegNext(RegNext(v))

	def rising(v: Bool) = v & !RegNext(v)

	def tickGen() = {
		val reg = RegInit(0.U(log2Up(fac).W))
		val tick = reg === (fac-1).U
		reg := Mux(tick, 0.U, reg+1.U)
		tick
	}

	def filter(v: Bool, t: Bool) = {
		val reg = RegInit(0.U(3.W))
		when(t) {
			reg := reg(1, 0) ## v
		}
		(reg(2)&reg(1)) | (reg(2)&reg(0)) | (reg(1)&reg(0))
	}

	val btnSync = sync(io.btn)

	val tick = tickGen()
	val btnDeb = Reg(Bool())
	when(tick) {
		btnDeb := btnSync
	}

	val btnClean = filter(btnDeb, tick)
	val risingEdge = rising(btnClean)

	// Use the rising edge of the debounced
	// and filtered button for the counter
	val reg = RegInit(0.U(8.W))
	when(risingEdge) {
		reg := reg+1.U
	}

	io.led := reg
}

// generate Verilog
object Debounce extends App {
  emitVerilog(new Debounce(), Array("--target-dir", "generated"))
	emitVerilog(new DebounceFunc(), Array("--target-dir", "generated"))
}

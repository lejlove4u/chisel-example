import chisel3._

class DelayOneClk extends Module {
	val io = IO(new Bundle {
		val dlyEna = Input(Bool())
		val dlyIn = Input(UInt(2.W))
		val dlyOut = Output(UInt(2.W))
	})
	def delay(x: UInt) = RegNext(x)

	val temp = WireDefault(0.U(2.W))
	temp := delay(io.dlyIn)

	when(io.dlyEna === true.B) {
		io.dlyOut := temp
	}.otherwise {
		io.dlyOut := io.dlyIn
	}
}

// generate Verilog
object DelayOneClk extends App {
	emitVerilog(new DelayOneClk(), Array("--target-dir", "generated"))
}

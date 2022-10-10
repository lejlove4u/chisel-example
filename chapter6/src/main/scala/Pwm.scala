import chisel3._
import chisel3.util.unsignedBitLength

class Pwm extends Module {
 	val io = IO(new Bundle {
		val din = Input(UInt(8.W))
		val dout = Output(Bool())
	})
	// Define the pwm function
	def pwm(nrCycles: Int, din: UInt) = {
		val cntReg = RegInit(0.U(unsignedBitLength(nrCycles-1).W))
		cntReg := Mux(cntReg === (nrCycles-1).U, 0.U, cntReg + 1.U)
		din > cntReg
	}

	// Use function for using the pwm
	val din = io.din
	val dout = pwm(10, din)
	io.dout := dout
}

// generate Verilog
object Pwm extends App {
  emitVerilog(new Pwm(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 **/

class Pwm1(clkFreq: Int) extends Module {
	val io = IO(new Bundle {
		val dout = Output(Bool())
	})
	// Define the pwm function
	def pwm(nrCycles: Int, din: UInt) = {
		val cntReg = RegInit(0.U(unsignedBitLength(nrCycles-1).W))
		cntReg := Mux(cntReg === (nrCycles-1).U, 0.U, cntReg + 1.U)
		din > cntReg
	}

	val MAX = clkFreq/1000 // 1 KHz

	val modulationReg = RegInit(0.U(32.W))
	val upReg = RegInit(true.B)

	when(modulationReg < clkFreq.U && upReg) {
		modulationReg := modulationReg + 1.U
	} .elsewhen(modulationReg === clkFreq.U && upReg) {
		upReg := false.B
	} .elsewhen(modulationReg > 0.U && !upReg) {
		modulationReg := modulationReg - 1.U
	} .otherwise {
		upReg := true.B
	}

	// divide modReg by 1024 (about the 1KHz)
	val sig = pwm(MAX, (modulationReg>>10).asUInt)
	io.dout := sig
}

// generate Verilog
object Pwm1 extends App {
	emitVerilog(new Pwm1(clkFreq = 100000000), Array("--target-dir", "generated"))
}



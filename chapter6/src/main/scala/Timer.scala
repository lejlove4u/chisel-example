import chisel3._

class Timer extends Module {
 	val io = IO(new Bundle {
		val din = Input(UInt(8.W))
		val load = Input(Bool())
		val done = Output(Bool())
	})
	val din = io.din
	val load = io.load

	val cntReg = RegInit(0.U(8.W))
	val done = cntReg === 0.U

 	val next = WireDefault(0.U)
 	when(load) {
 		next := din
 	} .elsewhen(!done) {
 		next := cntReg - 1.U
 	}
 	cntReg := next

	/**
	 * If we aim for a bit more concise code,
	 * we can directly assign the multiplexer values to the register reg cntReg,
	 * instead of using the intermediate wire next.*/

// 	when(load) {
// 		cntReg := din
// 	} .elsewhen(!done) {
// 		cntReg := cntReg - 1.U
// 	}

	io.done := done
}

// generate Verilog
object Timer extends App {
  emitVerilog(new Timer(), Array("--target-dir", "generated"))
	val verilog_str = getVerilogString(new Timer())
	println(verilog_str)
}

class TimerOther extends Module {
	val io = IO(new Bundle {
		val din = Input(UInt(8.W))
		val load = Input(Bool())
		val done = Output(Bool())
	})
	val din = io.din
	val load = io.load

	val cntReg = RegInit(0.U(8.W))
	val done = cntReg === 0.U
	val delayReg = RegInit(0.U(4.W))
	delayReg := Mux(delayReg === "hF".U, "h0".U, delayReg + 1.U)

	//  	val next = WireDefault(0.U)
	//  	when(load) {
	//  		next := din
	//  	} .elsewhen(!done && delayReg === "hF".U) {
	//  		next := cntReg - 1.U
	//  	}
	//  	cntReg := next

	/**
	 * If we aim for a bit more concise code,
	 * we can directly assign the multiplexer values to the register reg cntReg,
	 * instead of using the intermediate wire next.*/

	when(load) {
		cntReg := din
	} .elsewhen(!done && delayReg === "hF".U) {
		cntReg := cntReg - 1.U
	}

	io.done := done
}

// generate Verilog
object TimerOther extends App {
	emitVerilog(new TimerOther(), Array("--target-dir", "generated"))
	val verilog_str = getVerilogString(new TimerOther())
	println(verilog_str)
}


import chisel3._

class TickCounter extends Module {
 	val io = IO(new Bundle {
		val tickOut = Output(Bool())
	})
	val N_limit = 3
	val N_area = scala.math.pow(2, 32).toInt
	val N = if(N_limit >= N_area) N_area else N_limit
	val tickCounterReg = RegInit(0.U(32.W))
	val tick = tickCounterReg === (N-1).U

	tickCounterReg := tickCounterReg + 1.U
	when(tick) {
		tickCounterReg := 0.U
	}

	io.tickOut := tick
}

// generate Verilog
object TickCounter extends App {
  emitVerilog(new TickCounter(), Array("--target-dir", "generated"))
}

/**
********************************************************************************
 */

class SlowerCounter extends Module {
	val io = IO(new Bundle {
		val tickOut = Output(Bool())
		val tickOut1 = Output(Bool())
	})
	val N_limit = 3
	val N_area = scala.math.pow(2, 32).toInt
	val N = if(N_limit >= N_area) N_area else N_limit
	val tickCounterReg = RegInit(0.U(32.W))
	val tick = tickCounterReg === (N-1).U

	tickCounterReg := tickCounterReg + 1.U
	when(tick) {
		tickCounterReg := 0.U
	}

	val lowFreqCntReg = RegInit(0.U(4.W))

	when(tick) {
		lowFreqCntReg := lowFreqCntReg + 1.U
	}

	io.tickOut := tick
	io.tickOut1 := Mux(lowFreqCntReg === 3.U, true.B, false.B)
}

// generate Verilog
object SlowerCounter extends App {
	emitVerilog(new SlowerCounter(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */


class NerdCounter extends Module {
	val io = IO(new Bundle {
		val tick = Output(Bool())
	})
	val N_limit = 3
	val N_area = scala.math.pow(2, 32).toInt
	val N = if(N_limit >= N_area) N_area else N_limit
	val MAX = (N-2).S(8.W)
	val cntReg = RegInit(MAX)
	io.tick := false.B

	cntReg := cntReg - 1.S
	when(cntReg(7)) {
		cntReg := MAX
		io.tick := true.B
	}
}

// generate Verilog
object NerdCounter extends App {
	emitVerilog(new NerdCounter(), Array("--target-dir", "generated"))
}


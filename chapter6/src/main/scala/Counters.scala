import chisel3._

class FreeRunningCounter extends Module {
 	val io = IO(new Bundle {
		val cntOut = Output(UInt(4.W))
	})

	/**
	 * This code continuously counts from 0x0 to 0xF,
	 * but it has some problems.
	 * One of those problems is with Overflow.*/
	val cntReg = RegInit(0.U(4.W))
	cntReg := cntReg + 1.U
	io.cntOut := cntReg
}

// generate Verilog
object FreeRunningCounter extends App {
  emitVerilog(new FreeRunningCounter(), Array("--target-dir", "generated"))
}

/**
********************************************************************************
 */

class EventCounter extends Module {
	val io = IO(new Bundle {
		val event = Input(Bool())
		val cntOut = Output(UInt(4.W))
	})
	val event = io.event
	val cntEventReg = RegInit(0.U(4.W))

	when(event) {
		cntEventReg := cntEventReg + 1.U
	}

	io.cntOut := cntEventReg
}

// generate Verilog
object EventCounter extends App {
	emitVerilog(new EventCounter(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class UpCounter extends Module {
	val io = IO(new Bundle {
		val cntOut = Output(UInt(8.W))
	})
	val N_1 = (scala.math.pow(2, 8).toInt.U(9.W)) - 1.U
	val N = N_1(7, 0)
	val cntReg = RegInit(0.U(8.W))

	cntReg := cntReg + 1.U
	when(cntReg === N) {
		cntReg := 0.U
	}

	io.cntOut := cntReg
}

// generate Verilog
object UpCounter extends App {
	emitVerilog(new UpCounter, Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class UpCounterMux extends Module {
	val io = IO(new Bundle {
		val cntOut = Output(UInt(8.W))
	})
	val N_1 = (scala.math.pow(2, 8).toInt.U(9.W)) - 1.U
	val N = N_1(7, 0)
	val cntReg = RegInit(0.U(8.W))

	cntReg := Mux(cntReg === N, 0.U, cntReg + 1.U)
	io.cntOut := cntReg
}

// generate Verilog
object UpCounterMux extends App {
	emitVerilog(new UpCounterMux, Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class DownCounter extends Module {
	val io = IO(new Bundle {
		val cntOut = Output(UInt(8.W))
	})
	val N_1 = (scala.math.pow(2, 8).toInt.U(9.W)) - 1.U
	val N = N_1(7, 0)
	val cntReg = RegInit(N)

	/**
	 * When implementing a counter, using the when statement and
	 * using the Mux statement have the same result. */

	// When using the when statement
	cntReg := cntReg - 1.U
	when(cntReg === 0.U) {
		cntReg := N
	}

	// When using the Mux statement
	// cntReg := Mux(cntReg === 0.U, N, cntReg - 1.U)

	io.cntOut := cntReg
}

// generate Verilog
object DownCounter extends App {
	emitVerilog(new DownCounter, Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class GenCounter extends Module {
	val io = IO(new Bundle {
		val cntOut = Output(UInt(8.W))
		val cntOut1 = Output(UInt(8.W))
	})
	// This function returns a counter
	def genCounter(n: Int) = {
		val cntReg = RegInit(0.U(8.W))
		cntReg := Mux(cntReg === n.U, 0.U, cntReg + 1.U)
		cntReg
	}

	// Now we can easily create many counters
	val count10 = genCounter(10)
	val count99 = genCounter(99)

	io.cntOut := count10
	io.cntOut1 := count99
}

// generate Verilog
object GenCounter extends App {
	emitVerilog(new GenCounter, Array("--target-dir", "generated"))
}


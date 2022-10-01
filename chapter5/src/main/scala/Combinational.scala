import chisel3._

class Combinational extends Module {
 	val io = IO(new Bundle {
		val a = Input(UInt(4.W))
		val b = Input(UInt(4.W))
		val c = Input(UInt(4.W))
		val x = Output(UInt(4.W))
		val y = Output(UInt(4.W))
	})

	val a = io.a
	val b = io.b
	val c = io.c

	val e = (a & b) | c
	val f = ~e

	// reassignment to val
	// e = c & b

	// Cannot reassign to read-only Combinational.e: OpResult[UInt<4>]
	// e := c & b

	io.x := e
	io.y := f
}

// generate Verilog
object Combinational extends App {
  emitVerilog(new Combinational(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class CombWire extends Module {
	val io = IO(new Bundle {
		val cond = Input(Bool())
		val y = Output(UInt()) // The bit width determined by the value assigned to the signal w.
	})

	val cond = io.cond

	val w = Wire(UInt())

	w := 0.U
	when(cond) {
		w := 3.U
	}

	io.y := w
}

// generate Verilog
object CombWire extends App {
	emitVerilog(new CombWire(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class CombWireOthers extends Module {
	val io = IO(new Bundle {
		val cond = Input(Bool())
		val y = Output(UInt()) // The bit width determined by the value assigned to the signal w.
	})

	val cond = io.cond

	val w = Wire(UInt())

	when(cond) {
		w := 1.U
	} .otherwise {
		w := 2.U
	}

	io.y := w
}

// generate Verilog
object CombWireOthers extends App {
	emitVerilog(new CombWireOthers(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class CombWireElseWhen extends Module {
	val io = IO(new Bundle {
		val cond = Input(Bool())
		val cond2 = Input(Bool())
		val y = Output(UInt()) // The bit width determined by the value assigned to the signal w.
	})

	val cond = io.cond
	val cond2 = io.cond2

	val w = Wire(UInt())

	when(cond) {
		w := 1.U
	} .elsewhen(cond2) {
		w := 2.U
	} .otherwise {
		w := 3.U
	}

	io.y := w
}

// generate Verilog
object CombWireElseWhen extends App {
	emitVerilog(new CombWireElseWhen(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class CombWireDefaultWhen extends Module {
	val io = IO(new Bundle {
		val cond = Input(Bool())
		val y = Output(UInt()) // The bit width determined by the value assigned to the signal w.
	})

	val cond = io.cond

	// For more complex combinational circuits it might be practical
	// to assign a default value to a Wire.
	val w = WireDefault(0.U)

	when(cond) {
		w := 15.U
	}

	io.y := w
}

// generate Verilog
object CombWireDefaultWhen extends App {
	emitVerilog(new CombWireDefaultWhen(), Array("--target-dir", "generated"))
}


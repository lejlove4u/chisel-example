import chisel3._
import chisel3.util._

class DecBasic extends Module {
	val io = IO(new Bundle {
		val a0 = Input(Bool())
		val a1 = Input(Bool())
		val out = Output(UInt(4.W))
	})

	val sel = Wire(UInt(2.W))
	val result = WireDefault(0.U(4.W))

	sel := Cat(io.a1.asUInt, io.a0.asUInt)

	switch(sel) {
		is(0.U) { result := 1.U }
		is(1.U) { result := 2.U }
		is(2.U) { result := 4.U }
		is(3.U) { result := 8.U }
	}

	io.out := result
}

// generate Verilog
object DecBasic extends App {
  emitVerilog(new DecBasic(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class DecBitExpr extends Module {
	val io = IO(new Bundle {
		val a0 = Input(Bool())
		val a1 = Input(Bool())
		val out = Output(UInt(4.W))
	})

	val sel = Wire(UInt(2.W))
	val result = WireDefault(0.U(4.W))

	sel := Cat(io.a1.asUInt, io.a0.asUInt)

	switch(sel) {
		is("b00".U) { result := "b0001".U }
		is("b01".U) { result := "b0010".U }
		is("b10".U) { result := "b0100".U }
		is("b11".U) { result := "b1000".U }
	}

	io.out := result
}

// generate Verilog
object DecBitExpr extends App {
	emitVerilog(new DecBitExpr(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class DecShift extends Module {
	val io = IO(new Bundle {
		val a0 = Input(Bool())
		val a1 = Input(Bool())
		val out = Output(UInt(4.W))
	})

	val sel = Wire(UInt(2.W))
	val result = WireDefault(0.U(4.W))

	sel := Cat(io.a1.asUInt, io.a0.asUInt)
	result := 1.U << sel
	io.out := result
}

// generate Verilog
object DecShift extends App {
	emitVerilog(new DecShift(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 */

class EncBasic extends Module {
	val io = IO(new Bundle {
		val a0 = Input(Bool())
		val a1 = Input(Bool())
		val a2 = Input(Bool())
		val a3 = Input(Bool())
		val b0 = Output(Bool())
		val b1 = Output(Bool())
	})

	val a = Wire(UInt(2.W))
	val b = WireDefault(0.U)

	a := Cat(io.a3.asUInt, io.a2.asUInt, io.a1.asUInt, io.a0.asUInt)

	switch(a) {
		is("b0001".U) { b := "b00".U }
		is("b0010".U) { b := "b01".U }
		is("b0100".U) { b := "b10".U }
		is("b1000".U) { b := "b11".U }
	}

	io.b0 := b(0)
	io.b1 := b(1)
}

// generate Verilog
object EncBasic extends App {
	emitVerilog(new EncBasic(), Array("--target-dir", "generated"))
}


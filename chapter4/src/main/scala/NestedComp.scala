import chisel3._

/**
 * Defines a CompA component.
 */
class CompA extends Module {
	val io = IO(new Bundle {
		val a = Input(UInt(2.W))
		val b = Input(UInt(2.W))
		val x = Output(UInt(2.W))
		val y = Output(UInt(2.W))
	})
	// Output X puts the result of AND operation the two inputs,
	io.x := io.a & io.b

	// And output Y puts the result of OR operation the two inputs.
	io.y := io.a | io.b
}

/**
 * Defines a CompB component.
 */
class CompB extends Module {
	val io = IO(new Bundle {
		val in1 = Input(UInt(2.W))
		val in2 = Input(UInt(2.W))
		val out = Output(UInt(2.W))
	})
	// Output OUT puts the result of AND operation the two inputs.
	io.out := io.in1 & io.in2
}

/**
 * Defines a CompC component.
 */
class CompC extends Module {
	val io = IO(new Bundle {
		val in_a = Input(UInt(2.W))
		val in_b = Input(UInt(2.W))
		val in_c = Input(UInt(2.W))
		val out_x = Output(UInt(2.W))
		val out_y = Output(UInt(2.W))
	})
	// Create components A and B
	val compA = Module(new CompA())
	val compB = Module(new CompB())

	// Connect component A
	compA.io.a := io.in_a
	compA.io.b := io.in_b
	io.out_x := compA.io.x

	// Connect component B
	compB.io.in1 := compA.io.y
	compB.io.in2 := io.in_c
	io.out_y := compB.io.out
}

/**
 * Defines a CompD component.
 */
class CompD extends Module {
	val io = IO(new Bundle {
		val in = Input(UInt(2.W))
		val out = Output(UInt(2.W))
	})
	// Output OUT inverts the input.
	io.out := ~io.in
}

/**
 * Defines a NestedComp component.
 */
class NestedComp extends Module {
	val io = IO(new Bundle {
		val in_a = Input(UInt(2.W))
		val in_b = Input(UInt(2.W))
		val in_c = Input(UInt(2.W))
		val out_m = Output(UInt(2.W))
		val out_n = Output(UInt(2.W))
	})
	// Create component C and D
	val compC = Module(new CompC())
	val compD = Module(new CompD())

	// Connect component C
	compC.io.in_a := io.in_a
	compC.io.in_b := io.in_b
	compC.io.in_c := io.in_c
	io.out_m := compC.io.out_x

	// Connect component D
	compD.io.in := compC.io.out_y
	io.out_n := compD.io.out
}

// generate Verilog
object NestedComp extends App {
	// Generate verilog files
  emitVerilog(new NestedComp(), Array("--target-dir", "generated"))

	// Print verilog modules
	val verilogStr = getVerilogString(new NestedComp())
	println(verilogStr)
}

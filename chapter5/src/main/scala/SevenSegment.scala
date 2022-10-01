import chisel3._
import chisel3.util._

/**
 * 7-segment display mapping table
 * -------------------------------
 *      f
 *    +---+
 *   a| g |e
 *    +---+
 *   b|   |d
 *    +---+   * <- h
 *      c
 *  We will design an encoder with 4-bit input and
 *  8-bit output to control 7-segment display.
 * */
class EncSevenSegment extends Module {
	val io = IO(new Bundle {
		val code = Input(UInt(4.W))
		val a = Output(Bool())
		val b = Output(Bool())
		val c = Output(Bool())
		val d = Output(Bool())
		val e = Output(Bool())
		val f = Output(Bool())
		val g = Output(Bool())
		val h = Output(Bool())
	})

	val code = io.code
	val res = WireDefault("b1000_0000".U)

	switch(code) {
		is("h0".U) {res := "h3F".U}
		is("h1".U) {res := "h18".U}
		is("h2".U) {res := "h36".U}
		is("h3".U) {res := "h7C".U}
		is("h4".U) {res := "h59".U}
		is("h5".U) {res := "h6C".U}
		is("h6".U) {res := "h6F".U}
		is("h7".U) {res := "h38".U}
		is("h8".U) {res := "h7F".U}
		is("h9".U) {res := "h7D".U}
		is("hA".U) {res := "h7B".U}
		is("hB".U) {res := "h4F".U}
		is("hC".U) {res := "h27".U}
		is("hD".U) {res := "h1E".U}
		is("hE".U) {res := "h67".U}
		is("hF".U) {res := "h63".U}
	}

	io.a := res(0)
	io.b := res(1)
	io.c := res(2)
	io.d := res(3)
	io.e := res(4)
	io.f := res(5)
	io.g := res(6)
	io.h := res(7)
}

class SevenSegment extends Module {
	val io = IO(new Bundle {
		val code_in = Input(UInt(4.W))
		val code_out = Output(UInt(8.W))
	})
	val enc = Module(new EncSevenSegment())

	enc.io.code := io.code_in
	val a = enc.io.a
	val b = enc.io.b
	val c = enc.io.c
	val d = enc.io.d
	val e = enc.io.e
	val f = enc.io.f
	val g = enc.io.g
	val h = enc.io.h

	val code_out_cat = WireDefault(0.U(8.W))

	code_out_cat := Cat(g, f, e, d, c, b, a)

	// If the signal h is true, the final result is 0 because it is a bad case.
	io.code_out := Mux(h === false.B, code_out_cat, "h00".U)
}

// generate Verilog
object SevenSegment extends App {
  emitVerilog(new SevenSegment(), Array("--target-dir", "generated"))
}


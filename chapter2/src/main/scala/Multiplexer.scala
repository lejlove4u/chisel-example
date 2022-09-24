/*
 * 2.2.1 Multiplexer.
 */

import Chisel.Mux
import chisel3._

class mux_exam extends Module {
  val io = IO(new Bundle {
    val a = Input(Bool())
    val b = Input(Bool())
    val sel = Input(Bool())
    val y = Output(Bool())
  })

  val a = io.a
  val b = io.b
  val sel = io.sel

  val result = Mux(sel === true.B, a, b)
  io.y := result
}

// generate Verilog
object mux_exam extends App {
  emitVerilog(new mux_exam(), Array("--target-dir", "generated"))
}

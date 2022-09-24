/*
 * 2.3.1 Counting.
 */

import chisel3._

class cnt_exam extends Module {
  val io = IO(new Bundle {
    val flag_0 = Output(Bool())
    val flag_1 = Output(Bool())
    val flag_2 = Output(Bool())
    val flag_3 = Output(Bool())
  })

  val cntReg = RegInit(0.U(4.W))
  cntReg := Mux(cntReg === "hF".U, "h0".U, cntReg + "h1".U)

  // Assign flag_0 signal
  io.flag_0 := Mux(cntReg(0) === "b1".U, true.B, false.B)

  // Assign flag_1 signal
  io.flag_1 := Mux(cntReg(1) === "b1".U, true.B, false.B)

  // Assign flag_2 signal
  io.flag_2 := Mux(cntReg(2) === "b1".U, true.B, false.B)

  // Assign flag_3 signal
  io.flag_3 := Mux(cntReg(3) === "b1".U, true.B, false.B)

}

// generate Verilog
object cnt_exam extends App {
  emitVerilog(new cnt_exam(), Array("--target-dir", "generated"))
}

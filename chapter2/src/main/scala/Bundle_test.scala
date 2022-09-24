/*
 * 2.4.Bundle test.
 */

import chisel3._

/**
 * Define the bundle type
 */
class Channel() extends Bundle {
  val start_data = Bool()
  val end_data = Bool()
  val data = UInt(4.W)
}

class bundle_exam extends Module {
  val io = IO(new Bundle {
    val start_data = Output(Bool())
    val end_data = Output(Bool())
    val data = Output(UInt(4.W))
  });

  // Temp data (counter 0 to 15)
  val tempData = RegInit(0.U(4.W))
  tempData := Mux(tempData === "hF".U, 0.U, tempData + 1.U)

  // Bundle type instance
  val tempCh = Wire(new Channel())
  tempCh.start_data := Mux(tempData === "h0".U, true.B, false.B)
  tempCh.end_data := Mux(tempData === "hF".U, true.B, false.B)
  tempCh.data := tempData

  // Connect external signal
  io.start_data := tempCh.start_data;
  io.end_data := tempCh.end_data;
  io.data := tempCh.data;

}

// generate Verilog
object bundle_exam extends App {
  emitVerilog(new bundle_exam(), Array("--target-dir", "generated"))
}

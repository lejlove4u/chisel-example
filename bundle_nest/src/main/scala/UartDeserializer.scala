import chisel3._

class UartDeserializer extends UartDeserializerBase {
  
  val fifo_wr_ena = Wire(Bool())
  /** FIXME: Currently, WireDefault is used for operation tests,
   *         but it must be used with RegInit.
  */
  val fifo_wr_dat = Wire(UInt(8.W)) 
  
  val uart_rxd = io.rxd
  val fifo_wr_full = io.fifo_wr_s2m.full
  val fifo_wr_dcnt = io.fifo_wr_s2m.dcnt

  fifo_wr_ena := false.B
  fifo_wr_dat := 0.U

  io.fifo_wr_m2s.ena := fifo_wr_ena
  io.fifo_wr_m2s.dat := fifo_wr_dat

}

// generate Verilog
object UartDeserializer extends App {
  emitVerilog(new UartDeserializer(), Array("--target-dir", "generated"))
}

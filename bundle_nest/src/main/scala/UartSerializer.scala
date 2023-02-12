import chisel3._

class UartSerializer extends UartSerializerBase {
  
  val uart_txd = Wire(UInt(1.W))
  val fifo_rd_ena = Wire(Bool())

  val fifo_rd_dat = io.fifo_rd_s2m.dat
  val fifo_rd_empty = io.fifo_rd_s2m.empty
  val fifo_rd_dcnt = io.fifo_rd_s2m.dcnt

  uart_txd := 1.U
  fifo_rd_ena := false.B

  io.txd := uart_txd
  io.fifo_rd_m2s.ena := fifo_rd_ena

}

// generate Verilog
object UartSerializer extends App {
  emitVerilog(new UartSerializer(), Array("--target-dir", "generated"))
}

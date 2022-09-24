/*
 * 2.4 vec test.
 */

import chisel3._
import scala.math.pow

/**
 * Define the IO class
 */
class ioPort(width: Int) extends Bundle {
  val data = Output(Vec(width, UInt(width.W)))
}

class vec_exam extends Module {
  val PORT_WIDTH: Int = 8
  val io = IO(new ioPort(PORT_WIDTH))

  val CNT_MAX = (pow(2, PORT_WIDTH).toInt) - 1
  val CNT_MAX_U = WireDefault(0.U(PORT_WIDTH.W))
  CNT_MAX_U := CNT_MAX.U(PORT_WIDTH.W)

  // Define counter signal and counting this signal
  val cntReg = RegInit(0.U(PORT_WIDTH.W))
  cntReg := Mux(cntReg === CNT_MAX_U, 0.U, cntReg+1.U)

  // Define a tempPort
  val tempPort = Wire(new ioPort(PORT_WIDTH))

  for(port_idx <- 0 to PORT_WIDTH-1) {
    tempPort.data(port_idx) := Mux(cntReg(port_idx) === "b1".U, cntReg, 0.U)
  }

  io := tempPort
}

// generate Verilog
object vec_exam extends App {
  emitVerilog(new vec_exam(), Array("--target-dir", "generated"))
}


import chisel3._

class GenHardware extends Module {
  val io = IO(new Bundle {
    val squareIn = Input(UInt(8.W))
    val squareOut = Output(UInt(8.W))
    
    val len = Output(UInt(8.W))
    val data = Output(Vec(12, UInt(8.W)))
  })

  // FIXME: [CS-8] If the variable n is larger than the size of squareROM, an error occurs.
  // Therefore, we need to modify the size of variable n and squareROM to be the same.
  val n = io.squareIn
  val squareROM = VecInit(0.U, 1.U, 4.U, 9.U, 16.U, 25.U)
  val square = squareROM(n)

  io.squareOut := square

  val msg = "Hello World!"
  val text = VecInit(msg.map(_.U))
  val len = msg.length.U
  
  io.len := len
  io.data := text
}

object GenHardware extends App {
  emitVerilog(new GenHardware(), Array("--target-dir", "generated"))
}
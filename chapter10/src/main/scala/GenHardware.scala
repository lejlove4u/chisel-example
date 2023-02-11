
import chisel3._
import scala.math._

class GenHardware(sqrInWidth: Int, sqrOutWidth: Int) extends Module {
  val io = IO(new Bundle {
    val squareIn = Input(UInt(sqrInWidth.W))
    val squareOut = Output(UInt(sqrOutWidth.W))
    
    val len = Output(UInt(8.W))
    val data = Output(Vec(12, UInt(8.W)))
  })

  // FIXME: [CS-8] If the variable n is larger than the size of squareROM, an error occurs.
  // Therefore, we need to modify the size of variable n and squareROM to be the same.
  // The Jira issue CS-8 has been fixed using the scala power function.
  // The array size of squareROM has been modified to fit the bit width of variable n.
  val n = io.squareIn
  val sizeOfRom = pow(2, sqrInWidth)
  val squareROM = Wire(Vec(sizeOfRom.toInt, UInt(sqrInWidth.W))); 

  for(cnt <- 0 until sizeOfRom.toInt) {
    var tempVar = cnt;
    squareROM(cnt) := tempVar.toInt.asUInt(sqrInWidth.W)
  }

  val square = squareROM(n)
  io.squareOut := square

  val msg = "Hello World!"
  val text = VecInit(msg.map(_.U))
  val len = msg.length.U
  
  io.len := len
  io.data := text
}

object GenHardware extends App {
  emitVerilog(new GenHardware(4, 8), Array("--target-dir", "generated"))
}
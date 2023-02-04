
import chisel3._

case class Config(txDepth: Int, rxDepth: Int, width: Int)

object Config extends App {
    val param = Config(4, 2, 16)
    println("The width is " + param.width)
}

import chisel3._

class FunctionalComp extends Module {
    val io = IO(new Bundle{
        val inA = Input(UInt(4.W))
        val inB = Input(UInt(4.W))

        val equ = Output(Bool())
        val gt = Output(Bool())
        val lt = Output(Bool())

        val equ1 = Output(Bool())
        val gt1 = Output(Bool())
        val lt1 = Output(Bool())
    })

    def compare(a: UInt, b: UInt) = {
        val equ = a === b
        val gt = a > b
        val lt = a < b
        (equ, gt, lt)
    }

    val inA = io.inA
    val inB = io.inB

    val comp = compare(inA, inB)
    val equResult = comp._1
    val gtResult = comp._2
    val ltResult = comp._3

    io.equ := equResult
    io.gt := gtResult
    io.lt := ltResult

    val (equ1, gt1, lt1) = compare(inA, inB)

    io.equ1 := equ1
    io.gt1 := gt1
    io.lt1 := lt1

}

// Generate verilog code
object FunctionalComp extends App {
    emitVerilog(new FunctionalComp(), Array("--target-dir", "generated"))
}
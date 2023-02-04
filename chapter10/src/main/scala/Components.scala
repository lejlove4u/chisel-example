
import chisel3._

class CompFn extends Module {
    val io = IO(new Bundle{
        val a = Input(UInt(4.W))
        val b = Input(UInt(4.W))
        val out = Output(UInt(4.W))

        val c = Input(UInt(4.W))
        val d = Input(UInt(4.W))
        val out1 = Output(UInt(4.W))

        val delayIn = Input(UInt(4.W))
        val delayOut = Output(UInt(4.W))
    })

    // The adder function
    def addr(x: UInt, y: UInt) = {
        x + y
    }

    val a = io.a
    val b = io.b
    val c = io.c
    val d = io.d

    val x = addr(a, b)
    val y = addr(c, d)

    io.out := x
    io.out1 := y
    
    // The delay function
    def delay(x:UInt) = RegNext(x)

    val delayIn = io.delayIn

    val delayOut = delay(delay(delayIn))

    io.delayOut := delayOut
}

// Generate verilog code
object CompFn extends App {
    emitVerilog(new CompFn(), Array("--target-dir", "generated"))
}
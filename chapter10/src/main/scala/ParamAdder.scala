
import chisel3._

class ParamAdder(n: Int) extends Module {
    val io = IO(new Bundle{
        val a = Input(UInt(n.W))
        val b = Input(UInt(n.W))
        val c = Output(UInt(n.W))
    })
    io.c := io.a + io.b
}

class UseAdder extends Module {
    val io = IO(new Bundle{
        val x8 = Input(UInt(8.W))
        val y8 = Input(UInt(8.W))
        val res8 = Output(UInt(8.W))
        val x16 = Input(UInt(16.W))
        val y16 = Input(UInt(16.W))
        val res16 = Output(UInt(16.W))
    })

    val adder8 = Module(new ParamAdder(8))
    val adder16 = Module(new ParamAdder(16))

    adder8.io.a := io.x8
    adder8.io.b := io.y8
    io.res8 := adder8.io.c

    adder16.io.a := io.x16
    adder16.io.b := io.y16
    io.res16 := adder16.io.c

}

// Print verilog code
object UseAdder extends App {
    println(getVerilogString(new UseAdder()))
}
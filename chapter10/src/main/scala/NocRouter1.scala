
import chisel3._

class Payload1 extends Bundle {
    val data = UInt(16.W)
    val flag = Bool()
}

class Port[T <: Data](dt: T) extends Bundle {
    val address = UInt(8.W)
    val data = dt.cloneType
}

class NocRouter1[T <: Data](dt: T, n: Int) extends Module {
    val io = IO(new Bundle {
        val inPort = Input(Vec(n, dt))
        val outPort = Output(Vec(n, dt))
    })

    // Router the payload according to the address
    for (cnt <- 0 until n) {
        io.outPort(cnt) := io.inPort(cnt)
    }
}

class UseParamRouter1() extends Module {
    val io = IO(new Bundle {
        val in = Input(new Payload1)
        val inAddr = Input(UInt(8.W))
        val outA = Output(new Payload1)
        val outB = Output(new Payload1)
    })

    val router = Module(new NocRouter1(new Port(new Payload1), 2))

    router.io.inPort(0).address := io.inAddr
    router.io.inPort(0).data := io.in
    router.io.inPort(1).address := io.inAddr+3.U
    router.io.inPort(1).data := io.in
    io.outA := router.io.outPort(0).data
    io.outB := router.io.outPort(1).data
}

object UseParamRouter1 extends App {
    emitVerilog(new UseParamRouter1(), Array("--target-dir", "generated"))
    //println(getVerilogString(new UseParamRouter1()))
}
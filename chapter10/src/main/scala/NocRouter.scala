
import chisel3._

class Payload extends Bundle {
    val data = UInt(16.W)
    val flag = Bool()
}

class NocRouter[T <: Data](dt: T, n: Int) extends Module {
    val io = IO(new Bundle {
        val inPort = Input(Vec(n, dt))
        val address = Input(Vec(n, UInt(8.W)))
        val outPort = Output(Vec(n, dt))
    })

    // Router the payload according to the address
    for (cnt <- 0 until n) {
        io.outPort(cnt) := io.inPort(cnt)
    }
}

class UseParamRouter() extends Module {
    val io = IO(new Bundle {
        val in = Input(new Payload)
        val inAddr = Input(UInt(8.W))
        val outA = Output(new Payload)
        val outB = Output(new Payload)
    })

    val router = Module(new NocRouter(new Payload, 2))

    router.io.inPort(0) := io.in
    router.io.address(0) := io.inAddr
    router.io.inPort(1) := io.in
    router.io.address(1) := io.inAddr+3.U
    io.outA := router.io.outPort(0)
    io.outB := router.io.outPort(1)
}

object UseParamRouter extends App {
    emitVerilog(new UseParamRouter(), Array("--target-dir", "generated"))
    //println(getVerilogString(new UseParamRouter()))
}
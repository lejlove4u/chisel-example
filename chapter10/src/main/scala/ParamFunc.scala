
import chisel3._

class ComplexIO extends Bundle {
    val d = UInt(10.W)
    val b = Bool()
}

class ParamFunc extends Module {
    val io = IO(new Bundle{
        val selA = Input(Bool())
        val resA = Output(UInt(4.W))

        val selB = Input(Bool())
        val resB = Output(new ComplexIO())
    })

    // Define multiplexer using my data type
    def myMux[T <: Data](sel: Bool, tPath: T, fPath: T) = {
        val ret = WireDefault(fPath)
        when (sel) {
            ret := tPath
        }
        ret
    }

    val selA = io.selA
    val resA = myMux(selA, 5.U, 10.U)
    io.resA := resA

    val selB = io.selB
    val tVal = Wire(new ComplexIO)
    val fVal = Wire(new ComplexIO)

    tVal.b := true.B
    tVal.d := 42.U
    fVal.b := false.B
    fVal.d := 13.U

    val resB = myMux(selB, tVal, fVal)
    io.resB := resB
}

// Generate verilog code
object ParamFunc extends App {
    emitVerilog(new ParamFunc(), Array("--target-dir", "generated"))
}
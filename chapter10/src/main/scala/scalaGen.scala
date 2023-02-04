import chisel3._

class ScalaGen extends Module {
    val io = IO(new Bundle {
        val din = Input(UInt(1.W))
        val dout = Output(UInt(8.W))
    })

    val regVec = Reg(Vec(8, UInt(1.W)))

    regVec(0) := io.din
    for(cnt <- 1 until 8) {
        regVec(cnt) := regVec(cnt-1)
    }

    io.dout := regVec.asUInt

}

// Generate verilog code
object ScalaGen extends App {
    emitVerilog(new ScalaGen(), Array("--target-dir", "generated"))
}

// always @(posedge clock) begin
//     regVec_0 <= io_din; // @[scalaGen.scala 11:15]
//     regVec_1 <= regVec_0; // @[scalaGen.scala 13:21]
//     regVec_2 <= regVec_1; // @[scalaGen.scala 13:21]
//     regVec_3 <= regVec_2; // @[scalaGen.scala 13:21]
//     regVec_4 <= regVec_3; // @[scalaGen.scala 13:21]
//     regVec_5 <= regVec_4; // @[scalaGen.scala 13:21]
//     regVec_6 <= regVec_5; // @[scalaGen.scala 13:21]
//     regVec_7 <= regVec_6; // @[scalaGen.scala 13:21]
// end
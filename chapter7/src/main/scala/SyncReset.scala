import chisel3._

abstract class Counter(n: Int) extends Module {
  val io = IO(new Bundle{
    val cnt = Output(UInt(8.W))
    val tick = Output(Bool())
  })
}

class WhenCounter(n: Int) extends Counter(n) {

  val N = (n-1).U

  //- start when_counter
  val cntReg = RegInit(0.U(8.W))

  cntReg := cntReg + 1.U
  when(cntReg === N) {
    cntReg := 0.U
  }
  //- end

  io.tick := cntReg === N
  io.cnt := cntReg
}

class SyncReset extends Module {
	val io = IO(new Bundle() {
		val value = Output(UInt())
	})
	val syncReset = RegNext(RegNext(reset))
	val cnt = Module(new WhenCounter(5))
	cnt.reset := syncReset

	io.value := cnt.io.cnt
}

// generate Verilog
object SyncReset extends App {
  emitVerilog(new SyncReset(), Array("--target-dir", "generated"))
}

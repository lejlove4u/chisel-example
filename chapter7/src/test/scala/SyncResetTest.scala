import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SyncResetTest extends AnyFlatSpec with ChiselScalatestTester {
  "SyncResetTest" should "pass" in {
    test(new SyncReset).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.step(16)
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()
      dut.clock.step(16)
    }
  }
}


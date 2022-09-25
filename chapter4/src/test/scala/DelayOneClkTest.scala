import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DelayOneClkTest extends AnyFlatSpec with ChiselScalatestTester {
  "DelayOneClkTest" should "pass" in {
    test(new DelayOneClk).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.io.dlyEna.poke(false.B)
      for(cnt <- 0 until 3) {
        dut.io.dlyIn.poke(cnt.U(2.W))
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }

      dut.io.dlyEna.poke(true.B)
      for(cnt <- 0 until 3) {
        dut.io.dlyIn.poke(cnt.U(2.W))
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}


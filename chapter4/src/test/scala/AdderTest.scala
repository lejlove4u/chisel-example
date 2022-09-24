import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class AdderTest extends AnyFlatSpec with ChiselScalatestTester {
  "AdderTest" should "pass" in {
    test(new Counter10).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 9) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}


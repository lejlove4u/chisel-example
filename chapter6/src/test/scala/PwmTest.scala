import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class PwmTest extends AnyFlatSpec with ChiselScalatestTester {
  "PwmTest" should "pass" in {
    test(new Pwm).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Go to the default setting
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()

      // Load data
      dut.io.din.poke("h3".U)
      dut.clock.step()

      // Wait for timer is done!
      for(cnt <- 0 until 30) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}

class Pwm1Test extends AnyFlatSpec with ChiselScalatestTester {
  "Pwm1Test" should "pass" in {
    test(new Pwm1(clkFreq = 1000000)).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.setTimeout(50001)
      dut.clock.step(50000)
    }
  }
}


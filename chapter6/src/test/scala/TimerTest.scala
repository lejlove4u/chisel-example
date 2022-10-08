import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TimerTest extends AnyFlatSpec with ChiselScalatestTester {
  "TimerTest" should "pass" in {
    test(new Timer).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Go to the default setting
      dut.reset.poke(true.B)
      dut.io.load.poke(false.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()

      // Load data
      dut.io.din.poke("h5".U)
      dut.io.load.poke(true.B)
      dut.clock.step()
      dut.io.load.poke(false.B)
      dut.clock.step()

      // Wait for timer is done!
      for(cnt <- 0 until 81) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}

class TimerOtherTest extends AnyFlatSpec with ChiselScalatestTester {
  "TimerOtherTest" should "pass" in {
    test(new TimerOther).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Go to the default setting
      dut.reset.poke(true.B)
      dut.io.load.poke(false.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()

      // Load data
      dut.io.din.poke("h5".U)
      dut.io.load.poke(true.B)
      dut.clock.step()
      dut.io.load.poke(false.B)
      dut.clock.step()

      // Wait for timer is done!
      for(cnt <- 0 until 81) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}

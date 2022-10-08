import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TickCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  "TickCounterTest" should "pass" in {
    test(new TickCounter).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 12) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}

class SlowerCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  "SlowerCounterTest" should "pass" in {
    test(new SlowerCounter).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 24) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}

class NerdCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  "NerdCounterTest" should "pass" in {
    test(new NerdCounter).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 12) {
        dut.clock.step()
        println("Result is: " + dut.io.peek().toString)
      }
    }
  }
}


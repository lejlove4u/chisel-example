import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MainTest extends AnyFlatSpec with ChiselScalatestTester {
  "MainTest" should "pass" in {
    test(new Main).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.io.a.poke(0.U)
      dut.io.b.poke(1.U)
      dut.clock.step()
      println("Result is: "+dut.io.peek().toString)

      dut.io.a.poke(3.U)
      dut.io.b.poke(2.U)
      dut.clock.step()
      println("Result is: "+dut.io.peek().toString)
    }
  }
}

class MainTestExpect extends AnyFlatSpec with ChiselScalatestTester {
  "MainTestExpect" should "pass" in {
    test(new Main).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.io.a.poke(0.U)
      dut.io.b.poke(1.U)
      dut.clock.step()
      dut.io.out.expect(0.U) // 0 AND 1 => 0
      println("Result is: "+dut.io.peek().toString)

      dut.io.a.poke(3.U)
      dut.io.b.poke(2.U)
      dut.clock.step()
      dut.io.out.expect(2.U) // 3 AND 2 => 2
      println("Result is: "+dut.io.peek().toString)
    }
  }
}
class MainTestWaveform extends AnyFlatSpec with ChiselScalatestTester {
  "MainTestWaveform" should "pass" in {
    test(new Main).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(op1 <- 0 until 4) {
        for(op2 <- 0 until 4) {
          dut.io.a.poke(op1.U)
          dut.io.b.poke(op2.U)
          dut.clock.step()
          //dut.io.out.expect(op1.U&op2.U)
          println("Result is: "+dut.io.peek().toString)
        } // end of for (op2)
      } // end of for (op1)
    } // end of test
  } // end of "Waveform..."
} // end of class


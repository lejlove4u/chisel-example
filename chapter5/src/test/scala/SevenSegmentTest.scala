import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SevenSegmentTest extends AnyFlatSpec with ChiselScalatestTester {
  "SevenSegmentTest" should "pass" in {
    test(new SevenSegment).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(codeCnt <- 0 until 16) {
          dut.io.code_in.poke(codeCnt.U)
          dut.clock.step()
          println("Result is: "+dut.io.peek().toString)
      } // end of for (codeCnt)
    } // end of test
  } // end of "Waveform..."
} // end of class


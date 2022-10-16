import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DebounceTest extends AnyFlatSpec with ChiselScalatestTester {
  "DebounceTest" should "pass" in {
    test(new Debounce(2)).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 64) {
        val btn_b = ((cnt&8)>>3).B
         dut.io.btn.poke(btn_b)
         dut.clock.step()
         println("Result is: "+dut.io.peek().toString)
      } // end of for (cnt)
    } // end of test
  }

  "DebounceFuncTest" should "pass" in {
    test(new DebounceFunc(2)).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt <- 0 until 64) {
        val btn_b = ((cnt&8)>>3).B
        dut.io.btn.poke(btn_b)
        dut.clock.step()
        println("Result is: "+dut.io.peek().toString)
      } // end of for (cnt)
    } // end of test
  }
}


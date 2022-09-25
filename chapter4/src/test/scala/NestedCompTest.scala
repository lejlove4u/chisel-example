import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class NestedCompTest extends AnyFlatSpec with ChiselScalatestTester {
  "NestedCompTest" should "pass" in {
    test(new NestedComp).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      for(cnt_in_a <- 0 until 3) {
        for(cnt_in_b <- 0 until 3) {
          for (cnt_in_c <- 0 until 3) {
            dut.io.in_a.poke(cnt_in_a.U(2.W))
            dut.io.in_b.poke(cnt_in_b.U(2.W))
            dut.io.in_c.poke(cnt_in_c.U(2.W))
            dut.clock.step()
            println("Result is: " + dut.io.peek().toString)
          } // end of for (cnt_in_c)
        } // end of for (cnt_in_b)
      } // end of for (cnt_in_a)
    } // end of test
  } // Nested...
} // end of class


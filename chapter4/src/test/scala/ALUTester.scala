import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ALUTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALUTest" should "pass" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.io.a.poke(1.U)
      dut.io.b.poke(3.U)
      dut.io.fn.poke(0.U)
      dut.clock.step()
      dut.io.y.expect(4.U) // fn is 0 to means adder
      println("Result is: "+dut.io.peek().toString)

      dut.io.a.poke(12.U)
      dut.io.b.poke(7.U)
      dut.io.fn.poke(1.U)
      dut.clock.step()
      dut.io.y.expect(5.U) // fn is 1 to means subtract
      println("Result is: "+dut.io.peek().toString)

      dut.io.a.poke("b1010_0101_1000_1100".U)
      dut.io.b.poke("b1010_0101_1111_0100".U)
      dut.io.fn.poke(2.U)
      dut.clock.step()
      dut.io.y.expect("b1010_0101_1111_1100".U) // fn is 2 to means or operator
      println("Result is: "+dut.io.peek().toString)

      dut.io.a.poke("b1010_0101_1000_1100".U)
      dut.io.b.poke("b1010_0101_1111_0100".U)
      dut.io.fn.poke(3.U)
      dut.clock.step()
      dut.io.y.expect("b1010_0101_1000_0100".U) // fn is 3 to means and operator
      println("Result is: "+dut.io.peek().toString)
    } // end of test
  } // Nested...
} // end of class


import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class RisingMealyFsmTest extends AnyFlatSpec with ChiselScalatestTester {
  "RisingMealyFsmTest" should "pass" in {
    test(new RisingMealyFsm).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.step(3)

      dut.io.din.poke(true.B)
      dut.clock.step()
      dut.io.din.poke(false.B)
      dut.clock.step(6)

      dut.io.din.poke(true.B)
      dut.clock.step()
      dut.io.din.poke(false.B)
      dut.clock.step(6)
    }
  }
}

class RisingMooreFsmTest extends AnyFlatSpec with ChiselScalatestTester {
  "RisingMooreFsmTest" should "pass" in {
    test(new RisingMooreFsm).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.step(3)

      dut.io.din.poke(true.B)
      dut.clock.step()
      dut.io.din.poke(false.B)
      dut.clock.step(6)

      dut.io.din.poke(true.B)
      dut.clock.step()
      dut.io.din.poke(false.B)
      dut.clock.step(6)
    }
  }
}


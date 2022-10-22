import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SimpleFsmTest extends AnyFlatSpec with ChiselScalatestTester {
  "SimpleFsmTest" should "pass" in {
    test(new SimpleFsm).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.step(3)

      // The state machine will going to the orange state
      dut.io.badEvent.poke(true.B)
      dut.clock.step()
      dut.io.badEvent.poke(false.B)
      dut.clock.step(3)

      // The state machine will going to the red state
      dut.io.badEvent.poke(true.B)
      dut.clock.step()
      dut.io.badEvent.poke(false.B)
      dut.clock.step(3)

      // The state machine will going to the green state
      dut.io.clear.poke(true.B)
      dut.clock.step()
      dut.io.clear.poke(false.B)
      dut.clock.step(3)
    }
  }
}

class SimpleFsmSyncTest extends AnyFlatSpec with ChiselScalatestTester {
  "SimpleFsmSyncTest" should "pass" in {
    test(new SimpleFsmSync).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      dut.clock.step(3)

      // The state machine will going to the orange state
      dut.io.badEvent.poke(true.B)
      dut.clock.step()
      dut.io.badEvent.poke(false.B)
      dut.clock.step(6)

      // The state machine will going to the red state
      dut.io.badEvent.poke(true.B)
      dut.clock.step()
      dut.io.badEvent.poke(false.B)
      dut.clock.step(6)

      // The state machine will going to the green state
      dut.io.clear.poke(true.B)
      dut.clock.step()
      dut.io.clear.poke(false.B)
      dut.clock.step(6)
    }
  }
}


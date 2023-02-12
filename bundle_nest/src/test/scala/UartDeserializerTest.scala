import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class UartDeserializerTest extends AnyFlatSpec with ChiselScalatestTester {
  "MainTest" should "pass" in {
    test(new UartDeserializer).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>

      // Setup serial configuration
      dut.io.s_conf.baud.poke("hC".U)
      dut.io.s_conf.data.poke("h0".U)
      dut.io.s_conf.paty.poke("h0".U)
      dut.io.s_conf.stop.poke(false.B)

      // Setup FIFO default configuration
      dut.io.fifo_wr_s2m.full.poke(false.B)
      dut.io.fifo_wr_s2m.dcnt.poke(0.U)
      println("Result is: "+dut.io.peek().toString)
      dut.clock.step()
      println("Result is: "+dut.io.peek().toString)
    }
  }
}


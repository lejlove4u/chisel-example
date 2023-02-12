import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class UartSerializerTest extends AnyFlatSpec with ChiselScalatestTester {
  "MainTest" should "pass" in {
    test(new UartSerializer).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>

      // Setup serial configuration
      dut.io.s_conf.baud.poke("hC".U)
      dut.io.s_conf.data.poke("h0".U)
      dut.io.s_conf.paty.poke("h0".U)
      dut.io.s_conf.stop.poke(false.B)

      // Setup FIFO default configuration
      dut.io.fifo_rd_s2m.dat.poke(0.U)
      dut.io.fifo_rd_s2m.dcnt.poke(0.U)
      dut.io.fifo_rd_s2m.empty.poke(true.B)
      println("Result is: "+dut.io.peek().toString)
      dut.clock.step()
      println("Result is: "+dut.io.peek().toString)
    }
  }
}


import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class MemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  "InitMemoryTest" should "pass" in {
    test(new BasicMemory).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Some defaults
      dut.io.rdAddr.poke(0.U)
      dut.io.wrEna.poke(false.B)
      dut.io.wrAddr.poke(0.U)
      dut.io.wrData.poke(0.U)
      dut.clock.step()

      // Check all the initialization values are as expected
      val lines = scala.io.Source.fromFile("./src/main/resources/init.hex")
        .getLines.map{Integer.parseInt(_, 16)}.toSeq
      for(cnt <- 0 until 1024) {
        dut.io.rdAddr.poke(cnt.U)
        dut.clock.step()
        dut.io.rdData.expect(lines(cnt).U)
      }
    }
  }

  "BasicMemoryTest" should "pass" in {
    test(new BasicMemory).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Some defaults
      dut.io.rdAddr.poke(0.U)
      dut.io.wrEna.poke(false.B)
      dut.io.wrAddr.poke(0.U)
      dut.io.wrData.poke(0.U)
      dut.clock.step()

      // Write some data
      dut.io.wrEna.poke(true.B)
      for(wrCnt <- 0 until 1024) {
        dut.io.wrAddr.poke(wrCnt.U)
        dut.io.wrData.poke(((wrCnt+1)%255).asUInt(8.W))
        dut.clock.step()
      }
      dut.io.wrEna.poke(false.B)
      dut.clock.step()

      // Read and print second value
      for(rdCnt <- 0 until 1024) {
        dut.io.rdAddr.poke(rdCnt.U)
        dut.clock.step()
        dut.io.rdData.expect(((rdCnt+1)%255).asUInt(8.W))
        //println(s"Second memory data: ${dut.io.rdData.peek.litValue}")
      }
    }
  }

  "WriteFirstMemoryTest" should "pass" in {
    test(new WriteFirstMemory).withAnnotations(Seq(WriteVcdAnnotation)) {dut =>
      // Fill the memory
      dut.io.wrEna.poke(true.B)
      for (wrCnt <- 0 to 10) {
        dut.io.wrAddr.poke(wrCnt.U)
        dut.io.wrData.poke((wrCnt*10).U)
        dut.clock.step()
      }
      dut.io.wrEna.poke(false.B)

      dut.io.rdAddr.poke(10.U)
      dut.clock.step()
      dut.io.rdData.expect(100.U)
      dut.io.rdAddr.poke(5.U)
      dut.io.rdData.expect(100.U)
      dut.clock.step()
      dut.io.rdData.expect(50.U)

      // Same address read and write
      dut.io.wrAddr.poke(20.U)
      dut.io.wrData.poke(123.U)
      dut.io.wrEna.poke(true.B)
      dut.io.rdAddr.poke(20.U)
      dut.clock.step()
      dut.io.rdData.expect(123.U)
      println(s"Memory data: ${dut.io.rdData.peek.litValue}")

    }
  }
}

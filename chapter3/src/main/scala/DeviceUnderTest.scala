import chisel3._

class DeviceUnderTest extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(2.W))
    val b = Input(UInt(2.W))
    val out = Output(UInt(2.W))
  })
  io.out := io.a & io.b
}

class DeviceUnderTestPrintf extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(2.W))
    val b = Input(UInt(2.W))
    val out = Output(UInt(2.W))
  })
  io.out := io.a & io.b
  printf("dut: %d %d %d\n", io.a, io.b, io.out)
}

object DeviceUnderTestApp extends App {
  // Generate verilog code
  emitVerilog(new DeviceUnderTest(), Array("--target-dir", "generated"))

  // The verilog code is printed to log windows
  val logStr = getVerilogString(new DeviceUnderTest())
  println(logStr)
}

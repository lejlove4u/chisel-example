import chisel3._
import chisel3.util._
import chisel3.experimental.ExtModule

/*
 * For certain synthesis tools with built-in component libraries,
 * such as Xilinx' UNISIM library included in Vivado, it makes
 * sense to implement source-less blackboxes to instantiante such
 * primitives in one's generated Verilog. Beware that source-less
 * blackboxes can only be simulated in the vendors' simulators.
 * 
 * As examples, consider the Xilinx BUFGCE and the Intel/Altera
 * ALT_INBUF primitives below.
 * 
 * Beware that Verilog is case sensitive!
 * 
 * `BlackBox` modules are generated in the emitted Verilog, whereas
 * `ExtModule` modules are not.
 */

// The Xilinx FPGA  buffer primitive
class BUFGCE extends BlackBox(Map("SIM_DEVICE" -> "7SERIES")) {
	val io = IO(new Bundle {
		val CE = Input(Bool())
		val I = Input(Clock())
		val O = Output(Clock())
	})
}

// The Intel FPGA buffer primitive
class alt_inbuf extends ExtModule(Map("io_standard" -> "1.0 V",
                                      "location" -> "IOBANK_1",
                                      "enable_bus_hold" -> "on",
                                      "weak_pull_up_resistor" -> "off",
                                      "termination" -> "parallel 50 ohms")
                                      ) {
  val io = IO(new Bundle {
    val i = Input(Bool())
    val o = Output(Bool())
  })
}

class BboxExamUse extends Module {
 	val io = IO(new Bundle {
		val xClkEn = Input(Bool())
		val xClkIn = Input(Clock())
		val xClkOut = Output(Clock())
		
		val iIn = Input(Bool())
		val iOut = Output(Bool())
	})

	val xBufInst = Module(new BUFGCE())
	val iBufInst = Module(new alt_inbuf())

	xBufInst.io.CE := io.xClkEn
	xBufInst.io.I := io.xClkIn
	io.xClkOut := xBufInst.io.O

	iBufInst.io.i := io.iIn
	io.iOut := iBufInst.io.o
}

// ----------------------------------------------------------------------------

/*
 * To force a specific implementation of a component, consider
 * manually writing the corresponding Verilog and including it
 * in a blackbox. This can aid synthesis tools in recognizing
 * specific code constructs and map them to device primivites,
 * e.g., LUTRAM on Xilinx FPGAs.
 * 
 * As examples, below are three versions of the same blackbox
 * adder component.
 * 
 * All three versions can be simulated with either Verilog-based
 * simulator backend; i.e., either Verilator or VCS, not Treadle.
 */

class BlackBoxAdderIO extends Bundle {
  val a = Input(UInt(32.W))
  val b = Input(UInt(32.W))
  val cin = Input(Bool())
  val c = Output(UInt(32.W))
  val cout = Output(Bool())
}

// Generates the file in the test_run_dir subfolder if used for testing
// or in the --target-dir if emitted
class InlineBlackBoxAdder extends HasBlackBoxInline {
  val io = IO(new BlackBoxAdderIO)
  setInline("InlineBlackBoxAdder.v",
  s"""
  |module InlineBlackBoxAdder(a, b, cin, c, cout);
  |input  [31:0] a, b;
  |input  cin;
  |output [31:0] c;
  |output cout;
  |wire   [32:0] sum;
  |
  |assign sum  = a + b + cin;
  |assign c    = sum[31:0];
  |assign cout = sum[32];
  |
  |endmodule
  """.stripMargin)
}

class InlineBboxExamUse extends Module {
 	val io = IO(new BlackBoxAdderIO)
	val inlineInst = Module(new InlineBlackBoxAdder())

	inlineInst.io.a := io.a
	inlineInst.io.b := io.b
	inlineInst.io.cin := io.cin
	io.c := inlineInst.io.c
	io.cout := inlineInst.io.cout
}

// ----------------------------------------------------------------------------

// Accepts relative paths w.r.t. the project directory
class PathBlackBoxAdder extends HasBlackBoxPath {
  val io = IO(new BlackBoxAdderIO)
  addPath("./src/main/resources/PathBlackBoxAdder.v")
}

// Expects source file to be present in {project-dir}/src/main/resources
class ResourceBlackBoxAdder extends HasBlackBoxResource {
  val io = IO(new BlackBoxAdderIO)
  addResource("/ResourceBlackBoxAdder.v")
}

class PathBboxExamUse extends Module {
 	val io = IO(new BlackBoxAdderIO)
	val inst = Module(new PathBlackBoxAdder())

	inst.io.a := io.a
	inst.io.b := io.b
	inst.io.cin := io.cin
	io.c := inst.io.c
	io.cout := inst.io.cout
}

class ResBboxExamUse extends Module {
 	val io = IO(new BlackBoxAdderIO)
	val inst = Module(new ResourceBlackBoxAdder())

	inst.io.a := io.a
	inst.io.b := io.b
	inst.io.cin := io.cin
	io.c := inst.io.c
	io.cout := inst.io.cout
}

// generate Verilog
object BboxExam extends App {
  emitVerilog(new BboxExamUse(), Array("--target-dir", "generated"))
  emitVerilog(new InlineBboxExamUse(), Array("--target-dir", "generated"))
  emitVerilog(new PathBboxExamUse(), Array("--target-dir", "generated"))
  emitVerilog(new ResBboxExamUse(), Array("--target-dir", "generated"))
}

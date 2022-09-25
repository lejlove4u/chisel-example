import chisel3._
import chisel3.util._
import chisel3.experimental._

class BUFGCE extends BlackBox(Map("SIM_DEVICE"->"7SERIES")) {
	val io = IO(new Bundle {
		val I = Input(Clock())
		val CE = Input(Bool())
		val O = Output(Clock())
	})
}

class BlockBoxExam extends Module {
	val io = IO(new Bundle {
		val inClk = Input(Clock())
		val outClk = Output(Clock())
	})
	val gblBuf = Module(new BUFGCE())

	gblBuf.io.CE := true.B
	gblBuf.io.I := io.inClk
	io.outClk := gblBuf.io.O
}

// generate Verilog
object BlockBoxExam extends App {
	emitVerilog(new BlockBoxExam(), Array("--target-dir", "generated"))
}

/**
--------------------------------------------------------------------------------
 */

class alt_inbuf extends ExtModule(Map("io_standard" -> "1.0 V",
	"location" -> "IOBANK_1", "enable_bus_hold" -> "on",
	"weak_pull_up_resistor" -> "off",
	"termination" -> "parallel 50 ohms") ) {
	val io = IO(new Bundle {
		val i = Input(Bool())
		val o = Output(Bool())
	})
}

class ExtModuleExam extends Module {
	val io = IO(new Bundle {
		val sigIn = Input(Bool())
		val sigOut = Output(Bool())
	})
	val altInBuf = Module(new alt_inbuf())

	altInBuf.io.i := io.sigIn
	io.sigOut := altInBuf.io.o
}

// generate Verilog
object ExtModuleExam extends App {
	emitVerilog(new ExtModuleExam(), Array("--target-dir", "generated"))
}

/**
--------------------------------------------------------------------------------
 */

class BlackBoxAdderIO extends Bundle {
	val a = Input(UInt(32.W))
	val b = Input(UInt(32.W))
	val cin = Input(Bool())
	val c = Output(UInt(32.W))
	val cout = Output(Bool())
}

class InlineBlackBoxAdder extends HasBlackBoxInline {
	val io = IO(new BlackBoxAdderIO)
	setInline("InlineBlackBoxAdder.v",
		s"""
			 |module InlineBlackBoxAdder(a, b, cin, c, cout);
			 |input [31:0] a, b;
			 |input cin;
			 |output [31:0] c;
			 |output cout;
			 |wire [32:0] sum;
			 |
			 |assign sum = a + b + cin;
			 |assign c = sum[31:0];
			 |assign cout = sum[32];
			 |
			 |endmodule
""".stripMargin)
}

class InlineBlackBoxExam extends Module {
	val io = IO(new BlackBoxAdderIO)
	val inline = Module(new InlineBlackBoxAdder)
	inline.io <> io
}

// generate Verilog
object InlineBlackBoxAdder extends App {
	emitVerilog(new InlineBlackBoxExam(), Array("--target-dir", "generated"))
}

/**
--------------------------------------------------------------------------------
 */

class ResourceBlackBoxAdder extends HasBlackBoxResource {
	val io = IO(new BlackBoxAdderIO)
	addResource("/ResourceBlackBoxAdder.v")
}

class ResourceBlackBoxExam extends Module {
	val io = IO(new BlackBoxAdderIO)
	val resource = Module(new ResourceBlackBoxAdder)
	io <> resource.io
}

// generate Verilog
object ResourceBlackBoxExam extends App {
	emitVerilog(new ResourceBlackBoxExam(), Array("--target-dir", "generated"))
}

/**
--------------------------------------------------------------------------------
 */

class PathBlackBoxAdder extends HasBlackBoxPath {
	val io = IO(new BlackBoxAdderIO)
	addPath("./src/main/resources/PathBlackBoxAdder.v")
}

class PathBlackBoxExam extends Module {
	val io = IO(new BlackBoxAdderIO)
	val path = Module(new PathBlackBoxAdder)
	io <> path.io
}

// generate Verilog
object PathBlackBoxExam extends App {
	emitVerilog(new PathBlackBoxExam(), Array("--target-dir", "generated"))
}


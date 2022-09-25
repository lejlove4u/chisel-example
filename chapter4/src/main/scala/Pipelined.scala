import chisel3._
import chisel3.util._

class Fetch extends Module {
	val io = IO(new Bundle {
		val instr = Output(UInt(32.W))
		val pc = Output(UInt(32.W))
	})
	io.instr := 128.U(32.W)
	io.pc := "h10000004".U
}

class Decode extends Module {
	val io = IO(new Bundle {
		val instr = Input(UInt(32.W))
		val pc = Input(UInt(32.W))
		val aluOp = Output(UInt(5.W))
		val regA = Output(UInt(32.W))
		val regB = Output(UInt(32.W))
	})
	val tempOp = WireDefault(0.U(5.W))
	tempOp := io.instr(4, 0)
	io.aluOp := tempOp
	io.regA := io.instr & "h01011010".U
	io.regB := io.pc & "h01010000".U
}

class Execute extends Module {
	val io = IO(new Bundle {
		val aluOp = Input(UInt(5.W))
		val regA = Input(UInt(32.W))
		val regB = Input(UInt(32.W))
		val result = Output(UInt(32.W))
	})

	io.result := 0.U(32.W)

	switch(io.aluOp(1,0)) {
		is(0.U) {io.result := io.regA + io.regB}
		is(1.U) {io.result := io.regA - io.regB}
		is(2.U) {io.result := io.regA | io.regB}
		is(3.U) {io.result := io.regA & io.regB}
	}
}

class Pipelined extends Module {
	val io = IO(new Bundle {
		val result = Output(UInt(32.W))
	})
	val fetch = Module(new Fetch())
	val decode = Module(new Decode())
	val execute = Module(new Execute())

//	fetch.io <> decode.io
// 	decode.io <> execute.io
// 	io <> execute.io

	fetch.io.instr <> decode.io.instr
	fetch.io.pc <> decode.io.pc

	decode.io.aluOp <> execute.io.aluOp
	decode.io.regA <> execute.io.regA
	decode.io.regB <> execute.io.regB

	io.result <> execute.io.result
}

// generate Verilog
object Pipelined extends App {
  emitVerilog(new Pipelined(), Array("--target-dir", "generated"))
}

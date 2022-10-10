import chisel3._
import chisel3.util.Cat

class Shift extends Module {
 	val io = IO(new Bundle {
		val din = Input(Bool())
		val dout = Output(Bool())
	})
	val din = io.din.asUInt
	val shiftReg = Reg(UInt(4.W))
	//shiftReg := Cat(shiftReg(2, 0), din)
	shiftReg := shiftReg(2, 0) ## din
	val dout = shiftReg(3).asBool
	io.dout := dout
}

// generate Verilog
object Shift extends App {
  emitVerilog(new Shift(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 **/

class Shift1 extends Module {
	val io = IO(new Bundle {
		val serIn = Input(Bool())
		val q = Output(UInt(4.W))
	})
	val serIn = io.serIn.asUInt
	val outReg = RegInit(0.U(4.W))
	outReg := Cat(serIn, outReg(3, 1))
	val q = outReg
	io.q := q
}

// generate Verilog
object Shift1 extends App {
	emitVerilog(new Shift1(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 **/

class LoadShift extends Module {
	val io = IO(new Bundle {
		val load = Input(Bool())
		val d = Input(UInt(4.W))
		val serOut = Output(Bool())
	})
	val load = io.load
	val d = io.d
	val loadReg = RegInit(0.U(4.W))

	when(load) {
		loadReg := d
	} .otherwise {
		loadReg := Cat(0.U, loadReg(3, 1))
	}

	val serOut = loadReg(0)
	io.serOut := serOut
}

// generate Verilog
object LoadShift extends App {
	emitVerilog(new LoadShift(), Array("--target-dir", "generated"))
}


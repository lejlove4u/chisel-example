import chisel3._
import chisel3.util._

class dff_0 extends Module {
 	val io = IO(new Bundle {
		val d = Input(UInt(4.W))
		val q = Output(UInt(4.W))
	})
		val d = Wire(UInt())
		d := io.d
		val q = RegNext(d)
		io.q := q
}

// generate Verilog
object dff_0 extends App {
  emitVerilog(new dff_0(), Array("--target-dir", "generated"))
}

/**
********************************************************************************
 */

class delay extends Module {
	val io = IO(new Bundle {
		val delayIn = Input(UInt(4.W))
		val delayOut = Output(UInt(4.W))
	})
	val delayIn = io.delayIn
	val delayReg = Reg(UInt(4.W))
	delayReg := delayIn;
	io.delayOut := delayReg
}

// generate Verilog
object delay extends App {
	emitVerilog(new delay(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class reg_init extends Module {
	val io = IO(new Bundle {
		val inVal = Input(UInt(4.W))
		val outVal = Output(UInt(4.W))
	})
	val inVal = io.inVal
	val valReg = RegInit(0.U(4.W))
	valReg := inVal
	io.outVal := valReg
}

// generate Verilog
object reg_init extends App {
	emitVerilog(new reg_init(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class enable_reg extends Module {
	val io = IO(new Bundle {
		val enable = Input(Bool())
		val inVal = Input(UInt(4.W))
		val outVal = Output(UInt(4.W))
	})
	val enable = io.enable
	val inVal = io.inVal
	val enableReg = Reg(UInt(4.W))

	/**
	 * If both Reset and Enable are not activated,
	 * it is not known which value will be output.
	 */

	when(enable) {
		enableReg := inVal
	}

	io.outVal := enableReg
}

// generate Verilog
object enable_reg extends App {
	emitVerilog(new enable_reg(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class enable_reg_1 extends Module {
	val io = IO(new Bundle {
		val enable = Input(Bool())
		val inVal = Input(UInt(4.W))
		val outVal = Output(UInt(4.W))
	})
	val enable = io.enable
	val inVal = io.inVal
	val enableReg = RegInit(0.U(4.W))

	when(enable) {
		enableReg := inVal
	}

	io.outVal := enableReg
}

// generate Verilog
object enable_reg_1 extends App {
	emitVerilog(new enable_reg_1(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class enable_reg_2 extends Module {
	val io = IO(new Bundle {
		val enable = Input(Bool())
		val inVal = Input(UInt(4.W))
		val outVal = Output(UInt(4.W))
	})
	val enable = io.enable
	val inVal = io.inVal
	val enableReg = RegEnable(inVal, enable)

	/**
	 * If both Reset and Enable are not activated,
	 * it is not known which value will be output.
	 */

	io.outVal := enableReg
}

// generate Verilog
object enable_reg_2 extends App {
	emitVerilog(new enable_reg_2(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class enable_reg_3 extends Module {
	val io = IO(new Bundle {
		val enable = Input(Bool())
		val inVal = Input(UInt(4.W))
		val outVal = Output(UInt(4.W))
	})
	val enable = io.enable
	val inVal = io.inVal
	val enableReg = RegEnable(inVal, 0.U(4.W), enable)

	io.outVal := enableReg
}

// generate Verilog
object enable_reg_3 extends App {
	emitVerilog(new enable_reg_3(), Array("--target-dir", "generated"))
}

/**
 ********************************************************************************
 */

class rising_edge extends Module {
	val io = IO(new Bundle {
		val din = Input(UInt(1.W))
		val dout = Output(UInt(1.W))
	})
	val din = io.din
	val risingEdge = din & !RegNext(din)

	io.dout := risingEdge
}

// generate Verilog
object rising_edge extends App {
	emitVerilog(new rising_edge(), Array("--target-dir", "generated"))
}


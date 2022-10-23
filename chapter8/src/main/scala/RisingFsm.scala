import chisel3._
import chisel3.util._

class RisingMealyFsm extends Module {
	val io = IO(new Bundle {
		val din = Input(Bool())
		val risingEdge = Output(Bool())
	})

	// The two states
	val zero::one::Nil = Enum(2)

	// The state register
	val stateReg = RegInit(zero)

	// The default value for output
	io.risingEdge := false.B

	// Next state and output logic
	switch(stateReg) {
		is(zero) {
			when(io.din) {
				stateReg := one
				io.risingEdge := true.B
			}
		} // end of zero state

		is(one) {
			when(!io.din) {
				stateReg := zero
			}
		} // end of orange state
	} // end of switch

}

// generate Verilog
object RisingMealyFsm extends App {
  emitVerilog(new RisingMealyFsm(), Array("--target-dir", "generated"))
}

class RisingMooreFsm extends Module {
	val io = IO(new Bundle {
		val din = Input(Bool())
		val risingEdge = Output(Bool())
	})

	// The three states
	val zero::puls::one::Nil = Enum(3)

	// The state register
	val stateReg = RegInit(zero)

	// Next state logic
	switch(stateReg) {
		is(zero) {
			when(io.din) {
				stateReg := puls
			}
		} // end of zero state

		is(puls) {
			when(io.din) {
				stateReg := one
			} .otherwise {
				stateReg := zero
			}
		} // end of puls state

		is(one) {
			when(!io.din) {
				stateReg := zero
			}
		} // end of orange state
	} // end of switch

	// Output logic
	io.risingEdge := stateReg === puls

}

// generate Verilog
object RisingMooreFsm extends App {
	emitVerilog(new RisingMooreFsm(), Array("--target-dir", "generated"))
}


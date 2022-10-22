import chisel3._
import chisel3.util._

class SimpleFsm extends Module {
	val io = IO(new Bundle {
		val badEvent = Input(Bool())
		val clear = Input(Bool())
		val ringBell = Output(Bool())
	})

	// The three states
	val green::orange::red::Nil = Enum(3)

	// The state register
	val stateReg = RegInit(green)

	// Next state logic
	switch(stateReg) {
		is(green) {
			when(io.badEvent) {
				stateReg := orange
			}
		} // end of green state

		is(orange) {
			when(io.badEvent) {
				stateReg := red
			} .elsewhen(io.clear) {
				stateReg := green
			}
		} // end of orange state

		is(red) {
			when(io.clear) {
				stateReg := green
			}
		} // end of red state
	}

	// Output logic
	io.ringBell := stateReg === red
}

// generate Verilog
object SimpleFsm extends App {
  emitVerilog(new SimpleFsm(), Array("--target-dir", "generated"))
}

class SimpleFsmSync extends Module {
	val io = IO(new Bundle {
		val badEvent = Input(Bool())
		val clear = Input(Bool())
		val ringBell = Output(Bool())
	})

	// The sync signal
	val syncBadEvent = RegNext(RegNext(io.badEvent))
	val syncClear1 = RegNext(io.clear)
	val syncClear = RegNext(syncClear1)

	val clearDelay = WireDefault(false.B)
	clearDelay := io.clear | (syncClear | syncClear1)

	// The three states
	val green::orange::red::Nil = Enum(3)

	// The state register
	val stateReg = RegInit(green)

	// Next state logic
	switch(stateReg) {
		is(green) {
			when(syncBadEvent) {
				stateReg := orange
			}
		} // end of green state

		is(orange) {
			when(syncBadEvent) {
				stateReg := red
			} .elsewhen(clearDelay) {
				stateReg := green
			}
		} // end of orange state

		is(red) {
			when(clearDelay) {
				stateReg := green
			}
		} // end of red state
	}

	// Output logic
	io.ringBell := Mux(stateReg === red, true.B, false.B)
}

// generate Verilog
object SimpleFsmSync extends App {
	emitVerilog(new SimpleFsmSync(), Array("--target-dir", "generated"))
}

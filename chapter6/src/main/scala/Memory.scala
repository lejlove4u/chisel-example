import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
import firrtl.annotations.MemoryLoadFileType

class BasicMemory extends Module {
 	val io = IO(new Bundle {
		val rdAddr = Input(UInt(10.W))
		val rdData = Output(UInt(8.W))

		val wrEna = Input(Bool())
		val wrAddr = Input(UInt(10.W))
		val wrData = Input(UInt(8.W))
	})

	val mem = SyncReadMem(1024, UInt(8.W))

	// Initialize memory
	loadMemoryFromFile(mem, "./src/main/resources/init.hex",
		MemoryLoadFileType.Hex)

	// Read a data from memory
	io.rdData := mem.read(io.rdAddr)

	// Write data to memory
	when(io.wrEna) {
		mem.write(io.wrAddr, io.wrData)
	}
}

// generate Verilog
object BasicMemory extends App {
  emitVerilog(new BasicMemory(), Array("--target-dir", "generated"))
}

/**
 *******************************************************************************
 **/

class WriteFirstMemory extends Module {
	val io = IO(new Bundle {
		val rdAddr = Input(UInt(10.W))
		val rdData = Output(UInt(8.W))

		val wrEna = Input(Bool())
		val wrAddr = Input(UInt(10.W))
		val wrData = Input(UInt(8.W))
	})

	val mem = SyncReadMem(1024, UInt(8.W))
	val wrDataReg = RegNext(io.wrData)
	val doForwardReg = RegNext(io.wrAddr === io.rdAddr && io.wrEna)
	val memData = mem.read(io.rdAddr)

	// Initialize memory
	loadMemoryFromFile(mem, "./src/main/resources/init.hex",
		MemoryLoadFileType.Hex)

	when(io.wrEna) {
		mem.write(io.wrAddr, io.wrData)
	}

	io.rdData := Mux(doForwardReg, wrDataReg, memData)
}

// generate Verilog
object WriteFirstMemory extends App {
	emitVerilog(new WriteFirstMemory(), Array("--target-dir", "generated"))
}


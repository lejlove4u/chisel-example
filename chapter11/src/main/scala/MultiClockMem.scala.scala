
import chisel3._
import chisel3.util._

class MemIO(val addrWidth: Int=16, val dataWidth: Int=32) extends Bundle {
    val clk = Input(Clock())
    val enable = Input(Bool())
    val write = Input(Bool())
    val read = Input(Bool())
    val address = Input(UInt(log2Up(addrWidth).W))
    val wr_data = Input(UInt(dataWidth.W))
    val rd_data = Output(UInt(dataWidth.W))
}

class MultiClockMemory(ports: Int, addrWidth: Int, dataWidth: Int) extends Module {
    val io = IO(new Bundle {
        val ps = Vec(ports, new MemIO(addrWidth, dataWidth))
    })

    val ram = SyncReadMem(addrWidth, UInt(dataWidth.W))

    for(i <- 0 until ports) {
        val p = io.ps(i)
        withClock(p.clk) {
            val rd_data = WireDefault(0.U(dataWidth.W))
            when(p.enable) {
                when(p.read) {
                    rd_data := ram(p.address)
                } .otherwise {
                    rd_data := 0.U
                }

                when(p.write) {
                    ram(p.address) := p.wr_data
                }
            }
            p.rd_data := rd_data
        }
    }
}

object MultiClkMem extends App {
    emitVerilog(new MultiClockMemory(4, 16, 8), Array("--target-dir", "generated"))
}
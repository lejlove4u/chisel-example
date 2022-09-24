/*
 * 2.3 Register.
 */

import chisel3._

class d_flip_flop_0 extends Module {
  val io = IO(new Bundle {
    val d = Input(UInt(8.W))
    val q = Output(UInt(8.W))
  })

  val tempReg = RegInit(0.U(8.W))

  tempReg := io.d
  io.q := tempReg;
}

// generate Verilog
object d_flip_flop_0 extends App {
  emitVerilog(new d_flip_flop_0(), Array("--target-dir", "generated"))
}

/**
--------------------------------------------------------------------------------
*/

class d_flip_flop_1 extends Module {
  val io = IO(new Bundle {
    val d = Input(UInt(8.W))
    val q = Output(UInt(8.W))
  })

  val tempReg = RegNext(io.d, 0.U(8.W))
  io.q := tempReg;
}

// generate Verilog
object d_flip_flop_1 extends App {
  emitVerilog(new d_flip_flop_1(), Array("--target-dir", "generated"))
}


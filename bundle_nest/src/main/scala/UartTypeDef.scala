import chisel3._

/**
 * Defines the bundle type for serial configuration. */
class serial_conf_t extends Bundle {
  val baud = UInt(4.W)
  val data = UInt(2.W)
  val paty = UInt(2.W)
  val stop = Bool()
}

/**
 * Defines the fifo type for fifo access. */
class fifo_wr_m2s_t extends Bundle {
  val ena = Bool()
  val dat = UInt(8.W)
}

class fifo_wr_s2m_t extends Bundle {
  val full = Bool()
  val dcnt = UInt(10.W)
}

class fifo_rd_m2s_t extends Bundle {
  val ena = Bool()
}

class fifo_rd_s2m_t extends Bundle {
  val dat = UInt(8.W)
  val empty = Bool()
  val dcnt = UInt(10.W)
}

class UartSerializerBase extends Module {
  val io = IO(new Bundle {
    // The UART transmit interface
    val txd = Output(UInt(1.W))

    // The UART serial configuration interface
    val s_conf = Input {
      new serial_conf_t
    }

    // The FIFO interface
    val fifo_rd_m2s = Output {
      new fifo_rd_m2s_t
    }
    val fifo_rd_s2m = Input {
      new fifo_rd_s2m_t
    }
  })
}

class UartDeserializerBase extends Module {
  val io = IO(new Bundle {
    // The UART receive interface
    val rxd = Input(UInt(1.W))

    // The UART serial configuration interface
    val s_conf = Input {
      new serial_conf_t
    }

    // The FIFO interface
    val fifo_wr_m2s = Output {
      new fifo_wr_m2s_t
    }
    val fifo_wr_s2m = Input {
      new fifo_wr_s2m_t
    }
  })
}

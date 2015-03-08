package org.shahid;

import jssc.SerialPort;
import jssc.SerialPortException;

public class PlaneGUI {

  /**
   * @param args
   * @throws SerialPortException
   */
  public static void main(String[] args) throws SerialPortException {
    SerialPort serialPort = new SerialPort("/dev/ttyACM0");
    if (!serialPort.openPort()) {
      throw new RuntimeException("WTF");
    }
    System.out.println("Port opened");
    serialPort.setParams(115200, 8, 1, 0);
    PlaneCoordinates pc = new PlaneCoordinates(serialPort);
    serialPort.addEventListener(pc, SerialPort.MASK_RXCHAR);
  }
}

package org.shahid;

public class Angles {
  public final double yaw, pitch, roll;

  public Angles(String line) {
    String[] tokens = line.split("\\s+");
    if (tokens.length != 4) {
      throw new IllegalArgumentException("Invalid format: " + line);
    }

    yaw = Double.parseDouble(tokens[1]);
    pitch = Double.parseDouble(tokens[2]);
    roll = Double.parseDouble(tokens[3]);
  }

  @Override
  public String toString() {
    return "[" + yaw + ", " + pitch + ", " + roll + "]";
  }
}

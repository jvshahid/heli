package org.shahid;

import java.awt.BorderLayout;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Vector3d;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PlaneCoordinates implements SerialPortEventListener {
  private final SerialPort serialPort;
  private String str;
  private TransformGroup tg;

  public PlaneCoordinates(SerialPort serialPort) {
    // TODO Auto-generated constructor stub
    this.serialPort = serialPort;
    str = "";
    JFrame frame = new JFrame("Plane coordinates");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 400);
    frame.setLayout(new BorderLayout());

    // Create a canvas
    Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    frame.add("Center", c);

    // a universe is where all the objects live
    SimpleUniverse simpleU = new SimpleUniverse(c);

    BranchGroup scene = createSceneGraph();

    // set the ViewingPlatform (where the User is) to nominal, more on this in
    // the next lesson
    simpleU.getViewingPlatform().setNominalViewingTransform();

    simpleU.addBranchGraph(scene); // add your SceneGraph to the SimpleUniverse
    frame.setVisible(true);
  }

  /**
   * Create a new 3d scene
   *
   * @return the newly created 3d scene
   */
  public BranchGroup createSceneGraph() {
    // Here we will create a basic SceneGraph with a ColorCube object

    // This BranchGroup is the root of the SceneGraph, 'objRoot' is the name I
    // use,
    // and it is typically the standard name for it, but can be named anything.
    BranchGroup objRoot = new BranchGroup();

    tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    // create a ColorCube object of size 0.5
    ColorCube c = new ColorCube(0.5f);
    tg.addChild(c);
    objRoot.addChild(tg);

    // return Scene Graph
    return objRoot;
  }

  @Override
  public void serialEvent(SerialPortEvent serialPortEvent) {
    // System.out.println("Received event");
    if (!serialPortEvent.isRXCHAR()) {
      System.out.println("WTF");
      return;
    }

    String newString;
    try {
      newString = this.serialPort.readString();
    } catch (SerialPortException e) {
      System.out.println("");
      return;
    }

    int prevIdx = 0, idx = 0;
    while ((idx = newString.indexOf("\n", prevIdx)) != -1) {
      this.str += newString.substring(prevIdx, idx);
      try {
        Angles angles = new Angles(this.str);
        rotate(angles);
      } catch (IllegalArgumentException e) {
        System.out.println("Cannot parse line: " + e.getMessage());
      }
      this.str = "";
      prevIdx = idx + 1;
    }

    this.str += newString.substring(prevIdx);
  }

  private void rotate(Angles angles) {
    Transform3D t3d = new Transform3D();
    System.out.println("Drawing: " + angles);

    Transform3D rotation = new Transform3D();
    rotation.setEuler(new Vector3d(
        -Math.toRadians(angles.roll)
        ,-Math.toRadians(angles.yaw)
        ,Math.toRadians(angles.pitch)));
    t3d.mul(rotation);

    tg.setTransform(t3d);
  }
}

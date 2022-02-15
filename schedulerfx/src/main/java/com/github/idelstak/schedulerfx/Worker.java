package com.github.idelstak.schedulerfx;

import java.util.Random;
import javafx.scene.paint.Color;

public class Worker {

  private final String name;

  private Color color = getRandom();

  public Worker(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public String toString() {
    return name;
  }

  private Color getRandom() {
    Random rnd = new Random();
    int r = rnd.nextInt(100) + 155;
    int g = rnd.nextInt(100) + 155;
    int b = rnd.nextInt(100) + 155;
    return Color.rgb(r, g, b);
  }
}

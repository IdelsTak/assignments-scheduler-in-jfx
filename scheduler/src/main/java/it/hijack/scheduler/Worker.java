package it.hijack.scheduler;

import java.util.Random;

import javafx.scene.paint.Color;

public class Worker {

	private String name;

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

	public String toString() {
		return name;
	}

	private Color getRandom() {
		Random rnd = new Random();
		int r = rnd.nextInt(256);
		int g = rnd.nextInt(256);
		int b = rnd.nextInt(256);
		return Color.rgb(r, g, b);
	}
}

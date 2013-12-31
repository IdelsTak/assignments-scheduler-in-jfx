package it.hijack.scheduler;

import javafx.scene.paint.Color;

public class Worker {

	private String name;
	
	private Color color = Color.YELLOW;
	
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
}

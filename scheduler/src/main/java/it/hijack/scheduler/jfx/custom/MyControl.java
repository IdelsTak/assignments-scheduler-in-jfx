package it.hijack.scheduler.jfx.custom;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Control;

public class MyControl extends Control {
	
	private DoubleProperty value;

	public MyControl(){
		getStyleClass().add(getClass().getSimpleName());
		value = new SimpleDoubleProperty(0.0);
	}
	
	public final double getValue() {
		return value.get();
	}
	
	public final void setValue(double newValue) {
		value.set(newValue);
	}
	
	public final DoubleProperty valueProperty() {
		return value;
	}
	
	@Override
	protected String getUserAgentStylesheet() {
		System.out.println("getting userAgent Stylesheet");
		String val = getClass().getResource(getClass().getSimpleName() + ".css").toExternalForm();
		System.out.println(val);
		return getClass().getResource("mycontrol.css").toExternalForm();
	}
	
}

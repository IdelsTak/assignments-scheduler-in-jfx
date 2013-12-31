package it.hijack.scheduler.jfx.custom;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import com.sun.javafx.scene.control.skin.SkinBase;


public class MyControlSkin extends SkinBase<MyControl, MyControlBehavior> {
	
	private MyControl control;
	private boolean isDirty;

	public MyControlSkin(final MyControl control) {
		super(control, new MyControlBehavior(control));
		this.control = control;
		isDirty = true;
		init();
	}

	private void init() {
		registerChangeListener(control.valueProperty(), "VALUE");
	}

	@Override
	protected void handleControlPropertyChanged(String p) {
		super.handleControlPropertyChanged(p);
		
		if("VALUE".equals(p)) {
			isDirty = true;
			requestLayout();
		}
	}
	
	@Override
	protected void layoutChildren() {
		if(!isDirty) return;

		drawControl();
		isDirty = false;
		
		super.layoutChildren();
	}
	
	private void drawControl() {
		// do the magic here
		Shape shape = new Circle(100, 100, 100);
		shape.getStyleClass().add("shape");
		
		getChildren().clear();
		getChildren().addAll(shape);
	}

	@Override
	public MyControl getSkinnable() {
		return control;
	}

	@Override
	public void dispose() {
		control = null;
	}
}

package it.hijack.scheduler.jfx.custom;

import javafx.scene.input.MouseEvent;

import com.sun.javafx.scene.control.behavior.BehaviorBase;

public class MyControlBehavior extends BehaviorBase<MyControl> {

	public MyControlBehavior(final MyControl myControl) {
		super(myControl);
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		MyControlSkin skin = (MyControlSkin) getControl().getSkin();
		System.out.println("enter");
	}
}

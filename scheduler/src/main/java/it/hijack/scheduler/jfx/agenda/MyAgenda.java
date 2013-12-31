package it.hijack.scheduler.jfx.agenda;

import it.hijack.scheduler.Timetable;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;

public class MyAgenda extends Control {

	final private Timetable timetable;
	
	public MyAgenda(Timetable timetable) {
		this.timetable = timetable;
		getStyleClass().add(getClass().getSimpleName());
	}

	@Override
	protected String getUserAgentStylesheet() {
		return getClass().getResource(getClass().getSimpleName() + ".css").toExternalForm();
	}

	public Timetable getTimetable() {
		return timetable;
	}
	
	public void refresh(){
		MyAgendaSkin skin = (MyAgendaSkin)getSkin();
		skin.refresh();
	}

	public static class MyRectangle {
		private int row;
		private int col;
		private String text;
		private Region region;

		public MyRectangle(int row, int col, String text) {
			this.row = row;
			this.col = col;
			this.text = text;
		}
		
		public void setRegion(Region region) {
			this.region = region;
		}
		
		public Region getRegion() {
			return region;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
		
		public String getText() {
			return text;
		}
	}
}

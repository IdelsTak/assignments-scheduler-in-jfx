package it.hijack.scheduler.jfx.agenda;

public enum WeekDays {
	MONDAY(0), TUESDAY(1), WEDNSDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6);

	private int index;

	WeekDays(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static WeekDays fromIndex(int index) {
		for (WeekDays wd : values()) {
			if (wd.getIndex() == index)
				return wd;
		}
		throw new IllegalArgumentException("No enum for the given index");
	}
}

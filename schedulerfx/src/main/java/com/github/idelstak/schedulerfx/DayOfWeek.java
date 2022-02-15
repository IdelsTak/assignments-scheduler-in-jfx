package com.github.idelstak.schedulerfx;

public enum DayOfWeek {
  MONDAY(0),
  TUESDAY(1),
  WEDNSDAY(2),
  THURSDAY(3),
  FRIDAY(4),
  SATURDAY(5),
  SUNDAY(6);

  private final int index;

  DayOfWeek(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public static DayOfWeek fromIndex(int index) {
    for (DayOfWeek wd : values()) {
      if (wd.getIndex() == index) return wd;
    }
    throw new IllegalArgumentException("There is no Day of Week for the given index");
  }
}

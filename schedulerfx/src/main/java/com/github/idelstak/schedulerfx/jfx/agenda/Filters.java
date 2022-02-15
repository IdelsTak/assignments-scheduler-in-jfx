package com.github.idelstak.schedulerfx.jfx.agenda;

import com.github.idelstak.schedulerfx.Assignment;
import com.github.idelstak.schedulerfx.DayOfWeek;

import java.util.Collection;
import java.util.stream.Collectors;

public class Filters {

  public interface Predicate<T> {
    boolean apply(T type);
  }

  public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
    return target.stream().filter(predicate::apply).collect(Collectors.toList());
  }

  public static Collection<Assignment> getDailyAssignments(
      Collection<Assignment> assignments, final DayOfWeek dayOfWeek) {
    Predicate<Assignment> dailyAssignments =
        (Assignment item) -> item.getDayOfWeek().equals(dayOfWeek);

    return filter(assignments, dailyAssignments);
  }
}

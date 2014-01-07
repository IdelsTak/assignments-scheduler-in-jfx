package it.hijack.scheduler.jfx.agenda;

import it.hijack.scheduler.Assignment;
import it.hijack.scheduler.DayOfWeek;

import java.util.ArrayList;
import java.util.Collection;

public class Filters {

	public interface Predicate<T> {
		boolean apply(T type);
	}

	public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
		Collection<T> result = new ArrayList<T>();
		for (T element : target) {
			if (predicate.apply(element)) {
				result.add(element);
			}
		}
		return result;
	}
	
	public static Collection<Assignment> getDailyAssignments(Collection<Assignment> assignments, final DayOfWeek dayOfWeek) {
		Predicate<Assignment> dailyAssignments = new Predicate<Assignment>() {
			@Override
			public boolean apply(Assignment item) {
				return item.getDayOfWeek().equals(dayOfWeek);
			}
		};
		
		return filter(assignments, dailyAssignments);
	}
	
}

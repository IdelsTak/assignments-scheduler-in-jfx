package it.hijack.scheduler;

import java.util.HashSet;
import java.util.Set;

public class Timetable {

	Set<Assignment> assignments = new HashSet<>();

	public Assignment assign(Activity office) {
		Assignment assignment = new Assignment(office);
		assignments.add(assignment);
		return assignment;
	}

	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public Set<Assignment> getAssignmentsOf(Worker worker) {
		Set<Assignment> result = new HashSet<>();
		for (Assignment a : assignments) {
			if (a.isAssignedTo(worker)) {
				result.add(a);
			}
		}
		return result;
	}

	public Set<Assignment> getAssignmentsFor(Activity activity) {
		Set<Assignment> result = new HashSet<>();
		for (Assignment a : assignments) {
			if (activity != null && activity.equals(a.getActivity())) {
				result.add(a);
			}
		}
		return result;
	}

	public Set<Assignment> getAssignmentsFor(Customer cust) {
		Set<Assignment> result = new HashSet<>();
		for (Assignment a : assignments) {
			if (cust != null && cust.equals(a.getActivity().getCustomer())) {
				result.add(a);
			}
		}
		return result;
	}

	public int getTotalHoursOf(Worker arianna) {
		Set<Assignment> assignments = getAssignmentsOf(arianna);
		return getTotalHours(assignments);
	}

	public int getTotalHoursOf(Customer coop) {
		Set<Assignment> assignments = getAssignmentsFor(coop);
		return getTotalHours(assignments);
	}

	public int getTotalHoursOf(Activity activity) {
		Set<Assignment> assignments = getAssignmentsFor(activity);
		return getTotalHours(assignments);
	}
	
	private int getTotalHours(Set<Assignment> assignments) {
		int total = 0;
		for (Assignment a : assignments) {
			total += a.getTotalHours();
		}
		return total;
	}

}

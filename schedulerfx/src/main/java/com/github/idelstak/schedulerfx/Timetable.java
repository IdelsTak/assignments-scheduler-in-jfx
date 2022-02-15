package com.github.idelstak.schedulerfx;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    assignments.stream().filter(a -> (a.isAssignedTo(worker))).forEachOrdered(result::add);

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
    return assignments.stream()
        .filter(a -> (cust != null && cust.equals(a.getActivity().getCustomer())))
        .collect(Collectors.toSet());
  }

  public int getTotalHoursOf(Worker arianna) {
    return getTotalHours(getAssignmentsOf(arianna));
  }

  public int getTotalHoursOf(Customer coop) {
    return getTotalHours(getAssignmentsFor(coop));
  }

  public int getTotalHoursOf(Activity activity) {
    return getTotalHours(getAssignmentsFor(activity));
  }

  private int getTotalHours(Set<Assignment> assignments) {
    return assignments.stream().map(Assignment::getTotalHours).reduce(0, Integer::sum);
  }
}

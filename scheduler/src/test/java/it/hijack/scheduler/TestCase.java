package it.hijack.scheduler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Test;

public class TestCase {

	@Test
	public void canInitializeAWorkerWithAName() {
		Worker arianna = new Worker("Arianna");
		assertThat(arianna.getName(), is("Arianna"));
	}
	
	@Test
	public void canInitializeAnActivityWithAName() {
		Activity act = new Activity("Ufficio");
		assertThat(act.getName(), is("Ufficio"));
	}
	
	@Test
	public void canCreateAnAssignmentForAnActivity() throws Exception {
		Activity office = new Activity("ufficio");
		Assignment assignment = new Assignment(office);
		
		assertThat(assignment.getActivity(), is(office));
	}

	@Test
	public void canAssignAnAssignmentToAWorkerForAGivenShift() throws Exception {
		Worker arianna = new Worker("arianna");
		Activity officeActivity = new Activity("ufficio");
		Assignment office = new Assignment(officeActivity);
		office.assignTo(arianna).from(8).to(9);
		
		assertThat(office.getWorker(), is(arianna));
		assertThat(office.getStartHour(), is(8));
		assertThat(office.getStopHour(), is(9));
	}

	@Test
	public void canAssignAnActivityToAWorkerForAGivenShift() throws Exception {
		Customer coop = new Customer("cooperativa");
		Activity office = new Activity("ufficio", coop);
		Worker arianna = new Worker("arianna");
		
		Timetable timetable = new Timetable();
		Assignment assignment = timetable.assign(office).to(arianna).from(8).to(9);
		
		assertThat(assignment.getWorker(), is(arianna));
		assertThat(assignment.getStartHour(), is(8));
		assertThat(assignment.getStopHour(), is(9));
	}
	
	@Test
	public void canSpecifyTheDayOfAnAssignment() throws Exception {
		Activity office = new Activity("ufficio", new Customer("cooperativa"));
		
		Timetable timetable = new Timetable();
		Assignment assignment = timetable.assign(office).on(DayOfWeek.TUESDAY);
		
		assertThat(assignment.getDayOfWeek(), is(DayOfWeek.TUESDAY));
	}
	
	@Test
	public void canListAllAssignment() throws Exception {
		Timetable timetable = new Timetable();
		Activity office = new Activity("ufficio", new Customer("coop"));
		timetable.assign(office).to(new Worker("arianna")).from(8).to(9);
		timetable.assign(office).to(new Worker("giovanna")).from(8).to(9);
		
		Set<Assignment> assignments = timetable.getAssignments();
		assertThat(assignments.size(), is(2));
	}

	@Test
	public void canListAllAssignmentByWorker() throws Exception {
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		Activity office = new Activity("ufficio", new Customer("coop"));
		Activity gita = new Activity("gita", new Customer("rossi"));
		
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(gita).to(arianna).from(9).to(12);
		timetable.assign(office).to(giovanna).from(9).to(10);
		
		Set<Assignment> ariannaAssignments = timetable.getAssignmentsOf(arianna);
		Set<Assignment> giovannaAssignments = timetable.getAssignmentsOf(giovanna);
		
		assertThat(ariannaAssignments.size(), is(2));
		assertThat(giovannaAssignments.size(), is(1));
	}

	@Test
	public void canListAllAssignmentByActivity() throws Exception {
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		Activity office = new Activity("ufficio", new Customer("coop"));
		Activity gita = new Activity("gita", new Customer("rossi"));
		
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(gita).to(arianna).from(9).to(12);
		timetable.assign(office).to(giovanna).from(9).to(10);
		
		Set<Assignment> officeAssignments = timetable.getAssignmentsFor(office);
		Set<Assignment> gitaAssignments = timetable.getAssignmentsFor(gita);
		
		assertThat(officeAssignments.size(), is(2));
		assertThat(gitaAssignments.size(), is(1));
	}

	@Test
	public void canListAllAssignmentByCustomer() throws Exception {
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		Customer coop = new Customer("coop");
		Customer rossi = new Customer("rossi");
		
		Timetable timetable = new Timetable();
		Activity office = new Activity("ufficio", coop);
		Activity gita = new Activity("gita", rossi);
		Activity educazione = new Activity("educazione", rossi);
		Activity pulizie = new Activity("pulizie", rossi);
		
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(gita).to(arianna).from(9).to(12);
		timetable.assign(pulizie).to(arianna).from(15).to(16);
		
		timetable.assign(office).to(giovanna).from(9).to(10);
		timetable.assign(educazione).to(giovanna).from(12).to(15);
		
		Set<Assignment> coopAssignments = timetable.getAssignmentsFor(coop);
		Set<Assignment> rossiAssignments = timetable.getAssignmentsFor(rossi);
		
		assertThat(coopAssignments.size(), is(2));
		assertThat(rossiAssignments.size(), is(3));
	}
	
	@Test
	public void anActivityBelongsToACustomer() throws Exception {
		Customer coop = new Customer("il rubino");
		
		Activity office = new Activity("Ufficio", coop);
		
		assertThat(office.getCustomer(), is(coop));
	}
	
	@Test
	public void canChangeTheWorkerOfAnAssignment() throws Exception {
		Customer coop = new Customer("cooperativa");
		Activity office = new Activity("ufficio", coop);
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		Assignment assignment = timetable.assign(office).to(arianna).from(8).to(9);
		
		assignment.setWorker(giovanna);
		
		assertThat(assignment.getWorker(), is(giovanna));
		assertThat(timetable.getAssignmentsOf(giovanna).size(), is(1));
		assertThat(timetable.getAssignmentsOf(arianna).size(), is(0));
	}

	@Test
	public void canChangeTheTimeOfAnAssignment() throws Exception {
		Customer coop = new Customer("cooperativa");
		Activity office = new Activity("ufficio", coop);
		Worker arianna = new Worker("arianna");
		
		Timetable timetable = new Timetable();
		Assignment assignment = timetable.assign(office).to(arianna).from(8).to(9);
		
		assignment.from(10).to(15);
		
		assertThat(assignment.getStartHour(), is(10));
		assertThat(assignment.getStopHour(), is(15));
	}
	
	@Test
	public void canGetTheTotalHoursOfAnAssignment() throws Exception {
		Customer coop = new Customer("cooperativa");
		Activity office = new Activity("ufficio", coop);
		Worker arianna = new Worker("arianna");
		
		Assignment a = new Assignment(office);
		a.setWorker(arianna);
		a.from(10).to(15);

		assertThat(a.getTotalHours(), is(5));
	}
	
	@Test
	public void canGetTheTotalHoursByWorker() throws Exception {
		Customer coop = new Customer("cooperativa");
		Activity office = new Activity("ufficio", coop);
		Worker arianna = new Worker("arianna");
		Worker giovanna= new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(office).to(arianna).from(10).to(12);
		timetable.assign(office).to(arianna).from(15).to(19);
		timetable.assign(office).to(giovanna).from(15).to(19);
		
		assertThat(timetable.getTotalHoursOf(arianna), is(7));
		assertThat(timetable.getTotalHoursOf(giovanna), is(4));
	}

	@Test
	public void canGetTheTotalHoursByCustomer() throws Exception {
		Customer coop = new Customer("cooperativa");
		Customer rossi = new Customer("rossi");
		Activity office = new Activity("ufficio", coop);
		Activity pulizie = new Activity("pulizie", rossi);
		
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(office).to(arianna).from(10).to(12);
		timetable.assign(office).to(arianna).from(15).to(19);
		
		timetable.assign(pulizie).to(giovanna).from(10).to(19);
		
		assertThat(timetable.getTotalHoursOf(coop), is(7));
		assertThat(timetable.getTotalHoursOf(rossi), is(9));
	}

	@Test
	public void canGetTheTotalHoursByActivity() throws Exception {
		Customer coop = new Customer("cooperativa");
		Customer rossi = new Customer("rossi");
		
		Activity office = new Activity("ufficio", coop);
		Activity pulizie = new Activity("pulizie", rossi);
		
		Worker arianna = new Worker("arianna");
		Worker giovanna = new Worker("giovanna");
		
		Timetable timetable = new Timetable();
		timetable.assign(office).to(arianna).from(8).to(9);
		timetable.assign(office).to(arianna).from(10).to(12);
		
		timetable.assign(pulizie).to(arianna).from(8).to(9).getTotalHours();
		timetable.assign(pulizie).to(giovanna).from(5).to(20).getTotalHours();
		
		assertThat(timetable.getTotalHoursOf(office), is(3));
		assertThat(timetable.getTotalHoursOf(pulizie), is(16));
	}
}

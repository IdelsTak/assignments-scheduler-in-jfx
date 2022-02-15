package com.github.idelstak.schedulerfx.jfx.agenda;

import com.github.idelstak.schedulerfx.Activity;
import com.github.idelstak.schedulerfx.Assignment;
import com.github.idelstak.schedulerfx.DayOfWeek;
import com.github.idelstak.schedulerfx.Worker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.SkinBase;

public class MyAgendaSkin extends SkinBase<MyAgenda> {

  private final int gridColumns = 7;
  private final int gridRows = 12;
  private final int cellWidth = 100;
  private final int cellHeight = 30;
  private boolean isDirty;
  private Pane dragPane;
  private Rectangle dashedRectangle;
  private double dashedRectangleStartX;
  private double dashedRectangleStartY;

  private final ObjectProperty<StackPane> selectedStackPane = new SimpleObjectProperty<>();
  private final MyAgenda control;

  public MyAgendaSkin(MyAgenda control) {
    super(control);
    this.control = control;
    createNewAssignment(8, 2, DayOfWeek.MONDAY);
    drawControl();
    this.isDirty = false;
  }

  @Override
  protected void layoutChildren(
      double contentX, double contentY, double contentWidth, double contentHeight) {
    if (!isDirty) return;

    isDirty = false;
    super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
  }

  public void refresh() {
    reset();
    construct();
  }

  private void reset() {
    dragPane.getChildren().removeAll(stacks.keySet());
    stacks.clear();
  }

  private void construct() {
    Set<Assignment> assignments;
    WorkerFilter filter = getSkinnable().getWorkerFilter();
    if (filter.showAll()) {
      assignments = getSkinnable().getTimetable().getAssignments();
    } else {
      assignments = getSkinnable().getTimetable().getAssignmentsOf(filter.getWorker());
    }

    initSlots(assignments);

    for (DayOfWeek dow : DayOfWeek.values()) {
      Collection<Assignment> dailyAssignments = Filters.getDailyAssignments(assignments, dow);
      createDailyRectangles(dailyAssignments, dow);
    }

    for (Assignment assignment : assignments) {
      drawRectangleFor(assignment);
    }
  }

  private void createDailyRectangles(Collection<Assignment> assignments, DayOfWeek dayOfWeek) {
    int workers = countDifferentWorkers(assignments);
    if (workers > 1) {
      System.out.println(dayOfWeek + " has " + workers + " different workers!!");
    }

    List<Assignment> list = new ArrayList<>(assignments);
    Collections.sort(
        list,
        (Assignment o1, Assignment o2) ->
            o1.getWorker().getName().compareTo(o2.getWorker().getName()));
  }

  private int countDifferentWorkers(Collection<Assignment> assignments) {
    Set<Worker> workers = new HashSet<>();
    for (Assignment a : assignments) {
      workers.add(a.getWorker());
    }
    return workers.size();
  }

  private final Map<StackPane, Assignment> stacks = new HashMap<>();

  private void drawRectangleFor(Assignment assignment) {
    Rectangle rectangle = new Rectangle();
    rectangle.setWidth(cellWidth - 2);

    int totalHeight = assignment.getTotalHours() * cellHeight - 2;
    rectangle.setHeight(totalHeight);
    rectangle.setFill(assignment.getWorker().getColor());
    rectangle.setArcHeight(6);
    rectangle.setArcWidth(6);
    rectangle.setStroke(null);

    StackPane stack = new StackPane();
    stack.setMaxWidth(cellWidth - 2);
    int dayOfWeek = assignment.getDayOfWeek().getIndex(); // MONDAY is 0

    stack.setLayoutX(dayOfWeek * cellWidth + 2);
    int startingHourIndex = assignment.getStartHour() - 8;
    stack.setLayoutY(startingHourIndex * cellHeight + 2);

    Label lbl =
        new Label(assignment.getActivity().getName() + " - " + assignment.getWorker().getName());
    stack.getChildren().add(rectangle);
    stack.getChildren().add(lbl);
    Tooltip.install(stack, createToltipFor(assignment));

    stack.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        (MouseEvent evt) -> {
          if (((StackPane) evt.getSource()).contains(evt.getX(), evt.getY())) {
            selectedStackPane.set((StackPane) evt.getSource());
            Rectangle r = (Rectangle) selectedStackPane.get().getChildren().get(0);
            r.setStroke(Color.RED);

            Assignment ass = stacks.get(selectedStackPane.get());
            getSkinnable().setSelectedAssignment(ass);
          }
        });

    stack.addEventHandler(MouseEvent.ANY, Event::consume);

    dragPane.getChildren().add(stack);
    stacks.put(stack, assignment);
  }

  private Tooltip createToltipFor(Assignment assignment) {
    Tooltip t = new Tooltip();
    t.setText(assignment.getActivity().getName() + " - " + assignment.getWorker().getName());
    return t;
  }

  private void drawControl() {
    System.out.println("drawControl()");

    Pane container = new Pane();
    dragPane = new Pane();

    BorderPane borderPane = new BorderPane();

    container.prefWidthProperty().bind(control.widthProperty());
    container.prefHeightProperty().bind(control.heightProperty());

    Region hoursColumn = createHoursColumn();
    Region weekHeader = createWeekHeader(hoursColumn);
    Region grid = createGrid(gridColumns, gridRows);
    grid.setStyle("-fx-border-color: GRAY");
    dragPane.getChildren().add(grid);

    borderPane.setTop(weekHeader);
    borderPane.setLeft(hoursColumn);
    borderPane.setCenter(dragPane);

    construct();

    container.getChildren().addAll(borderPane);

    addSelectionCapability();

    configureStackSelectionBehaviour();

    getChildren().clear();
    getChildren().addAll(container);
  }

  private void configureStackSelectionBehaviour() {
    selectedStackPane.addListener(
        (ObservableValue<? extends StackPane> arg0, StackPane old, StackPane newValue) -> {
          if (old != null) ((Rectangle) old.getChildren().get(0)).setStroke(null);
        });
  }

  private Region createGrid(int columns, int rows) {
    GridPane grid = new GridPane();
    grid.setStyle("-fx-background-color: #DDD");

    for (int r = 0; r < rows; r++) {
      grid.getRowConstraints().add(new RowConstraints(cellHeight));
    }
    for (int c = 0; c < columns; c++) {
      grid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
    }

    for (int c = 0; c < columns; c++) {
      for (int r = 0; r < rows; r++) {
        Rectangle cell = new Rectangle(cellWidth - 1, cellHeight - 1, Color.ALICEBLUE);
        grid.add(cell, c, r);
        GridPane.setHalignment(cell, HPos.RIGHT);
        GridPane.setValignment(cell, VPos.BOTTOM);
      }
    }

    grid.setGridLinesVisible(false);
    return grid;
  }

  private void addSelectionCapability() {
    dragPane.setOnMousePressed(
        (MouseEvent evt) -> {
          System.out.println("pressed");
          double verticalTicks = evt.getY() / cellHeight;
          double flooredVerticalTicks = Math.floor(verticalTicks);

          double horizontalTicks = evt.getX() / cellWidth;
          double flooredHorizontalTicks = Math.floor(horizontalTicks);

          dashedRectangleStartX = flooredHorizontalTicks * cellWidth;
          dashedRectangleStartY = flooredVerticalTicks * cellHeight;

          // System.out.println("ORIGIN: " + dashedRectangleStartY);

          dashedRectangle = new Rectangle();
          dashedRectangle.setX(dashedRectangleStartX + 2);
          dashedRectangle.setY(dashedRectangleStartY + 2);
          dashedRectangle.setWidth(cellWidth - 2);
          dashedRectangle.setHeight(cellHeight - 2);
          dashedRectangle.setFill(Color.ORANGE);
          dashedRectangle.setArcHeight(6);
          dashedRectangle.setArcWidth(6);
          dashedRectangle.setStroke(null);
          dragPane.getChildren().add(dashedRectangle);
        });

    dragPane.setOnMouseDragged(
        (MouseEvent evt) -> {
          // System.out.println(evt.getX() + ", " + evt.getY());
          if (evt.getY() <= dashedRectangleStartY) {
            return;
          }

          if (evt.getY() >= (cellHeight * gridRows)) {
            return;
          }

          double deltaY = evt.getY() - dashedRectangleStartY;
          double ticks = deltaY / cellHeight;
          double ceiledTicks = Math.ceil(ticks);
          double rectangleHeight = ceiledTicks * cellHeight - 2;
          dashedRectangle.setHeight(rectangleHeight);
        });

    dragPane.setOnMouseReleased(
        (MouseEvent evt) -> {
          System.out.println("released");
          int dayOfWeekIndex = (int) Math.ceil(dashedRectangleStartX / cellWidth);
          int startingHour = (int) Math.ceil(dashedRectangleStartY / cellHeight) + 8;
          int cells = (int) Math.ceil(dashedRectangle.getHeight() / cellHeight);

          DayOfWeek dayOfWeek = DayOfWeek.fromIndex(dayOfWeekIndex);
          control.setCursor(Cursor.DEFAULT);
          dragPane.getChildren().remove(dashedRectangle);
          dashedRectangle = null;

          createNewAssignment(startingHour, cells, dayOfWeek);
          refresh();
        });
  }

  private void createNewAssignment(int startHour, int hours, DayOfWeek dayOfWeek) {
    Worker worker = getSkinnable().getWorkerInCreation();
    Activity activity = getSkinnable().getActivityInCreation();
    getSkinnable()
        .getTimetable()
        .assign(activity)
        .to(worker)
        .from(startHour)
        .to(startHour + hours)
        .on(dayOfWeek);
  }

  private Region createWeekHeader(Region hoursColumn) {
    HBox header = new HBox(0);
    Rectangle rect = new Rectangle();
    rect.widthProperty().bind(hoursColumn.widthProperty());

    header.getChildren().add(rect);

    for (DayOfWeek day : DayOfWeek.values()) {
      Label label = createWeekDayLabel(day.name());
      header.getChildren().add(label);
    }

    return header;
  }

  private Label createWeekDayLabel(String text) {
    Label label = new Label(text);
    label.setMinWidth(cellWidth);
    return label;
  }

  private Region createHoursColumn() {
    VBox hours = new VBox();
    for (int h = 0; h < gridRows; h++) {
      Label label = createHourLabel(h + 8);
      hours.getChildren().add(label);
    }
    hours.setSpacing(0);
    return hours;
  }

  private Label createHourLabel(int hour) {
    String text = new DecimalFormat("00").format(hour);
    Label label = new Label(text + ":00");
    label.setMinHeight(cellHeight);
    return label;
  }

  //  @Override
  //  public MyAgenda getSkinnable() {
  //    return control;
  //  }

  private Slot[][] slots;
  private Set<Assignment> overlappingAssignments;

  private void initSlots(Set<Assignment> assignments) {
    slots = new Slot[DayOfWeek.values().length][24];
    for (DayOfWeek dow : DayOfWeek.values()) {
      for (int h = 8; h < gridRows + 8; h++) {
        Slot slot = new Slot(dow, h);
        slots[dow.getIndex()][h] = slot;
      }
    }

    overlappingAssignments = new HashSet<>();
    for (Assignment assignment : assignments) {
      int startHour = assignment.getStartHour();
      int totalHours = assignment.getTotalHours();
      for (int h = startHour; h < startHour + totalHours; h++) {
        Slot slot = slots[assignment.getDayOfWeek().getIndex()][h];
        slot.add(assignment);
        if (slot.getAssignments().size() > 1) {
          overlappingAssignments.addAll(slot.getAssignments());
        }
      }
    }
  }

  private static class Slot {
    private final List<Assignment> assignments = new ArrayList<>();
    private final DayOfWeek dow;
    private final int hour;

    public Slot(DayOfWeek dow, int hour) {
      this.dow = dow;
      this.hour = hour;
    }

    public void add(Assignment assignment) {
      assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
      return assignments;
    }

    public DayOfWeek getDow() {
      return dow;
    }

    public int getHour() {
      return hour;
    }
  }
}

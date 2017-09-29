package com.freshek.dziennikplus;

import com.freshek.dziennikplus.diary.api.UonetPlusAPI;
import com.freshek.dziennikplus.diary.objects.Grade;
import com.freshek.dziennikplus.diary.objects.GradeModifier;
import com.freshek.dziennikplus.diary.objects.GradesList;
import com.freshek.dziennikplus.diary.objects.tables.ScheduleTableRow;
import com.freshek.dziennikplus.ui.GradeTableEntry;
import com.freshek.dziennikplus.ui.ScheduleTableEntry;
import com.freshek.dziennikplus.utils.http.MathUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;

import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Created by Freshek on 19.09.17.
 *
 * Controller to manage the displayed
 * content
 */
public class Controller {
    @FXML
    private TableView gradesTable;
    @FXML
    private TableColumn subjectColumn;
    @FXML
    private TableColumn gradesColumn;
    @FXML
    private TableColumn predictedGradesAvgColumn;
    @FXML
    private TableColumn gradesAvgColumn;
    @FXML
    private TableColumn calculatedGradesAvgColumn;
    @FXML
    private Label statusLbl;

    @FXML
    private TableView scheduleTable;
    @FXML
    private TableColumn lessonIdColumn;
    @FXML
    private TableColumn lessonTimeColumn;
    @FXML
    private TableColumn mondayLessonColumn;
    @FXML
    private TableColumn tuesdayLessonColumn;
    @FXML
    private TableColumn wednesdayLessonColumn;
    @FXML
    private TableColumn thursdayLessonColumn;
    @FXML
    private TableColumn fridayLessonColumn;

    private UonetPlusAPI api;
    private ObservableList<GradeTableEntry> gradeTableEntries = FXCollections.observableArrayList();
    private ObservableList<ScheduleTableEntry> scheduleTableEntries = FXCollections.observableArrayList();

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Timeline updateTimeLine = new Timeline(new KeyFrame(
            Duration.millis(15 * 60 * 1000),
            a -> update()
    ));

    private TrayIcon trayIcon;

    public void init(UonetPlusAPI api) {
        this.api = api;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("res/img/icon48.png"), "Dziennik+");
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("tray not supported");
        }

        updateTimeLine.play();

        update();

        gradesTable.setItems(gradeTableEntries);

        subjectColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("subject"));
        gradesColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, GradesList>("grades"));
        predictedGradesAvgColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("predictedGradesAvg"));
        gradesAvgColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("gradesAvg"));
        calculatedGradesAvgColumn.setCellValueFactory(new PropertyValueFactory<Grade, String>("calculatedGradesAvgStr"));

        scheduleTable.setItems(scheduleTableEntries);

        lessonIdColumn.setCellValueFactory(new PropertyValueFactory<ScheduleTableEntry, String>("lessonId"));
        lessonTimeColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("lessonTime"));
        mondayLessonColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("mondayLesson"));
        tuesdayLessonColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("tuesdayLesson"));
        wednesdayLessonColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("wednesdayLesson"));
        thursdayLessonColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("thursdayLesson"));
        fridayLessonColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("fridayLesson"));
    }

    private void update() {
        statusLbl.setText("Pobieranie danych z serwera dziennika…");

        List<GradeTableEntry> gradesTableRows = api.getGradesEntries();

        if (gradeTableEntries.size() > 0) {
            for (GradeTableEntry row : gradesTableRows) {
                Enumerable<GradeTableEntry> e = Linq4j.asEnumerable(gradeTableEntries);
                GradeTableEntry entry = e.first(x -> x.getSubject().equals(row.getSubject()));

                if (row.getGradesList().size() > entry.getGradesList().size()) {
                    trayIcon.displayMessage("Aktualizacja!", "Aktualizacja ocen z przedmiotu: " + row.getSubject(), TrayIcon.MessageType.INFO);
                }
            }
        }

        gradeTableEntries.clear();
        for (GradeTableEntry row : gradesTableRows) {
            gradeTableEntries.add(row);
        }

        List<ScheduleTableRow> scheduleTableRows = api.getScheduleEntries();

        scheduleTableEntries.clear();
        for (ScheduleTableRow row : scheduleTableRows) {
            ScheduleTableEntry entry = new ScheduleTableEntry(
                    row.getLessonId(),
                    row.getLessonStart(),
                    row.getLessonEnd(),
                    row.getMondayLesson(),
                    row.getTuesdayLesson(),
                    row.getWednesdayLesson(),
                    row.getThursdayLesson(),
                    row.getFridayLesson());

             scheduleTableEntries.add(entry);
        }

        updateTimeLine.playFromStart();

        statusLbl.setText("Gotowe! Aktualizacja o: " + dateFormat.format(ZonedDateTime.now().plusMinutes(15)));
    }
}

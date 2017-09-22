package com.freshek.dziennikplus;

import com.freshek.dziennikplus.diary.api.UonetPlusAPI;
import com.freshek.dziennikplus.diary.objects.Grade;
import com.freshek.dziennikplus.diary.objects.GradeModifier;
import com.freshek.dziennikplus.diary.objects.GradesTableRow;
import com.freshek.dziennikplus.ui.GradeTableEntry;
import com.freshek.dziennikplus.utils.http.MathUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
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
    private TabPane tabPane;
    @FXML
    private Label statusLbl;
    @FXML
    private VBox mainContainer;

    private UonetPlusAPI api;
    private ObservableList<GradeTableEntry> gradeTableEntries = FXCollections.observableArrayList();

    public void init(UonetPlusAPI api) {
        this.api = api;

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(5 * 60 * 1000),
                a -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        update();

        gradesTable.setItems(gradeTableEntries);

        subjectColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("subject"));
        gradesColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("grades"));
        predictedGradesAvgColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("predictedGradesAvg"));
        gradesAvgColumn.setCellValueFactory(new PropertyValueFactory<GradeTableEntry, String>("gradesAvg"));
        calculatedGradesAvgColumn.setCellValueFactory(new PropertyValueFactory<Grade, Float>("calculatedGradesAvg"));
    }

    private void update() {
        statusLbl.setText("Pobieranie danych z serwera dziennika…");

        List<GradesTableRow> gradesTableRows = api.getGradesEntries();

        gradeTableEntries.clear();
        for (GradesTableRow row : gradesTableRows) {
            gradeTableEntries.add(new GradeTableEntry(
                    row.getSubject(),
                    row.getGrades(),
                    row.getPredictedGradesAvg(),
                    row.getGradesAvg(),
                    row.getGrades().size() == 0 ? "-" : String.valueOf(MathUtil.calcAvgOfGrades(row.getGrades()))));
        }

        statusLbl.setText("Gotowe!");
    }
}

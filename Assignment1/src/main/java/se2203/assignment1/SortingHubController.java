package se2203.assignment1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

public class SortingHubController implements Initializable {
    private Integer[] intArray;
    private int[] intArrayPrimitive;
    private List<Integer> intList;
    private double reqWidth;
    private SortingStrategy sortingMethod;
    private Thread sortingThread;

    @FXML
    private Rectangle rec;
    @FXML
    private Slider slideInput;
    @FXML
    private Label slideValue;
    @FXML
    private ComboBox<String> cBox;
    @FXML
    private AnchorPane pane;

    public void setSortStrategy() {
        // Checks for merge sort
        if (cBox.getSelectionModel().getSelectedItem() == "Merge Sort") {
            sortingMethod = new MergeSort(intArrayPrimitive, this);
        } else { // Checks for selection sort
            sortingMethod = new SelectionSort(intArrayPrimitive, this);
        }
    }

    // Listener for the reset button
    public void resetClicked() {
        sortingThread.stop(); // Stops any threads currently running (stopping the animation)
        // Setting the values for the slider
        slideValue.setText("64");
        slideInput.setValue(64);
        cBox.setValue("Merge Sort"); // Reset the combo box to display Merge sort
        // Create another random array and display it on the screen
        arrayCreator(64);
        updateGraph(intArrayPrimitive);
    }

    public void sortClicked() {
        setSortStrategy(); // Checks which sorting method is selected
        sortingThread  = new Thread(sortingMethod); // Creating a new sorting thread sending the sorting method
        sortingThread.start(); // Begins the thread
    }

    public void updateGraph(int[] data) {
        // Calculates the required width which is the pref width / length of array
        reqWidth = pane.getPrefWidth() / data.length;
        pane.getChildren().clear(); // Clears pane, prevents overlapping rectangle drawings
        for (int i = 0; i < data.length; i++) {
            // Creating rectangle objects that have the required coordinates and dimensions
            rec = new Rectangle(2+(i*reqWidth), data[i]*310/data.length, reqWidth-1, 310-data[i]*310/data.length);
            rec.setFill(Color.RED); // Fills colour to red
            pane.getChildren().add(rec); // Adds rectangles to pane to display them
        }
    }

    // Event listener for slider
    public void sliderListener() {
        // Makes slider value a number without decimal places
        slideValue.setText(String.format("%.0f", slideInput.getValue()));
        // Creating array based on the integer value of the slider
        arrayCreator((int)slideInput.getValue());
        // Update graph to display rectangles on the screen
        updateGraph(intArrayPrimitive);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // INITIALIZING COMBO BOX
        cBox.getItems().setAll("Merge Sort", "Selection Sort");
        cBox.getSelectionModel().selectFirst();

        // INITIALIZING ARRAY
        arrayCreator((int)slideInput.getValue());
        updateGraph(intArrayPrimitive);
    }

    // arrayCreator method to initialize array size and shuffle values
    public void arrayCreator (int size) {
        intArrayPrimitive = new int[size];
        intArray = new Integer[size];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i;
        }
        intList = Arrays.asList(intArray);
        Collections.shuffle(intList);
        intList.toArray(intArray);
        for (int i = 0; i < intArray.length; i++) {
            intArrayPrimitive[i] = intArray[i];
        }
    }
}
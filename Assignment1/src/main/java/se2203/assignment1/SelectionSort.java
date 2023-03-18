package se2203.assignment1;

import javafx.application.Platform;

public class SelectionSort extends SortingHubController implements SortingStrategy{
    private int[] list;
    private SortingHubController controller;

    // Constructor with parameters
    public SelectionSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    // SelectionSort algorithm
    @Override
    public void sort(int[] list){
        // Selection Sorting algorithm
        for (int i = 0; i < list.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < list.length; j++){
                if (list[j] > list[index]){
                    index = j; // Finding which index has the greatest value
                }
            }
            // Swapping values
            int smallerNumber = list[index];
            list[index] = list[i];
            list[i] = smallerNumber;

            // Sleep to produce an animation
            try {
                Thread.sleep(65);
            } catch (InterruptedException e) {

            } // Now update grpah
            Platform.runLater(() ->{
                controller.updateGraph(list);
            });
        }
    }

    // Thread for run
    @Override
    public void run() {
        sort(list);
    }
}

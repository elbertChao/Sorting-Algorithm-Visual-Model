package se2203.assignment1;

import javafx.application.Platform;

public class MergeSort extends SortingHubController implements SortingStrategy{
    private  int[] list;
    private SortingHubController controller;

    // Constructor with parameters
    public MergeSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    // MergeSort Algorithm
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        int curr_size;
        int left_start;
        for (curr_size = 1; curr_size <= n - 1; curr_size = 2 * curr_size) {
            for (left_start = 0; left_start < n - 1; left_start += 2 * curr_size) {
                int mid = Math.min(left_start + curr_size - 1, n - 1);
                int right_end = Math.min(left_start + 2 * curr_size - 1, n - 1);
                try {
                    Thread.sleep(65);
                } catch (InterruptedException e) {
                }
                merge(arr, left_start, mid, right_end);
                Platform.runLater(() ->{
                    controller.updateGraph(arr);
                });
            }
        }
    }

    public static void merge(int[] arr, int start, int mid, int end) {
        //Dividing the array 'arr' into two parts
        int p1StartIndex = start;
        int p1EndIndex = mid;
        int startIndexP2 = mid + 1;
        int endIndexP2 = end;
        int[] temp = new int[end - start + 1]; //to store the sorted data
        int i = 0; //index for the temp array
        while ((p1StartIndex <= p1EndIndex) && (startIndexP2 <= endIndexP2))
        {
            if (arr[p1StartIndex] > arr[startIndexP2]) {
                temp[i] = arr[p1StartIndex];
                p1StartIndex++;
            } else {
                temp[i] = arr[startIndexP2];
                startIndexP2++;
            }
            i++;
        }
        //copying the leftovers from either of the parts to the temp array
        while (p1StartIndex <= p1EndIndex) {
            temp[i] = arr[p1StartIndex];
            p1StartIndex++;
            i++;
        }
        while (startIndexP2 <= endIndexP2) {
            temp[i] = arr[startIndexP2];
            startIndexP2++;
            i++;
        }
        //updating part of the arr array with the sorted content in temp
        for (int k = start, j = 0; k <= end; k++, j++)
            arr[k] = temp[j];
    }

    // Run for the thread
    @Override
    public void run() {
        sort(list);
    }
}

package se2203.assignment1;

import java.lang.Runnable;

public interface SortingStrategy extends Runnable {
    public void sort(int[] list);

    public void run ();
}

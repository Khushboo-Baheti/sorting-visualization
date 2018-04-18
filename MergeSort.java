import javax.swing.*;
import java.awt.*;

public class MergeSort extends JPanel implements Runnable {

    private final int array[];
    private final int sleepMs;
    private int yellowIndex = -1;
    private int greenIndex = -1;
    private int columnWidth = 4;
    private int borderWidth = 1;

    public MergeSort(int[] array, int sleepMs) {
        this.array = array.clone();
        this.sleepMs = sleepMs;
        setBackground(Color.BLACK);
    }


    @Override
    public void run() {
        sort(array);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Merge Sort", 200, 200);

        for (int i = 0; i < array.length; i++) {
            if (greenIndex == -1 || i > greenIndex) {
                g.setColor(Color.WHITE);
                g.fillRect(columnWidth * i, getHeight() - array[i], columnWidth, array[i]);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(columnWidth * i, getHeight() - array[i], columnWidth, array[i]);
            }
            g.setColor(Color.BLACK);
            g.fillRect(columnWidth * i, getHeight() - array[i], borderWidth, array[i]);
        }

        if(yellowIndex != -1) {
            g.setColor(Color.YELLOW);
            g.fillRect(columnWidth * yellowIndex, getHeight() - array[yellowIndex], columnWidth, array[yellowIndex]);
        }
    }

    public void sort(int[] a) {
        int[] aux = new int[a.length];           // creates the new auxiliary array
        sort(a, aux, 0, a.length - 1);
        greenIndex = array.length - 1;
		yellowIndex = -1;
		repaint();
    }

    /**
     * merge the sorted elements in two halves
     *
     * @param a   array to be sorted
     * @param aux auxiliary array with copy of array a
     * @param lo  first part the array to be sorted
     * @param mid mid point of array
     * @param hi  last part of array to be sorted
     */
    private void merge(int[] a, int aux[], int lo, int mid, int hi) {
        repaint();
        sleep();
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];               // Copy elements from array to auxiliary array
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            yellowIndex = k;
            repaint();
            if (i > mid) {                // when i pointer is exhausted , increment j
                a[k] = aux[j++];
            } else if (j > hi) {          // j pointer is exhausted  , increment i and k
                a[k] = aux[i++];
            } else if (aux[j] < aux[i]) { // aux[j] less than aux[i] ,increment j and k
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
		if (hi > greenIndex) {
			greenIndex = hi;
			repaint();
			sleep();
		}
    }

    /**
     * Sort and merge the element in each half
     *
     * @param a   array to be sorted
     * @param aux auxiliary array with copy of array a
     * @param lo  first part of array to be sorted
     * @param hi  last part of the array to be sorted
     */
    private void sort(int[] a, int[] aux, int lo, int hi) {
        sleep();
        repaint();
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;          // compute the mid point
        sort(a, aux, lo, mid);                 // sort the first half of array
        sort(a, aux, mid + 1, hi);         // sort the second half of the array
        merge(a, aux, lo, mid, hi);            // merge the sorted array

    }

    private void sleep() {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

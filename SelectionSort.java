import javax.swing.JPanel;
import java.awt.*;

public class SelectionSort extends JPanel implements Runnable {

    private int array[];
    private int sleepMs;
    private int yellowIndex = -1;
    private int greenIndex = -1;
    private int columnWidth = 4;
    private int borderWidth = 1;

    public SelectionSort(int[] array, int sleepMs) {
        this.array = array.clone();
        this.sleepMs = sleepMs;
        setBackground(Color.BLACK);
    }

    @Override
    public void run() {
        int N = array.length;
        sort(array);
        greenIndex = N;
        yellowIndex = -1;
        repaint();
    }

    public void sort(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;                       // Initialize element in ith position as minimum
            for (int j = i + 1; j < N; j++) {  // Initialize next element right to i as j
                if (a[j] < a[min]) {           // Compares if j is less than min
                    min = j;                   // resets min
                }
                yellowIndex = min;
                repaint();
                sleep(sleepMs/4);
            }
            swap(a, i, min);                   // swap the minimum with i
            greenIndex = i;
            repaint();
            sleep(sleepMs);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Selection Sort", 200, 200);

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

        if (yellowIndex != -1) {
            g.setColor(Color.YELLOW);
            g.fillRect(columnWidth * yellowIndex, getHeight() - array[yellowIndex], columnWidth, array[yellowIndex]);
        }

    }

    private static void swap(int[] array, int i, int j) {
        if (i == j) {
            return;
        }

        int t = array[i];
        array[i] = array[j];
        array[j] = t;

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

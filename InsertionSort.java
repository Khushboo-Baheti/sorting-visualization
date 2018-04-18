import javax.swing.JPanel;
import java.awt.*;

public class InsertionSort extends JPanel implements Runnable {

    private int array[];
    private int sleepMs;
    private int yellowIndex = -1;
    private int greenIndex = -1;
    private int columnWidth = 4;
    private int borderWidth = 1;

    public InsertionSort(int[] array, int sleepMs) {
        this.array = array.clone();
        this.sleepMs = sleepMs;
        setBackground(Color.BLACK);
    }

    @Override
    public void run() {
        int N = array.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0; j--) {
                yellowIndex = j;
                repaint();
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                    try {
                        Thread.sleep(sleepMs);
                    } catch (InterruptedException e) {
                    }
                } else {
                    break;
                }
            }
            greenIndex = i;
            repaint();
        }
        yellowIndex = -1;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Insertion Sort", 200, 200);

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

}

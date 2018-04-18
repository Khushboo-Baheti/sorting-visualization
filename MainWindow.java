import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainWindow {
    private static int sleepMs = 20;

    private static void startVisualization(int[] dataset, String datasetName, JFrame frame) throws InterruptedException {
        frame.setLayout(new GridLayout(1, 1, 0, 0));
        JLabel label = new JLabel(datasetName, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 32));
        frame.getContentPane().add(label);
        frame.setVisible(true);

        Thread.sleep(20000);
        frame.getContentPane().remove(label);
        frame.setLayout(new GridLayout(2, 3, 0, 0));

        BubbleSort bb = new BubbleSort(dataset, sleepMs);
        bb.setPreferredSize(new Dimension(450, 400));
        frame.add(bb);

        SelectionSort ss = new SelectionSort(dataset, sleepMs);
        ss.setPreferredSize(new Dimension(450, 400));
        frame.add(ss);

        InsertionSort is = new InsertionSort(dataset, sleepMs);
        is.setPreferredSize(new Dimension(450, 400));
        frame.add(is);

        QuickSort qs = new QuickSort(dataset, sleepMs);
        qs.setPreferredSize(new Dimension(450, 400));
        frame.add(qs);

        MergeSort ms = new MergeSort(dataset, sleepMs);
        ms.setPreferredSize(new Dimension(450, 400));
        frame.add(ms);

        JPanel dummyPanel = new JPanel();
        dummyPanel.setPreferredSize(new Dimension(450, 400));
        dummyPanel.setBackground(Color.BLACK);
        frame.add(dummyPanel);

        frame.setVisible(true);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(bb);
        executor.execute(ss);
        executor.execute(is);
        executor.execute(qs);
        executor.execute(ms);
        executor.shutdown();
        // Wait until all the tasks are completed, this timeout should be high enough for visualization to complete.
        executor.awaitTermination(120, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        int dataset1[] = new int[100];
        for(int i = 0; i < 100; i++) {
            dataset1[i] = 100 - i;
        }

        JFrame frame = new JFrame("Sorting Algorithms Visualization");
        frame.setSize(1400, 800);
        frame.setLayout(new GridLayout(2, 3, 0, 0));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startVisualization(dataset1, "Reverse Order Input", frame);
        //startVisualization(shuffleArray(dataset1), "Random Order Input", frame);

        Thread.sleep(5000);
        // dispose the window
        frame.dispose();
    }

    private static int[] shuffleArray(int[] input) {
        List<Integer> list = Arrays.stream(input).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        return list.stream().mapToInt(n -> n).toArray();
    }
}


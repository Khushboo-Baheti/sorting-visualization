import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BubbleSort extends JPanel implements Runnable {
	private int array[];
	private int sleepMs; // Sleep after every swap
	private int yellowIndex = -1; // Element which is being compared with other elements and swapped if not in order
	private int greenIndex = -1; // Elements after this index are in sorted order
	private int columnWidth = 4; // Width of every element in pixels
	private int borderWidth = 1; // Black border width around every element

	public BubbleSort(int[] array, int sleepMs) {
		this.array = array.clone();
		this.sleepMs = sleepMs;
		setBackground(Color.BLACK);
	}

	@Override
	public void run() {
		int n = array.length;   

		for(int i = 0; i < n; i++) {             // check for each array element
			for(int j = 1; j < (n-i); j++) {		//check for (total array elements) - (last i elements which are at their final positions in sorting list)  
				yellowIndex = j;
				repaint();

				if(array[j-1] > array[j]) {  
					swap_elements(array, j-1, j );  // if the previous element is greater than j element, it will swap them 
					try {
						Thread.sleep(sleepMs);
					} catch (InterruptedException e) {
					}
				}
			}
			greenIndex = n - i - 1;
			repaint();
		}
		// All the elements are sorted and everything should be green
		greenIndex = 0;
		yellowIndex = -1;
		repaint();
	}

    @Override
    public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Bubble Sort", 200, 200);
    	
		for (int i = 0; i < array.length; i++) {
			if (greenIndex == -1 || i < greenIndex) {
				g.setColor(Color.WHITE);
				g.fillRect(columnWidth * i, getHeight() - array[i], columnWidth, array[i]);
			} else {
				g.setColor(Color.GREEN);
				g.fillRect(columnWidth * i, getHeight() - array[i], columnWidth, array[i]);
			}
			// Black borders
			g.setColor(Color.BLACK);
			g.fillRect(columnWidth * i, getHeight() - array[i], borderWidth, array[i]);
		}

		if(yellowIndex != -1) {
			g.setColor(Color.YELLOW);
			g.fillRect(columnWidth * yellowIndex, getHeight() - array[yellowIndex], columnWidth, array[yellowIndex]);
		}

    }

	// Method to swap the array elements
	private void swap_elements(int array[], int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
}
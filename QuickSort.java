import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class QuickSort extends JPanel implements Runnable {
	private int array[];
	private int sleepMs; // Sleep after every swap
	private int columnWidth = 4; // Width of every element in pixels
	private int borderWidth = 1; // Black border width around every element

	private int blueIndex = -1; // For pivot
	private int redIndex = -1; // For left element getting swapped
	private int yellowIndex = -1; // For right element which is being swapped
	private int greenIndex = -1; // Elements are sorted till this point

	public QuickSort(int[] array, int sleepMs) {
		this.array = array.clone();
		this.sleepMs = sleepMs;
		setBackground(Color.BLACK);
	}

	@Override
	public void run() {
		try {
			sortInternal(array, 0, array.length - 1);
			// All the elements are sorted and everything should be green
			greenIndex = array.length - 1;
			redIndex = blueIndex = yellowIndex = -1;
			repaint();
		} catch (InterruptedException e) {
		}
	}

	private void sortInternal(int array[], int low, int high) throws InterruptedException {	 // low is Starting index, high is Ending index
		if (low < high) {
			int partitionIndex = partition(array, low, high); 		// partitionIndex is the partition index
			sortInternal(array, low, partitionIndex - 1);						// recursively sort array index before and after the division
			sortInternal(array, partitionIndex + 1, high);
		}
		if (low > greenIndex) {
			greenIndex = low;
			redIndex = yellowIndex = -1;
		}
		repaint();
	}

	/* Takes last element as pivot and places it at correct position in sorted array,
	 * and places all smaller than pivot to left of pivot and all greater elements to right of pivot */
	private int partition(int array[], int low, int high) throws InterruptedException {
		blueIndex = high;
		repaint();
		Thread.sleep(sleepMs);

		int pivot = array[high]; 			// element to be placed at right position 
		int i = (low-1); 				// index of smaller element( initially 0-1 = -1) 
		for (int j = low; j < high; j++) {
			// If current element is smaller than or equal to pivot
			if (array[j] <= pivot) {
				redIndex = i;
				yellowIndex = j;
				repaint();
				Thread.sleep(sleepMs/4);

				i++;						// increment index of smaller element 
				swap_elements(array,i,j);		// swap them

				yellowIndex = i;
				redIndex = j;
				repaint();
			}
		}

		swap_elements(array, i+1, high);   // place pivot at correct position by swapping

		return i+1;
	}

	// Method to swap the array elements
	private void swap_elements(int array[], int a, int b) {      
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

    @Override
    public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Quick Sort", 200, 200);
    	
		for (int i = 0; i < array.length; i++) {
			if (greenIndex == -1 || i > greenIndex) {
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
		if(blueIndex != -1) {
			g.setColor(Color.BLUE);
			g.fillRect(columnWidth * blueIndex, getHeight() - array[blueIndex], columnWidth, array[blueIndex]);
		}
		if(redIndex != -1) {
			g.setColor(Color.RED);
			g.fillRect(columnWidth * redIndex, getHeight() - array[redIndex], columnWidth, array[redIndex]);
		}
    }
}


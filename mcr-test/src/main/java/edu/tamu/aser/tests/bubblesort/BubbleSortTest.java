package edu.tamu.aser.tests.bubblesort;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnit4MCRRunner.class)
public class BubbleSortTest {

    @Test
    public void testSortPositiveNumbers() throws Exception {
        sortAndCheck(new int[] { 463, 5,1});
    }

    public void sortAndCheck(int[] array) throws Exception {
        BubbleSort bs = new BubbleSort(array);
        bs.Sort();
    }

}

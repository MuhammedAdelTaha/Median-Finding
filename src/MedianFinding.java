import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MedianFinding {
    public MedianFinding(){}

    /**
     * Takes an array and two indexes and swap them.
     * Note that this function has a side effect on the array.
     * */
    private void swap(List<Integer> array, int i1, int i2){
        if(i1 == i2)
            return;
        int tmp = array.get(i1);
        array.set(i1, array.get(i2));
        array.set(i2, tmp);
    }

    /**
     * This function uses randomized divide and conquer approach to find the median of an array.
     * Note that this function has a side effect on the array.
     * */
    public int randomizeDCSol(List<Integer> array, int p, int r){
        if (p == r)
            return array.get(p);

        int n = array.size();
        Random random = new Random();
        swap(array, p, random.nextInt(p, r));
        int pivot = partition(array, p, r);

        if (pivot > n / 2)
            return randomizeDCSol(array, p, pivot - 1);
        else if (pivot < n / 2)
            return randomizeDCSol(array, pivot + 1, r);
        else
            return array.get(pivot);
    }

    /**
     * This function takes an array and upper/lower bounds and partition the array around the lower bound p
     * such that all elements on the left of the pivot will < the pivot and the elements on the right of the
     * pivot will be > the pivot.
     * Note that this function has a side effect on the array.
     * */
    private int partition(List<Integer> array, int p, int r){
        int i = p, j = p + 1, pivotVal = array.get(p);
        while(j <= r) {
            while(j <= r && array.get(j) > pivotVal)
                j++;
            if (j > r)
                break;
            swap(array, i + 1, j);
            i++; j++;
        }
        swap(array, p, i);
        return i;
    }

    /**
     * This function uses deterministic selection (median of medians) to get the median of an array.
     * Note that this function has a side effect on the array.
     * */
    public int deterministicSol(List<Integer> array, int p, int r){
        if (p == r)
            return array.get(p);

        int n = array.size();
        swap(array, p, medianOfMedians(array.subList(p, r)) + p);
        int pivot = partition(array, p, r);

        if (pivot > n / 2)
            return randomizeDCSol(array, p, pivot - 1);
        else if (pivot < n / 2)
            return randomizeDCSol(array, pivot + 1, r);
        else
            return array.get(pivot);
    }

    /**
     * This function takes an array and get the pivot that the array will be partitioned around.
     * */
    private int medianOfMedians(List<Integer> array){

        int n = array.size();
        //List of medians values on the array
        List<Integer> medians = new ArrayList<>();
        //List of medians indexes on the array
        List<Integer> mediansIndexes = new ArrayList<>();

        //m is the number of sections that have exactly 5 elements
        int m = n / 5;
        //Get the median for the possible remaining elements
        int remaining = n % 5;
        //Check if there is a remaining section or not
        int remainingSection = 0;
        if (remaining != 0)
            remainingSection = 1;

        //Base case if m == 0, then the array size is less than 5
        //OR m == 1 && remaining sections == 0, then the array contains 5 elements
        if (m == 0 || (m == 1 && remainingSection == 0)) {
            return getMedianIdx(array);
        }

        //Iterate over these sections and get the median value and index for every section then add it to the lists
        for (int i = 0; i < m + remainingSection; i++) {
            //The start would be from i (the section number) * 5 (the section size)
            int start = i * 5;
            //The end = start + 4 in all sections except the remaining section end = start + remaining - 1
            int end = start;
            if (i == m)
                end += remaining - 1;
            else
                end += 4;
            // Get the median index locally within subArray
            // Then get it globally by adding size_of_section * section_num
            int medianIdx = getMedianIdx(array.subList(start, end)) + 5 * i;
            // Then add the global index in the mediansIndexes and the median value in the medians
            int median = array.get(medianIdx);
            mediansIndexes.add(medianIdx);
            medians.add(median);
        }

        return mediansIndexes.get(medianOfMedians(medians));
    }

    /**
     * Brute force function to get the median of an array of size <= 5.
     * */
    private int getMedianIdx(List<Integer> array) {
        //The index of the median
        int i, n = array.size();
        for (i = 0; i < n; i++) {
            //Count numbers that are less than array[i]
            int lessCount = 0;
            for (int j = 0; j < n; j++){
                if (i == j)
                    continue;
                if (array.get(j) < array.get(i))
                    lessCount++;
            }
            //Numbers that are less than array[i] must be equal half the array size
            if (lessCount == n / 2)
                break;
        }
        return i;
    }

    /**
     * This function uses a naive approach to get the median of the array by sorting it and get the mid-element.
     * */
    public int naiveSol(List<Integer> array){
        List<Integer> cp = new ArrayList<>(array);
        cp.sort(Comparator.comparingInt(num -> num));
        return cp.get(cp.size() / 2);
    }
}

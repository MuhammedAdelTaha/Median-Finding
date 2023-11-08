import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestAndAnalysis {
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String RESET = "\033[0m";

    public TestAndAnalysis(){}

    /**
     * This function takes a file name, an array size, and a range of numbers to select from,
     * then generates a random distinct numbers and write them into a file.
     * Note that first line in file represents the size of the array.
     * Note that the size of the range of numbers to select from should be >= the array size to be able to select
     *      distinct number of element withing this range.
     * */
    public void writeRandArray(String pathname, int size, int lowerBound, int upperBound) {
        if (upperBound < lowerBound || size > Math.abs((long) upperBound - lowerBound)) {
            System.out.println(RED + "Enter a valid data..." + RESET);
            return;
        }

        File file = new File(pathname);
        File path = file.getParentFile();
        if (path != null)
            path.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathname))) {
            writer.write(size + "\n");
            Set<Integer> exceptions = new HashSet<>();
            for (int i = 0; i < size; i++) {
                try {
                    int randChoice = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
                    while (exceptions.contains(randChoice)) {
                        randChoice = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
                    }
                    exceptions.add(randChoice);
                    writer.write(randChoice + "\n");
                } catch (IOException e) {
                    System.out.println(RED + "Error happened when writing the file..." + RESET);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "Enter a valid file name..." + RESET);
        }
    }

    /**
     * This function takes a file name and parse the data in it and put it in the given array.
     * */
    private boolean addElements(List<Integer> array, String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                array.add(Integer.parseInt(reader.readLine()));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * This function takes an array, then get the median from the implemented three methods and return true if they
     * are all equal and false otherwise.
     * */
    public void testCorrectness(String filename){
        MedianFinding mf = new MedianFinding();

        List<Integer> array = new ArrayList<>();
        if (!this.addElements(array, filename)) {
            System.out.println(RED + "Enter a valid file name..." + RESET);
            return;
        }
        int n = array.size();

        List<Integer> cp1 = new ArrayList<>(array);
        int median1 = mf.randomizeDCSol(cp1, 0,  n - 1);

        for (int i = 0; i < 100; i++){
            cp1 = new ArrayList<>(array);
            int tmp = mf.randomizeDCSol(cp1, 0, n - 1);
            if (median1 != tmp){
                System.out.println(GREEN + "Accepted :)" + RESET);
            }
        }

        List<Integer> cp2 = new ArrayList<>(array);
        int median2 = mf.deterministicSol(cp2, 0, n - 1);

        List<Integer> cp3 = new ArrayList<>(array);
        int median3 = mf.naiveSol(cp3);

        if (median1 == median2 && median2 == median3) {
            System.out.println(GREEN + "Accepted :)" + RESET);
        }
        else {
            System.out.println(RED + "Failed :(" + RESET);
        }
    }

    public void timeAnalysis(String filename) {
        List<Integer> array = new ArrayList<>();
        this.addElements(array, filename);
        int n = array.size();

        MedianFinding mf = new MedianFinding();

        List<Integer> cp1 = new ArrayList<>(array);

        long start = System.currentTimeMillis();
        mf.randomizeDCSol(cp1, 0,  n - 1);
        System.out.println("Randomized Divide and Conquer execution time: " + (System.currentTimeMillis() - start));

        List<Integer> cp2 = new ArrayList<>(array);

        start = System.currentTimeMillis();
        mf.deterministicSol(cp2, 0, n - 1);
        System.out.println("Deterministic solution execution time: " + (System.currentTimeMillis() - start));

        List<Integer> cp3 = new ArrayList<>(array);

        start = System.currentTimeMillis();
        mf.naiveSol(cp3);
        System.out.println("Naive solution execution time: " + (System.currentTimeMillis() - start));

        System.out.println("\n--------------------------------------------------------\n");
    }
}

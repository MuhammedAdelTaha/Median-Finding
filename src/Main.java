public class Main {

    public static void main(String[] args) {
        TestAndAnalysis tst = new TestAndAnalysis();

        //You can create a test file using this method
        //tst.writeRandArray("test9.txt", 10_000_000, Integer.MIN_VALUE, Integer.MAX_VALUE);

        //First we should test the correctness
        tst.testCorrectness("test1.txt");
        tst.testCorrectness("test2.txt");
        tst.testCorrectness("test3.txt");
        tst.testCorrectness("test4.txt");
        tst.testCorrectness("test5.txt");
        tst.testCorrectness("test6.txt");
        tst.testCorrectness("test7.txt");
        tst.testCorrectness("test8.txt");

        System.out.println("\n========================================================\n");

        //Second we should test the time complexity
        tst.timeAnalysis("test1.txt");
        tst.timeAnalysis("test2.txt");
        tst.timeAnalysis("test3.txt");
        tst.timeAnalysis("test4.txt");
        tst.timeAnalysis("test5.txt");
        tst.timeAnalysis("test6.txt");
        tst.timeAnalysis("test7.txt");
        tst.timeAnalysis("test8.txt");

    }
}
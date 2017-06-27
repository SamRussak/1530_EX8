import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Exercise8 {


    private Exercise8(long numOfThreads, long totalThreadsIterations) {

        ArrayList<PiThread> threads = new ArrayList<>();

        // Assumes all entries are evenly divisible
        for(int i = 0; i < numOfThreads; i++){
            PiThread piThread = new PiThread(totalThreadsIterations/numOfThreads);
            threads.add(piThread);
            piThread.start();
        }

        int totalIn = 0;

        for (PiThread p : threads) {
            try{
                p.join();
                totalIn = totalIn + p.getInside();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }


        double piIHope = (totalIn/ (float) totalThreadsIterations)*4;
        System.out.println("Pi Estimate: "+piIHope);



    }

    class PiThread extends Thread implements Runnable {

        private long iterations;
        private ThreadLocalRandom random;

        private int inside;

        PiThread(long iterations){

            this.iterations = iterations;
            this.random = ThreadLocalRandom.current();
        }

        public void run() {

            for (int i = 0; i < iterations; i++){
                double dartThrowX = random.nextDouble(1);
                double dartThrowY = random.nextDouble(1);


                double insideCalc = Math.pow(dartThrowX,2)+Math.pow(dartThrowY,2);
                if(insideCalc < 1){
                    inside++;
                }
            }
        }

        private int getInside() {
            return inside;
        }
    }


    public static void main(String[] args){

        try{
            long numberOfThreads = Long.valueOf(args[0]);
            long totalThreadIterations = Long.valueOf(args[1]);
            new Exercise8(numberOfThreads,totalThreadIterations);
        }catch (Exception e){
            System.out.println("Invalid Command Line arguements");
            System.out.println("Please follow the below format");
            System.out.println("Exercise8 numberOfThreads totalThreadIterations");
        }


    }

}

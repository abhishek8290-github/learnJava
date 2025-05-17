package day2;
import day2.interfaces.Runnable;;


public class BenchmarkMiddleware implements Runnable {


    public Runnable task ;

    public void setTask(Runnable task){
        this.task = task;

    }


    @Override
    public void run() {
        /*
         * === Benchmark: Analysis Completed ===
Time: 1,238,912 ns
Memory Used: 1,045,672 bytes
Files Processed: 5

         */
        Runtime runtime = Runtime.getRuntime();
        
        
        
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        long start = System.nanoTime();
	    task.run(); 
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;
	    long end = System.nanoTime();
        System.out.println("=== Benchmark: Analysis Completed ===");
	    System.out.println("Time: " + (end - start) + " ns");
        System.out.println("Memory Used: " + memoryUsed + " bytes");
    }

}
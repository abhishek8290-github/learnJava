package day2;



public class Main {
    public static void main(String[] args) {

        BenchmarkMiddleware benchmark = new BenchmarkMiddleware();
        fileAnalyser fileAnalyser = new fileAnalyser();
        benchmark.setTask(fileAnalyser);
        benchmark.run();

        
        
    }    

}

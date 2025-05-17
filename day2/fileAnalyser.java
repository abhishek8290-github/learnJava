package day2;
import java.io.File;

import day2.interfaces.Runnable;

public class fileAnalyser implements Runnable {
    
    public static String getCurrentWorkingDirectory(){
        return System.getProperty("user.dir");
    }
    public static File[] getAllFilesFromAnAddress(String fileAddress){
        File folder = new File(fileAddress);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public static boolean checkFileExtension(File file, String extension){
        return file.getName().endsWith(extension);
    }
    
    public  void run() {
        String fileAddress = getCurrentWorkingDirectory() + "/day2/files";
        
        File[] files = getAllFilesFromAnAddress(fileAddress);


        fileStats.IfileStats[] fileStatsArr = new fileStats.IfileStats[files.length];
        int i =0;
        for (File file : files ){
            if(!checkFileExtension(file, ".txt")) continue;
            fileStats.IfileStats _fileStats = fileStats.getFileState(file);
            fileStatsArr[i++] = _fileStats;
        }

        for (fileStats.IfileStats _fileStats : fileStatsArr){
            System.out.print(_fileStats.toString());
        }
    }
}

/*
 * **Multi-File Analyzer with Benchmarking and Report Generation**

**Objective:**

Build a Java application that:

- Analyzes **multiple text files**
- Extracts detailed statistics (word count, frequency, unique words)
- Benchmarks performance (time + memory)
- Generates a **structured report** as output
- Uses clean OOP design, exception handling, collections, file I/O, and a middleware for benchmarking

✅ Requirements

#📥 Input

- A **directory path** provided by the user (e.g. `"./texts/"`)
- The directory contains multiple `.txt` files (assume UTF-8 text)
- Your program must read **all `.txt` files**

⚙️ For Each File

- Read the file
- Count:- Number of lines    - Number of words    - Number of unique words    
- Identify **top 5 most frequent words**
- Ignore punctuation and case

📤 Output Report (as file `report.txt`)

For each file:


File: file1.txt
Lines: 120
Words: 945
Unique Words: 348
Top 5 Words:
1. the – 52
2. java – 41
3. and – 35
4. you – 33
5. code – 31


At the end of the report:


=== Benchmark: Analysis Completed ===
Time: 1,238,912 ns
Memory Used: 1,045,672 bytes
Files Processed: 5


🧱 Technical Constraints

You **must use**:

- `File`, `BufferedReader`, `Files.walk()` or `Files.list()`
- `HashMap`, `TreeMap`, `PriorityQueue` (for top words)
- OOP: at least two classes (`FileAnalyzer`, `ReportGenerator`, etc.)
- Exception handling (invalid paths, read errors, empty files)
- Your own **`BenchmarkUtil` middleware** to time and profile the entire operation
- `String` and `Pattern` or `split()` for tokenizing words
- Optional: Use concurrency (`ExecutorService`) to process files in parallel

🔁 Bonus Features (Optional)

- Use `ExecutorService` to analyze files concurrently
- Use `java.time` to timestamp start/end time in the report
- Compress the report as a `.zip` file after generation
- Track words across **all files** and print the **global top 10**



 */
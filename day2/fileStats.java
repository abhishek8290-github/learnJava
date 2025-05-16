package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class fileStats {

  public static class IfileStats {
    int numberOfLines; 
    int numberOfWords; 
    String longestWord;
    String mostFrequentWord; 
    int mostFrequentWordCount;
    int numberOfUniqueWords;
    String fileName;
    HashMap<String, Integer> wordFrequency;
    PriorityQueue<Map.Entry<String, Integer>> topWords;
    
    

    public IfileStats() {
      this.numberOfLines = 0;
      this.numberOfWords = 0;
      this.numberOfUniqueWords = 0;
      this.longestWord = "";
      this.mostFrequentWord = "";
      this.mostFrequentWordCount = 0;
      this.fileName = "";
      this.wordFrequency = new HashMap<>();

      // Read More !!
      this.topWords = new PriorityQueue<>((a,b)-> b.getValue().compareTo(a.getValue()) 
  );

      
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("File: ").append(this.fileName).append("\n");
        output.append("Lines: ").append(this.numberOfLines).append("\n");
        output.append("Words: ").append(this.numberOfWords).append("\n");
        output.append("Unique Words: ").append(this.numberOfUniqueWords).append("\n");
        output.append("Top 5 Words:\n");


        for (int i = 0; i < 5; i++) {
            if(topWords.isEmpty()) break;
            Map.Entry<String, Integer> entry = topWords.poll();
            output.append(i + 1 + ". " + entry.getValue() + " - " + entry.getKey()+"\n");
        }


        return output.toString();
    }
  }

  public static IfileStats getFileState(File file) {
    IfileStats fileStats = new IfileStats();

    fileStats.fileName = file.getName();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));

      while (true) {
        String line = reader.readLine();
        if (line == null) break;
        fileStats.numberOfLines++;

        // In the getFileState method, replace the simple split with regex pattern
        // Change this line:
        // for (String word : line.split(" ")) {
        
        // To this pattern that handles multiple cases:
        for (String word : line.toLowerCase().split("\\s+|[^a-zA-Z0-9]+")) {
            if (word.isEmpty()) continue;  // Skip empty strings
            int wordCount = fileStats.wordFrequency.getOrDefault(word, 0) + 1;
        
            int wordLength = word.length();
            // this is not working as expected
            if (wordLength > 2 && wordCount > fileStats.mostFrequentWordCount) {
              fileStats.mostFrequentWordCount = wordCount;
              fileStats.mostFrequentWord = word;
            }
        
            if (wordLength > fileStats.longestWord.length()) {
              fileStats.longestWord = word;
            }
        
            fileStats.wordFrequency.put(word, wordCount);
            fileStats.numberOfWords++;
        }
      }

      fileStats.numberOfUniqueWords = fileStats.wordFrequency.size();
 
      
      
      fileStats.wordFrequency.forEach((w, c) -> {
        fileStats.topWords.add(Map.entry(w, c)); 
      });

      reader.close();
    } catch (Exception error) {
      System.out.println("Error: " + error);
      return fileStats;
    }

    return fileStats;
  }
}

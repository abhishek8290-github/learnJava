package day2;
import java.io.File;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;


public class fileStats {
    
    public static class IfileStats {
        int numberOfLines; // Done 
        int numberOfWords; // Done 
        String longestWord; // Done 
        String mostFrequentWord; // Done 
        int mostFrequentWordCount; // Done 
        int numberOfUniqueWords;
        String fileName;
        HashMap<String, Integer> wordFrequency;
        
        public IfileStats() {
            this.numberOfLines = 0;
            this.numberOfWords = 0;
            this.numberOfUniqueWords = 0;
            this.longestWord = "";
            this.mostFrequentWord = "";
            this.mostFrequentWordCount = 0;
            this.fileName = "";
            this.wordFrequency = new HashMap<>();
        }
        @Override
        public String toString() {


            /**
File: file1.txt
Lines: 120
Words: 945
Unique Words: 348
Top 5 Words:
1. the – 52
2. java – 41
3. and – 35
4. you – 33
5. code – 31 */

    }
    
    public static IfileStats getFileState(File file){
        
        IfileStats fileStats = new IfileStats();

        
        HashMap<String, Integer> _map = new HashMap<>();

        
        String fileContent = "";
        fileStats.fileName = file.getName();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));    
            
            while (true){
                String line = reader.readLine();
                if(line == null) break;
                fileStats.numberOfLines++;
                fileContent += line;
            }

            for(String word : fileContent.split(" ")){
                int wordCount = _map.getOrDefault(word, 0) +  1;                
                int wordLength = word.length();

                if(wordLength> 2 && wordCount > fileStats.mostFrequentWordCount){
                    fileStats.mostFrequentWordCount = wordCount;
                    fileStats.mostFrequentWord = word;
                }
                
                if(wordLength> fileStats.longestWord.length()){
                    fileStats.longestWord = word;
                }

                _map.put(word, wordCount);
                fileStats.numberOfWords++;
                
            }
            fileStats.numberOfUniqueWords = _map.size();
            fileStats.wordFrequency = _map;

            reader.close();
            
        } 
        catch (Exception  error) {
            System.out.println("Error: " + error);
            return fileStats;
            
        }
        
        
        return fileStats;
    }



}

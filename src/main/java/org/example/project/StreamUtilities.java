package org.example.project;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtilities {
    Stream<String> streamOfStrings;
    List<String> cleanedList = new ArrayList<>();
    List<Integer> countwords = null;
    Map<String, Integer> wordsPerCount = new HashMap<>();

    public StreamUtilities() {
        Path path = Paths.get("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\TheBrothersKaramazov.txt");
        try {
            this.streamOfStrings = Files.lines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<String> getStreamOfStrings() {
        return streamOfStrings;
    }

    public List<String> removeEmptyLineAndPunctuation(){
        this.streamOfStrings.map(String::toLowerCase).forEach(
                (line) -> {
                    if (line.length() != 0) {
                        cleanedList.add(line.replaceAll("\\p{Punct}", "").
                                replaceAll("—", " ").
                                replaceAll("“", "").
                                replaceAll("”", "").
                                replaceAll("’", "").
                                replaceAll(String.valueOf((char) 65279), "").
                                replaceAll("‘", ""));
                    }
                }
        );
        return cleanedList;
    }

    public long numberLine(List<String> lines){
        return lines.stream().count();
    }

    public List<String> getStopWord(){
        List<String> lines = null;
        try {
            lines = Files.lines(Paths.get("C:\\Users\\hojja\\IdeaProjects\\stream_ex\\src\\main\\java\\org\\example\\stopword.txt"))
                    .collect(Collectors.toList());;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public List<Integer> countWordsPerSentence(){
        List<String> stopWords = getStopWord();
        this.countwords = cleanedList.stream().map(
                (line) ->{
                    String[] splited = line.split("\\s+");
                    Stream<String> removeStopWord = Arrays.stream(splited).filter(
                            word -> !stopWords.contains(word)
                    );
                    return removeStopWord.toArray();
                }
        ).map(word -> word.length).toList();
        return this.countwords;
    }

    public int totalWord(){
        return this.countwords.stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String , Integer> wordRepetitions(){
        // \s+: matches sequence of one or more whitespace characters.
        cleanedList.stream().map(line -> line.split("\\s+")).forEach(words ->
        {
            for (String s:words) {
                if (wordsPerCount.containsKey(s)){
                    wordsPerCount.put(s, wordsPerCount.get(s) + 1);
                }
                else {
                    wordsPerCount.put(s,1);
                }
            }
        });
        return wordsPerCount;
    }

    public Map<String , List<Integer>> indexingWord(){
        return IntStream.range(0, cleanedList.size())
                .boxed()
                // {{1,2}, {3,4,5}, {6,7,8}} -> {1, 2, 3, 4, 5, 6, 7, 8}
                .flatMap(index -> Arrays.stream(cleanedList.get(index).split("\\s+"))
                        //Remove Duplicate
                        .distinct()
                        .map(word -> Map.entry(word, (index + 1))))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    public Map<String, List<String>> sentenceByFirstWord(){
        return cleanedList.stream().collect(
                Collectors.groupingBy(
                        line -> line.split("\\s+")[0],
                        Collectors.toList()
                )
        );
    }

    public double averageWord(){
        return cleanedList.stream().mapToInt(String::length).average().orElse(0.0);
    }

    public int wordsCountByCondition(){
        return countwords.stream().mapToInt(Integer::intValue).reduce(
                0, (total, numb_words) -> total + (numb_words % 2 == 0 ? numb_words: -numb_words)
        );
    }


}

package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        StreamUtilities streamUtilities = new StreamUtilities();
        List<String> cleanedLines = streamUtilities.removeEmptyLineAndPunctuation();
        System.out.println("Total Lines: " + streamUtilities.numberLine(cleanedLines));


        // frequency of word in each line
        List<Integer> number_word_eachLine = streamUtilities.countWordsPerSentence();
        System.out.println("Number word in each line is done.");
        System.out.println("Total word: " + streamUtilities.totalWord());
        Map<String, Integer> count_words = streamUtilities.wordRepetitions();
        System.out.println("count miüsov: " + count_words.get("miüsov"));


        // Indexing Word
        Map<String , List<Integer>> index_word = streamUtilities.indexingWord();
        System.out.println("part: " + index_word.get("part"));

        // Map firstword -> other words
        Map<String, List<String>> first_Word_Other_Word = streamUtilities.sentenceByFirstWord();
        System.out.println("part: " + first_Word_Other_Word.get("part"));

        // Get average words in lines
        System.out.println("Average " + streamUtilities.averageWord());

        // Get words Count By Condition
        System.out.println("Words Count By Condition: " + streamUtilities.wordsCountByCondition());
    }
}
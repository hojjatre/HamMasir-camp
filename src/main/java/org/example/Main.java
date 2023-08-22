package org.example;
import java.util.List;
import java.util.Map;
import java.util.List;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
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


        // JAVA Thread
        Utilities utilities = new Utilities();

        List<Account> accounts = utilities.readAccounts();
        List<Transaction> transactions = utilities.readTransaction();

        // Number of thread : 5
        CustomThreadPool threadPool = new CustomThreadPool(5);

        for (Transaction transaction:transactions) {
            threadPool.submit(transaction);
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("\nFinal accounts:");
        for (Account account: accounts) {
            System.out.println(account.getId() + ", " + account.getName() + ", " + account.getBalance());
        }
    }
}
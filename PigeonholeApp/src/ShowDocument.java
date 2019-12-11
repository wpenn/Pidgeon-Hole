import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*This class keeps track of a document. 
 *Edited version of Swapneel's original
 *document class to account for websites
 *instead of .txt files
 *
 */
public class ShowDocument implements Comparable<ShowDocument> {
    
    private HashMap<String, Integer> termFrequency;
    private String url;
    
    public ShowDocument(String showName, String season, String episode) {
        showName = showName.trim().replaceAll("\\s+","-").toLowerCase();
        if (Integer.parseInt(season) < 10) {
            season = "0" + season;
        }
        if (Integer.parseInt(episode) < 10) {
            episode = "0" + episode;
        }
        url = "https://www.springfieldspringfield.co.uk/view_episode_scripts.php?tv-show=" + showName + "&episode=s" + season + "e" + episode;
        termFrequency = new HashMap<String, Integer>();
        processDocument();
    }
    
    private void processDocument() {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements div = doc.getElementsByClass("scrolling-script-container");
            Scanner in = new Scanner(div.text());
            while (in.hasNext()) {
                String nextWord = in.next();
                String filteredWord = nextWord.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                if (!(filteredWord.equalsIgnoreCase("")) && !(filteredWord.equalsIgnoreCase("and"))
                        && !(filteredWord.equalsIgnoreCase("or")) && !(filteredWord.equalsIgnoreCase("the")) && 
                        !(filteredWord.equalsIgnoreCase("a")) && !(filteredWord.equalsIgnoreCase("an")) && !(filteredWord.equalsIgnoreCase("if"))
                        && !(filteredWord.equalsIgnoreCase("when")) && !(filteredWord.equalsIgnoreCase("where")) && !(filteredWord.equalsIgnoreCase("how")) 
                        && !(filteredWord.equalsIgnoreCase("why")) && !(filteredWord.equalsIgnoreCase("me")) && !(filteredWord.equalsIgnoreCase("my")) 
                        && !(filteredWord.equalsIgnoreCase("who")) && !(filteredWord.equalsIgnoreCase("what")) && !(filteredWord.equalsIgnoreCase("but"))) {
                    if (termFrequency.containsKey(filteredWord)) {
                        int oldCount = termFrequency.get(filteredWord);
                        termFrequency.put(filteredWord, ++oldCount);
                    } else {
                        termFrequency.put(filteredWord, 1);
                    }
                }
            }
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(ShowDocument o) {
        return url.compareTo(o.url);
    }
    
    /**
     * This method will return a set of all the terms which occur in this document.
     * @return a set of all terms in this document
     */
    public Set<String> getTermList() {
        return termFrequency.keySet();
    }
    
    /**
     * This method will return the term frequency for a given word.
     * If this document doesn't contain the word, it will return 0
     * @param word The word to look for
     * @return the term frequency for this word in this document
     */
    public double getTermFrequency(String word) {
        if (termFrequency.containsKey(word)) {
            return termFrequency.get(word);
        } else {
            return 0;
        }
    }
    
}
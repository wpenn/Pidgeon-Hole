import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*This class keeps track of a document. 
 *Edited version of Swapneel's original
 *document class to account for websites
 *instead of .txt files
 *
 *author: Tirtha Kharel
 */
public class MusicDocument implements Comparable<MusicDocument> {
    
    private HashMap<String, Integer> termFrequency;
    private String url;
    
    public MusicDocument(String artistName, String songName) {
        artistName = artistName.trim().replaceAll("\\s+","").toLowerCase();
        songName = songName.trim().replaceAll("\\s+","").toLowerCase();
        if (artistName.indexOf("the") == 0) {
            artistName = artistName.substring(3).trim();
        }
        url = "https://www.azlyrics.com/lyrics/" + artistName + "/" + songName + ".html";
        termFrequency = new HashMap<String, Integer>();
        processDocument();
    }
    
    private void processDocument() {
        try {
            Document doc = Jsoup.connect(url).get();
            List<Element> l = doc.select("div");
            Element div = null;
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).className().equals("ringtone")) {
                    div = l.get(i + 1);
                    break;
                }
            }
            Scanner in = new Scanner(div.text());
            while (in.hasNext()) {
                String nextWord = in.next();
                String filteredWord = nextWord.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                if (!(filteredWord.equalsIgnoreCase("")) && !(filteredWord.equalsIgnoreCase("and"))
                        && !(filteredWord.equalsIgnoreCase("or")) && !(filteredWord.equalsIgnoreCase("the")) && 
                        !(filteredWord.equalsIgnoreCase("a")) && !(filteredWord.equalsIgnoreCase("an"))) {
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
    public int compareTo(MusicDocument o) {
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
import java.util.ArrayList;

//Gautam Narayan

//class that carries out the cosine similarity for tv shows
public class ShowMatcher {
    private ShowDocument a, b;
    private ArrayList<ShowDocument> docs;
    
    public ShowMatcher(String showNameA, String seasonA, String episodeA, String showNameB, String seasonB, String episodeB) {
        docs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            a = new ShowDocument(showNameA, seasonA, episodeA);
            b = new ShowDocument(showNameB, seasonB, episodeB);
            docs.add(a);
            docs.add(b);
        }
    }
    
    public double getSimilarity() {
        ShowCorpus c = new ShowCorpus(docs);
        ShowVectorSpaceModel vectorSpace = new ShowVectorSpaceModel(c);
        return vectorSpace.cosineSimilarity(a, b);
    }
}

import java.util.ArrayList;

//Tirtha Kharel

//class that carries out the cosine similarity for songs
public class MusicMatcher {
    private MusicDocument a, b;
    private ArrayList<MusicDocument> docs;
    
    public MusicMatcher(String artistNameA, String songNameA, 
            String artistNameB, String songNameB) {
        docs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            a = new MusicDocument(artistNameA, songNameA);
            b = new MusicDocument(artistNameB, songNameB);
            docs.add(a);
            docs.add(b);
        }
    }
    
    public double getSimilarity() {
        MusicCorpus c = new MusicCorpus(docs);
        MusicVectorSpaceModel vectorSpace = new MusicVectorSpaceModel(c);
        return vectorSpace.cosineSimilarity(a, b);
    }
    
}

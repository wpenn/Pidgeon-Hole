import java.util.List;
import java.util.ArrayList;

//main class where the server stuff will be run from
public class Test {
    
    public static void main(String[] args) {
        Profile p = new Profile();
        Profile p2 = new Profile();
        p.setName("Gautam");
        p.setArtistName("logic");
        p.setSongName("44 more");
        p.setShowName("Suits");
        p.setSeason("2");
        p.setEpisode("5");
        p2.setName("Samarth");
        p2.setArtistName("j cole");
        p2.setSongName("power trip");
        p2.setShowName("suits");
        p2.setSeason("2");
        p2.setEpisode("4");
        List<Profile> s = new ArrayList<>();
        s.add(p);
        s.add(p2);
        List<Profile> matched = Match.match(s);
        for (Profile a : matched) {
            System.out.println(a.getBestMatch().toString());
        }
    }
}

import java.util.ArrayList;
import java.util.List;

public class Match {
    
    public static List<Profile> match(List<Profile> profiles) {
        List<Profile> temp = new ArrayList<>();
        while (profiles.size() > 1) {
            Profile comparer = profiles.remove(0);
            comparer.calculateBestMatch(profiles);
            temp.add(comparer);
        }
        temp.add(profiles.get(0));
        return temp;
    }
    
}

import java.util.HashMap;
import java.util.List;

public class Profile implements Comparable<Profile>{
	private String name = "";
	private String favoriteFood = "";
	private String favoriteSongName = "";
	private String favoriteArtistName = "";
	private String showName = "";
	private String season = "";
	private String episode = "";
	private HashMap<Double, Profile> matches = new HashMap<>();
	private Profile bestMatch = null;
	private double highestSim = 0;
	
	public Profile() {}

	//Setters
	public void setName(String name) { this.name = name; }
	public void setFood(String food) { this.favoriteFood = food; }
	public void setSongName(String name) { this.favoriteSongName = name; }
	public void setArtistName(String artist) { this.favoriteArtistName = artist; }
	public void setShowName(String showName) { this.showName = showName; }
	public void setSeason(String season) { this.season = season; }
	public void setEpisode(String episode) { this.episode = episode; }
	
	//Getters
	public String getName() { return this.name; }
	public String getFood() { return this.favoriteFood; }
	public String getSongName() { return this.favoriteSongName; }
	public String getArtistName() { return this.favoriteArtistName; }
	public String getShowName() { return this.showName; }
	public String getSeason() { return this.season; }
	public String getEpisode() { return this.episode; }
	
	
	public boolean isProfileSet() {
		return !(name.isEmpty() || favoriteFood.isEmpty() || favoriteSongName.isEmpty()
				|| favoriteArtistName.isEmpty() || showName.isEmpty() || season.isEmpty()
				|| episode.isEmpty());
	}

    @Override
    public int compareTo(Profile o) {
        return this.name.compareTo(o.getName());
    }
    
    @Override
    public String toString() {
        return this.name.toUpperCase();
    }
    
    public void update(Profile p, double sim) {
        if (sim > highestSim) {
            highestSim = sim;
            bestMatch = p;
        }
    }
    
	public void calculateBestMatch(List<Profile> profileList) {
	    for (Profile p : profileList) {
	        MusicMatcher m = new MusicMatcher(this.favoriteArtistName, this.favoriteSongName, 
	                p.getArtistName(), p.getSongName());
	        ShowMatcher s = new ShowMatcher(this.showName, this.season, this.episode,
	                p.getShowName(), p.getSeason(), p.getEpisode());
	        double sim = m.getSimilarity() + s.getSimilarity();
	        p.update(this, sim);
	        if (sim > highestSim) {
	            highestSim = sim;
	            bestMatch = p;
	        }
	    }
	}
	
	public Profile getBestMatch() {
	    return bestMatch;
	}
}

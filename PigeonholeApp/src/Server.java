/*
 * Help from: 
 * https://www.geeksforgeeks.org/socket-programming-in-java/
 * https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */

// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.util.*;

import org.jsoup.select.Evaluator.Matches;

import java.net.*;

// Server class 
public class Server 
{ 
	private static ArrayList<Profile> userProfiles = new ArrayList<Profile>();
	
	public static void main(String[] args) throws IOException { 
		
		/*
		 * CHANGE MAXIMUM NUMBER OF CLIENTS HERE (IDEALLY AN EVEN NUMBER)
		 */	
		final int MAX_CLIENTS = 2;
		
		ServerSocket ss = new ServerSocket(4000);
		
		System.out.println("Server Running ...");
		while (userProfiles.size() < MAX_CLIENTS - 1) { 
			Socket s = null; 

			try{  
				s = ss.accept(); 

				System.out.println("A new client is connected : " + s); 

				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

				System.out.println("Assigning new thread for this client");

				Thread t = new ClientHandler(s, dis, dos); 

				t.start();
			} 
			catch (Exception e){
				s.close(); 
				ss.close();
				e.printStackTrace(); 
			} 
		}
		System.out.println("\n\n\n");
		System.out.println("All Clients Loaded. Waiting for Client Profiles ...");
		ArrayList<Profile> unfinishedProfiles = findUnfinishedProfiles(userProfiles);
		while (unfinishedProfiles.size() > 0) {
			unfinishedProfiles = findUnfinishedProfiles(unfinishedProfiles);
		}
		
		System.out.println("\n\nBEST MATCHES: (loading...)");
		List<Profile> matched = Match.match(userProfiles);
		for(Profile a: matched) {
			System.out.println(a.getName() + ": " + a.getBestMatch().toString());
		}
		
		try {
			System.out.println("Server Closing...");
			ss.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static ArrayList<Profile> findUnfinishedProfiles(ArrayList<Profile> oldProfiles){
		ArrayList<Profile> newUnfinished = new ArrayList<Profile>();
		for(Profile profile: oldProfiles) {
			if(!profile.isProfileSet()) {
				newUnfinished.add(profile);
			}
		}
		return newUnfinished;
	}
	
	private static class ClientHandler extends Thread {  
		final DataInputStream dis; 
		final DataOutputStream dos; 
		final Socket s; 
		Profile userProfile = new Profile();
		
		public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) { 
			this.s = s; 
			this.dis = dis; 
			this.dos = dos;
		} 

		@Override
		public void run() { 
			userProfiles.add(userProfile);
			String received;
			
			try {
				dos.writeUTF("What is your name?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setName(received);

				dos.writeUTF("What is your favorite food (or food genre)?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setFood(received);

				dos.writeUTF("What is the name of your favorite music artist?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setArtistName(received);

				dos.writeUTF("What is your favorite song by them?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setSongName(received);
				
				dos.writeUTF("What is your favorite TV Show?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setShowName(received);
				
				dos.writeUTF("What is your favorite Season from the Show?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setSeason(received);
				
				dos.writeUTF("What is your favorite Episode from that Season?");
				received = dis.readUTF();
				dos.writeUTF("Response: " + received);
				userProfile.setEpisode(received);

				System.out.println("Client " + this.s + " exiting program..."); 
				dos.writeUTF("Profile created, exiting program ...");
				
				this.dis.close(); 
				this.dos.close(); 
				this.s.close();
					
			} catch (IOException e) {}
			finally {}
		} 
	} 

} 


/*
 * Help from: 
 * https://www.geeksforgeeks.org/socket-programming-in-java/
 * https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */

// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

// Client class 
public class Client { 
	public static void main(String[] args) throws IOException { 
		try { 
			Scanner scn = new Scanner(System.in); 
			
			/*
			 * ENTER CORRECT IP ADDRESS HERE
			 */
			Socket s = new Socket("158.130.175.33", 4000);
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			while (true) { 
				System.out.println(dis.readUTF()); 
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend); 
				
				// If client sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equals("Exit")) { 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
				
				String received = dis.readUTF(); 
				System.out.println(received); 
			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close();
			s.close();
		} catch(Exception e){} 
	} 
} 
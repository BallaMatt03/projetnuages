package server;

import java.util.Scanner;

import server.webservices.nuage.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		
		server.start();
		
		System.out.println("Type a char then <return> to stop the server");	
		Scanner sc = new Scanner(System.in);
		sc.next();
		sc.close();
		
		System.out.println("Server stopped !");
	}

}

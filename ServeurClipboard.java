package com.clipboard.victor;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurClipboard {
 
	int lenBufR=0;
	int port = 0;
	ServerSocket socketEcoute;
	Socket socketConnexion = null;
	
	
	public ServeurClipboard(int port) throws IOException {
		this.port = port;
		
		socketEcoute = new ServerSocket();
		// Le serveur se declare aupres de la couche transport sur le port 5099
		socketEcoute.bind(new InetSocketAddress(this.port));
	}
	
	public void close() throws IOException {
		socketEcoute.close();
		System.out.println("Fermeture de la socket d'écoute");		
		System.out.println("Arret du serveur .");
	}
	
	public void waitClient() throws IOException {
		socketConnexion = socketEcoute.accept();
		System.out.println("Un client s'est connecté : " + socketConnexion.getInetAddress() + ":" + socketConnexion.getPort());
	}
	
	public void closeClient() throws IOException {
		// Fermeture de la socket de connexion
		System.out.println("Fermeture de la socket de connexion");
		socketConnexion.close();
	}
	
	public void sendText(String txt) throws IOException, InterruptedException{
			
		String text = txt;
		byte[] buf = new byte[64*1024];			
		OutputStream os = socketConnexion.getOutputStream();			 
		
		System.out.println("Contenu : " + text);
		buf = text.getBytes();		
		
		//envoie du contenu du clipboard
		os.write(buf, 0, buf.length);	

		
	}
}

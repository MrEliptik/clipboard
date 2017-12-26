package com.victor.clipboard_GUI;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientWrapper {

	static String str_received = null;
	
	// Pour gérer le presse papier
	Clipboard clp = null;
	ClientClipboard clientTCP = null;
	GUI_client gui = null;
	
	public ClientWrapper() {
		clp = new Clipboard();
		clientTCP = new ClientClipboard(new InetSocketAddress("127.0.0.1",5099));
		gui = new GUI_client();
	}
	
	public static void main(String[] args) {

		System.out.println("Demarrage du client ...");
		
		ClientWrapper clientWrapper = new ClientWrapper();
		try 
		{
			clientWrapper.clientTCP.connect();
			str_received = clientWrapper.clientTCP.readStream();
			clientWrapper.clientTCP.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//écriture dans le Clipboard
		clientWrapper.clp.writeContent(str_received);
		System.out.println("Contenu du Clipboard recu et importé : \n" + str_received);
		clientWrapper.gui.displayNotification("Nouveau contenu");
	}

}

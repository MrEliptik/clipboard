package com.clipboard.victor;
import java.io.IOException;

public class ServerWrapper {

	ServeurClipboard serveurTCP = null;
	Clipboard clp = null;
	
	
	public ServerWrapper(int port) throws IOException {
		serveurTCP = new ServeurClipboard(port);
		clp = new Clipboard();
	}
	
	public static void main(String[] args) {
		int port = 5099; // port could be set by user
		
		ServerWrapper serverWrapper = null;
		try {
			serverWrapper = new ServerWrapper(port);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			System.out.println("Démarrage du serveur sur port : " + port);
			
			
			while(true) {
				//Attente d'un client
				System.out.println("Attente d'un client");
				serverWrapper.serveurTCP.waitClient();
				if(serverWrapper.clp.readContent() != null) {
					serverWrapper.serveurTCP.sendText(serverWrapper.clp.readContent());	
				}
				serverWrapper.serveurTCP.closeClient();
			}			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

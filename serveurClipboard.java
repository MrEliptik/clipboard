package version1_fonctionnel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class serveurClipboard {
 
	int lenBufR=0;
	
	public static void main(String[] args) throws Exception{
		serveurClipboard serveurTCP = new serveurClipboard();
		serveurTCP.executeTexte();
		//TO DO : si fichier utilisé l'autre methode 
		//serveurTCP.execute(clp.readContent());
	
	}
	public void executeTexte() throws IOException, InterruptedException{
		System.out.println("Demarrage du serveur ...");
		
		// Le serveur se declare aupres de la couche transport sur le port 5099
		ServerSocket socketEcoute = new ServerSocket();
		socketEcoute.bind(new InetSocketAddress(5099));
		Clipboard clp = new Clipboard();
		byte[] buf = new byte[64*1024];
		
		while(true)
		{
			// Attente de la connexion d'un client
			Socket socketConnexion = socketEcoute.accept();
			System.out.println("Un client s'est connecté : " + socketConnexion.getInetAddress() + ":" + socketConnexion.getPort());
			
			OutputStream os = socketConnexion.getOutputStream();
			
			String Texte = clp.readContent();
			
			System.out.println("Contenu : " + Texte);
			buf = Texte.getBytes();
			
			//envoie du contenu du clipboard
			os.write(buf, 0, buf.length);
			
			// Fermeture de la socket de connexion
			System.out.println("Fermeture de la socket de connexion");
			socketConnexion.close();
			break;
		}		
		System.out.println("Fermeture de la scoket d'écoute");
		socketEcoute.close();
		
		System.out.println("Arret du serveur .");
	}

	public void execute(String file) throws IOException, InterruptedException{
		System.out.println("Demarrage du serveur ...");
		
		// Le serveur se declare aupres de la couche transport sur le port 54000
		ServerSocket socketEcoute = new ServerSocket();
		socketEcoute.bind(new InetSocketAddress(54000));
		
		FileInputStream fis = new FileInputStream(file);
		
		
		int len;
		byte[] buf = new byte[64*1024];
		
		while(true)
		{
			// Attente de la connexion d'un client
			Socket socketConnexion = socketEcoute.accept();
			System.out.println("Un client s'est connecté : " + socketConnexion.getInetAddress() + ":" + socketConnexion.getPort());
			
			OutputStream os = socketConnexion.getOutputStream();
			
			System.out.println("Total file size to read (in bytes) : " + fis.available());
			buf = new String(String.valueOf(fis.available()) + "!").getBytes();
			
			
			// Un client s'est connecte, le serveur lit le message envoye par le le client (sans garantie de lire tout le message)
			os.write(buf); //Envoie de la taille du fichier
			
			Thread.sleep(500);
			while((len = fis.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
			
			// Fermeture de la socket de connexion
			System.out.println("Fermeture de la socket de connexion");
			socketConnexion.close();
			if(len == -1) break; fis.close();
		}		
		System.out.println("Fermeture de la scoket d'écoute");
		socketEcoute.close();
		System.out.println("Arret du serveur .");
	}
}

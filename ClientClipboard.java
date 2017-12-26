package com.victor.clipboard_GUI;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientClipboard {

	private InputStream is;
	InetSocketAddress adr = null;
	Socket socket = null;

	// Le buffer qui permettra de lire dans la socket 
	int lenBufR = 64*1024;
	byte[] bufR = new byte[lenBufR];
	int i = 0;
	int filesize = 0;
	long startTime = 0, stopTime;
	
	
	public ClientClipboard(InetSocketAddress adr) {
		this.adr = adr;
		//Creation de la socket
		socket = new Socket();
	}
	
	public void connect() throws IOException {
		System.out.println("Connexion au serveur");
		socket.connect(adr);
	}
	
	public void close() throws IOException {
		System.out.println("Fermeture du client");
		socket.close();	
	}
	
	public String readStream() throws IOException {
		
		is = socket.getInputStream();
		
		//on sauvagarde le temps a ce moment
		startTime = System.currentTimeMillis();

		//lecture de la trame TCP
		int nbRead = is.read(bufR);
		String str = new String(bufR,0,nbRead);
		
		stopTime = System.currentTimeMillis();
		System.out.println("Tansfer success in " +(stopTime-startTime) + "ms, " + str.length() + " caracters transfered");
		return str;		
	}
	

	private void execute(String file) throws IOException, InterruptedException
	{
		FileOutputStream fos = new FileOutputStream(file);

		System.out.println("Demarrage du client ...");
		
		//Creation de la socket
		Socket socket = new Socket();
		
		// Connexion au serveur 
		System.out.println("Connexion au serveur");
		InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", 5099);
		socket.connect(adrDest);		
	
		int len;
		long nbOctets = 0, nbOctetsRecus = 0;
		int lenBufR = 64*1024;
		bufR = new byte[lenBufR];
		long startTime = 0, stopTime, loopTime;
		
		is = socket.getInputStream();
	
		String message = "";
		
		while(message.endsWith("!")==false)
		{
			int nbRead = is.read(bufR);
			String str = new String(bufR,0,nbRead);
			message = message+str;
		}
		// On enleve ensuite le ! 
		message = message.substring(0, message.length()-1);
		nbOctets = Integer.parseInt(message);
		System.out.println("File size : " + nbOctets + " octets");
		
		startTime = System.currentTimeMillis();
		System.out.println(startTime);
		loopTime = startTime;
		while((len = is.read(bufR)) != -1) {
				
			fos.write(bufR, 0, len);
			nbOctetsRecus += len;
			if((System.currentTimeMillis() - loopTime) >= 1000) {
				System.out.println((nbOctetsRecus*100L/nbOctets) + "% : "+ nbOctetsRecus + "/"+nbOctets+" octets reçus");
				loopTime = System.currentTimeMillis();
			}
				
			
		}
		stopTime = System.currentTimeMillis();
		System.out.println(stopTime);
		System.out.println("Tansfer success in " +(stopTime-startTime) + "ms, " + nbOctets + " octets transférés");
		System.out.println("Fermeture du client");
		socket.close();
		fos.close();
	}

}

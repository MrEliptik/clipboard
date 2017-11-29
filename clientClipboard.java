package ClipBoard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class clientClipboard {

	private InputStream is;

	
	// Le buffer qui permettra de lire dans la socket 
	byte[] bufR;
	
	int i = 0;
	int filesize = 0;
	
	public static void main(String[] args) throws Exception {
		clientClipboard clientTCP = new clientClipboard();
		clientTCP.execute("C:\\Users\\victo\\Videos\\out.mp4");
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

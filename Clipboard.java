package com.victor.clipboard_GUI;


import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Clipboard {
	
	public String readContent() {
		String txt = null;
		
		/** Lecture du contenu : */
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		try {
		        /** Vérification que le contenu est de type texte. */
		        if( t!=null && t.isDataFlavorSupported(DataFlavor.stringFlavor) ) {
		                txt = (String)t.getTransferData(DataFlavor.stringFlavor);
		        }
		} catch( UnsupportedFlavorException e1) {
		        e1.printStackTrace();
		} catch( IOException e2 ) {
		        e2.printStackTrace();
		} catch( IllegalStateException e) {
		        e.printStackTrace();
		        /** Le presse-papier n'est peut-être pas disponible */
		}
		
		return txt;

	}
	
	public void writeContent(String toWrite) {
		try {
	        StringSelection ss = new StringSelection(toWrite);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
		} catch( IllegalStateException e) {
	        e.printStackTrace();
	        /** Le presse-papier n'est peut-être pas disponible */
		}

	}

	public void writeContent(byte[] bufR) {
		try {
	        StringSelection ss = new StringSelection(bufR.toString());
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
		} catch( IllegalStateException e) {
	        e.printStackTrace();
	        /** Le presse-papier n'est peut-être pas disponible */
		}
		
	}
	
	
}

package com.victor.clipboard_GUI;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI_server extends JFrame{

	TrayIcon trayIcon;
	
	public GUI_server() {    
		super();
		
		/* Initializing the window */
		build();
	}

	private void build() {
		
		setContentPane(buildContentPane());
	}
	
	public void displayNotification(String mes) {
		trayIcon.displayMessage("ClipBoard", mes, TrayIcon.MessageType.INFO);
	}
	
	private JPanel buildContentPane(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		/* ########## ICON TRAY ############# */
		
	    //Check the SystemTray is supported
	    if (!SystemTray.isSupported()) {
	        System.out.println("SystemTray is not supported");
	        return null;
	    }
	    final PopupMenu popup = new PopupMenu();
	    Image img = Toolkit.getDefaultToolkit().getImage("images/clipboard.png");
	    trayIcon = new TrayIcon(img, "ClipBoard server", popup);
	    final SystemTray tray = SystemTray.getSystemTray();
	   
	    // Create a pop-up menu components
	    MenuItem aboutItem = new MenuItem("About");
	    CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
	    CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
	    Menu displayMenu = new Menu("Display");
	    MenuItem errorItem = new MenuItem("Error");
	    MenuItem warningItem = new MenuItem("Warning");
	    MenuItem infoItem = new MenuItem("Info");
	    MenuItem noneItem = new MenuItem("None");
	    MenuItem exitItem = new MenuItem("Exit");
	   
	    //Add components to pop-up menu
	    popup.add(aboutItem);
	    popup.addSeparator();
	    popup.add(cb1);
	    popup.add(cb2);
	    popup.addSeparator();
	    popup.add(displayMenu);
	    displayMenu.add(errorItem);
	    displayMenu.add(warningItem);
	    displayMenu.add(infoItem);
	    displayMenu.add(noneItem);
	    popup.add(exitItem);
	    // For windows to resize the icon and display it
	    trayIcon.setImageAutoSize(true);
	    trayIcon.setPopupMenu(popup);
	   
	    try {
	        tray.add(trayIcon);
	        trayIcon.displayMessage("ClipBoard", "Ready !", TrayIcon.MessageType.INFO);
	    } catch (AWTException e) {
	        System.out.println("TrayIcon could not be added.");
	    }
		
		return panel;
	}
	
}

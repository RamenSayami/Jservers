/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jservers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;
import com.example.ramen.menu.Model.Order;

/**
 *
 * @author shreejal
 */

class ServerThread extends Thread {

	public String ClientName;
	public SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public String username = "";
	public ObjectInputStream streamIn;
	public ObjectOutputStream streamOut;
	// public ServerFrame ui;
	public KitchenInterface ui;

	// public Logger log = new Loglogger

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;
	}

	public void send(Order msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		// ui.ta_server.append("\nServer Thread " + ID + " running.");

		while (socket.isConnected()) {
			System.out.println("ClientName: "+ClientName);
			//TODO ask client for its name
			//TODO Send out an askName.class object
			//TODO if recieved messae is not order, its gotta be askName, It should return names
			//TODO better if create enum for it? or jus simple strings?
			
			if(ClientName == null){
//				askName();
			}
			
			try {
				// streamIn =new ObjectInputStream(socket.getInputStream());
				
				Object obj = streamIn.readObject();
				Order order = (Order) obj;
//				ui.addToList(order);
					//TODO add object in ui.OrderList 
					
				ui.addToOrderList(order);
				
				
//				DefaultTableModel model = (DefaultTableModel) ui.OrderInTable
//						.getModel();
//				model.addRow(new Object[] { order.getTableNo(),
//						order.getDishName(), order.getQuantity(),
//						order.getStatusString() });
				// server.handle(ID, msg);
			} catch (Exception ioe) {
				System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				// server.remove(ID);
				ioe.printStackTrace();
				//TODO catch myName.class object as well, to find out the name of the client
				
				stop();
			}
		}
	}

	public void open() throws IOException {
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}

public class SocketServer implements Runnable {
	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 13000;
	public KitchenInterface ui;

	// public Database db;

	public SocketServer(KitchenInterface frame) {

		clients = new ServerThread[50];
		ui = frame;

		// db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			/*ui.ta_server.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());*/
			System.out.println("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
//			ui.ta_server
//					.append("Can not bind to port : " + port + "\nRetrying");
			System.out.println("Can not bind to port : " + port + "\nRetrying");
			ui.RetryStart(0);
		}
	}

	public SocketServer(KitchenInterface frame, int Port) {

		clients = new ServerThread[50];
		ui = frame;
		port = Port;
		// db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
//			ui.ta_server.append("Server startet. IP : "
//					+ InetAddress.getLocalHost() + ", Port : "
//					+ server.getLocalPort());
			System.out.println("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
//			ui.ta_server.append("\nCan not bind to port " + port + ": "
//					+ ioe.getMessage());
			System.out.println("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
//				ui.ta_server.append("\nWaiting for a client ...");
//				addThread(server.accept());
				System.out.println("\nWaiting for a client ...");
				
				addThread(server.accept());
			} catch (Exception ioe) {
//				ui.ta_server.append("\nServer error: \n");
				System.out.println("\nServer error: \n");
				ui.RetryStart(0);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private void addThread(Socket socket) {
//		ui.ta_server.append("\nClient accepted: " + socket);
		System.out.println("\nClient accepted: " + socket);
		clients[clientCount] = new ServerThread(this, socket);
		try {
			clients[clientCount].open();
			clients[clientCount].start();
			clientCount++;
		} catch (IOException ioe) {
//			ui.ta_server.append("\nError opening thread: " + ioe);
			System.out.println("\nError opening thread: " + ioe);
		}
	}

}

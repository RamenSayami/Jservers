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

import javax.swing.table.DefaultTableModel;

import Model.Order;

/**
 *
 * @author shreejal
 */

class ServerThread extends Thread {
	
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;
    public ServerFrame ui;

    public ServerThread(SocketServer _server, Socket _socket){
    	super();
        server = _server;
        socket = _socket;
        ID     = socket.getPort();
        ui = _server.ui;
    }
    
    public void send(Order msg){
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
        } 
        catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }
    
    public int getID(){  
	    return ID;
    }
   
    @SuppressWarnings("deprecation")
	public void run(){  
    	ui.ta_server.append("\nServer Thread " + ID + " running.");
        while (true){  
    	    try{  
                streamIn = new ObjectInputStream(socket.getInputStream());
                Order row = (Order) streamIn.readObject();
                
                DefaultTableModel model = (DefaultTableModel) ui.Cash_table.getModel();
                model.addRow(new Object[]{row.getTableNo(),row.getDishName(),row.getQuantity(),row.getStatusString(),row.getUnitPrice()});
    	    	//server.handle(ID, msg);
            }
            catch(Exception ioe){  
            	System.out.println(ID + " ERROR reading: " + ioe.getMessage());
               // server.remove(ID);
            	ioe.printStackTrace();
            	
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
    	if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
        if (streamOut != null) streamOut.close();
    }
}






public class SocketServer implements Runnable{
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread       thread = null;
    public int clientCount = 0, port = 13000;
    public ServerFrame ui;
   // public Database db;

    public SocketServer(ServerFrame frame){
       
        clients = new ServerThread[50];
        ui = frame;
       
        //db = new Database(ui.filePath);
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
	    ui.ta_server.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
            ui.ta_server.append("Can not bind to port : " + port + "\nRetrying"); 
            ui.RetryStart(0);
	}
    }
    
    public SocketServer(ServerFrame frame, int Port){
       
        clients = new ServerThread[50];
        ui = frame;
        port = Port;
       // db = new Database(ui.filePath);
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
	    ui.ta_server.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
            ui.ta_server.append("\nCan not bind to port " + port + ": " + ioe.getMessage()); 
	}
    }
	
    public void run(){  
	while (thread != null){  
            try{  
		ui.ta_server.append("\nWaiting for a client ..."); 
	        addThread(server.accept()); 
	    }
	    catch(Exception ioe){ 
                ui.ta_server.append("\nServer error: \n");
                ui.RetryStart(0);
	    }
        }
    }
	
    public void start(){  
    	if (thread == null){  
            thread = new Thread(this); 
	    thread.start();
	}
    }
    
    @SuppressWarnings("deprecation")
    public void stop(){  
        if (thread != null){  
            thread.stop(); 
	    thread = null;
	}
    }
    
    private void addThread(Socket socket){  
            ui.ta_server.append("\nClient accepted: " + socket);
	    clients[clientCount] = new ServerThread(this, socket);
	    try{  
	      	clients[clientCount].open(); 
	        clients[clientCount].start();  
	        clientCount++; 
	    }
	    catch(IOException ioe){  
	      	ui.ta_server.append("\nError opening thread: " + ioe); 
	    }
    }
    
}

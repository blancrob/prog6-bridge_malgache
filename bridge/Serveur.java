package bridge;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

class Recepteur extends Thread {
	Socket socket;
	/*BufferedReader entree;
	PrintStream sortie;*/
        BufferedOutputStream sortie;
        BufferedReader entree;

	Recepteur(Socket socket) {
		this.socket = socket;
		try {
			entree = new BufferedReader(new InputStreamReader
 				(socket.getInputStream()));
			this.start();
		}
		catch(IOException exc) {
			try {
				socket.close();
			}
			catch(IOException e){}
		}
	}

	public void run() {
		String texte;
		int compteur = 0;

		try {
		    while(!(texte = entree.readLine()).equals("*+*+*+*+")) {
			compteur += (new StringTokenizer(texte, " ,.;:_-+*/\\.;\"'{}()=><\t!\n")).countTokens();
		    }
		    sortie.close();
		    entree.close();
		    socket.close();
		}
		catch(IOException e) {}
	}
}

class Serveur {
    int portEcoute;
    ServerSocket server;
    Socket socket;
    private boolean isRunning = true;
        
    public Serveur(){
        portEcoute = 12345;
        Thread t = new Thread(new Runnable(){
          public void run(){
             while(isRunning == true){
                    
                try {
                    server = new ServerSocket(portEcoute);
                    socket = server.accept();
                    socket.setTcpNoDelay(true);                
                    Thread t = new Thread(new Recepteur(socket));
                } catch (IOException e) {
                   e.printStackTrace();
                }
             }
                 
             try {
                server.close();
             } catch (IOException e) {
                e.printStackTrace();
                server = null;
             }
          }
       });
       t.start();
    }
}


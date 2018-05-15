import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {
	public static void main(String[] arg) {
		int portEcouteServeur = 12345;
		BufferedReader lecteurFichier;
		BufferedReader entree ;
		PrintStream sortie;
		String ligne ;
		Socket socket;
		try {
			socket = new Socket(arg[1],portEcouteServeur);
			lecteurFichier =
				new BufferedReader(new FileReader(arg[0]));
			sortie = new PrintStream(socket.getOutputStream());
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			do {
				ligne = lecteurFichier.readLine();

 				sortie.println(ligne);
 			} while (ligne != null);
			sortie.println("*+*+*+*+");
			System.out.println(entree.readLine());
			sortie.close();
			entree.close();
			socket.close();
		}
		catch(FileNotFoundException exc) {
			System.out.println("Fichier introuvable");
		}
		catch(UnknownHostException exc) {
				System.out.println("destinataire inconnu");
		}
		catch(IOException exc) {
				System.out.println("probleme d'entree-sortie");
		}
	}
}
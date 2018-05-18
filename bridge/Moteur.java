/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Moteur {
    Configuration config;
    Joueur j1, j2;
    
    int joueur1donneur, joueur2donneur;
    
    public Moteur(){
        
    }
    
    /**
     * Initialise le début de jeu
     */
    public void initialiser(){
        
        config = new Configuration();
        
        j1 = new Joueur();
        j2 = new Joueur();
        
        joueur1donneur=0;
        joueur2donneur=0;
        
        config.conditionVictoire=0;
        config.mancheMax=0;
        config.scoreMax=0;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Choix de la condition de victoire:\n1: Nombre de Manches || 2: Score");
        String str = sc.nextLine();
        config.conditionVictoire = Integer.parseInt(str);
        
        if(config.conditionVictoire==1){
            System.out.println("Entrez le nombre de manches:");
            str = sc.nextLine();
            config.mancheMax = Integer.parseInt(str);
        }else{
            System.out.println("Entrez le score maximum:");
            str = sc.nextLine();
            config.scoreMax = Integer.parseInt(str);
        }
        
        System.out.println("1: Joueur Contre Joueur || 2: Joueur Contre Ordinateur || 3: Ordinateur Contre Ordinateur");
        str = sc.nextLine();
        config.mode = Integer.parseInt(str);
        
        if(config.mode == 2){System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
        }else if(config.mode == 3){
            System.out.println("Difficulté Joueur 1:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j1.difficulte = Integer.parseInt(str);
            
            System.out.println("Difficulté Joueur 2:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
            
        }
    }
    
    /**
     * Initialise le début de la manche, distribue les cartes aux joueurs
     */
    public void initialiserManche(){
        j1.tas = new PileCartes();
        j2.tas = new PileCartes();
        distribuer();
        config.taille=11;
        config.set_atout();
    }
    
    /**
     * Sauvegarder la configuration courante dans un fichier dont le nom est demandé 
     * @throws IOException 
     */
    public void sauvegarder() throws IOException{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Entrez le nom du fichier de sauvegarde:");
        String str = sc.nextLine();
        File fichier =  new File(str) ;

        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
       
        oos.writeObject(config);
        oos.writeObject(j1);
        oos.writeObject(j2);
    }
    
    /**
     * Charger la configuration contenue dans un fichier dont le nom est demandé
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void charger() throws IOException, ClassNotFoundException{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Entrez le nom du fichier à charger:");
        String str = sc.nextLine();
        File fichier =  new File(str) ;

        // ouverture d'un flux sur un fichier
       ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;

        // désérialization de l'objet
       config = (Configuration)ois.readObject() ;
       j1 = (Joueur)ois.readObject() ;
       j2 = (Joueur)ois.readObject() ;
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public void jouerCoupPremier(){
        Iterator<Carte> it;
        Carte[] main = new Carte[20];
        
        if(config.donneur==1){
            it = j1.main.iterateur();
        }
        else{
            it = j2.main.iterateur();
        }

        int i=0;
        do{
           main[i]=it.next();
           i++;
        }while(i<20 && it.hasNext());

        if(config.donneur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        
        if(config.mode == 1 || (config.mode == 2 && config.donneur==1)){    //Cas où le joueur est un humain
            

            System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            while(choix<0){
                if(choix==-1){  //Choix Sauvegarder
                    try {
                        sauvegarder();
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-2){    //Choix Charger
                   
                    try {
                        charger();
                        main = new Carte[20];
                        if(config.donneur==1){
                            it = j1.main.iterateur();
                        }
                        else{
                            it = j2.main.iterateur();
                        }
                        i=0;
                        do{
                           main[i]=it.next();
                           i++;
                        }while(i<20 && it.hasNext());

                        if(config.donneur==1){
                            System.out.println("Main Joueur 1");
                        }
                        else{
                            System.out.println("Main Joueur 2");
                        }
                        for(i=0; i<20 && main[i]!=null; i++){
                            System.out.print("["+i+"]:");
                            afficherCarte(main[i]);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger:");
                sc = new Scanner(System.in);
                str = sc.nextLine();
                choix = Integer.parseInt(str);
            }

            Carte c = main[choix];
            if (config.donneur==1){ //On enlève la carte choisie de la main du donneur
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteP = c;
            
            if(config.joueur == 1){
                config.joueur = 2;
            }else{
                config.joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int[] nbCartes = new int[6];
            int lg = 0;
            for(i=1; i<6; i++){
                if(config.pioche[i].premiere()!=null){
                    piocheIA[lg]=config.pioche[i].premiere();
                    nbCartes[lg]=config.pioche[i].taille();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia = null;
            if(config.donneur==1){
                difficulte = j1.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 2:
                        ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 3:
                        ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 4:
                        ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j1.score, j2.score);
                        break;
                    case 5:
                        ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j1.score, j2.score);
                        break;
                    case 6:
                        ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j2.main, null);
                        break;
                }
            }else{
                difficulte = j2.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 2:
                        ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 3:
                        ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 4:
                        ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j2.score, j1.score);
                        break;
                    case 5:
                        ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j2.score, j1.score);
                        break;
                    case 6:
                        ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j1.main, null);
                        break;
                }
            }
            
            c = ia.jouer();
  
            if (config.donneur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteP = c;

            System.out.println("Le joueur " + config.donneur + " a joué:");
            afficherCarte(c);
            
        }
    }
    
    /**
     * Le joueur qui n'a pas le statut de donneur ou qui a perdu joue en deuxième
     */
    public void jouerCoupSecond(){
        Iterator<Carte> it;
        PileCartes mainjoueur;
        Carte[] main = new Carte[20];
        
        if(config.receveur==1){
            mainjoueur= j1.main;
        }
        else{
            mainjoueur = j2.main;
        }
        it = mainjoueur.iterateur();

        int i=0;
        do{
           main[i]=it.next();
           i++;
        }while(it.hasNext());

        if(config.receveur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        
        if(config.mode == 1 || (config.mode == 2 && config.donneur==2)){
            switch (config.carteP.couleur){
                case 1:
                    System.out.println("Fournir du TREFLE si vous en avez");
                    break;
                case 2:
                    System.out.println("Fournir du CARREAU si vous en avez");
                    break;
                case 3:
                    System.out.println("Fournir du COEUR si vous en avez");
                    break;
                case 4:
                    System.out.println("Fournir du PIQUE si vous en avez");
                    break;
            }
            

            boolean condition = false;
            System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger:");   //Choix entre jouer une carte et sauvegarder ou charger
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            while(choix<0){
                if(choix==-1){  //Choix Sauvegarder
                    try {
                        sauvegarder();
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-2){    //Choix Charger
                   
                    try {
                        charger();
                        main = new Carte[20];
                        if(config.receveur==1){
                            it = j1.main.iterateur();
                        }
                        else{
                            it = j2.main.iterateur();
                        }
                        i=0;
                        do{
                           main[i]=it.next();
                           i++;
                        }while(i<20 && it.hasNext());

                        if(config.receveur==1){
                            System.out.println("Main Joueur 1");
                        }
                        else{
                            System.out.println("Main Joueur 2");
                        }
                        for(i=0; i<20 && main[i]!=null; i++){
                            System.out.print("["+i+"]:");
                            afficherCarte(main[i]);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger:");
                sc = new Scanner(System.in);
                str = sc.nextLine();
                choix = Integer.parseInt(str);
            }

            if(config.receveur == 1){
                if (main[choix].couleur != config.carteP.couleur && j1.main.contient(config.carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != config.carteP.couleur && j1.main.contient(config.carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }else{
                if (main[choix].couleur != config.carteP.couleur && j2.main.contient(config.carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != config.carteP.couleur && j2.main.contient(config.carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }


            Carte c = main[choix];
            if(config.receveur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteS = c;

            if(config.joueur == 1){
                config.joueur = 2;
            }else{
                config.joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int[] nbCartes = new int[6];
            int lg = 0;
            for(i=1; i<6; i++){
                if(config.pioche[i].premiere()!=null){
                    piocheIA[lg]=config.pioche[i].premiere();
                    nbCartes[lg]=config.pioche[i].taille();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia = null;
            if(config.receveur==1){
                difficulte = j1.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 2:
                        ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 3:
                        ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 4:
                        ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score);
                        break;
                    case 5:
                        ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score);
                        break;
                    case 6:
                        ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.main, null);
                        break;
                }
            }else{
                difficulte = j2.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 2:
                        ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 3:
                        ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                        break;
                    case 4:
                        ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score);
                        break;
                    case 5:
                        ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score);
                        break;
                    case 6:
                        ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.main, null);
                        break;
                }
            }
            
            c = ia.jouer();
            
            if (config.receveur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteS = c;

            System.out.println("Le joueur " + config.receveur + " a joué:");
            afficherCarte(c);
            
        }
    }
    
    public Carte jouerCoupIA(int joueur){
        Carte c = null;
        Carte[] piocheIA = new Carte[6];
        int[] nbCartes = new int[6];
        int lg = 0;
        for(int i=1; i<6; i++){
            if(config.pioche[i].premiere()!=null){
                piocheIA[lg]=config.pioche[i].premiere();
                nbCartes[lg]=config.pioche[i].taille();
                lg++;
            }
        }

        PileCartes cartesDejaJouees = new PileCartes();
        cartesDejaJouees.pile.addAll(j1.tas.pile);
        cartesDejaJouees.pile.addAll(j2.tas.pile);

        int difficulte;
        IA ia = null;
        
        if(joueur==1){
            difficulte = j1.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 2:
                    ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 3:
                    ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 4:
                    ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score);
                    break;
                case 5:
                    ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score);
                    break;
                case 6:
                    ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.main, null);
                    break;
            }
        }else{
            difficulte = j2.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 2:
                    ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 3:
                    ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 4:
                    ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score);
                    break;
                case 5:
                    ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score);
                    break;
                case 6:
                    ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.main, null);
                    break;
            }
        }

        c = ia.jouer();

        if (joueur==1){
            j1.main.retirer(c);
        }else{
            j2.main.retirer(c);
        }

        if(config.carteP==null){
            config.carteP=c;
        }else{
            config.carteS=c;
        }
        
        return c;
    }
    
    /**
     * Enlève les deux cartes jouées de la table pour les ranger dans le tas du gagnant
     */
    public void rangerPli(){
        config.piochees.retirer(config.carteP);
        config.piochees.retirer(config.carteS);
        if(config.gagnant==1){
            j1.tas.ajouter(config.carteP);
            j1.tas.ajouter(config.carteS);
            config.carteP=null;
            config.carteS=null;
        }else{
            j2.tas.ajouter(config.carteP);
            j2.tas.ajouter(config.carteS);
            config.carteP=null;
            config.carteS=null;
        }
    }
    
    /**
     * Permet au joueur désigné en paramètre de piocher
     * @param piocheur Le numéro du joueur qui va piocher
     */
    public void pioche(int piocheur){
        Carte c=null;
        
        if(config.mode == 1 || (config.mode == 2 && piocheur==1)){ //Si l'on est en mode joueur contre joueur ou alors que c'est le tour du joueur 1
        
            System.out.println("Joueur "+ piocheur +", choisissez une carte:");

            Scanner sc = new Scanner(System.in);    //on lit le choix du joueur
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            
            for(int i=0; i<6; i++){ //On ajoute à la main du joueur la carte choisie
                if(choix==i+1){
                    if(piocheur == 1){
                        c = config.pioche[i].retirer();
                        j1.main.ajouter(c);
                    }else{
                        j2.main.ajouter(c);
                    }
                }
            }
            
        }else{  //Si c'est à l'ordinateur de jouer
            
            c = null;
            Carte[] piocheIA = new Carte[6];
            int[] nbCartes = new int[6];
            int lg = 0;
            for(int i=0; i<6; i++){
                if(config.pioche[i].premiere()!=null){
                    piocheIA[lg]=config.pioche[i].premiere();
                    nbCartes[lg]=config.pioche[i].taille();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia = null;
            if(piocheur==1){
                difficulte = j1.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 2:
                        ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 3:
                        ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 4:
                        if(config.gagnant==piocheur){
                            ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, j1.score, j2.score);
                        }else{
                            ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j1.score, j2.score);
                        }
                        break;
                    case 5:
                        if(config.gagnant==piocheur){
                            ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, j1.score, j2.score);
                        }else{
                            ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j1.score, j2.score);
                        }
                        break;
                    case 6:
                        if(config.gagnant==piocheur){
                            ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, null, config.pioche);
                        }else{
                            ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, null, config.pioche);
                        }
                        break;
                }
            }else{
                difficulte = j2.difficulte;
                switch(difficulte){
                    case 1:
                        ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 2:
                        ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 3:
                        ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                        break;
                    case 4:
                        if(config.gagnant==piocheur){
                            ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, j2.score, j1.score);
                        }else{
                            ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j2.score, j1.score);
                        }
                        break;
                    case 5:
                        if(config.gagnant==piocheur){
                            ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, j2.score, j1.score);
                        }else{
                            ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, j2.score, j1.score);
                        }
                        break;
                    case 6:
                        if(config.gagnant==piocheur){
                            ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, true, null, config.pioche);
                        }else{
                            ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, false, null, config.pioche);
                        }
                        break;
                }
            }
            
            c = ia.piocher();
            
            for(int i=0; i<6; i++){
                if(c == config.pioche[i].premiere()){
                    if (piocheur==1){   
                        j1.main.ajouter(config.pioche[i].retirer());
                    }else{
                        j2.main.ajouter(config.pioche[i].retirer());
                    }
                }
            }

            System.out.println("Le joueur " + piocheur + " a pioché:");
            afficherCarte(c);
        }
        
        config.piochees.ajouter(c);
    }
    
    /**
     * Affiche la carte passée en paramètre et va à la ligne
     * @param c La carte à afficher
     */
    public void afficherCarte(Carte c){
        switch(c.couleur){
                case 1:
                    System.out.print("TREFLE");
                    break;
                case 2:
                    System.out.print("CARREAU");
                    break;
                case 3:
                    System.out.print("COEUR");
                    break;
                case 4:
                    System.out.print("PIQUE");
                    break;
            }
        System.out.print("|");
        switch(c.valeur){
            case 11:
                System.out.println("VALET");
                break;
            case 12:
                System.out.println("DAME");
                break;
            case 13:
                System.out.println("ROI");
                break;
            case 14:
                System.out.println("AS");
                break;
            default:
                System.out.println(c.valeur);
        }
    }
    
    /**
     * Crée un paquet de 52 cartes de base et les distribue dans les piles et aux joueurs
     */
    public void distribuer(){
        PileCartes paquet = new PileCartes();
        paquet.paquet();
        
        for(int i=0;i<5;i++){
            config.pile1.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile2.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile3.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile4.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile5.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile6.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<11;i++){
            j1.main.ajouter(paquet.aleatoire(false));
        }
        for(int i=0;i<11;i++){
            j2.main.ajouter(paquet.aleatoire(false));
        }
    }
    
    public boolean finManche(){
        return config.taille==0;
    }
    
    public boolean finPartie(){
        return ((config.conditionVictoire==1 && config.manche>=config.mancheMax) || (config.conditionVictoire==2 && (j1.scoreTotal>=config.scoreMax || j2.scoreTotal>=config.scoreMax)));
    }
    
    public void moteur() throws ClassNotFoundException{
        
        initialiser();
        
        while((config.conditionVictoire==1 && config.manche<config.mancheMax) || (config.conditionVictoire==2 && (j1.scoreTotal<config.scoreMax && j2.scoreTotal<config.scoreMax))){
        
            
            if(config.donneurInitial==0){
                config.donneurInitial=1;
                config.donneur=1;
            }else if(config.donneurInitial==1){
                config.donneurInitial=2;
                config.donneur=2;
            }else{
               config.donneurInitial=1;
               config.donneur=1; 
            }
            
            config.manche++;
            initialiserManche();
            
            System.out.println("MANCHE "+ config.manche);
            System.out.println();

            switch(config.atout){
                case 0:
                    System.out.println("SANS ATOUT");
                    break;
                case 1:
                    System.out.println("Atout: TREFLE");
                    break;
                case 2:
                    System.out.println("Atout: CARREAU");
                    break;
                case 3:
                    System.out.println("Atout: COEUR");
                    break;
                case 4:
                    System.out.println("Atout: PIQUE");
                    break;
            }
            System.out.println();

            while(config.taille>0){
                if(config.piochable()){
                    config.afficherPioche();
                }
                System.out.println("Tour joueur "+ config.donneur);
                System.out.println();
                jouerCoupPremier();
                System.out.println("Tour Joueur "+ config.receveur);
                System.out.println();
                jouerCoupSecond();
                config.taille--;
                config.gagnantPli();
                System.out.println("Le joueur "+ config.gagnant +" gagne le pli");
                System.out.println();
                rangerPli();
                if(config.piochable()){
                    config.afficherPioche();
                    pioche(config.gagnant);
                    System.out.println();
                    config.afficherPioche();
                    pioche(config.perdant);
                    System.out.println();
                    config.taille++;
                }
                config.donneur = config.gagnant;
                if(config.donneur==1){
                    config.receveur = 2;
                }else{
                    config.receveur = 1;
                }
                System.out.println("-----------------------------------------");
                System.out.println("-----------------------------------------");
                System.out.println("-----------------------------------------");
            }
            System.out.println();
            if(j1.tas.taille()>j2.tas.taille()){
                System.out.println("LE JOUEUR 1 GAGNE");
                if(config.donneurInitial==1){
                    joueur1donneur++;
                }
            }else if(j1.tas.taille()<j2.tas.taille()){
                System.out.println("LE JOUEUR 2 GAGNE");
                if(config.donneurInitial==2){
                    joueur2donneur++;
                }
            }
            else{
                System.out.println("EGALITE");
            }
            System.out.println();

            j1.score=j1.tas.taille()/2;
            j2.score=j2.tas.taille()/2;
            System.out.println("Manche: "+ config.manche);
            System.out.println("Score joueur 1: "+ (j1.score));
            System.out.println("Score joueur 2: "+ (j2.score));
            System.out.println();
            System.out.println("Tas joueur 1:");
            Iterator<Carte> it = j1.tas.iterateur();
            while(it.hasNext()){
                afficherCarte(it.next());
            }

            System.out.println();
            System.out.println("Tas joueur 2:");
            it = j2.tas.iterateur();
            while(it.hasNext()){
                afficherCarte(it.next());
            }
            
            j1.scoreTotal=j1.scoreTotal+j1.score;
            j2.scoreTotal=j2.scoreTotal+j2.score;
        }
        
        System.out.println();
        System.out.println("Score Total joueur 1: "+ (j1.scoreTotal));
        System.out.println("Score Total joueur 2: "+ (j2.scoreTotal));
        if(j1.scoreTotal>j2.scoreTotal){
            System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
        }else if(j1.scoreTotal<j2.scoreTotal){
            System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
        }
        else{
            System.out.println("EGALITE");
        }
        System.out.println("Nombre de victoires du joueur 1 en commençant la manche donneur: " + joueur1donneur);
        System.out.println("Nombre de victoires du joueur 2 en commençant la manche donneur: " + joueur2donneur);
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        Moteur m = new Moteur();
        m.moteur();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;
import java.util.Iterator;
import java.util.Scanner;

public class Moteur2 {
    static Configuration config;
    static Joueur j1, j2;
    
    public Moteur2(){
        config = new Configuration();
        
        j1 = new Joueur();
        j2 = new Joueur();
    }
    
    /**
     * Initialise le début de jeu
     */
    public void initialiser(){
        
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
        
        if(config.mode == 2){System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
        }else if(config.mode == 3){
            System.out.println("Difficulté Joueur 1:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j1.difficulte = Integer.parseInt(str);
            
            System.out.println("Difficulté Joueur 2:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
            
        }
    }
    
    /**
     * Initialise le début de la manche
     */
    public void initialiserManche(){
        j1.tas = new PileCartes();
        j2.tas = new PileCartes();
        distribuer();
        config.taille=11;
        config.set_atout();
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public Carte jouerCoupPremier(Carte card){   //Carte card en +
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
        
            
        //System.out.println("Donnez le numéro de la carte à jouer:");
        //Scanner sc = new Scanner(System.in);
        //String str = sc.nextLine();
        //int choix = Integer.parseInt(str);

        //Carte c = main[choix];

        //
        Carte c;
        c = card;
        //

        if (config.donneur==1){
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
        return c;
    }
    
    /**
     * Le joueur qui n'a pas le statut de donneur ou qui a perdu joue en deuxième
     */
    public Carte jouerCoupSecond(Carte card){
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

        //boolean condition = false;

        //System.out.println("Donnez le numéro de la carte à jouer:");
        //Scanner sc = new Scanner(System.in);
        //String str = sc.nextLine();
        //int choix = Integer.parseInt(str);

        //Carte c = main[choix];

        //
        Carte c;
        c = card;
        //

/*  //A revoir et modifier correctement
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
*/           
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
        return card;
    }
    
    /**
     * Enlève les deux cartes jouées de la table pour les ranger dans le tas du gagnant
     */
    public void rangerPli(){
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
    public void pioche(int piocheur, Carte card, int nbPile){
        //System.out.println("Joueur "+ piocheur +", choisissez une carte:");
        //Scanner sc = new Scanner(System.in);    //on lit le choix du joueur
        //String str = sc.nextLine();
        //int choix = Integer.parseInt(str);

        /*
        for(int i=0; i<6; i++){ //On ajoute à la main du joueur la carte choisie
            if(choix==i+1){
                if(piocheur == 1){
                    j1.main.ajouter(config.pioche[i].retirer());
                }else{
                    j2.main.ajouter(config.pioche[i].retirer());
                }
            }
        }
        */

        //
        if(piocheur == 1){
            j1.main.ajouter(config.pioche[nbPile].retirer());
        }else{
            j2.main.ajouter(config.pioche[nbPile].retirer());
        }
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
    
    /*public static void moteur(){
        
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
                
                //
                Carte c = new Carte();
                System.out.println("Carte jouee");
                afficherCarte(jouerCoupPremier(c));
                //
                
                System.out.println("Tour Joueur "+ config.receveur);
                System.out.println();
                
                //
                System.out.println("Carte jouee");
                afficherCarte(jouerCoupSecond(c));
                //
                
                config.taille--;
                config.gagnantPli();
                System.out.println("Le joueur "+ config.gagnant +" gagne le pli");
                System.out.println();
                rangerPli();
                if(config.piochable()){
                    config.afficherPioche();
                    //
                    pioche(config.gagnant,c,1);
                    //
                    System.out.println();
                    config.afficherPioche();
                    //
                    pioche(config.perdant,c,1);
                    //
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
            }else if(j1.tas.taille()<j2.tas.taille()){
                System.out.println("LE JOUEUR 2 GAGNE");
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
    }
    
    public static void main(String[] args) {
        moteur();
    }*/
}

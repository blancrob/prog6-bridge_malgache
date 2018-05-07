/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;
import java.util.Iterator;
import java.util.Scanner;

public class Moteur {
    static PileCartes pile1, pile2, pile3, pile4, pile5, pile6;
    static PileCartes[] pioche;
    static Joueur j1, j2;
    static Carte carteP, carteS;
    static int atout, joueur, donneur, receveur, scorej1, scorej2, manche, gagnant, perdant, taille, mode;
    
    /**
     * Initialise le début de jeu
     */
    public static void initialiser(){
        
        pile1 = new PileCartes();
        pile2 = new PileCartes();
        pile3 = new PileCartes();
        pile4 = new PileCartes();
        pile5 = new PileCartes();
        pile6 = new PileCartes();
        
        pioche = new PileCartes[6];
        pioche[0] = pile1;
        pioche[1] = pile2;
        pioche[2] = pile3;
        pioche[3] = pile4;
        pioche[4] = pile5;
        pioche[5] = pile6;
        
        j1 = new Joueur();
        j2 = new Joueur();
        
        distribuer();
        set_atout();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("1: Joueur Contre Joueur || 2: Joueur Contre Ordinateur || 3: Ordinateur Contre Ordinateur");
        String str = sc.nextLine();
        mode = Integer.parseInt(str);
        
        if(mode == 2){System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
        }else if(mode == 3){
            System.out.println("Difficulté Joueur 1:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j1.difficulte = Integer.parseInt(str);
            
            System.out.println("Difficulté Joueur 2:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
            
        }
        
        joueur=1;
        donneur=1;
        receveur=2;
        gagnant=0;
        perdant=0;
        taille=11;
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public static void jouerCoupPremier(){
        Iterator<Carte> it;
        Carte[] main = new Carte[20];
        
        if(donneur==1){
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

        if(donneur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        
        if(mode == 1 || (mode == 2 && donneur==1)){
            

            System.out.println("Donnez le numéro de la carte à jouer:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);

            Carte c = main[choix];
            if (donneur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            carteP = c;
            
            if(joueur == 1){
                joueur = 2;
            }else{
                joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int lg = 0;
            for(i=1; i<6; i++){
                if(pioche[i].premiere()!=null){
                    piocheIA[lg]=pioche[i].premiere();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia;
            if(donneur==1){
                ia = new IA(j1.main, cartesDejaJouees, piocheIA, lg, atout);
                difficulte = j1.difficulte;
            }else{
                ia = new IA(j2.main, cartesDejaJouees, piocheIA, lg, atout);
                difficulte = j2.difficulte;
            }
            
            switch(difficulte){
                case 1:
                    c = ia.iaNovice();
                    break;
                case 2:
                    c = ia.iaFacile();
                    break;
                case 3:
                    c = ia.iaMoyenne();
                    break;
                case 4:
                    c = ia.iaDifficile();
                    break;
            }
            if (donneur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            carteP = c;

            System.out.println("Le joueur " + donneur + " a joué:");
            afficherCarte(c);
            
        }
    }
    
    /**
     * Le joueur qui n'a pas le statut de donneur ou qui a perdu joue en deuxième
     */
    public static void jouerCoupSecond(){
        Iterator<Carte> it;
        PileCartes mainjoueur;
        Carte[] main = new Carte[20];
        
        if(receveur==1){
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

        if(receveur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        
        if(mode == 1 || (mode == 2 && donneur==2)){
            switch (carteP.couleur){
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
            System.out.println("Donnez le numéro de la carte à jouer:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);

            if(receveur == 1){
                if (main[choix].couleur != carteP.couleur && j1.main.contient(carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != carteP.couleur && j1.main.contient(carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }else{
                if (main[choix].couleur != carteP.couleur && j2.main.contient(carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != carteP.couleur && j2.main.contient(carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }


            Carte c = main[choix];
            if(receveur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            carteS = c;

            if(joueur == 1){
                joueur = 2;
            }else{
                joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int lg = 0;
            for(i=1; i<6; i++){
                if(pioche[i].premiere()!=null){
                    piocheIA[lg]=pioche[i].premiere();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia;
            if(receveur==1){
                ia = new IA(j1.main, cartesDejaJouees, piocheIA, lg, atout, carteP);
                difficulte = j1.difficulte;
            }else{
                ia = new IA(j2.main, cartesDejaJouees, piocheIA, lg, atout, carteP);
                difficulte = j2.difficulte;
            }
            
            switch(difficulte){
                case 1:
                    c = ia.iaNovice();
                    break;
                case 2:
                    c = ia.iaFacile();
                    break;
                case 3:
                    c = ia.iaMoyenne();
                    break;
                case 4:
                    c = ia.iaDifficile();
                    break;
            }
            if (receveur==1){
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            carteS = c;

            System.out.println("Le joueur " + receveur + " a joué:");
            afficherCarte(c);
            
        }
    }
    
    /**
     * Met à jour la variable gagnant, qui indique qui est le gagnant du dernier pli joué
     */
    public static void gagnantPli(){
        if(carteP.couleur == carteS.couleur){
            if(carteP.valeur>carteS.valeur){
                gagnant = donneur;
                perdant = receveur;
            }
            else{
                gagnant = receveur;
                perdant = donneur;
            }
        }
        else if(carteS.couleur==atout){
            gagnant = receveur;
            perdant = donneur;
        }
        else{
            gagnant = donneur;
            perdant = receveur;
        }
    }
    
    /**
     * Enlève les deux cartes jouées de la table pour les ranger dans le tas du gagnant
     */
    public static void rangerPli(){
        if(gagnant==1){
            j1.tas.ajouter(carteP);
            j1.tas.ajouter(carteS);
            carteP=null;
            carteS=null;
        }else{
            j2.tas.ajouter(carteP);
            j2.tas.ajouter(carteS);
            carteP=null;
            carteS=null;
        }
    }
    
    /**
     * Vérifie si une pioche n'est pas vide
     * @return true si il est encore possible de piocher, false sinon
     */
    public static boolean piochable(){
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Affiche le contenu de la pioche
     */
    public static void afficherPioche(){
        System.out.println("Pioche:");
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                System.out.print(pioche[i].taille() +"||");
                System.out.print("["+(i+1)+"]:");
            afficherCarte(pioche[i].premiere());
            }
        }
        System.out.println();
    }
    
    /**
     * Permet au joueur désigné en paramètre de piocher
     * @param piocheur Le numéro du joueur qui va piocher
     */
    public static void pioche(int piocheur){
        if(mode == 1 || (mode == 2 && piocheur==1)){ //Si l'on est en mode joueur contre joueur ou alors que c'est le tour du joueur 1
        
            System.out.println("Joueur "+ piocheur +", choisissez une carte:");

            Scanner sc = new Scanner(System.in);    //on lit le choix du joueur
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            
            for(int i=0; i<6; i++){ //On ajoute à la main du joueur la carte choisie
                if(choix==i+1){
                    if(piocheur == 1){
                        j1.main.ajouter(pioche[i].retirer());
                    }else{
                        j2.main.ajouter(pioche[i].retirer());
                    }
                }
            }
            
        }else{  //Si c'est à l'ordinateur de jouer
            
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int[] nbCartes = new int[6];
            int lg = 0;
            for(int i=0; i<6; i++){
                if(pioche[i].premiere()!=null){
                    piocheIA[lg]=pioche[i].premiere();
                    nbCartes[lg]=pioche[i].taille();
                    lg++;
                }
            }
            
            PileCartes cartesDejaJouees = new PileCartes();
            cartesDejaJouees.pile.addAll(j1.tas.pile);
            cartesDejaJouees.pile.addAll(j2.tas.pile);
            
            int difficulte;
            IA ia;
            if(piocheur==1){
                ia = new IA(j1.main, cartesDejaJouees, piocheIA, lg, atout);
                difficulte = j1.difficulte;
            }else{
                ia = new IA(j2.main, cartesDejaJouees, piocheIA, lg, atout);
                difficulte = j2.difficulte;
            }
            
            switch(difficulte){
                case 1:
                    c = ia.piocheNoviceEtFacile();
                    break;
                case 2:
                    c = ia.piocheNoviceEtFacile();
                    break;
                case 3:
                    c = ia.piocheMoyenne();
                    break;
                case 4:
                    if(gagnant==piocheur){
                        c = ia.piocheDifficile(true,nbCartes);
                    }else{
                        c = ia.piocheDifficile(false,nbCartes);
                    }
                    break;
                
            }
            for(int i=0; i<6; i++){
                if(c == pioche[i].premiere()){
                    if (piocheur==1){   
                        j1.main.ajouter(pioche[i].retirer());
                    }else{
                        j2.main.ajouter(pioche[i].retirer());
                    }
                }
            }

            System.out.println("Le joueur " + piocheur + " a pioché:");
            afficherCarte(c);
        }
    }
    
    /**
     * Affiche la carte passée en paramètre et va à la ligne
     * @param c La carte à afficher
     */
    public static void afficherCarte(Carte c){
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
     * Regarde la première carte de chaque pile pour définir l'atout
     */    
    public static void set_atout(){
        Carte res = pile1.premiere();
        
        for(int i=1; i<6; i++){
            if(res.valeur < pioche[i].premiere().valeur){   //si la carte courante a une plus grande valeur que la carte résultat, elle deviens la carte résultat
                res = pioche[i].premiere();
            }
            else if(res.valeur == pioche[i].premiere().valeur){ //si les valeurs des cartes sont égales, si la couleur de la carte courante est plus forte, elle deviens la carte résultat
                if(res.couleur < pioche[i].premiere().valeur){
                    res = pioche[i].premiere();
                }
            }
        }
        
       atout = res.couleur;
       
    }
    
    /**
     * Crée un paquet de 52 cartes de base et les distribue dans les piles et aux joueurs
     */
    public static void distribuer(){
        PileCartes paquet = new PileCartes();
        paquet.paquet();
        
        for(int i=0;i<5;i++){
            pile1.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            pile2.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            pile3.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            pile4.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            pile5.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            pile6.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<11;i++){
            j1.main.ajouter(paquet.aleatoire(false));
        }
        for(int i=0;i<11;i++){
            j2.main.ajouter(paquet.aleatoire(false));
        }
    }
    
    public static void moteur(){
        initialiser();
        
        switch(atout){
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
        
        while(taille>0){
            if(piochable()){
                afficherPioche();
            }
            System.out.println("Tour joueur "+ donneur);
            System.out.println();
            jouerCoupPremier();
            System.out.println("Tour Joueur "+ receveur);
            System.out.println();
            jouerCoupSecond();
            taille--;
            gagnantPli();
            System.out.println("Le joueur "+ gagnant +" gagne le pli");
            System.out.println();
            rangerPli();
            if(piochable()){
                afficherPioche();
                pioche(gagnant);
                System.out.println();
                afficherPioche();
                pioche(perdant);
                System.out.println();
                taille++;
            }
            donneur = gagnant;
            if(donneur==1){
                receveur = 2;
            }else{
                receveur = 1;
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
        
        System.out.println("Score joueur 1: "+ (j1.tas.taille()/2));
        System.out.println("Score joueur 2: "+ (j2.tas.taille()/2));
        System.out.println();
        System.out.println("Tas joueur 1:");
        Iterator<Carte> it = j1.tas.iterateur();
        do{
            afficherCarte(it.next());
        }while(it.hasNext());
        
        System.out.println();
        System.out.println("Tas joueur 2:");
        it = j2.tas.iterateur();
        do{
            afficherCarte(it.next());
        }while(it.hasNext());
    }
    
    public static void main(String[] args) {
        moteur();
    }
}

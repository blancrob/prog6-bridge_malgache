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
    static PileCartes pile1, pile2, pile3, pile4, pile5, pile6, mainj1, mainj2, tasj1, tasj2;
    static PileCartes[] pioche;
    static Carte carteP, carteS;
    static int atout, joueur, donneur, receveur, scorej1, scorej2, manche, gagnant, taille, mode, difficulte;
    
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
        mainj1 = new PileCartes();
        mainj2 = new PileCartes();
        tasj1 = new PileCartes();
        tasj2 = new PileCartes();
        
        pioche = new PileCartes[6];
        pioche[0] = pile1;
        pioche[1] = pile2;
        pioche[2] = pile3;
        pioche[3] = pile4;
        pioche[4] = pile5;
        pioche[5] = pile6;
        
        distribuer();
        set_atout();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("1: Joueur Contre Joueur || 2: Joueur Contre Ordinateur");
        String str = sc.nextLine();
        mode = Integer.parseInt(str);
        
        difficulte = 0;
        
        if(mode == 2){
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Difficile");
            str = sc.nextLine();
            difficulte = Integer.parseInt(str);
        }
        
        joueur=1;
        donneur=1;
        receveur=2;
        gagnant=0;
        taille=11;
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public static void jouerCoupPremier(){
        Iterator<Carte> it;
        Carte[] main = new Carte[11];
        
        if(mode == 1 || donneur==1){
            if(donneur==1){
                it = mainj1.iterateur();
            }
            else{
                it = mainj2.iterateur();
            }

            int i=0;
            do{
               main[i]=it.next();
               i++;
            }while(i<11 && it.hasNext());

            if(donneur==1){
                System.out.println("Main Joueur 1");
            }
            else{
                System.out.println("Main Joueur 2");
            }
            for(i=0; i<taille && main[i]!=null; i++){
                System.out.print("["+i+"]:");
                afficherCarte(main[i]);
            }

            System.out.println("Donnez le numéro de la carte à jouer:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);

            Carte c = main[choix];
            if (donneur==1){
                mainj1.retirer(c);
            }else{
                mainj2.retirer(c);
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
            for(int i=1; i<6; i++){
                if(pioche[i].premiere()!=null){
                    piocheIA[lg]=pioche[i].premiere();
                    lg++;
                }
            }
            
            IA ia = new IA(mainj2,new PileCartes(), piocheIA, lg, atout);
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
                mainj1.retirer(c);
            }else{
                mainj2.retirer(c);
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
        Carte[] main = new Carte[11];
        
        if(mode == 1 || receveur == 1){
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
            if(receveur==1){
                mainjoueur= mainj1;
            }
            else{
                mainjoueur = mainj2;
            }
            it = mainjoueur.iterateur();

            int i=0;
            do{
               main[i]=it.next();
               i++;
            }while(i<11 && it.hasNext());

            if(donneur==2){
                System.out.println("Main Joueur 1");
            }
            else{
                System.out.println("Main Joueur 2");
            }
            for(i=0; i<taille && main[i]!=null; i++){
                System.out.print("["+i+"]:");
                afficherCarte(main[i]);
            }

            boolean condition = false;
            System.out.println("Donnez le numéro de la carte à jouer:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);

            if (main[choix].couleur != carteP.couleur && mainj1.contient(carteP.couleur)){
                while(!condition){
                    System.out.println("Jouez une carte de la couleur demandée");
                    str = sc.nextLine();
                    choix = Integer.parseInt(str);
                    if (!(main[choix].couleur != carteP.couleur && mainj1.contient(carteP.couleur))){
                        condition = true;
                    }
                }
            }


            Carte c = main[choix];
            if(receveur==1){
                mainj1.retirer(c);
            }else{
                mainj2.retirer(c);
            }
            taille--;
            carteS = c;

            if(joueur == 1){
                joueur = 2;
            }else{
                joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur jouearte c;
            Carte c = null;
            Carte[] piocheIA = new Carte[6];
            int lg = 0;
            for(int i=1; i<6; i++){
                if(pioche[i].premiere()!=null){
                    piocheIA[lg]=pioche[i].premiere();
                    lg++;
                }
            }
            
            IA ia = new IA(mainj2,new PileCartes(), piocheIA, lg, atout, carteP);
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
                mainj2.retirer(c);
            }else{
                mainj1.retirer(c);
            }
            taille--;
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
            }
            else{
                if(donneur == 1){
                    gagnant = 2;
                }else{
                    gagnant = 1;
                }
            }
        }
        else if(carteS.couleur==atout){
            if(donneur == 1){
                gagnant = 2;
            }else{
                gagnant = 1;
            }
        }
        else{
            gagnant = donneur;
        }
    }
    
    /**
     * Enlève les deux cartes jouées de la table pour les ranger dans le tas du gagnant
     */
    public static void rangerPli(){
        if(gagnant==1){
            tasj1.ajouter(carteP);
            tasj1.ajouter(carteS);
            carteP=null;
            carteS=null;
        }else{
            tasj1.ajouter(carteP);
            tasj1.ajouter(carteS);
            carteP=null;
            carteS=null;
        }
    }
    
    /**
     * Vérifie si une pioche n'est pas vide
     * @return true si il est encore possible de piocher, false sinon
     */
    public static boolean piochable(){
        if(pile1.premiere()!=null){
            return true;
        }
        if(pile2.premiere()!=null){
            return true;
        }
        if(pile3.premiere()!=null){
            return true;
        }
        if(pile4.premiere()!=null){
            return true;
        }
        if(pile5.premiere()!=null){
            return true;
        }
        if(pile6.premiere()!=null){
            return true;
        }
        return false;
    }
    
    /**
     * Permet aux deux joueurs de piocher, d'abord le gagnant, puis l'autre
     */
    public static void pioche(){
        System.out.println("Pioche:");
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                System.out.print("["+(i+1)+"]:");
            afficherCarte(pioche[i].premiere());
            }
        }
        
        System.out.println("Gagnant, choisissez une carte:");
        
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int choix = Integer.parseInt(str);
        
        if (gagnant == 1){
            switch(choix){
                case 1:
                    mainj1.ajouter(pile1.retirer());
                    break;
                case 2:
                    mainj1.ajouter(pile2.retirer());
                    break;
                case 3:
                    mainj1.ajouter(pile3.retirer());
                    break;
                case 4:
                    mainj1.ajouter(pile4.retirer());
                    break;
                case 5:
                    mainj1.ajouter(pile5.retirer());
                    break;
                case 6:
                    mainj1.ajouter(pile6.retirer());
                    break;
            }
        }else{
            switch(choix){
                case 1:
                    mainj2.ajouter(pile1.retirer());
                    break;
                case 2:
                    mainj2.ajouter(pile2.retirer());
                    break;
                case 3:
                    mainj2.ajouter(pile3.retirer());
                    break;
                case 4:
                    mainj2.ajouter(pile4.retirer());
                    break;
                case 5:
                    mainj2.ajouter(pile5.retirer());
                    break;
                case 6:
                    mainj2.ajouter(pile6.retirer());
                    break;
            }
        
        }
        System.out.println("Pioche:");
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                System.out.print("["+(i+1)+"]:");
            afficherCarte(pioche[i].premiere());
            }
        }
        
        System.out.println("Perdant, choisissez une carte:");
        
        sc = new Scanner(System.in);
        str = sc.nextLine();
        choix = Integer.parseInt(str);
        
        if (gagnant == 2){
            switch(choix){
                case 1:
                    mainj1.ajouter(pile1.retirer());
                    break;
                case 2:
                    mainj1.ajouter(pile2.retirer());
                    break;
                case 3:
                    mainj1.ajouter(pile3.retirer());
                    break;
                case 4:
                    mainj1.ajouter(pile4.retirer());
                    break;
                case 5:
                    mainj1.ajouter(pile5.retirer());
                    break;
                case 6:
                    mainj1.ajouter(pile6.retirer());
                    break;
            }
        }else{
            switch(choix){
                case 1:
                    mainj2.ajouter(pile1.retirer());
                    break;
                case 2:
                    mainj2.ajouter(pile2.retirer());
                    break;
                case 3:
                    mainj2.ajouter(pile3.retirer());
                    break;
                case 4:
                    mainj2.ajouter(pile4.retirer());
                    break;
                case 5:
                    mainj2.ajouter(pile5.retirer());
                    break;
                case 6:
                    mainj2.ajouter(pile6.retirer());
                    break;
            }
        
        }
        taille++;
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
        
       if(res.valeur < pile2.premiere().valeur){
           res = pile2.premiere();
       }
       else if(res.valeur == pile2.premiere().valeur){
           if(res.couleur < pile2.premiere().valeur){
               res = pile2.premiere();
           }
       }
       
       if(res.valeur < pile3.premiere().valeur){
           res = pile3.premiere();
       }
       else if(res.valeur == pile3.premiere().valeur){
           if(res.couleur < pile3.premiere().valeur){
               res = pile3.premiere();
           }
       }
       
       if(res.valeur < pile4.premiere().valeur){
           res = pile4.premiere();
       }
       else if(res.valeur == pile4.premiere().valeur){
           if(res.couleur < pile4.premiere().valeur){
               res = pile4.premiere();
           }
       }
       
       if(res.valeur < pile5.premiere().valeur){
           res = pile5.premiere();
       }
       else if(res.valeur == pile5.premiere().valeur){
           if(res.couleur < pile5.premiere().valeur){
               res = pile5.premiere();
           }
       }
       
       if(res.valeur < pile6.premiere().valeur){
           res = pile6.premiere();
       }
       else if(res.valeur == pile6.premiere().valeur){
           if(res.couleur < pile6.premiere().valeur){
               res = pile6.premiere();
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
            mainj1.ajouter(paquet.aleatoire(false));
        }
        for(int i=0;i<11;i++){
            mainj2.ajouter(paquet.aleatoire(false));
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
        
        while(taille>0){
            System.out.println("Le joueur "+ donneur +" commence");
            jouerCoupPremier();
            System.out.println("Tour Joueur "+ receveur);
            jouerCoupSecond();
            gagnantPli();
            if (gagnant == 1){
                System.out.println("Le joueur 1 gagne le pli");
            }else{
                System.out.println("Le joueur 2 gagne le pli");
            }
            rangerPli();
            piochable();
            if(piochable()){
                pioche();
            }
            donneur = gagnant;
            if(donneur==1){
                receveur = 2;
            }else{
                receveur = 1;
            }
        }
    }
    
    public static void main(String[] args) {
        moteur();
    }
}

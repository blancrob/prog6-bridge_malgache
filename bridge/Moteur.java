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
    static Carte carteP, carteS;
    static int atout, joueur, donneur, scorej1, scorej2, manche;
    
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
        
        distribuer();
        set_atout();
        
        joueur=1;
        donneur=1;
    }
    
    public static void jouerCoupPremier(){
        Iterator<Carte> it;
        Carte[] main = new Carte[11];
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
        for(i=0; i<11 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            switch(main[i].couleur){
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
            System.out.println("|"+main[i].valeur);
        }
        
        System.out.println("Donnez le numéro de la carte à jouer:");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int choix = Integer.parseInt(str);
        
        Carte c = main[choix];
        mainj1.retirer(c);
        carteP = c;
        
        
        
        
        if(donneur==1){
            it = mainj1.iterateur();
        }
        else{
            it = mainj2.iterateur();
        }
        
        i=0;
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
        for(i=0; i<11 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            switch(main[i].couleur){
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
            System.out.println("|"+main[i].valeur);
        }
        
        if(joueur == 1){
            joueur = 2;
        }else{
            joueur = 1;
        }
    }
    
    public static void jouerCoupSecond(){
        Iterator<Carte> it;
        PileCartes mainjoueur;
        Carte[] main = new Carte[11];
        if(donneur==2){
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
        for(i=0; i<11 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            switch(main[i].couleur){
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
            System.out.println("|"+main[i].valeur);
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
        if(donneur!=1){
            mainj1.retirer(c);
        }else{
            mainj2.retirer(c);
        }
        carteS = c;
        
        
        
        
        if(donneur==2){
            it = mainj1.iterateur();
        }
        else{
            it = mainj2.iterateur();
        }
        
        i=0;
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
        for(i=0; i<11 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            switch(main[i].couleur){
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
            System.out.println("|"+main[i].valeur);
        }
        
        if(joueur == 1){
            joueur = 2;
        }else{
            joueur = 1;
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
        jouerCoupPremier();
    }
    
    public static void main(String[] args) {
        moteur();
    }
}

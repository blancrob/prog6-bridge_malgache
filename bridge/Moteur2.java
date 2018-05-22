/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;
import java.util.Iterator;
import java.util.Scanner;

public class Moteur2 extends Moteur {
    public Moteur2(){
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();
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
        
        j1.main.trier();
        j2.main.trier();
        
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

        if(config.receveur == 1){
            if (card.couleur != config.carteP.couleur && j1.main.contient(config.carteP.couleur)){
                return null;
            }
        }else{
            if (card.couleur != config.carteP.couleur && j2.main.contient(config.carteP.couleur)){
                return null;
            }
        }   
           
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
        
        j1.main.trier();
        j2.main.trier();
        
        return c;
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
    
    
}

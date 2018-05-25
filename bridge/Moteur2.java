/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Scanner;

public class Moteur2 extends Moteur {
    public Moteur2(){
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();
    }
    
    public void initialiser(String nomj1, String nomj2, int j1difficulte, int j2difficulte, int conditionVictoire, int mancheMax,int scoreMax, int mode){
        
        j1.nom = nomj1;
        j1.difficulte = j1difficulte;
        j2.nom = nomj2;
        j2.difficulte = j2difficulte;
        
        //On initialise les valeurs de la structure
        config.conditionVictoire=conditionVictoire;
        config.mancheMax=mancheMax;
        config.scoreMax=scoreMax;
        config.mode=mode;
    }
    
    /**
     * Sauvegarder la configuration courante dans un fichier dont le nom est passé en paramètre
     * @param nom nom du fichier de sauvegarde
     * @throws IOException 
     */
    public void sauvegarder(String nom) throws IOException{
        File fichier =  new File(nom) ;

        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
       
        oos.writeObject(copieEtat());
    }
    
    /**
     * Charger la configuration contenue dans un fichier dont le nom est passé en paramètre
     * @param nom nom du fichier à charger
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void charger(String nom) throws IOException, ClassNotFoundException{
        File fichier =  new File(nom) ;

        // ouverture d'un flux sur un fichier
       ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;

        // désérialization de l'objet
        EtatGlobal e = (EtatGlobal)ois.readObject();
        
       config = e.config;
       j1 = e.j1;
       j2 = e.j2;
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
        
        config.addUndo(copieEtat());    //Copie de l'état courant dans la pile undo pour y revenir en cas d'annulation du coup courant
        config.viderRedo();
        
        Carte c;
        c = card;

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
        
        //j1.main.trier();
        //j2.main.trier();
        
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
        
        if(config.carteP==null){
            System.out.println("C'est vide dude");
        }else{
            System.out.println("Pas Vide");
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
        
        config.addUndo(copieEtat());    //Copie de l'état courant dans la pile undo pour y revenir en cas d'annulation du coup courant
        config.viderRedo();
        
        Carte c;
        c = card;

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
        
        //j1.main.trier();
        //j2.main.trier();
        
        return c;
    }
    
    /**
     * Permet au joueur désigné en paramètre de piocher
     * @param piocheur Le numéro du joueur qui va piocher
     */
    public void pioche(int piocheur, Carte card, int nbPile){
        config.viderRedo();
        if(piocheur == 1){
            j1.main.ajouter(config.pioche[nbPile].retirer());
        }else{
            j2.main.ajouter(config.pioche[nbPile].retirer());
        }
    }
    
    
}

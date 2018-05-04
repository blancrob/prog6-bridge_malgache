/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;
import java.util.Random;

/**
 * Cette classe contient les différentes méthodes implémentant les différents niveaux de l'IA.
 */
public class IA {
    PileCartes main;
    PileCartes cartesDejaJouees;
    Carte[] pioche;
    int lg;
    int atout;
    Carte courante;
   
    public IA(){
        main = new PileCartes();
        cartesDejaJouees = new PileCartes();
        pioche = new Carte[0];
        lg = 0;
        atout = 0;
        courante = null;
    }
    
    public IA(PileCartes m, PileCartes c, Carte[] p, int l, int at, Carte cour){
        main = m;
        cartesDejaJouees = c;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
    }
    
    public IA(PileCartes m, PileCartes c, Carte[] p, int l, int at){
        main = m;
        cartesDejaJouees = c;
        pioche = p;
        lg = l;
        atout = at;
        courante = null;
    }
            
    /**************************************************************************************************************************
     * IA Novice.
     * Joue un coup aléatoire parmis les coups possibles.
     * @return Carte à jouer
     */
    public Carte iaNovice(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return main.aleatoire(true);
        }
        else{ // si l'adversaire à joué 
            int couleur = courante.couleur; // on récupère la couleur jouée par l'adversaire
            Carte res = new Carte();
            Iterator<Carte> it = main.iterateur();
            do{
                res = it.next();
            }while(res.couleur!=couleur && it.hasNext()); // Recherche d'une carte de la bonne couleur 
            return res;
        }
    }
    
     /**************************************************************************************************************************
     * IA Facile.
     * Si IA commence : Aléatoire 
     * SI IA termine :
        * 1)Joue un coup gagnant aléatoire dans la bonne couleur, perdant sinon
        * 2)Joue un atout aléatoire
        * 3)Joue une carte aléatoire
     * @return Carte à jouer
     */
    public Carte iaFacile(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return main.aleatoire(true);
        }
        else{ // si l'adversaire a joué 
            int couleur = courante.couleur; // on récupère la couleur jouée par l'adversaire
            Carte res = new Carte();
            res = main.aleatoireGagnant(couleur,courante.valeur);
            if (res == null){
                res = main.aleatoire(couleur);
            }
            if (res == null){
                res = main.aleatoire(atout);
            }
            if (res == null){
               res = main.aleatoire(true);
            }
            return res;
        }
    }
    
    /**
     * pioche une carte de façon aléatoire parmis les différents tas.
     * @return une carte parmis les cartes de la pioche. 
     */
    public Carte piocheNoviceEtFacile(){
        Random r = new Random();
        return pioche[r.nextInt(lg-1)];
    }
    
    
    /**************************************************************************************************************************
     * Ia Moyenne : Coup gagnant/Coup non perdant.
     * Si IA commence : joue la plus grande carte 
     * SI IA termine :
        * 1) Joue la plus petite carte gagnante de la bonne couleur
        * 2) Joue la plus petite carte perdante de la bonne couleur
        * 3) Joue le plus petit atout 
        * 4) Joue la plus petite carte d'une autre couleur
     * @return une carte à jouer
     */
    public Carte iaMoyenne(){
        if(courante == null){ // si c'est au tour de l'IA elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (this.fournir(courante.couleur)){ // si on a la couleur demandée 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la carte gagnante juste au dessus
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ // si on a pas la couleur demandée
                if (this.fournir(atout)){ // on joue de l'atout si possible
                    res = main.min(atout); // on joue le plus petit atout 
                }
                else{ // si on a pas d'atout 
                    res = main.min(); // on donne la plus petite carte de la main 
                }
            }
            return res;
        }
       
    }
    
     /**
     * pioche une carte , le plus grand atout possible, si pas d'atout la plus grande carte d'une autre couleur 
     * @return la carte carte parmis les cartes de la pioche. 
     */
    public Carte piocheMoyenne(){
        int i = 0;
        Carte res = choisirMeilleureCarte(atout); // Si on a des atout dans la picohe on prend le plus grand 
        
        if (res == null){ // si pas d'atout dans la pioche 
            res = pioche[0];
            while(i<lg){
                if (pioche[i].valeur>res.valeur){ // Recherche de la carte de valeur maximale 
                    res = pioche[i];
                }
                i++;
            }
        }   
        return res;
    }
    
  /**************************************************************************************************************************
    * Ia Difficile 
    * Si IA commence 
    *   
    * 
    * si Adversaire a déjà joué 
    *       Si on a la couleur demandée
    *           Si on peut gagner : mettre la gagne gagnante juste au dessus
    *           Sinon : mettre la plus petite carte 
    * 
    *       Si on a pas la couleur 
    *           Regarder la pioche 
    *           Si pioche cool : jouer le plus petit atout possible si il y a 
    *           Si pioche pas cool : mettre la plus petite carte 
    */
    public Carte iaDifficile(){
        return main.aleatoire(true);
    }
    
    /**
     * Choisir plus gros atout si il y en a
     * Choisir plus grosse carte autre couleur 
     * Si que des truc pourris , quand 2 trucs égaux choisir celui ou la pile est la plus petite (proba plus faible d'avoir une cool dessous)
     * @return 
     */

    public Carte piocheDifficile(){
        Carte res = choisirMeilleureCarte(atout);
        return res;
    }
    
    
    /**
     * @param couleur demandée
     * @return true si on a une carte de la couleur passée en paramètre
     */
    public boolean fournir(int couleur){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = main.iterateur();
        do{
            tmp = it.next();
            if (tmp.couleur == couleur){
                res = tmp;
            }
        }while(res == null && it.hasNext());
        
        return res != null;
    }
    
    /**
     * @param couleur
     * @return la carte de plus grande valeur de la couleur passée en paramètre présente dans la pioche
     */
    public Carte choisirMeilleureCarte(int couleur){
        Carte res = null;
        for(int i = 0; i < lg; i++){
           if (pioche[i].couleur == couleur){
               if(res == null){
                   res = pioche[i];
               }
               else{
                   if(res.valeur < pioche[i].valeur){
                       res = pioche[i];
                   }
               }
           } 
        }
        return res;
    }
}

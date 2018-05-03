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
            
    /**
     * IA aléatoire.
     * Joue un coup aléatoire parmis les coups possibles.
     * @return Carte à jouer
     */
    public Carte iaFacile(){
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
    
    /**
     * pioche une carte de façon aléatoire parmis les différents tas.
     * @return une carte parmis les cartes de la pioche. 
     */
    public Carte piocheFacile(){
        Random r = new Random();
        return pioche[r.nextInt(lg-1)];
    }
    
    
    /**
     * Coup gagnant/Coup non perdant.
     * joue une carte permettant de gagner le plie si possible sinon de perdre le moins de point possible.
     * @return une carte gagnante, de valeur maximal si premier à jouer et sinon de valeur minimal si impossible de gagner le plie.
     */
    public Carte iaMoyenne(){
        if(courante == null){ // si c'est au tour de l'IA elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (this.fournir(courante.couleur)){ // si on a la couleur demandé 
                res = main.max(courante.couleur); // A CHANGER !!!!! prendre la carte gagnante juste au dessus !!!!!!
                if (res.valeur<courante.valeur){ // si on a pas de carte gsupérieure de la couleur en question 
                    res = main.min(courante.couleur); // on donne la plus petite carte de cette couleur 
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
     * @return une carte parmis les cartes de la pioche. 
     */
    public Carte piocheMoyenne(){
        int i = 0;
        Carte res = pioche[0];
        while(i<lg){
            if (pioche[i].valeur>res.valeur){ // Recherche de la carte de valeur maximale 
                res = pioche[i];
            }
            i++;
        }
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
    
    
}

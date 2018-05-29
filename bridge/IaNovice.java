/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;
import java.util.Random;

/**
 * IA Novice. 
 * Joue un coup aléatoire parmis les coups possibles.
 * Pioche une carte de façon aléatoire parmis les différents tas.
 */
public class IaNovice implements IA {
    PileCartes main;
    PileCartes cartesDejaJouees;
    PileCartes cartesPiochees;
    Carte[] pioche;
    int lg;
    int atout;
    Carte courante;
    
    public IaNovice(PileCartes m, PileCartes c,PileCartes pi, Carte[] p, int l, int at, Carte cour){
        main = m;
        cartesDejaJouees = c;
        cartesPiochees = pi;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
    }
    
     /**
     * Joue un coup aléatoire parmis les coups possibles.
     * @return Carte à jouer
     */
    @Override
    public Carte jouer(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return IA_Util.aleatoire(main);
        }
        else{ // si l'adversaire à joué 
            Carte res = IA_Util.aleatoire(main,courante);
            if (res == null){
                res = IA_Util.aleatoire(main);
            }
            return res;
        }
    }
    
    
     /** 
     * pioche une carte de façon aléatoire parmis les différents tas de la pioche.
     * @return une carte parmis les cartes de la pioche. 
     */
    @Override
    public Carte piocher(){
        /*Random r = new Random();
        int tmp;
        if(lg>1){
            tmp = r.nextInt(lg-1);
            while(pioche[tmp]==null){
                tmp = r.nextInt(lg-1);
            }
        }else{
            tmp=0;
        }
        return pioche[tmp];*/
        Random r = new Random();
        return pioche[r.nextInt(lg)];
    }
    
    
}

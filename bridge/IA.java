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
        if (courante == null){
            return main.aleatoire(true);
        }
        else{
            int couleur = courante.couleur;
            Carte res = new Carte();
            Iterator<Carte> it = main.iterateur();
            do{
                res = it.next();
            }while(res.couleur!=couleur && it.hasNext());
            return res;
        }
    }
    
    /**
     * pioche une carte de façon aléatoire.
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
        if(courante == null){
            return main.max();
        }
        else{
            Carte res;
            if (this.fournir(courante.couleur)){
                res = main.max(courante.couleur);
                if (res.valeur<courante.valeur){
                    res = main.min(courante.couleur);
                }
            }else{
                if (this.fournir(atout)){
                    res = main.min(atout);
                }
                else{
                    res = main.min();
                }
            }
            return res;
        }
       
    }
    
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
    
    
    public Carte piocheMoyenne(){
        int i = 0;
        Carte res = pioche[0];
        while(i<lg){
            if (pioche[i].valeur>res.valeur){
                res = pioche[i];
            }
            i++;
        }
        return res;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.ArrayList;
import java.util.Iterator;
import static bridge.Carte.*;
import java.util.Random;

public class PileCartes {
    ArrayList<Carte> pile;
    
    public PileCartes(){
        pile = new ArrayList();
    }
    
    public PileCartes(Carte c){
        pile = new ArrayList();
        pile.add(c);
    }
    
    public int taille(){
        return pile.size();
    }
    
    public boolean vide(){
        return pile.isEmpty();
    }
    
    public void ajouter(Carte c){
        pile.add(c);
    }
    
    public Carte premiere(){
        Carte res = null;
        if(!pile.isEmpty()){
            Iterator<Carte> it = pile.iterator();
            res = it.next();
        }
        
        return res;
    }
    
    public Carte retirer(){
        Carte res = null;
        if(!pile.isEmpty()){
            Iterator<Carte> it = pile.iterator();
            res = it.next();
            pile.remove(res);
        }
        
        return res;
    }
    
    public void paquet(){
        pile.clear();
        
        for(int i=1; i<15; i++){
            pile.add(new Carte(COEUR, i, false));
        }
        for(int i=1; i<15; i++){
            pile.add(new Carte(PIQUE, i, false));
        }
        for(int i=1; i<15; i++){
            pile.add(new Carte(CARREAU, i, false));
        }
        for(int i=1; i<15; i++){
            pile.add(new Carte(TREFLE, i, false));
        }
    }
    
    public Carte aleatoire(boolean visible){
        Random r = new Random();
        int i = r.nextInt(pile.size());
        Carte res = pile.get(i);
        pile.remove(res);
        if (res.visible!=visible){
            res.visible=visible;
        }
        
        return res;
    }
    
}

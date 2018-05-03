/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.ArrayList;
import java.util.Iterator;

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
    
    public void ajouter(Carte c){
        pile.add(c);
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
    
}

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
    
    /**
     * Construit une ArrayList vide
     */
    public PileCartes(){
        pile = new ArrayList();
    }
    
    /**
     * La pile contient la carte passée en paramètre
     * @param c Carte à insérer à la création
     */
    public PileCartes(Carte c){
        pile = new ArrayList();
        pile.add(c);
    }
    
    /**
     * @return le nombre d'éléments dans la pile 
     */
    public int taille(){
        return pile.size();
    }
    
    /**
     * @return true si la pile est vide, false sinon
     */
    public boolean vide(){
        return pile.isEmpty();
    }
    
    /**
     * @return un Iterateur de Carte de la pile
     */
    public Iterator<Carte> iterateur(){
        Iterator<Carte> it = pile.iterator();
        return it;
    }
    
    /**
     * Ajouter une carte à la pile
     * @param c la carte à ajouter
     */
    public void ajouter(Carte c){
        pile.add(c);
    }
    
    /**
     * @return la première carte de la pile, celle sur le dessus
     */
    public Carte premiere(){
        Carte res = null;
        if(!pile.isEmpty()){
            Iterator<Carte> it = pile.iterator();
            res = it.next();
        }
        
        return res;
    }
    
    
    /**
     * Renvoyer la première carte de la pile, celle sur le dessus, et la supprimer dans la pile
     * @return la première carte de la pile
     */
    public Carte retirer(){
        Carte res = null;
        if(!pile.isEmpty()){
            Iterator<Carte> it = pile.iterator();
            res = it.next();
            pile.remove(res);
        }
        
        return res;
    }
    
    /**
     * Vide la pile puis la remplis avec un exemplaire de chaque carte d'un jeu de 52 cartes
     */
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
    
    /**
     * Renvoie une carte aléatoire de la pile, et la supprime dans la pile
     * @param visible définis si la carte sera renvoyée visible ou non
     * @return une carte aléatoire de la pile
     */
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
    
    /**
     * @return la carte de la pile de plus grande valeur
     */
    public Carte max(){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = pile.iterator();
        
        res = it.next();
        
        do{
            tmp = it.next();
            if(tmp.valeur>res.valeur){
                res = tmp;
            }
        }while(it.hasNext());
        
        return res;
    }
    
    /**
     * @param couleur la couleur atendue pour la carte
     * @return la carte de la pile de la couleur demandée de plus grande valeur si elle existe, null sinon
     */
    public Carte max(int couleur){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = pile.iterator();
        
        do{
            tmp = it.next();
            if(tmp.couleur==couleur){
                res = tmp;
            }
        }while(res==null && it.hasNext());
        
        do{
            tmp = it.next();
            if(tmp.couleur == couleur && tmp.valeur>res.valeur){
                res = tmp;
            }
        }while(it.hasNext());
        
        return res;
    }
    
    /**
     * @return la carte de la pile de plus faible valeur 
     */
    public Carte min(){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = pile.iterator();
        
        res = it.next();
        
        do{
            tmp = it.next();
            if(tmp.valeur<res.valeur){
                res = tmp;
            }
        }while(it.hasNext());
        
        return res;
    }
    
    /**
     * @param couleur la couleur atendue pour la carte
     * @return la carte de la pile de la couleur demandée de plus faible valeur si elle existe, null sinon
     */
    public Carte min(int couleur){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = pile.iterator();
        
        do{
            tmp = it.next();
            if(tmp.couleur==couleur){
                res = tmp;
            }
        }while(res==null && it.hasNext());
        
        do{
            tmp = it.next();
            if(tmp.couleur == couleur && tmp.valeur<res.valeur){
                res = tmp;
            }
        }while(it.hasNext());
        
        return res;
    }
    
    /**
     * @param couleur la couleur atendue pour la carte
     * @param valeur la valeur minimale de la carte
     * @return la carte de la pile de la couleur demandée, de valeur supérieure au paramètre valeur, et de plus faible valeur si elle existe, null sinon
     */
    public Carte minGagnant(int couleur, int valeur){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = pile.iterator();
        
        do{
            tmp = it.next();
            if(tmp.couleur==couleur && tmp.valeur == valeur){
                res = tmp;
            }
        }while(res==null && it.hasNext());
        
        do{
            tmp = it.next();
            if(tmp.couleur == couleur && tmp.valeur<res.valeur && tmp.valeur>valeur){
                res = tmp;
            }
        }while(it.hasNext());
        
        return res;
    }
    
}

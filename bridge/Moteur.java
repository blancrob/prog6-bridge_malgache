/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.Carte.*;

public class Moteur {
    static PileCartes pile1, pile2, pile3, pile4, pile5, pile6, mainj1, mainj2;
    static int atout;
    
    public static void initialiser(){
        distribuer();
        
        
        
    }
    
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
    }
    
    public static void main(String[] args) {
        moteur();
    }
}

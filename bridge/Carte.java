/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

public class Carte {
    int valeur;
    int couleur;
    boolean visible;
    
    static final int V=11;
    static final int D=12;
    static final int R=13;
    static final int AS=14;
    
    static final int PIQUE=4;
    static final int COEUR=3;
    static final int CARREAU=2;
    static final int TREFLE=1;
    
    
    public Carte(){
        valeur = 0;
        couleur = 0;
        visible = false;
    }
    
    public Carte(int couleur, int valeur, boolean visible){
        this.valeur = valeur;
        this.couleur = couleur;
        this.visible = visible;
    }
    
}

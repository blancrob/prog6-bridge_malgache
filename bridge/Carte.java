/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Carte extends Parent implements Cloneable, Serializable{
    
    int valeur;
    int couleur;
    boolean visible;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    //final int hauteur_carte = 165;
    //final int largeur_carte = 135;
    
    final int hauteur_carte = (int)(hauteur_scene/5.45);
    final int largeur_carte = (int)(largeur_scene/14.223);
    
    static final int V=11;
    static final int D=12;
    static final int R=13;
    static final int AS=14;
    
    static final int PIQUE=4;
    static final int COEUR=3;
    static final int CARREAU=2;
    static final int TREFLE=1;
    
    Rectangle face, dos;
    
    public Carte(){
        valeur = 0;
        couleur = 0;
        visible = false;
    }
    
    public Carte(int couleur, int valeur, boolean visible){
        this.valeur = valeur;
        this.couleur = couleur;
        this.visible = visible;
        
        face = new Rectangle(largeur_carte,hauteur_carte,Color.TRANSPARENT);
        dos = new Rectangle(largeur_carte,hauteur_carte,Color.TRANSPARENT);
        
        this.getChildren().add(face);
        this.getChildren().add(dos);
        
    }
    
    @Override
    public Carte clone(){
        Carte res = new Carte();
        res.valeur = valeur;
        res.couleur = couleur;
        res.visible = visible;
        
        return res;
    }

    void setFill(Color web) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

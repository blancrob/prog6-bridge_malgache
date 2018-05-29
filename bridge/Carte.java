/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    int hauteur_carte = (int)(hauteur_scene/5.45);
    int largeur_carte = (int)(largeur_scene/14.223);
    
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
    
    private void writeObject(final ObjectOutputStream out) throws IOException{
      out.writeInt(this.valeur);
      out.writeInt(this.couleur);
      out.writeBoolean(this.visible);
      out.writeInt(this.hauteur_carte);
      out.writeInt(this.largeur_carte);
      out.writeDouble(face.getArcHeight());
      out.writeDouble(face.getArcWidth());
      out.writeDouble(face.getHeight());
      out.writeDouble(face.getWidth());
      out.writeDouble(face.getX());
      out.writeDouble(face.getY());
      out.writeDouble(dos.getArcHeight());
      out.writeDouble(dos.getArcWidth());
      out.writeDouble(dos.getHeight());
      out.writeDouble(dos.getWidth());
      out.writeDouble(dos.getX());
      out.writeDouble(dos.getY());
   }
 
   /**
    * Deserialize this instance from input stream.
    * 
    * @param in Input Stream from which this instance is to be deserialized.
    * @throws IOException Thrown if error occurs in deserialization.
    * @throws ClassNotFoundException Thrown if expected class is not found.
    */
   private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException
   {
       face = new Rectangle();
       dos = new Rectangle();
      this.valeur = in.readInt();
      this.couleur = in.readInt();
      this.visible = in.readBoolean();
      this.hauteur_carte = in.readInt();
      this.largeur_carte = in.readInt();
      this.face.setArcHeight(in.readDouble()); 
      this.face.setArcWidth(in.readDouble()); 
      this.face.setHeight(in.readDouble()); 
      this.face.setWidth(in.readDouble()); 
      this.face.setX(in.readDouble()); 
      this.face.setY(in.readDouble()); 
      this.dos.setArcHeight(in.readDouble()); 
      this.dos.setArcWidth(in.readDouble()); 
      this.dos.setHeight(in.readDouble()); 
      this.dos.setWidth(in.readDouble()); 
      this.dos.setX(in.readDouble()); 
      this.dos.setY(in.readDouble()); 
   }

    void setFill(Color web) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

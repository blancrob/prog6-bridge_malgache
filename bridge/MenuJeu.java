/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author haudryf
 */
public class MenuJeu extends Parent {
    
    Rectangle menu;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public MenuJeu() {
             
        menu = new Rectangle(largeur_scene/5, hauteur_scene, Color.PERU);
        this.getChildren().add(menu);
        
    }
    
}

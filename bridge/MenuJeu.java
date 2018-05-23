/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author haudryf
 */
public class MenuJeu extends Parent {
    
    Rectangle menu;
    
    Button quit;
    Button save;
    Button option;
    Button restart;
    Button rules;
    Button help;
    
    Label tour;
    
    Rectangle mancheAtout;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public MenuJeu() {
             
        menu = new Rectangle(largeur_scene/5, hauteur_scene, Color.web("4c1130"));
        this.getChildren().add(menu);
        
        quit = new Button("Quitter la partie");
        quit.setPrefWidth(300);
        quit.setPrefHeight(90);
        quit.setTranslateX(largeur_scene/48);
        quit.setTranslateY(hauteur_scene-hauteur_scene/10);
        this.getChildren().add(quit);
        
        save = new Button("Sauvegarder");
        save.setPrefWidth(300);
        save.setPrefHeight(90);
        save.setTranslateX(largeur_scene/48);
        save.setTranslateY(hauteur_scene-hauteur_scene/5);
        this.getChildren().add(save);
        
        option = new Button("Options");
        option.setPrefWidth(140);
        option.setPrefHeight(90);
        option.setTranslateX(largeur_scene/48);
        option.setTranslateY(hauteur_scene-hauteur_scene/3.3);
        this.getChildren().add(option);
        
        help = new Button("Aide");
        help.setPrefWidth(140);
        help.setPrefHeight(90);
        help.setTranslateX(largeur_scene/9.6);
        help.setTranslateY(hauteur_scene-hauteur_scene/3.3);
        this.getChildren().add(help);
        
        restart = new Button("Recommencer");
        restart.setPrefWidth(140);
        restart.setPrefHeight(90);
        restart.setTranslateX(largeur_scene/48);
        restart.setTranslateY(hauteur_scene-hauteur_scene/2.465);
        this.getChildren().add(restart);
        
        rules = new Button("Regles");
        rules.setPrefWidth(140);
        rules.setPrefHeight(90);
        rules.setTranslateX(largeur_scene/9.6);
        rules.setTranslateY(hauteur_scene-hauteur_scene/2.465);
        this.getChildren().add(rules);
        
        tour = new Label("Tour joueur");
        tour.setFont(new Font("Arial",25));
        tour.setPrefWidth(300);
        tour.setPrefHeight(90);
        tour.setTranslateX(largeur_scene/48);
        tour.setTranslateY(hauteur_scene-hauteur_scene/2);
        tour.setTextFill(Color.YELLOW);
        tour.setStyle("-fx-alignment: center;");
        this.getChildren().add(tour);
        
        mancheAtout = new Rectangle(largeur_scene/4,hauteur_scene/10, Color.web("4c1130"));
        mancheAtout.setTranslateX(largeur_scene/48);
        mancheAtout.setTranslateY(hauteur_scene-hauteur_scene/1.5);
        this.getChildren().add(mancheAtout);
        
    }
    
}

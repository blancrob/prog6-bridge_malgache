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
    Label manche;
    Label image_atout;
    Label atout;
    Label condition_victoire;
    Label joueur1;
    Label joueur2;
    Label titre;
    Button hist;
    
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
        
        tour = new Label("Tour : Joueur 1");
        tour.setFont(new Font("Arial",25));
        tour.setTextFill(Color.YELLOW);
        tour.setPrefWidth(300);
        tour.setPrefHeight(90);
        tour.setTranslateX(largeur_scene/48);
        tour.setTranslateY(hauteur_scene-hauteur_scene/1.92);    
        tour.setStyle("-fx-alignment: center;");
        this.getChildren().add(tour);
        
        mancheAtout = new Rectangle(largeur_scene/5.6,hauteur_scene/10, Color.TRANSPARENT);
        mancheAtout.setTranslateX(largeur_scene/100);
        mancheAtout.setTranslateY(hauteur_scene-hauteur_scene/1.55);
        mancheAtout.setArcWidth(20);
        mancheAtout.setArcHeight(20);
        mancheAtout.setStroke(Color.BLACK);
        mancheAtout.setStrokeWidth(2);
        this.getChildren().add(mancheAtout);
        
        manche = new Label("Manche 1");
        manche.setFont(new Font("Arial",25));
        manche.setTextFill(Color.YELLOW);
        manche.setTranslateX(largeur_scene/35);
        manche.setTranslateY(hauteur_scene-hauteur_scene/1.64);
        this.getChildren().add(manche);
            
        atout = new Label("Atout");
        atout.setFont(new Font("Arial",18));
        atout.setTextFill(Color.YELLOW);
        atout.setTranslateX(largeur_scene/7.75);
        atout.setTranslateY(hauteur_scene-hauteur_scene/1.72);
        this.getChildren().add(atout);
        
        image_atout = new Label("");
        image_atout.setFont(new Font("Arial",18));
        image_atout.setTextFill(Color.YELLOW);
        image_atout.setTranslateX(largeur_scene/7.75);
        image_atout.setTranslateY(hauteur_scene-hauteur_scene/1.72);
        this.getChildren().add(atout);
        
        condition_victoire = new Label("Partie en 1 manche");
        condition_victoire.setFont(new Font("Arial",22));
        condition_victoire.setTextFill(Color.YELLOW);
        condition_victoire.setPrefWidth(300);
        condition_victoire.setPrefHeight(90);
        condition_victoire.setTranslateX(largeur_scene/48);
        condition_victoire.setTranslateY(hauteur_scene-hauteur_scene/1.35);       
        condition_victoire.setStyle("-fx-alignment: center;");
        this.getChildren().add(condition_victoire);
        
        joueur2 = new Label("Joueur 2              0");
        joueur2.setFont(new Font("Arial",22));
        joueur2.setTextFill(Color.YELLOW);
        joueur2.setPrefWidth(300);
        joueur2.setPrefHeight(90);
        joueur2.setTranslateX(largeur_scene/48);
        joueur2.setTranslateY(hauteur_scene-hauteur_scene/1.2);
        joueur2.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur2);
        
        joueur1 = new Label("Joueur 1              0");
        joueur1.setFont(new Font("Arial",22));
        joueur1.setTextFill(Color.YELLOW);
        joueur1.setPrefWidth(300);
        joueur1.setPrefHeight(90);
        joueur1.setTranslateX(largeur_scene/48);
        joueur1.setTranslateY(hauteur_scene-hauteur_scene/1.12);
        joueur1.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur1);
        
        titre = new Label("Bridge Chinois");
        titre.setFont(new Font("Arial",30));
        titre.setTextFill(Color.YELLOW);
        titre.setPrefWidth(300);
        titre.setPrefHeight(90);
        titre.setTranslateX(largeur_scene/48);
        titre.setTranslateY(hauteur_scene-hauteur_scene/1.03);
        titre.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
        this.getChildren().add(titre);
        
        hist = new Button("Historique");
        hist.setFont(new Font("Arial",12));
        hist.setPrefWidth(85);
        hist.setPrefHeight(25);
        hist.setTranslateX(largeur_scene/8);
        hist.setTranslateY(hauteur_scene-hauteur_scene/1.315);
        this.getChildren().add(hist);
        
        
        
    }
}

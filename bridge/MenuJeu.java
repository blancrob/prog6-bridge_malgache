/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    
    int tour_joueur;
    int l;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public void tourJ(int i){
        tour_joueur = i;
    }
    
    public int lock(){
        if(l == 1){
            return 1;
        }else{
            return 0;
        }
    }
    
    public MenuJeu(Moteur m) {
             
        menu = new Rectangle(largeur_scene/5, hauteur_scene, Color.web("270404"));//gris foncé : 4b4c4e Violet : 343638 bleu fonce :040447
        this.getChildren().add(menu);
        
        quit = new Button("Quitter la partie");
        quit.setPrefWidth(largeur_scene/6.4);
        quit.setPrefHeight(hauteur_scene/12);
        quit.setTranslateX(largeur_scene/48);
        quit.setTranslateY(hauteur_scene-hauteur_scene/10);
        this.getChildren().add(quit);
        
        save = new Button("Sauvegarder");
        save.setPrefWidth(largeur_scene/6.4);
        save.setPrefHeight(hauteur_scene/12);
        save.setTranslateX(largeur_scene/48);
        save.setTranslateY(hauteur_scene-hauteur_scene/5);
        this.getChildren().add(save);
        
        option = new Button("Options");
        option.setPrefWidth(largeur_scene/13.715);
        option.setPrefHeight(hauteur_scene/12);
        option.setTranslateX(largeur_scene/48);
        option.setTranslateY(hauteur_scene-hauteur_scene/3.3);
        this.getChildren().add(option);
        
        help = new Button("Aide");
        help.setPrefWidth(largeur_scene/13.715);
        help.setPrefHeight(hauteur_scene/12);
        help.setTranslateX(largeur_scene/9.6);
        help.setTranslateY(hauteur_scene-hauteur_scene/3.3);
        this.getChildren().add(help);
        
        restart = new Button("Recommencer");
        restart.setPrefWidth(largeur_scene/13.715);
        restart.setPrefHeight(hauteur_scene/12);
        restart.setTranslateX(largeur_scene/48);
        restart.setTranslateY(hauteur_scene-hauteur_scene/2.465);
        this.getChildren().add(restart);

        rules = new Button("Regles");
        rules.setPrefWidth(largeur_scene/13.715);
        rules.setPrefHeight(hauteur_scene/12);
        rules.setTranslateX(largeur_scene/9.6);
        rules.setTranslateY(hauteur_scene-hauteur_scene/2.465);
        this.getChildren().add(rules);
        
        tour = new Label("Tour : Joueur "+tour_joueur);
        tour.setFont(new Font("Arial",25));
        tour.setTextFill(Color.WHITE);
        tour.setPrefWidth(largeur_scene/6.4);
        tour.setPrefHeight(hauteur_scene/12);
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
        manche.setTextFill(Color.WHITE);
        manche.setTranslateX(largeur_scene/35);
        manche.setTranslateY(hauteur_scene-hauteur_scene/1.64);
        this.getChildren().add(manche);
            
        atout = new Label("Atout");
        atout.setFont(new Font("Arial",18));
        atout.setTextFill(Color.WHITE);       
        atout.setTranslateX(largeur_scene/7.75);
        atout.setTranslateY(hauteur_scene-hauteur_scene/1.72);
        if(m.config.atout == 0){
            atout.setText("Sans Atout");
            atout.setTranslateX(largeur_scene/8.7);
            atout.setTranslateY(hauteur_scene-hauteur_scene/1.72);
        }
        this.getChildren().add(atout);
        
        image_atout = new Label();
        image_atout.setFont(new Font("Arial",18));
        image_atout.setTranslateX(largeur_scene/7.78);
        image_atout.setTranslateY(hauteur_scene-hauteur_scene/1.59);  
        Image img;
        switch(m.config.atout){
            case 1:
                img = new Image("images/SYMBOLE_TREFLE.png");
                break;
            case 2:
                img = new Image("images/SYMBOLE_CARREAU.png");
                break;
            case 3:
                img = new Image("images/SYMBOLE_COEUR.png");
                break;
            case 4:
                img = new Image("images/SYMBOLE_PIQUE.png");
                break;
            default:
                img = new Image("images/Vide.png");
                break;
        }
        image_atout.setGraphic(new ImageView(img));
        this.getChildren().add(image_atout);
        
        if(m.config.conditionVictoire==1){
            condition_victoire = new Label("Partie en "+m.config.mancheMax+" manches");
        }
        else{
            condition_victoire = new Label("Partie en "+m.config.scoreMax+" points");
        }
        condition_victoire.setFont(new Font("Arial",22));
        condition_victoire.setTextFill(Color.WHITE);
        condition_victoire.setPrefWidth(largeur_scene/6.4);
        condition_victoire.setPrefHeight(hauteur_scene/12);
        condition_victoire.setTranslateX(largeur_scene/48);
        condition_victoire.setTranslateY(hauteur_scene-hauteur_scene/1.35);       
        condition_victoire.setStyle("-fx-alignment: center;");
        this.getChildren().add(condition_victoire);
        
        joueur2 = new Label("Joueur 2              0");
        joueur2.setFont(new Font("Arial",22));
        joueur2.setTextFill(Color.WHITE);
        joueur2.setPrefWidth(largeur_scene/6.4);
        joueur2.setPrefHeight(hauteur_scene/12);
        joueur2.setTranslateX(largeur_scene/48);
        joueur2.setTranslateY(hauteur_scene-hauteur_scene/1.2);
        joueur2.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur2);
        
        joueur1 = new Label("Joueur 1              0");
        joueur1.setFont(new Font("Arial",22));
        joueur1.setTextFill(Color.WHITE);
        joueur1.setPrefWidth(largeur_scene/6.4);
        joueur1.setPrefHeight(hauteur_scene/12);
        joueur1.setTranslateX(largeur_scene/48);
        joueur1.setTranslateY(hauteur_scene-hauteur_scene/1.12);
        joueur1.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur1);
        
        titre = new Label("Bridge Chinois");
        titre.setFont(new Font("Arial",30));
        titre.setTextFill(Color.WHITE);
        titre.setPrefWidth(largeur_scene/6.4);
        titre.setPrefHeight(hauteur_scene/12);
        titre.setTranslateX(largeur_scene/48);
        titre.setTranslateY(hauteur_scene-hauteur_scene/1.03);
        titre.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
        this.getChildren().add(titre);
        
        hist = new Button("Historique");
        hist.setFont(new Font("Arial",12));
        hist.setPrefWidth(largeur_scene/22.588);
        hist.setPrefHeight(hauteur_scene/43.2);
        hist.setTranslateX(largeur_scene/8.25);
        hist.setTranslateY(hauteur_scene-hauteur_scene/1.315);
        this.getChildren().add(hist);
        
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tour.setText("Tour : Joueur "+tour_joueur);
            }
        };
        timer.start();
        
        /*wRestart wres = new wRestart();
        this.getChildren().add(wres);
        wres.setVisible(false);
        
        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                wres.setVisible(true);
                wres.toFront();
                l = 1;
            }
        });
        
        wres.non.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                wres.setVisible(false);
                l = 0;
            }
        });
        
        wres.oui.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                wres.setVisible(false);
                l = 0;
            }
        });
        */
    }
}

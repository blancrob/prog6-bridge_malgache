/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import static java.lang.System.exit;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    Label scorej1;
    Label scorej2;
    Label titre;
    Button hist;
    Label plisJ1;
    Label plisJ2;
    Label nom_plisJ1;
    Label nom_plisJ2;
    Label image_tour;
    Label image_tourMenu;
    int tour_joueur;
    int mode;
    int l;
    String name;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public void tourJ(int i){
        tour_joueur = i;
    }
    
    public void mode(int i){
        mode = i;
    }
    
    public int lock(){
        if(l == 1){
            return 1;
        }else{
            return 0;
        }
    }
    
    public MenuJeu(Moteur m) {
             
        menu = new Rectangle(largeur_scene/5, hauteur_scene, Color.web("4c1130"));//gris foncé : 4b4c4e autre gris : 343638 bleu fonce :040447 violet : 4c1130
        this.getChildren().add(menu);
        
        quit = new Button("Quitter la partie");
        quit.setPrefWidth(largeur_scene/6.4);
        quit.setPrefHeight(hauteur_scene/12);
        quit.setTranslateX(largeur_scene/48);
        quit.setTranslateY(hauteur_scene-hauteur_scene/10);
        this.getChildren().add(quit);
        quit.setOnAction((ActionEvent event) -> {
            exit(0);
        });
        
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
        
        tour = new Label("Tour : "+name);
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
        
        manche = new Label("Manche "+m.config.manche);
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
        
        if(m.config.conditionVictoire==2){
            joueur2 = new Label(m.j2.nom);
            scorej2=new Label(""+m.j2.scoreTotal);
        }
        else{
            joueur2 = new Label(m.j2.nom);
            scorej2=new Label(""+m.j2.manchesGagnees);
        }
        joueur2.setFont(new Font("Arial",22));
        joueur2.setTextFill(Color.WHITE);
        joueur2.setPrefWidth(largeur_scene/16);
        joueur2.setPrefHeight(hauteur_scene/12);
        joueur2.setTranslateX(largeur_scene/48);
        joueur2.setTranslateY(hauteur_scene-hauteur_scene/1.2);
        joueur2.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur2);
        
        scorej2.setFont(new Font("Arial",22));
        scorej2.setTextFill(Color.WHITE);
        scorej2.setPrefWidth(largeur_scene/3.9);
        scorej2.setPrefHeight(hauteur_scene/12);
        scorej2.setTranslateX(largeur_scene/38);
        scorej2.setTranslateY(hauteur_scene-hauteur_scene/1.2);
        scorej2.setStyle("-fx-alignment: center;");
        this.getChildren().add(scorej2);
        
        if(m.config.conditionVictoire==2){
            joueur1 = new Label(m.j1.nom);
            scorej1=new Label(  m.j1.scoreTotal+"");
        }
        else{
            joueur1 = new Label(m.j1.nom);
            scorej1=new Label(m.j1.manchesGagnees+"");
        }
        joueur1.setFont(new Font("Arial",22));
        joueur1.setTextFill(Color.WHITE);
        joueur1.setPrefWidth(largeur_scene/16);
        joueur1.setPrefHeight(hauteur_scene/12);
        joueur1.setTranslateX(largeur_scene/48);
        joueur1.setTranslateY(hauteur_scene-hauteur_scene/1.12);
        joueur1.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur1);
        
                
        scorej1.setFont(new Font("Arial",22));
        scorej1.setTextFill(Color.WHITE);
        scorej1.setPrefWidth(largeur_scene/3.9);
        scorej1.setPrefHeight(hauteur_scene/12);
        scorej1.setTranslateX(largeur_scene/38);
        scorej1.setTranslateY(hauteur_scene-hauteur_scene/1.12);
        scorej1.setStyle("-fx-alignment: center;");
        this.getChildren().add(scorej1);
        
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
        
        nom_plisJ1 = new Label(m.j1.nom);
        nom_plisJ1.setFont(new Font("Arial",18));
        nom_plisJ1.setTextFill(Color.WHITE);
        nom_plisJ1.setPrefWidth(largeur_scene/10);
        nom_plisJ1.setPrefHeight(hauteur_scene/20);
        nom_plisJ1.setTranslateX(largeur_scene/1.115);
        nom_plisJ1.setTranslateY(hauteur_scene/1.18);
        nom_plisJ1.setStyle("-fx-alignment: center;");
        this.getChildren().add(nom_plisJ1);
        
        nom_plisJ2 = new Label(m.j2.nom);
        nom_plisJ2.setFont(new Font("Arial",18));
        nom_plisJ2.setTextFill(Color.WHITE);
        nom_plisJ2.setPrefWidth(largeur_scene/10);
        nom_plisJ2.setPrefHeight(hauteur_scene/20);
        nom_plisJ2.setTranslateX(largeur_scene/1.115);
        nom_plisJ2.setTranslateY(hauteur_scene/14);
        nom_plisJ2.setStyle("-fx-alignment: center;");
        this.getChildren().add(nom_plisJ2);
        
        plisJ1 = new Label(String.valueOf("Plis : "+m.j1.score));
        plisJ1.setFont(new Font("Arial",18));
        plisJ1.setTextFill(Color.WHITE);
        plisJ1.setPrefWidth(largeur_scene/10);
        plisJ1.setPrefHeight(hauteur_scene/20);
        plisJ1.setTranslateX(largeur_scene/1.115);
        plisJ1.setTranslateY(hauteur_scene/1.15);
        plisJ1.setStyle("-fx-alignment: center;");
        this.getChildren().add(plisJ1);
        
        plisJ2 = new Label(String.valueOf("Plis : "+m.j2.score));
        plisJ2.setFont(new Font("Arial",18));
        plisJ2.setTextFill(Color.WHITE);
        plisJ2.setPrefWidth(largeur_scene/10);
        plisJ2.setPrefHeight(hauteur_scene/20);
        plisJ2.setTranslateX(largeur_scene/1.115);
        plisJ2.setTranslateY(hauteur_scene/10.5);
        plisJ2.setStyle("-fx-alignment: center;");
        this.getChildren().add(plisJ2);
        
        image_tour = new Label();
        image_tour.setFont(new Font("Arial",18));
        image_tour.setTranslateX(largeur_scene/1.115);
        //image_tour.setTranslateY(hauteur_scene/13);  // en haut
        image_tour.setTranslateY(hauteur_scene/1.175); // en bas
        image_tour.setGraphic(new ImageView("images/hand-right.png"));       
        this.getChildren().add(image_tour);
        
        image_tourMenu = new Label();
        image_tourMenu.setFont(new Font("Arial",18));
        image_tourMenu.setTranslateX(largeur_scene/45);
        image_tourMenu.setTranslateY(hauteur_scene-hauteur_scene/2);
        image_tourMenu.setGraphic(new ImageView("images/hand-right.png"));       
        this.getChildren().add(image_tourMenu);
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tour.setText("Tour : "+name);
                if(tour_joueur == 1){
                    name = m.j1.nom;
                }
                else if(tour_joueur == 2){
                    name = m.j2.nom;
                }
                if(mode == 2 && tour_joueur == 1){
                    image_tour.setTranslateY(hauteur_scene/1.175);
                }
                else if (mode == 2 && tour_joueur == 2){
                    image_tour.setTranslateY(hauteur_scene/13);
                }
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

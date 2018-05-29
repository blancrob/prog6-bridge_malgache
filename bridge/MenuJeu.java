/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author haudryf
 */
public class MenuJeu extends Parent {
    
    Rectangle menu;
    
    Button quit;
    Button save;
    Button load;
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
    int tour_pioche;
    int gagnant;
    int l;
    String name;
    //Les couleurs
    final static String Gris = ("343638");
    final static String Rouge=("330000");
    final static String Violet=("4c1130");
    public static String CouleurBandeau;
   
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public void tourJ(int i){
        tour_joueur = i;
    }
    
    public void mode(int i){
        mode = i;
    }
    
    public void tourP(int tour_p, int w){
        tour_pioche = tour_p;  
        gagnant = w;
    }
    
    public int lock(){
        if(l == 1){
            return 1;
        }else{
            return 0;
        }
    }
    
    public static void couleurBandeau(String CouleurPlateau){
        if (CouleurPlateau.equals(Bridge.Bleu))
            CouleurBandeau=Gris;
        else if (CouleurPlateau.equals(Bridge.Rouge))
            CouleurBandeau=Rouge;
        else if(CouleurPlateau.equals(Bridge.Vert))
            CouleurBandeau=Violet;
    }
    
    void aides(){
        Rectangle helpspace = new Rectangle(largeur_scene / 5.2, hauteur_scene / 5, Color.GREY);
        helpspace.setArcHeight(15);
        helpspace.setArcWidth(25);
        helpspace.setOpacity(0.75);
        helpspace.setTranslateX((largeur_scene - largeur_scene / 1.85) / 2.3);
        helpspace.setTranslateY((hauteur_scene - hauteur_scene / 1.2));
        Text raccourcis = new Text("RACCOURCIS CLAVIER");
        Text recommencer = new Text("T : Recommencer");
        Text option = new Text("O : Options");
        Text help = new Text("H : Aide");
        Text regles = new Text("R : Regles");
        Text charger = new Text("C : Charger");
        Text save = new Text("S : Sauvegarder");
        Text quit = new Text("Q : Quitter");
        raccourcis.setFont(new Font(30));
        raccourcis.setFill(Color.LIGHTGREY);
        raccourcis.setX(largeur_scene / 4.6);
        raccourcis.setY(hauteur_scene-(hauteur_scene/1.25));
        recommencer.setFont(new Font(20));
        recommencer.setFill(Color.LIGHTGREY);
        recommencer.setX(largeur_scene / 4.6);
        recommencer.setY(hauteur_scene-(hauteur_scene/1.3));
        option.setFont(new Font(20));
        option.setFill(Color.LIGHTGREY);
        option.setX(largeur_scene / 4.6);
        option.setY(hauteur_scene-(hauteur_scene/1.33));
        help.setFont(new Font(20));
        help.setFill(Color.LIGHTGREY);
        help.setX(largeur_scene / 4.6);
        help.setY(hauteur_scene-(hauteur_scene/1.365));
        regles.setFont(new Font(20));
        regles.setFill(Color.LIGHTGREY);
        regles.setX(largeur_scene / 4.6);
        regles.setY(hauteur_scene-(hauteur_scene/1.4));
        charger.setFont(new Font(20));
        charger.setFill(Color.LIGHTGREY);
        charger.setX(largeur_scene / 4.6);
        charger.setY(hauteur_scene-(hauteur_scene/1.44));
        save.setFont(new Font(20));
        save.setFill(Color.LIGHTGREY);
        save.setX(largeur_scene / 4.6);
        save.setY(hauteur_scene-(hauteur_scene/1.48));
        quit.setFont(new Font(20));
        quit.setFill(Color.LIGHTGREY);
        quit.setX(largeur_scene / 4.6);
        quit.setY(hauteur_scene-(hauteur_scene/1.53));
        this.getChildren().addAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        raccourcis.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        recommencer.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        help.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        regles.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        save.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        quit.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
        helpspace.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,option,help,regles,charger,save,quit);
        });
    }
    
    void regles(){
        Rectangle rulesspace = new Rectangle(largeur_scene/3,hauteur_scene/3.55,Color.GREY);
        rulesspace.setArcHeight(15);
        rulesspace.setArcWidth(25);
        rulesspace.setY((hauteur_scene/2.75));
        Text rulesmessage1 = new Text("Jouez une carte plus forte que votre adversaire !");
        Text rulesmessage2 = new Text("Elle doit être de la même couleur. Si vous");
        Text rulesmessage3 = new Text("ne pouvez pas fournir une carte de la même");
        Text rulesmessage4 = new Text("couleur, utilisez un atout, ou defaussez vous.");
        Text rulesmessage5 = new Text("Une fois le pli terminé, le vaiqueur le ramasse et");
        Text rulesmessage6 = new Text("pioche une carte parmi les tas centraux.");
        Text rulesmessage7 = new Text(" Le perdant du pli pioche après le vainqueur.");
        rulesmessage1.setFont(new Font(25));
        rulesmessage1.setFill(Color.LIGHTGREY);
        rulesmessage1.setX(largeur_scene/1000);
        rulesmessage1.setY(hauteur_scene/2.5);
        rulesmessage2.setFont(new Font(25));
        rulesmessage2.setFill(Color.LIGHTGREY);
        rulesmessage2.setX(largeur_scene/1000);
        rulesmessage2.setY(hauteur_scene/2.3);
        rulesmessage3.setFont(new Font(25));
        rulesmessage3.setFill(Color.LIGHTGREY);
        rulesmessage3.setX(largeur_scene/1000);
        rulesmessage3.setY(hauteur_scene/2.15);
        rulesmessage4.setFont(new Font(25));
        rulesmessage4.setFill(Color.LIGHTGREY);
        rulesmessage4.setX(largeur_scene/1000);
        rulesmessage4.setY(hauteur_scene/2);
        rulesmessage5.setFont(new Font(25));
        rulesmessage5.setFill(Color.LIGHTGREY);
        rulesmessage5.setX(largeur_scene/1000);
        rulesmessage5.setY(hauteur_scene/1.85);
        rulesmessage6.setFont(new Font(25));
        rulesmessage6.setFill(Color.LIGHTGREY);
        rulesmessage6.setX(largeur_scene/1000);
        rulesmessage6.setY(hauteur_scene/1.75);
        rulesmessage7.setFont(new Font(25));
        rulesmessage7.setFill(Color.LIGHTGREY);
        rulesmessage7.setX(largeur_scene/1000);
        rulesmessage7.setY(hauteur_scene/1.65);
        this.getChildren().addAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        rulesmessage1.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage2.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage3.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage4.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage5.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage6.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesmessage7.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
        rulesspace.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3,rulesmessage4,rulesmessage5,rulesmessage6,rulesmessage7);
        });
    }
    public MenuJeu(Moteur m, String couleurPlateau) {
        couleurBandeau(couleurPlateau);
        System.out.println("couleur : " +CouleurBandeau);
        menu = new Rectangle(largeur_scene/5, hauteur_scene, Color.web(CouleurBandeau));
        this.getChildren().add(menu);
        
        quit = new Button("Quitter la partie");
        quit.setPrefWidth(largeur_scene/6.4);
        quit.setPrefHeight(hauteur_scene/12);
        quit.setTranslateX(largeur_scene/48);
        quit.setTranslateY(hauteur_scene-hauteur_scene/10);
        this.getChildren().add(quit);

        save = new Button("Sauvegarder");
        save.setPrefWidth(largeur_scene/13.715);
        save.setPrefHeight(hauteur_scene/12);
        save.setTranslateX(largeur_scene/9.6);
        save.setTranslateY(hauteur_scene-hauteur_scene/5);
        this.getChildren().add(save);
        
        load = new Button("Charger");
        load.setPrefWidth(largeur_scene/13.715);
        load.setPrefHeight(hauteur_scene/12);
        load.setTranslateX(largeur_scene/48);
        load.setTranslateY(hauteur_scene-hauteur_scene/5);
        this.getChildren().add(load);

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
        help.setOnKeyPressed(keyEvent ->{
            KeyCode h = keyEvent.getCode();
            if (h.equals(KeyCode.H)){
                aides();
            }
        });
        help.setOnAction((ActionEvent event) -> {
            aides();
        });
        
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
        rules.setOnKeyPressed(keyEvent ->{
            KeyCode r = keyEvent.getCode();
            if (r.equals(KeyCode.R)){
                regles();
            }
        });
        rules.setOnAction((ActionEvent event) -> {
            regles();
        });
        
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
            condition_victoire = new Label("Partie en "+m.config.mancheMax+" manche(s)");
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
        image_tour.setTranslateX(largeur_scene/3.9);
        //image_tour.setTranslateY(hauteur_scene/13);  // en haut
        image_tour.setTranslateY(hauteur_scene/1.175); // en bas
        image_tour.setGraphic(new ImageView("images/hand-right.png"));       
        this.getChildren().add(image_tour);
        
        image_tourMenu = new Label();
        image_tourMenu.setFont(new Font("Arial",18));
        image_tourMenu.setTranslateX(largeur_scene/45);
        image_tourMenu.setTranslateY(hauteur_scene-hauteur_scene/2);
        image_tourMenu.setGraphic(new ImageView("images/hand-right_mini.png"));       
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
                if(mode == 2 && tour_joueur == 1 && tour_pioche == 0){
                    image_tour.setTranslateY(hauteur_scene/1.175);
                }
                if (mode == 2 && tour_joueur == 1 && (tour_pioche == 1 && gagnant == 1)||(tour_pioche == 2 && gagnant != 1)){
                    image_tour.setTranslateY(hauteur_scene/2.2);
                }
                if (mode == 2 && tour_joueur == 2){
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

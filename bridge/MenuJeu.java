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
    void quitter(){
        Rectangle quitno = new Rectangle(largeur_scene/3,hauteur_scene/3.6,Color.GREY);
        Rectangle quityes = new Rectangle(largeur_scene/6,hauteur_scene/3.6,Color.GREY);
        Text quitmessage = new Text("Voulez vous vraiment quitter ?");
        //Text yesmessage = new Text("OUI");
        //Text nomessage = new Text("NON");
        quitmessage.setFont(new Font(45));
        //yesmessage.setFont(new Font(45));
        //nomessage.setFont(new Font(45));
        quitmessage.setFill(Color.LIGHTGREY);
        //yesmessage.setFill(Color.LIGHTGREY);
        //nomessage.setFill(Color.LIGHTGREY);
        quitmessage.setX(largeur_scene/70);
        //yesmessage.setX(largeur_scene/4.5);
        //nomessage.setX(largeur_scene/1.38);
        quitmessage.setY(quityes.getHeight()-(hauteur_scene/8));
        //yesmessage.setY(quityes.getHeight()-135);
        //nomessage.setY(quityes.getHeight()-135);
        //quitno.toFront();
        //quityes.toFront();
        //quitmessage.toFront();
        MenuJeu.this.getChildren().addAll(quitno,quityes,quitmessage);
        quitno.setOnMouseEntered((MouseEvent me) -> {
            quitno.setFill(Color.RED);
            //MenuJeu.this.getChildren().remove(quitmessage);
            //MenuJeu.this.getChildren().add(nomessage);
        });
        quitno.setOnMouseExited((MouseEvent me) -> {
            quitno.setFill(Color.GREY);
            //MenuJeu.this.getChildren().remove(nomessage);
            //MenuJeu.this.getChildren().add(quitmessage);
        });
        quitno.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(quitno,quityes,quitmessage);
        });
        quityes.setOnMouseEntered((MouseEvent me) -> {
            quityes.setFill(Color.GREEN);
            //MenuJeu.this.getChildren().remove(quitmessage);
            //MenuJeu.this.getChildren().add(yesmessage);
        });
        quityes.setOnMouseExited((MouseEvent me) -> {
            quityes.setFill(Color.GREY);
            //MenuJeu.this.getChildren().remove(yesmessage);
            //MenuJeu.this.getChildren().add(quitmessage);
        });
        quityes.setOnMouseClicked((MouseEvent me) -> {
            exit(0);
        });
    }
    void aides(){
        Rectangle helpspace = new Rectangle(largeur_scene/4,hauteur_scene/3.6,Color.GREY);
        Text raccourcis = new Text("RACCOURCIS CLAVIER");
        Text recommencer = new Text("T : Recommencer");
        Text help = new Text("H : Aide");
        Text regles = new Text("R : Regles");
        Text save = new Text("S : Sauvegarder");
        Text quit = new Text("Q : Quitter"); 
        helpspace.setY((hauteur_scene/1.35));
        raccourcis.setFont(new Font(30));
        raccourcis.setFill(Color.LIGHTGREY);
        raccourcis.setX(largeur_scene/70);
        raccourcis.setY(hauteur_scene-(hauteur_scene/4.7));
        recommencer.setFont(new Font(25));
        recommencer.setFill(Color.LIGHTGREY);
        recommencer.setX(largeur_scene/70);
        recommencer.setY(hauteur_scene-(hauteur_scene/5.4));
        help.setFont(new Font(25));
        help.setFill(Color.LIGHTGREY);
        help.setX(largeur_scene/70);
        help.setY(hauteur_scene-(hauteur_scene/6.35));
        regles.setFont(new Font(25));
        regles.setFill(Color.LIGHTGREY);
        regles.setX(largeur_scene/70);
        regles.setY(hauteur_scene-(hauteur_scene/7.7));
        save.setFont(new Font(25));
        save.setFill(Color.LIGHTGREY);
        save.setX(largeur_scene/70);
        save.setY(hauteur_scene-(hauteur_scene/9.8));
        quit.setFont(new Font(25));
        quit.setFill(Color.LIGHTGREY);
        quit.setX(largeur_scene/70);
        quit.setY(hauteur_scene-(hauteur_scene/13.5));
        this.getChildren().addAll(helpspace,raccourcis,recommencer,help,regles,save,quit);
        helpspace.setOnMouseEntered((MouseEvent me) -> {
            helpspace.setFill(Color.LIGHTGREY);
            raccourcis.setFill(Color.GREY);
            recommencer.setFill(Color.GREY);
            help.setFill(Color.GREY);
            regles.setFill(Color.GREY);
            save.setFill(Color.GREY);
            quit.setFill(Color.GREY);
        });
        helpspace.setOnMouseExited((MouseEvent me) -> {
            helpspace.setFill(Color.GREY);
            raccourcis.setFill(Color.LIGHTGREY);
            recommencer.setFill(Color.LIGHTGREY);
            help.setFill(Color.LIGHTGREY);
            regles.setFill(Color.LIGHTGREY);
            save.setFill(Color.LIGHTGREY);
            quit.setFill(Color.LIGHTGREY);
        });
        helpspace.setOnMouseClicked((MouseEvent me) -> {
            this.getChildren().removeAll(helpspace,raccourcis,recommencer,help,regles,save,quit);
        });
    }
    void regles(){
        Rectangle rulesspace = new Rectangle(largeur_scene/3,hauteur_scene/2.16,Color.GREY);
        rulesspace.setY((hauteur_scene/3.6));
        Text rulesmessage1 = new Text("olala c'est bien les regles, on adore les regles,");
        Text rulesmessage2 = new Text("les regles c'est notre dada, sauf que d'apres ");
        Text rulesmessage3 = new Text("laurence faut pas les lires");
        rulesmessage1.setFont(new Font(30));
        rulesmessage1.setFill(Color.LIGHTGREY);
        rulesmessage1.setX(largeur_scene/70);
        rulesmessage1.setY(rulesspace.getHeight()-(hauteur_scene/6.75));
        rulesmessage2.setFont(new Font(30));
        rulesmessage2.setFill(Color.LIGHTGREY);
        rulesmessage2.setX(largeur_scene/70);
        rulesmessage2.setY(rulesspace.getHeight()-(hauteur_scene/9));
        rulesmessage3.setFont(new Font(30));
        rulesmessage3.setFill(Color.LIGHTGREY);
        rulesmessage3.setX(largeur_scene/70);
        rulesmessage3.setY(rulesspace.getHeight()-(hauteur_scene/13.5));
        this.getChildren().addAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3);
        rulesspace.setOnMouseEntered((MouseEvent me) -> {
            rulesspace.setFill(Color.LIGHTGREY);
            rulesmessage1.setFill(Color.GREY);
            rulesmessage2.setFill(Color.GREY);
            rulesmessage3.setFill(Color.GREY);
        });
        rulesspace.setOnMouseExited((MouseEvent me) -> {
            rulesspace.setFill(Color.GREY);
            rulesmessage1.setFill(Color.LIGHTGREY);
            rulesmessage2.setFill(Color.LIGHTGREY);
            rulesmessage3.setFill(Color.LIGHTGREY);
        });
        rulesspace.setOnMouseClicked((MouseEvent me) -> {
            MenuJeu.this.getChildren().removeAll(rulesspace,rulesmessage1,rulesmessage2,rulesmessage3);
        });
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
        quit.setOnKeyPressed(keyEvent ->{
            KeyCode q = keyEvent.getCode();
            if (q.equals(KeyCode.Q)){
                quitter();
            }
        });
        quit.setOnAction((ActionEvent event) -> {
            quitter();
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
        
        if(m.config.conditionVictoire==1)
            joueur2 = new Label(m.j2.nom+"\t\t\t"+m.j2.manchesGagnees);
        else
            joueur2 = new Label(m.j2.nom+"\t\t\t"+m.j2.scoreTotal);

        joueur2.setFont(new Font("Arial",22));
        joueur2.setTextFill(Color.WHITE);
        joueur2.setPrefWidth(largeur_scene/6.4);
        joueur2.setPrefHeight(hauteur_scene/12);
        joueur2.setTranslateX(largeur_scene/48);
        joueur2.setTranslateY(hauteur_scene-hauteur_scene/1.2);
        joueur2.setStyle("-fx-alignment: center;");
        this.getChildren().add(joueur2);
        
        if(m.config.conditionVictoire==1)
            joueur1 = new Label(m.j1.nom+"\t\t\t"+m.j1.manchesGagnees);
        else
            joueur1 = new Label(m.j1.nom+"\t\t\t"+m.j1.scoreTotal);
        
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

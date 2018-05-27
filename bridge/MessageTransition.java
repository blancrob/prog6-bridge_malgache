/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Florent
 */
public class MessageTransition extends Parent {

    public String message;

    Rectangle fond_message;
    Rectangle fond_victoire;
    Text message_joueur;
    Text message_victoire;

    int posX = 0;
    int posY = 700;

    public MessageTransition(int choix, String nom, double largeur, double hauteur) {
        fond_message = new Rectangle(largeur / 2.05, hauteur / 3.6, Color.GREY);
        fond_message.setHeight(hauteur / 5);
        fond_message.setWidth(largeur / 2.05);
        fond_message.setTranslateX((largeur - largeur / 5) / 2.3);
        fond_message.setTranslateY(hauteur - hauteur / 2.8);
        fond_message.setOpacity(0.75);
        fond_message.setArcHeight(15);
        fond_message.setArcWidth(25);
        this.getChildren().add(fond_message);

        switch (choix) {
            case 1:
                message_joueur = new Text(nom+", à toi de jouer !");
                break;
            case 2:
                message_joueur = new Text(nom+", pioche une carte");
                break;
        }
        message_joueur.setFont(new Font(50));
        message_joueur.setFill(Color.LIGHTGREY);
        message_joueur.setTranslateX(largeur/2.2);
        message_joueur.setTranslateY(hauteur-fond_message.getHeight()/0.85);
        message_joueur.setTextAlignment(TextAlignment.CENTER);

        this.getChildren().add(message_joueur);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                fond_message.setFill(Color.LIGHTGREY);
                message_joueur.setFill(Color.GREY);
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                fond_message.setFill(Color.GREY);
                message_joueur.setFill(Color.LIGHTGREY);
            }
        });
        this.setOnMouseClicked((MouseEvent me) -> {
            this.setVisible(false);
        });
    }

    public MessageTransition(Moteur m, double largeur, double hauteur) {
        fond_victoire = new Rectangle(largeur, hauteur / 3.6, Color.GREY);
        fond_victoire.setArcHeight(15);
        fond_victoire.setArcWidth(25);
        this.getChildren().add(fond_victoire);

        message_victoire = new Text("TEST");
        
        if(m.finPartie()){  //En cas de fin de Partie
            if(m.config.conditionVictoire==1 && m.j1.manchesGagnees != m.j2.manchesGagnees){  //Partie en Nombre de Manches
                if(m.config.mode == 1){  //En Joueur Contre Joueur
                    if(m.j1.manchesGagnees>m.j2.manchesGagnees){  //Si le joueur 1 gagne
                        message_victoire = new Text("Bravo " + m.j1.nom + ", Victoire avec " + m.j1.manchesGagnees + " manches gagnées !");
                    }else if(m.j2.manchesGagnees>m.j1.manchesGagnees){  //Si le joueur 2 gagne
                        message_victoire = new Text("Bravo " + m.j2.nom + ", Victoire avec " + m.j2.manchesGagnees + " manches gagnées !");
                    }
                }else if(m.config.mode == 2){
                    if(m.j1.manchesGagnees>m.j2.manchesGagnees){  //Si le joueur 1 gagne
                        message_victoire = new Text("Bravo " + m.j1.nom + ", Victoire avec " + m.j1.manchesGagnees + " manches gagnées !");
                    }else{  //Si le joueur 2 gagne
                        message_victoire = new Text("Défaite ... l'IA l'emporte avec " + m.j2.manchesGagnees + " manches gagnées");
                    }
                }
            }else{  //Partie au score
                if(m.config.mode == 1){  //En Joueur Contre Joueur
                    if(m.j1.scoreTotal>m.j2.scoreTotal){  //Si le joueur 1 gagne
                        message_victoire = new Text("Bravo " + m.j1.nom + ", Victoire avec " + m.j1.scoreTotal + " plis !");
                    }else if(m.j2.scoreTotal>m.j1.scoreTotal){  //Si le joueur 2 gagne
                        message_victoire = new Text("Bravo " + m.j2.nom + ", Victoire avec " + m.j2.scoreTotal + " plis !");
                    }
                }else if(m.config.mode == 2){
                    if(m.j1.scoreTotal>m.j2.scoreTotal){  //Si le joueur 1 gagne
                        message_victoire = new Text("Bravo " + m.j1.nom + ", Victoire avec " + m.j1.scoreTotal + " plis !");
                    }else{  //Si le joueur 2 gagne
                        message_victoire = new Text("Défaite ... l'IA l'emporte avec " + m.j2.scoreTotal + " plis");
                    }
                }
            }
            
            
            message_victoire.setFont(new Font(50));
            message_victoire.setFill(Color.LIGHTGREY);
            message_victoire.setX(300 / 2.5);
            message_victoire.setY(fond_victoire.getHeight() - 135);
            this.getChildren().add(message_victoire);

            this.setTranslateX(posX);
            this.setTranslateY(posY);

            this.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    fond_victoire.setFill(Color.LIGHTGREY);
                    message_victoire.setFill(Color.GREY);
                }
            });
            this.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    fond_victoire.setFill(Color.GREY);
                    message_victoire.setFill(Color.LIGHTGREY);
                }
            });
            this.setOnMouseClicked((MouseEvent me) -> {
                this.setVisible(false);
            });
            
        }else{  //En cas de fin de manche mais pas de fin de partie

            if (m.j1.score == m.j2.score) {
                message_victoire = new Text("Egalité");
            } else {
                if(m.config.mode == 1){  //En Joueur Contre Joueur
                    if(m.j1.score>m.j2.score){  //Si le joueur 1 gagne
                        message_victoire = new Text(m.j1.nom + ", Victoire de la manche " + m.config.manche + " avec " + m.j1.score + " plis");
                    }else{  //Si le joueur 2 gagne
                        message_victoire = new Text(m.j2.nom + ", Victoire de la manche " + m.config.manche + " avec " + m.j2.score + " plis");
                    }
                }else if(m.config.mode == 2){
                    if(m.j1.score>m.j2.score){  //Si le joueur 1 gagne
                        message_victoire = new Text("Victoire de la manche " + m.config.manche + " avec " + m.j1.score + " plis");
                    }else{  //Si le joueur 2 gagne
                        message_victoire = new Text("Défaite de la manche " + m.config.manche + " avec " + m.j1.score + " plis");
                    }
                }
            }
            
            message_victoire.setFont(new Font(50));
            message_victoire.setFill(Color.LIGHTGREY);
            message_victoire.setX(300 / 2.5);
            message_victoire.setY(fond_victoire.getHeight() - 135);
            this.getChildren().add(message_victoire);

            this.setTranslateX(posX);
            this.setTranslateY(posY);

            this.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    fond_victoire.setFill(Color.LIGHTGREY);
                    message_victoire.setFill(Color.GREY);
                }
            });
            this.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    fond_victoire.setFill(Color.GREY);
                    message_victoire.setFill(Color.LIGHTGREY);
                }
            });
            this.setOnMouseClicked((MouseEvent me) -> {
                this.setVisible(false);
            });
        }
    }
}

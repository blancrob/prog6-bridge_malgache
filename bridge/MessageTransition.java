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

/**
 *
 * @author Florent
 */
public class MessageTransition extends Parent {
    
    public String message;
    
    Rectangle fond_message;
    Text message_joueur;
    
    int posX = 0;
    int posY = 700;
    
    public MessageTransition(int choix, String nom, double largeur){
        fond_message = new Rectangle(largeur,300,Color.GREY);
        fond_message.setArcHeight(15);
        fond_message.setArcWidth(25);
        this.getChildren().add(fond_message);
        
        switch(choix){
            case 1:
                message_joueur = new Text(nom+", prêt à jouer ?");
                break;
            case 2:
                message_joueur = new Text(nom+", c'est à toi de piocher !");
                break;
        }
        //message_joueur = new Text(nom+" Prêt?");
        message_joueur.setFont(new Font(50));
        message_joueur.setFill(Color.LIGHTGREY);
        message_joueur.setX(largeur/2.5);
        message_joueur.setY(fond_message.getHeight()-135);
        this.getChildren().add(message_joueur);
        
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        
        this.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                fond_message.setFill(Color.LIGHTGREY);
                message_joueur.setFill(Color.GREY);
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                fond_message.setFill(Color.GREY);
                message_joueur.setFill(Color.LIGHTGREY);
            }
        });
        this.setOnMouseClicked((MouseEvent me) -> {
            this.setVisible(false);
        });
    }
}

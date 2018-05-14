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
    
    int posX = 632;
    int posY = 455;
    
    public MessageTransition(){
        fond_message = new Rectangle(250,150,Color.YELLOW);
        fond_message.setArcHeight(10);
        fond_message.setArcWidth(10);
        this.getChildren().add(fond_message);
        
        message_joueur = new Text("Prêt?");
        message_joueur.setFont(new Font(25));
        message_joueur.setFill(Color.GREY);
        message_joueur.setX(100);
        message_joueur.setY(75);
        this.getChildren().add(message_joueur);
        
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        
        this.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                fond_message.setFill(Color.LIGHTGREY);
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                fond_message.setFill(Color.YELLOW);
            }
        });
    }
}

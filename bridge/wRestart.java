package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author haudryf
 */
public class wRestart extends Parent {
    
    Rectangle menu;
    
    Button oui;
    Button non;
    
    Label message;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    
    public wRestart(){
        menu = new Rectangle (largeur_scene/2,hauteur_scene/2, Color.LIMEGREEN);
        menu.setTranslateX(largeur_scene/4);
        menu.setTranslateY(hauteur_scene/4);
        this.getChildren().add(menu);
        
        oui = new Button("OUI");
        oui.setTranslateX(800);
        oui.setTranslateY(600);
        oui.setPrefWidth(100);
        oui.setPrefHeight(50);
        this.getChildren().add(oui);
        
        
        non = new Button("NON");
        non.setTranslateX(1000);
        non.setTranslateY(600);
        non.setPrefWidth(100);
        non.setPrefHeight(50);
        this.getChildren().add(non);
        
        message = new Label("Recommencer la partie ?");
        message.setFont(new Font("Arial",25));
        message.setTextFill(Color.WHITE);
        message.setTranslateX(800);
        message.setTranslateY(450);
        this.getChildren().add(message);
        
    }
    
}

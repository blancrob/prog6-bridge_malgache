package bridge;

import static java.lang.System.exit;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuConfiguration extends Application {
    
    //Les Variables
    private final int h_scene = 720;   //taille hauteur case
    private final int l_scene = 1280;  //taille largeur case
    public int player1mode = 1;
    public int player2mode = 1;
    public int jour = 1;
    
    //La fenetre
    public GridPane pane = new GridPane();
    public Scene scene = new Scene(pane,h_scene,l_scene,Color.GREY);
    
    //Les Boutons 
    public Button newgame = new Button("Nouvelle Partie");
    public Button loadgame = new Button("Charger Partie");
    public Button rules = new Button("Règles");
    public Button options = new Button("Options");
    public Button quit = new Button("<   Quitter");
    public Button firstmenu = new Button("Menu Principal");
    public Button launchgame = new Button("Lancer Partie");
    public Button advancedoptions = new Button("Options Avancées");
    public Button daynight = new Button("Jour/Nuit");
    
    //Les Textes
    public Label bridgechinois = new Label("Bridge Chinois");
    public Label bridgechinois2 = new Label("Bridge Chinois");
    public Label configgame = new Label("  Configuration de la partie");
    public Label choiceplayer = new Label("     Choix des joueurs");
    public Label player1title = new Label("Joueur 1");
    public Label player2title = new Label("Joueur 2");
    public Label victorysetup = new Label("    Condition de victoire");
    public Label p1human = new Label("Humain");
    public Label p1computer = new Label("Ordinateur");
    public Label p2human = new Label("Humain");
    public Label p2computer = new Label("Ordinateur");
    public Label points = new Label("Points");
    public Label rounds = new Label("Manches");
    
    //Les TextFields
    public TextField player1name = new TextField();
    public TextField player2name = new TextField();
    public TextField nbpoints = new TextField();
    public TextField nbrounds = new TextField();
    
    public String name1final = ("");
    public String name2final = ("");
    public int nbpointsfinal = 0;
    public int nbroundsfinal = 0;
    
    //Les ComboBox
    public ComboBox iaLevel1 = new ComboBox();
    public ComboBox iaLevel2 = new ComboBox();
    public int combobox1set = 0;
    public int combobox2set = 0;
    
    //Les CheckBox
    public CheckBox cbhuman1 = new CheckBox();
    public CheckBox cbhuman2 = new CheckBox();
    public CheckBox cbcomputer1 = new CheckBox();
    public CheckBox cbcomputer2 = new CheckBox();
    public CheckBox cbpoints = new CheckBox();
    public CheckBox cbrounds = new CheckBox();
    
    public String computer1final = ("");
    public String computer2final = ("");
    
    
    public static void main() {
        Application.launch(MenuConfiguration.class);   
    }
    
    public void firstMenu(Stage primaryStage, Button newgame, Button loadgame, Button rules, Button options, Button quit){
        primaryStage.setTitle("Menu Principal");
        pane = new GridPane();
        scene = new Scene(pane,l_scene,h_scene);
        
        //Espace entre les cases du GridPane
        pane.setHgap(75);
        pane.setVgap(45);
        
        //Espace boutons Menu principal
        VBox menu1buttons = new VBox();
        menu1buttons.setSpacing(60);
        newgame.setMaxWidth(320.0);
        loadgame.setMaxWidth(320.0);
        rules.setMaxWidth(320.0);
        options.setMaxWidth(320.0);
        menu1buttons.getChildren().addAll(newgame,loadgame,rules,options,daynight);
        pane.add(menu1buttons,5,3);
        
        bridgechinois.setFont(new Font(50));
        
        pane.add(quit,0,0);
        pane.add(bridgechinois,5,1);
        
        newgame.setOnAction((ActionEvent event) ->{
            nouvellePartie(primaryStage, firstmenu, launchgame, advancedoptions);
        });
        
        quit.setOnAction((ActionEvent event) -> {
            exit(0);
        });
        
        daynight.setOnAction((ActionEvent event) ->{
            if(jour == 1){
                pane.setStyle("-fx-color : darkgrey; -fx-background-color: darkgrey;");
                bridgechinois.setTextFill(Color.DARKGREEN);
                quit.setStyle("-fx-text-fill: darkgreen");
                newgame.setStyle("-fx-text-fill: darkgreen");
                loadgame.setStyle("-fx-text-fill: darkgreen");
                rules.setStyle("-fx-text-fill: darkgreen");
                options.setStyle("-fx-text-fill: darkgreen");
                daynight.setTextFill(Color.DARKGREEN);
                jour = 0;
            }
            else{
                pane.setStyle("-fx-color : white; -fx-background-color: white;");
                bridgechinois.setTextFill(Color.BLACK);
                bridgechinois.setTextFill(Color.BLACK);
                quit.setStyle("-fx-text-fill: black");
                newgame.setStyle("-fx-text-fill: black");
                loadgame.setStyle("-fx-text-fill: black");
                rules.setStyle("-fx-text-fill: black");
                options.setStyle("-fx-text-fill: black");
                daynight.setTextFill(Color.BLACK);
                jour = 1;
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void nouvellePartie (Stage primaryStage, Button firstmenu, Button launchgame, Button advancedoptions){
        primaryStage.setTitle("Nouvelle Partie");
        pane = new GridPane();
        scene = new Scene(pane,l_scene,h_scene);
        if(jour == 1){
            pane.setStyle("-fx-background-color: white;");
            bridgechinois2.setStyle("-fx-text-fill: black");
            configgame.setStyle("-fx-text-fill: black");
            choiceplayer.setStyle("-fx-text-fill: black");
            player1title.setStyle("-fx-text-fill: black");
            player2title.setStyle("-fx-text-fill: black");
            victorysetup.setStyle("-fx-text-fill: black");
            p1human.setStyle("-fx-text-fill: black");
            p1computer.setStyle("-fx-text-fill: black");
            p2human.setStyle("-fx-text-fill: black");
            p2computer.setStyle("-fx-text-fill: black");
            points.setStyle("-fx-text-fill: black");
            rounds.setStyle("-fx-text-fill: black");
            firstmenu.setTextFill(Color.BLACK);
            launchgame.setTextFill(Color.BLACK);
        }
        else{
            pane.setStyle("-fx-background-color: darkgrey;");
            bridgechinois2.setStyle("-fx-text-fill: darkgreen");
            configgame.setStyle("-fx-text-fill: darkgreen");
            choiceplayer.setStyle("-fx-text-fill: darkgreen");
            player1title.setStyle("-fx-text-fill: darkgreen");
            player2title.setStyle("-fx-text-fill: darkgreen");
            victorysetup.setStyle("-fx-text-fill: darkgreen");
            p1human.setStyle("-fx-text-fill: darkgreen");
            p1computer.setStyle("-fx-text-fill: darkgreen");
            p2human.setStyle("-fx-text-fill: darkgreen");
            p2computer.setStyle("-fx-text-fill: darkgreen");
            points.setStyle("-fx-text-fill: darkgreen");
            rounds.setStyle("-fx-text-fill: darkgreen");
            firstmenu.setTextFill(Color.DARKGREEN);
            launchgame.setTextFill(Color.DARKGREEN);
        }
        //Espace entre les cases du GridPane
        pane.setHgap(140);
        pane.setVgap(70);
        
        //Espace Joueur 1
        //Menu Deroulant
        if(combobox1set == 0){
            iaLevel1.getItems().addAll(
                "Novice",
                "Facile",
                "Moyenne",
                "Avancée",
                "Difficile",
                "Experte"
            );
            iaLevel1.setValue("Novice");
            combobox1set = 1;
        }
        //box human
        HBox firsthuman = new HBox();
        firsthuman.setSpacing(5);
        firsthuman.getChildren().addAll(cbhuman1,p1human);
        //box niveau de l'ordinateur
        HBox computer1 = new HBox();
        computer1.setSpacing(5);
        computer1.getChildren().addAll(cbcomputer1,p1computer,iaLevel1);
        //box globale
        VBox player1 = new VBox();
        player1.setSpacing(20);
        player1.getChildren().addAll(player1title,player1name,firsthuman,computer1);
        pane.add(player1,1,2);
        
        //Espace Joueur 2
        //Menu Deroulant
        if (combobox2set == 0){
            iaLevel2.getItems().addAll(
                "Novice",
                "Facile",
                "Moyenne",
                "Avancée",
                "Difficile",
                "Experte"
            );
            iaLevel2.setValue("Novice");
            combobox2set = 1;
        }
        //box human
        HBox secondhuman = new HBox();
        secondhuman.setSpacing(5);
        secondhuman.getChildren().addAll(cbhuman2,p2human);
        //box niveau de l'ordinateur
        HBox computer2 = new HBox();
        computer2.setSpacing(5);
        computer2.getChildren().addAll(cbcomputer2,p2computer,iaLevel2);
        //box globale
        VBox player2 = new VBox();
        player2.setSpacing(20);
        player2.getChildren().addAll(player2title, player2name,secondhuman,computer2);
        pane.add(player2,3,2);
        
        //Espace Boutons du bas
        HBox botbutton = new HBox();
        botbutton.getChildren().addAll(firstmenu,launchgame);
        pane.add(botbutton,2,4);
        
        //Espace des titres
        VBox titles = new VBox();
        titles.getChildren().addAll(bridgechinois2,configgame);
        pane.add(titles,2,1);
        
        //Espace Options avancées
        pane.add(advancedoptions,3,3);
        
        //Espace titre choix joueur
        pane.add(choiceplayer,2,2);
        
        //Espace victory setup
        VBox victorycond = new VBox();
        victorycond.setSpacing(15);
        //Espace points
        HBox pointscond = new HBox();
        pointscond.setSpacing(10);
        nbpoints.setPromptText("nombre de points");
        pointscond.getChildren().addAll(cbpoints,points,nbpoints);
        //Espace manches
        HBox roundscond = new HBox();
        roundscond.setSpacing(10);
        nbrounds.setPromptText("nombre de manches");
        roundscond.getChildren().addAll(cbrounds,rounds,nbrounds);
        
        victorycond.getChildren().addAll(victorysetup,pointscond,roundscond);
        pane.add(victorycond,2,3);
        
        bridgechinois2.setFont(new Font(38));
        configgame.setFont(new Font(20));
        choiceplayer.setFont(new Font(22));
        player1title.setFont(new Font(20));
        player2title.setFont(new Font(20));
        victorysetup.setFont(new Font(20));
        p1human.setFont(new Font(15));
        p1computer.setFont(new Font(15));
        
        player1name.setPrefColumnCount(15);
        player2name.setPrefColumnCount(15);
        player1name.setPromptText("Entrer votre nom.");
        player2name.setPromptText("Entrer votre nom.");
        
        //checkbox humain 1
        cbhuman1.setOnAction((ActionEvent event)->{
            cbcomputer1.setSelected(false);
        });
        //checkbox humain 2
        cbhuman2.setOnAction((ActionEvent event)->{
            cbcomputer2.setSelected(false);
        });
        //checkbox ordi 1
        cbcomputer1.setOnAction((ActionEvent event)->{
            cbhuman1.setSelected(false);
        });
        //checkbox ordi 2
        cbcomputer2.setOnAction((ActionEvent event)->{
            cbhuman2.setSelected(false);
        });
        //checkbox points
        cbpoints.setOnAction((ActionEvent event)->{
            cbrounds.setSelected(false);
            nbrounds.setText("");
        });
        //checkbox manches
        cbrounds.setOnAction((ActionEvent event)->{
            cbpoints.setSelected(false);
            nbpoints.setText("");
        });
        //bouton menu principal
        firstmenu.setOnAction((ActionEvent event)->{
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if(jour == 1){
                pane.setStyle("-fx-color : white; -fx-background-color: white;");
            }
            else{
                pane.setStyle("-fx-color : darkgrey; -fx-background-color: darkgrey;");    
            }
        });
        //bouton lancer partie
        launchgame.setOnAction((ActionEvent event)->{
            //last vérif param si rien n'est choisi
                if(!cbhuman1.isSelected() && !cbcomputer1.isSelected()){
                    cbhuman1.setSelected(true);
                }
                if(!cbhuman2.isSelected() && !cbcomputer2.isSelected()){
                    cbcomputer2.setSelected(true);
                    iaLevel2.setValue("Facile");
                }
            //si pas de nom de joueur choisi, mettre joueur 1 par defaut sinon prendre le nom choisi
            if ((player1name.getText().isEmpty())) {
                player1name.setText("Joueur 1");
                name1final = "Joueur 1";
            }
            else{
                name1final = player1name.getText();
            }
            //si pas de nom de joueur choisi, mettre joueur 2 par defaut sinon prendre le nom choisi
            if ((player2name.getText().isEmpty())) {
                player2name.setText("Joueur 2");
                name2final = "Joueur 2";
            }
            else{
                name2final = player2name.getText();
            }
            if(!cbpoints.isSelected() && !cbrounds.isSelected()){
                cbrounds.setSelected(true);
                nbrounds.setText("2");
                nbroundsfinal = 2;
            }
            //si pas de nombre de point choisi, mettre à 14
            if ((nbpoints.getText().isEmpty())) {
                nbpoints.setText("14");
                nbpointsfinal = 14;
            }
            else{
                nbpointsfinal = Integer.parseInt(nbpoints.getText());
            }
            //si pas de nombre de manche choisi, mettre à 2
            if ((nbrounds.getText().isEmpty())) {
                nbrounds.setText("2");
                nbroundsfinal = 2;
            }
            else{
                nbroundsfinal = Integer.parseInt(nbrounds.getText());
            }
           
            //param final pour le niveau de l'ordi 1
            if(cbcomputer1.isSelected()){
                computer1final = iaLevel1.getSelectionModel().getSelectedItem().toString();
                System.out.println("niveau de l'IA 1: " + computer1final);
            }
            else{
                System.out.println("Joueur 1 : Humain");
            }
            //param final pour le niveau de l'ordi 2
            if(cbcomputer2.isSelected()){
                computer2final = iaLevel2.getSelectionModel().getSelectedItem().toString();
                System.out.println("niveau de l'IA 2: " + computer2final);
            }
            else{
                System.out.println("Joueur 2 : Humain");
            }
            System.out.println(name1final + " contre " + name2final);
            //param final nombre de points/manches
            if(cbpoints.isSelected()){
                System.out.println("Partie en "+ nbpointsfinal +" points");
            }
            else if(cbrounds.isSelected()){
                System.out.println("Partie en "+ nbroundsfinal +" manches");
            }
            System.out.println("");
            //validation des parametres
            if((cbhuman1.isSelected() || cbcomputer1.isSelected()) && (cbhuman2.isSelected() || cbcomputer2.isSelected()) && (cbpoints.isSelected() || cbrounds.isSelected()) 
                    && (cbcomputer1.isSelected() || cbhuman1.isSelected()) 
                    && (cbcomputer2.isSelected() || cbhuman2.isSelected())
                    ){
                exit(0);
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void start(Stage primaryStage) {
        firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
    }
}

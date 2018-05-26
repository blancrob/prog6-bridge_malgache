package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static java.lang.System.exit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Bridge extends Application {

    //Les Variables
    // private final int h_scene = 720;   //taille hauteur case
    // private final int l_scene = 1280;  //taille largeur case
    Dimension screenSizeMenu = Toolkit.getDefaultToolkit().getScreenSize();
    private final int l_scene = (int) screenSizeMenu.getWidth();
    private final int h_scene = (int) screenSizeMenu.getHeight();
    public int player1mode = 1;
    public int player2mode = 1;
    public int jour = 1;

    //La fenetre
    public GridPane pane = new GridPane();
    public Scene scene = new Scene(pane, h_scene, l_scene, Color.GREY);

    //Les Boutons 
    public Button newgame = new Button("Nouvelle Partie");
    public Button loadgame = new Button("Charger Partie");
    public Button rules = new Button("Règles");
    public Button options = new Button("Options");
    public Button quit = new Button("<   Quitter");
    public Button firstmenu = new Button("\t   Menu Principal    \t\t");
    public Button launchgame = new Button("\t     Lancer Partie  \t\t");
    public Button advancedoptions = new Button("Options Avancées");
    public Button daynight = new Button("Jour/Nuit");

    //Les Textes
    public Label bridgechinois = new Label("Bridge Chinois");
    public Label bridgechinois2 = new Label("Bridge Chinois");
    public Label configgame = new Label("Configuration de la partie");
    public Label choiceplayer = new Label("     Choix des joueurs");
    public Label player1title = new Label("Vous");
    public Label player2title = new Label("Votre adversaire");
    public Label victorysetup = new Label("   Condition de victoire");
    public Label p1human = new Label("Humain");
    //public Label p1computer = new Label("Ordinateur");
    //public Label p2human = new Label("Humain");
    //public Label p2computer = new Label("Ordinateur");
    //public Label points = new Label("Points    ");
    //public Label rounds = new Label("Manches");
    
    

    //Les TextFields
    public TextField player1name = new TextField();
    public TextField player2name = new TextField();
    public TextField nbpoints = new TextField();
    public TextField nbrounds = new TextField();

    public static String name1final = ("");
    public static String name2final = ("");
    public static int typegame = 0;
    public static int typemode = 0;
    public static int nbpointsfinal = 0;
    public static int nbroundsfinal = 0;
    public static int joueur1level = 0;
    public static int joueur2level = 0;
    //Les ComboBox
    public ComboBox iaLevel2 = new ComboBox();
    public int combobox1set = 0;
    public int combobox2set = 0;

    //Les RadioButtons
    public RadioButton cbhuman2 = new RadioButton("Humain");
    public RadioButton cbcomputer2 = new RadioButton("Ordinateur");
    public RadioButton cbpoints = new RadioButton("Points");
    public RadioButton cbrounds = new RadioButton("Manches");
    
   
            
    

    public String computer1final = ("");
    public String computer2final = ("");
    
    public int messageActif = 0;
    MessageTransition mt=null;
    public boolean messagePioche=false;
    public boolean finTour=false;

    public void transitionMenuJeu(Stage primaryStage) {
        m = new Moteur2();
        m.initialiser(name1final, name2final, joueur1level, joueur2level, typegame, nbroundsfinal, nbpointsfinal, typemode);
        init_manche();
        root = new AnchorPane();
        for (int i = 0; i < j1main.length; i++) {
            root.getChildren().add(j1main[i].face);
            root.getChildren().add(j1main[i].dos);
        }
        for (int i = 0; i < j2main.length; i++) {
            root.getChildren().add(j2main[i].face);
            root.getChildren().add(j2main[i].dos);
        }
        for (int j = 0; j < pile.length; j++) {
            for (int i = 0; i < pile[j].length; i++) {
                if (pile[j][i] != null) {
                    root.getChildren().add(pile[j][i].face);
                    root.getChildren().add(pile[j][i].dos);
                }
            }
        }
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root, largeur_scene, hauteur_scene, Color.web("274e13"));//bleu : 042955 vert :274e13 rouge : 480c19
        root.setStyle("-fx-background-color:#274e13;");
        root.getChildren().add(bandeau);
        
        bandeau.restart.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(false);
            nbpoints.setText("");
            cbhuman2.setSelected(false);
            nouvellePartie(primaryStage, firstmenu, launchgame, advancedoptions);
        });
        
        bandeau.save.setOnAction((ActionEvent event) -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(System.getProperty("user.dir")));
            fc.setInitialFileName(new SimpleDateFormat("hh_mm_ss_dd_mm_yyyy").format(new Date())+".save");
            File f = fc.showSaveDialog(primaryStage);
            try {
                m.sauvegarder(f.getName());
            } catch (IOException ex) {
                Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        undo = new Button();
        ImageView imgUndo = new ImageView(new Image("images/undo.png"));
        undo.setGraphic(imgUndo);
        undo.setPrefWidth(85);
        undo.setPrefHeight(25);
        undo.setTranslateX(largeur_scene / 3.9);
        undo.setTranslateY(hauteur_scene - hauteur_scene / 11);
        root.getChildren().add(undo);
        undo.setOnMouseClicked((MouseEvent me) -> {
            m.undo();
        });
        redo = new Button();
        ImageView imgRedo = new ImageView(new Image("images/redo.png"));
        redo.setGraphic(imgRedo);
        redo.setPrefWidth(85);
        redo.setPrefHeight(25);
        redo.setTranslateX(largeur_scene / 3.3);
        redo.setTranslateY(hauteur_scene - hauteur_scene / 11);
        root.getChildren().add(redo);
        redo.setOnMouseClicked((MouseEvent me) -> {
            m.redo();
        });
        System.out.println(screenSize.getWidth());
        System.out.println(screenSize.getHeight());
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();
        launchedjeu = true;
    }

    public void firstMenu(Stage primaryStage, Button newgame, Button loadgame, Button rules, Button options, Button quit) {
        primaryStage.setTitle("Menu Principal");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);

        //Espace entre les cases du GridPane
        pane.setHgap(125);
        pane.setVgap(60);

        //Espace boutons Menu principal
        VBox menu1buttons = new VBox();
        menu1buttons.setSpacing(90);
        newgame.setMaxWidth(480.0);
        loadgame.setMaxWidth(480.0);
        rules.setMaxWidth(480.0);
        options.setMaxWidth(480.0);
        menu1buttons.getChildren().addAll(newgame, loadgame, rules, options, daynight);
        pane.add(menu1buttons, 5, 3);

        bridgechinois.setFont(new Font(75));

        pane.add(quit, 0, 0);
        pane.add(bridgechinois, 5, 1);

        newgame.setOnAction((ActionEvent event) -> {
            nouvellePartie(primaryStage, firstmenu, launchgame, advancedoptions);
        });
        
        loadgame.setOnAction((ActionEvent event) -> {
           FileChooser fc = new FileChooser();
           fc.setInitialDirectory(new File(System.getProperty("user.dir")));
           File f = fc.showOpenDialog(primaryStage); //sauver showSaveDialog
            try {
                m = new Moteur2();
                m.charger(f.getName());
            } catch (IOException ex) {
                Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        quit.setOnAction((ActionEvent event) -> {
            exit(0);
        });

        daynight.setOnAction((ActionEvent event) -> {
            if (jour == 1) {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
                bridgechinois.setTextFill(Color.WHITE);
                quit.setStyle("-fx-text-fill: white");
                newgame.setStyle("-fx-text-fill: white");
                loadgame.setStyle("-fx-text-fill: white");
                rules.setStyle("-fx-text-fill: white");
                options.setStyle("-fx-text-fill: white");
                daynight.setTextFill(Color.WHITE);
                jour = 0;
            } else {
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

    public int stringToIntDifficulte(String difficulte) {
        switch (difficulte) {
            case "":
                return 0;
            case "Novice":
                return 1;
            case "Facile":
                return 2;
            case "Moyenne":
                return 3;
            case "Avancée":
                return 4;
            case "Difficile":
                return 5;
            case "Experte":
                return 6;
        }
        return -1;
    }

    public void nouvellePartie(Stage primaryStage, Button firstmenu, Button launchgame, Button advancedoptions) {
        primaryStage.setTitle("Nouvelle Partie");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);
        if (jour == 1) {
            pane.setStyle("-fx-background-color: white;");
            bridgechinois2.setStyle("-fx-text-fill: black");
            configgame.setStyle("-fx-text-fill: black");
            choiceplayer.setStyle("-fx-text-fill: black");
            player1title.setStyle("-fx-text-fill: black");
            player2title.setStyle("-fx-text-fill: black");
            victorysetup.setStyle("-fx-text-fill: black");
            p1human.setStyle("-fx-text-fill: black");
            //p1computer.setStyle("-fx-text-fill: black");
            //p2human.setStyle("-fx-text-fill: black");
            //p2computer.setStyle("-fx-text-fill: black");
            //points.setStyle("-fx-text-fill: black");
            //rounds.setStyle("-fx-text-fill: black");
            firstmenu.setTextFill(Color.BLACK);
            launchgame.setTextFill(Color.BLACK);
        } else {
            pane.setStyle("-fx-color: black; -fx-background-color: #3b3f42;");
            bridgechinois2.setStyle("-fx-text-fill: white");
            configgame.setStyle("-fx-text-fill: white");
            choiceplayer.setStyle("-fx-text-fill: white");
            player1title.setStyle("-fx-text-fill: white");
            player2title.setStyle("-fx-text-fill: white");
            victorysetup.setStyle("-fx-text-fill: white");
            p1human.setStyle("-fx-text-fill: white");
            //p1computer.setStyle("-fx-text-fill: white");
            //p2human.setStyle("-fx-text-fill: white");
            //p2computer.setStyle("-fx-text-fill: white");
            //points.setStyle("-fx-text-fill: white");
            //rounds.setStyle("-fx-text-fill: white");
            firstmenu.setTextFill(Color.WHITE);
            launchgame.setTextFill(Color.WHITE);
        }
        //Espace entre les cases du GridPane
        pane.setHgap(160);
        pane.setVgap(80);
        
        //Espace Joueur 1
        //box globale
        VBox player1 = new VBox();
        player1.setSpacing(30);
        player1.getChildren().addAll(player1title, player1name/* firsthuman, computer1*/);
        pane.add(player1, 1, 2);

        //Espace Joueur 2
        //Menu Deroulant
        if (combobox2set == 0) {
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
        secondhuman.setSpacing(10);
        secondhuman.getChildren().addAll(cbhuman2/*, p2human*/);
        //box niveau de l'ordinateur
        HBox computer2 = new HBox();
        computer2.setSpacing(10);
        computer2.getChildren().addAll(cbcomputer2, /*p2computer,*/ iaLevel2);
        //box globale
        VBox player2 = new VBox();
        player2.setSpacing(30);
        player2.getChildren().addAll(player2title, player2name, secondhuman, computer2);
        pane.add(player2, 3, 2);

        //Espace Boutons du bas
        HBox botbutton = new HBox();
        
        botbutton.getChildren().addAll(firstmenu, launchgame);
        pane.add(botbutton, 2, 4);

        //Espace des titres
        VBox titles = new VBox();
        titles.getChildren().addAll(bridgechinois2, configgame);
        pane.add(titles, 2, 1);

        //Espace Options avancées
        pane.add(advancedoptions, 3, 3);

        //Espace titre choix joueur
        pane.add(choiceplayer, 2, 2);

        //Espace victory setup
         cbrounds.setStyle(name1final);
               
        VBox victorycond = new VBox();
        victorycond.setSpacing(30);
        //Espace points
        HBox pointscond = new HBox();
        pointscond.setSpacing(20);
        nbpoints.setPromptText("nombre de points");
        pointscond.getChildren().addAll(cbpoints, /*points,*/ nbpoints);
        //Espace manches
        HBox roundscond = new HBox();
        roundscond.setSpacing(20);
        nbrounds.setPromptText("nombre de manches");
        roundscond.getChildren().addAll(cbrounds,/* rounds,*/ nbrounds);

        victorycond.getChildren().addAll(victorysetup, pointscond, roundscond);
        pane.add(victorycond, 2, 3);

        bridgechinois2.setFont(new Font(70));
        configgame.setFont(new Font(40));
        choiceplayer.setFont(new Font(40));
        player1title.setFont(new Font(40));
        player2title.setFont(new Font(40));
        victorysetup.setFont(new Font(40));
        p1human.setFont(new Font(20));
        //p1computer.setFont(new Font(20));
        //p2human.setFont(new Font(20));
        //p2computer.setFont(new Font(20));
        //points.setFont(new Font(20));
        //rounds.setFont(new Font(20));
        cbhuman2.setFont(new Font(20));
        cbcomputer2.setFont(new Font(20));
        cbpoints.setFont(new Font(20));
        cbrounds.setFont(new Font(20));
        
        advancedoptions.setMaxWidth(400);
                
        player1name.setPrefColumnCount(25);
        player2name.setPrefColumnCount(25);
        player1name.setPromptText("Entrez votre nom");
        player2name.setPromptText("Entrez le nom de l'adversaire");

        //checkbox humain 2
        cbhuman2.setOnAction((ActionEvent event) -> {
            cbcomputer2.setSelected(false);
            cbhuman2.setSelected(true);
        });
        //checkbox ordi 2
        cbcomputer2.setOnAction((ActionEvent event) -> {
            cbhuman2.setSelected(false);
            cbcomputer2.setSelected(true);
        });
        
        iaLevel2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    cbhuman2.setSelected(false);
                    cbcomputer2.setSelected(true);
                }
        });
        //checkbox points
        /*cbrounds.setOnAction((ActionEvent event) -> {
            cbpoints.setSelected(false);
            nbrounds.getOnAction();
        });
        cbpoints.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            
        });*/
        cbpoints.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
           // nbrounds.setText("");
            cbpoints.setSelected(true);
        });
        nbpoints.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            cbpoints.setSelected(true);
        });
        //checkbox manches
        cbrounds.setOnAction((ActionEvent event) -> {
            cbpoints.setSelected(false);
         //   nbpoints.setText("");
              cbrounds.setSelected(true);
        });
        nbrounds.setOnMouseClicked((MouseEvent event) -> {
            nbrounds.selectAll();
            cbrounds.setSelected(true);
            cbpoints.setSelected(false);
        });
  
        nbpoints.setOnMouseClicked((MouseEvent event) -> {
            nbpoints.selectAll();
            cbpoints.setSelected(true);
            cbrounds.setSelected(false);
        });
        
        
        //bouton menu principal
        firstmenu.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(false);
            nbpoints.setText("");
            cbhuman2.setSelected(false);
            cbcomputer2.setSelected(false);
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: white;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
        //Config par defaut
        cbcomputer2.setSelected(true);
        iaLevel2.setValue("Facile");
        cbrounds.setSelected(true);
        nbrounds.setText("2");
         player1name.setText("Joueur 1");
         player2name.setText("Joueur 2");

        //bouton lancer partie
        launchgame.setOnAction((ActionEvent event) -> {
            //last vérif param si rien n'est choisi
            if (!cbhuman2.isSelected() && !cbcomputer2.isSelected()) {
                cbcomputer2.setSelected(true);
                iaLevel2.setValue("Facile");
            }
            if ((player1name.getText().isEmpty())) {
                player1name.setText("Joueur 1");
                name1final = "Joueur 1";
            } else {
                name1final = player1name.getText();
            }
            //si pas de nom de joueur choisi, mettre joueur 2 par defaut sinon prendre le nom choisi
            if ((player2name.getText().isEmpty())) {
                player2name.setText("Joueur 2");
                name2final = "Joueur 2";
            } else {
                name2final = player2name.getText();
            }
            if (!cbpoints.isSelected() && !cbrounds.isSelected()) {
                cbrounds.setSelected(true);
                nbrounds.setText("2");
                nbroundsfinal = 2;
            }
            //si pas de nombre de point choisi, mettre à 14
            if ((nbpoints.getText().isEmpty())) {
                nbpoints.setText("14");
                nbpointsfinal = 14;
            } else {
                nbpointsfinal = Integer.parseInt(nbpoints.getText());
            }
            //si pas de nombre de manche choisi, mettre à 2
            if ((nbrounds.getText().isEmpty())) {
                nbrounds.setText("2");
                nbroundsfinal = 2;
            } else {
                nbroundsfinal = Integer.parseInt(nbrounds.getText());
            }

            //param final pour le niveau de l'ordi 1
                System.out.println("Joueur 1 : Humain");
            //param final pour le niveau de l'ordi 2
            if (cbcomputer2.isSelected()) {
                computer2final = iaLevel2.getSelectionModel().getSelectedItem().toString();
                System.out.println("niveau de l'IA 2: " + computer2final);
            } else {
                System.out.println("Joueur 2 : Humain");
            }
            System.out.println(name1final + " contre " + name2final);

            joueur1level = stringToIntDifficulte(computer1final);
            joueur2level = stringToIntDifficulte(computer2final);
            if (joueur1level == 0 && joueur2level == 0) {
                typemode = 1;
            } else {
                if ((joueur1level != 0 && joueur2level == 0) || (joueur1level == 0 && joueur2level != 0)) {
                    typemode = 2;
                } else {
                    if (joueur1level != 0 && joueur2level != 0) {
                        typemode = 3;
                    }
                }
            }

            //param final nombre de points/manches
            if (cbpoints.isSelected()) {
                System.out.println("Partie en " + nbpointsfinal + " points");
                typegame = 2;
            } else if (cbrounds.isSelected()) {
                System.out.println("Partie en " + nbroundsfinal + " manches");
                typegame = 1;
            }
            System.out.println("");
            System.out.println("level joueur 2: " + joueur2level);
            System.out.println("Mode de la partie: " + typemode);
            System.out.println("Type de la partie: " + typegame);
            //validation des parametres
            if ((cbhuman2.isSelected() || cbcomputer2.isSelected()) && (cbpoints.isSelected() || cbrounds.isSelected())&& (cbcomputer2.isSelected() || cbhuman2.isSelected())) {
                transitionMenuJeu(primaryStage);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private final int J1 = 1;
    private final int J2 = 2;
    private final int IA = 2;

    public Moteur2 m;

    AnchorPane root;

    public Carte[] j1main;
    public Carte[] j2main;
    public Carte[][] pile;
    public Carte[] j1plis;
    public Carte[] j2plis;
    public Carte[] dosPioche;

    long temps = 0;
    long temps2 = 0;
    int carte_jouee = 0;
    int tour_joueur = J1;
    int tour_pioche = 0;
    int k = 0;

    Carte J1_carte_jouee;
    Carte J2_carte_jouee;

    public int clean = 0;
    public int pause = 0;

    public int j1_lock = 0;
    public int j2_lock = 0;

    public int select = 0;
    
    public int cheat = 0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final double largeur_scene = screenSize.getWidth();    
    private final double hauteur_scene = screenSize.getHeight();
    private final double souris_carte = hauteur_scene - (hauteur_scene / 5.45) + 1;

    //MessageTransition MessageT;
    MenuJeu bandeau;

    Button undo;
    Button redo;

    public void init_manche() {
        carte_jouee = 0;
        tour_pioche = 0;
        tour_joueur = J1;
        temps = 0;
        temps2 = 0;
        k = 0;
        clean = 0;
        pause = 0;
        j1_lock = 0;
        j2_lock = 0;
        select = 0;
        cheat = 0;

        m.config.carteP = null;
        m.config.carteS = null;
        J1_carte_jouee = null;
        J2_carte_jouee = null;

        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];
        dosPioche = new Carte[6];

        switch (m.config.donneurInitial) {
            case 0:
                m.config.donneurInitial = 1;
                m.config.donneur = 1;
                m.config.receveur = 2;
                break;
            case 1:
                m.config.donneurInitial = 2;
                m.config.donneur = 2;
                m.config.receveur = 1;
                break;
            default:
                m.config.donneurInitial = 1;
                m.config.donneur = 1;
                m.config.receveur = 2;
                break;
        }

        m.config.manche++;
        m.initialiserManche();

        System.out.println("MANCHE " + m.config.manche);
        System.out.println();

        switch (m.config.atout) {
            case 1:
                System.out.println("Atout: TREFLE");
                break;
            case 2:
                System.out.println("Atout: CARREAU");
                break;
            case 3:
                System.out.println("Atout: COEUR");
                break;
            case 4:
                System.out.println("Atout: PIQUE");
                break;
        }

        System.out.println();

        init_mainJ1J2();
        init_pile(pile);

        System.out.println();
        System.out.println("Pile ");

        for (int j = 0; j < pile.length; j++) {
            for (int i = 0; i < pile[j].length; i++) {
                if (pile[j][i] != null) {
                    m.afficherCarte(pile[j][i]);
                    pile[j][i].dos.setVisible(false);
                    pile[j][i].face.setVisible(true);
                }
            }
            System.out.println();
        }

        System.out.println("Main J1");

        for (int i = 0; i < j1main.length; i++) {
            if (j1main[i] != null) {
                m.afficherCarte(j1main[i]);
                j1main[i].dos.setVisible(false);
                j1main[i].face.setVisible(true);
            }
        }

        System.out.println();
        System.out.println("Main J2");

        for (int i = 0; i < j2main.length; i++) {
            if (j2main[i] != null) {
                m.afficherCarte(j2main[i]);
                j2main[i].dos.setVisible(false);
                j2main[i].face.setVisible(true);
            }
        }

        System.out.println();

        if (m.config.mode == 1 && m.config.donneur == J1) {
            tour_joueur = J1;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, J2);
        } else if (m.config.mode == 1 && m.config.donneur == J2) {
            tour_joueur = J2;
            affichage_face_main(j2main, J2);
            affichage_dos_main(j1main, J1);
        } else if (m.config.mode == 2 && m.config.donneur == J1) {
            tour_joueur = J1;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, IA);
        } else if (m.config.mode == 2 && m.config.donneur == IA) {
            tour_joueur = IA;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, IA);
            temps = System.currentTimeMillis();
        }
        affichage_dos_pile(pile);
        affichage_face_pile(pile);
        maj_handler_main();
        maj_handler_pile();

        bandeau = new MenuJeu(m);
        bandeau.tourJ(tour_joueur);

        if (m.config.manche > 1) {
            root.getChildren().clear();
            for (int i = 0; i < j1main.length; i++) {
                if (j1main[i] != null) {
                    root.getChildren().add(j1main[i].face);
                    root.getChildren().add(j1main[i].dos);
                }
            }

            for (int i = 0; i < j2main.length; i++) {
                if (j2main[i] != null) {
                    root.getChildren().add(j2main[i].face);
                    root.getChildren().add(j2main[i].dos);
                }
            }

            for (int j = 0; j < pile.length; j++) {
                for (int i = 0; i < pile[j].length; i++) {
                    if (pile[j][i] != null) {
                        root.getChildren().add(pile[j][i].face);
                        root.getChildren().add(pile[j][i].dos);
                    }
                }
            }
            root.getChildren().add(bandeau);
        }
    }

    public void init_main(Carte[] main, int j) {
        String color;
        String number;

        if (j == J1) {
            for (int i = 0; i < m.j1.main.pile.size(); i++) {
                main[i] = m.j1.main.pile.get(i);
            }
        } else {
            for (int i = 0; i < m.j2.main.pile.size(); i++) {
                main[i] = m.j2.main.pile.get(i);
            }
        }

        for (int i = 0; i < main.length; i++) {
            switch (main[i].couleur) {
                case 1:
                    color = "TREFLE";
                    break;
                case 2:
                    color = "CARREAU";
                    break;
                case 3:
                    color = "COEUR";
                    break;
                default:
                    color = "PIQUE";
                    break;
            }

            number = Integer.toString(main[i].valeur);
            String carte = color + "_" + number + ".png";
            ImagePattern img = new ImagePattern(new Image("images/" + carte));
            main[i].face.setFill(img);
            main[i].face.toFront();

            if (m.config.mode == 2 && j == IA) {
                main[i].face.setVisible(false);
            }
        }
    }

    public void init_mainJ1J2() {
        init_main(j1main, J1);
        init_main(j2main, J2);
    }

    public void init_pile(Carte[][] pile) {
        String color;
        String number;

        for (int j = 0; j < m.config.pioche.length; j++) {
            for (int i = 0; i < m.config.pioche[j].pile.size(); i++) {
                pile[j][i] = m.config.pioche[j].pile.get(i);
            }
            if (m.config.pioche[j].pile.size() == 0) {
                pile[j][0] = null;
            }
        }

        for (int j = 0; j < pile.length; j++) {
            for (int i = 0; i < pile[j].length; i++) {
                if (pile[j][0] != null) {
                    switch (pile[j][i].couleur) {
                        case 1:
                            color = "TREFLE";
                            break;
                        case 2:
                            color = "CARREAU";
                            break;
                        case 3:
                            color = "COEUR";
                            break;
                        default:
                            color = "PIQUE";
                            break;
                    }
                    number = Integer.toString(pile[j][i].valeur);
                    String carte = color + "_" + number + ".png";
                    ImagePattern img = new ImagePattern(new Image("images/" + carte));
                    pile[j][i].face.setFill(img);
                }
            }
        }        
    }

    public void maj_plis(Carte[] plis, int j) {
        Iterator<Carte> it;
        if (j == 1) {
            it = m.j1.tas.iterateur();
        } else {
            it = m.j2.tas.iterateur();
        }
        Carte c;
        int i = 0;
        while (it.hasNext()) {
            c = it.next();
            plis[i] = c;
            i++;
        }
    }

    public void affichage_dos_plis(Carte[] plis, int j) {
        int t;
        double posY;
        if (j == J1) {
            t = m.j1.tas.pile.size();
            posY = hauteur_scene / 1.5;
        } else {
            t = m.j2.tas.pile.size();
            posY = hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte;
        }
        for (int i = 0; i < t; i++) {
            plis[i].face.setVisible(false);
            plis[i].dos.setTranslateX(largeur_scene - plis[i].largeur_carte * 1.25);
            plis[i].dos.setTranslateY(posY);
            plis[i].dos.setVisible(true);
        }
    }

    public void affichage_dos_plis(Carte[] plis, int j, double posY) {
        int t;
        if (j == J1) {
            t = m.j1.tas.pile.size();
        } else {
            t = m.j2.tas.pile.size();
        }
        for (int i = 0; i < t; i++) {
            if (plis[i] != null) {
                plis[i].face.setVisible(false);
                plis[i].dos.setTranslateX(largeur_scene - plis[i].largeur_carte * 1.25);
                plis[i].dos.setTranslateY(posY);
                plis[i].dos.setVisible(true);
            }
        }
    }

    public void affichage_face_main(Carte[] main, int j) {
        int t;
        if (j == 1) {
            t = m.j1.main.pile.size();
        } else {
            t = m.j2.main.pile.size();
        }
        for (int i = 0; i < t; i++) {
            main[i].face.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
            main[i].face.setTranslateY(hauteur_scene - main[i].hauteur_carte * 0.75);
            main[i].face.setVisible(true);
        }
    }

    public void affichage_face_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            if (pile[j][0] != null) {
                //pile[j][0].face.setTranslateX(largeur_scene / 2.75 + (pile[j][0].largeur_carte * 1.35 * j));
                pile[j][0].face.setTranslateX(largeur_scene/3 + (pile[j][0].largeur_carte * 1.4 * j));
                pile[j][0].face.setTranslateY((hauteur_scene / 2) - (pile[j][0].hauteur_carte / 2));
                pile[j][0].face.setVisible(true);
                pile[j][0].face.toFront();
            }
        }
    }
    
    public void affichage_dos_main(Carte[] main, int j) {
        int t;
        
        String color;
        String number;
        
        for (int i = main.length; i < 11; i++) {
            main[i].dos.setVisible(false);
        }

        if (j == J1) {
            //t = j1main.length;
            t = m.j1.main.pile.size();
            init_main(j1main, J1);
        } else {
            //t = j2main.length;
            t = m.j2.main.pile.size();
            if (m.config.mode == 1) {
                init_main(j2main, J2);
            } else {
                init_main(j2main, IA);
            }
        }
        
        if(m.config.mode == 1){
            for (int i = 0; i < t; i++) {
                main[i].face.setVisible(false);

                main[i].dos.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].dos.setTranslateY(-(main[i].hauteur_carte * 0.25));

                ImagePattern img = new ImagePattern(new Image("images/DOS_ROUGE.png")); //DOS_BLEU, DOS_ROUGE, DOS_OR, DOS_NOIR, DOS_VERT
                main[i].dos.setFill(img);
                main[i].dos.setVisible(true);
                main[i].dos.toFront();
            }      
        }
        
        else if(m.config.mode == 2) {
            for (int i = 0; i < t; i++) {
                switch (main[i].couleur) {
                    case 1:
                        color = "TREFLE";
                        break;
                    case 2:
                        color = "CARREAU";
                        break;
                    case 3:
                        color = "COEUR";
                        break;
                    default:
                        color = "PIQUE";
                        break;
                }

                number = Integer.toString(main[i].valeur);

                String carte = color + "_" + number + ".png";

                ImagePattern img = new ImagePattern(new Image("images/" + carte));
                main[i].face.setFill(img);

                ImagePattern img2 = new ImagePattern(new Image("images/DOS_ROUGE.png")); //DOS_BLEU, DOS_ROUGE, DOS_OR, DOS_NOIR, DOS_VERT
                main[i].dos.setFill(img2);

                main[i].face.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].face.setTranslateY(0);

                main[i].dos.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].dos.setTranslateY(-(main[i].hauteur_carte * 0.25));

                if(cheat == 0){
                    main[i].face.setVisible(false);
                    main[i].dos.setVisible(true);
                    main[i].dos.toFront();
                } else {
                    main[i].dos.setVisible(false);
                    main[i].face.setVisible(true);
                    main[i].face.toFront();
                }
            }
        }
    }     
    
    public void affichage_dos_pile(Carte[][] pile) {
        String card;
        ImagePattern img;
        for (int j = 0; j < pile.length; j++) {
            if(m.config.pioche[j].pile.size() > 0 && m.config.pioche[j] != null){
                System.out.println("Taille de la pile numéro "+j+" : "+m.config.pioche[j].pile.size());
                
                switch(m.config.pioche[j].pile.size()){
                    case 5:
                        card = "pile_5_ROUGE.png";
                        break;
                    case 4:
                        card = "pile_4_ROUGE.png";
                        break;
                    case 3:
                        card = "pile_3_ROUGE.png";
                        break;
                    case 2:
                        card = "pile_2_ROUGE.png";             
                        break;
                    default:
                        card = "ICONE_PIQUE.png";
                        break;                    
                }
                if(m.config.pioche[j].pile.size() > 1){
                    System.out.println("RAS");
                    img = new ImagePattern(new Image("images/" + card));
                    pile[j][m.config.pioche[j].pile.size()-1].dos.setFill(img);
                    pile[j][m.config.pioche[j].pile.size()-1].dos.setVisible(true);
                    pile[j][m.config.pioche[j].pile.size()-1].dos.setTranslateX(largeur_scene/2.9 + (pile[j][0].largeur_carte * 1.4 * j));
                    pile[j][m.config.pioche[j].pile.size()-1].dos.setTranslateY((hauteur_scene/2) - (pile[j][0].hauteur_carte/2));     
                }                
                else if(m.config.pioche[j].pile.size() == 1){
                    pile[j][m.config.pioche[j].pile.size()-1].dos.setFill(Color.web("274e13"));
                }
            }
        }     
    }
    
    
    public void carte_select_P(Carte[] main, int k) {
        carte_jouee = 1;

        Carte card = m.jouerCoupPremier(main[k]);

        if (m.config.mode == 1 && m.config.donneur == 1) {
            J1_carte_jouee = main[k];
            //main[k].face.setTranslateX(710);
            //main[k].face.setTranslateY(480);
            main[k].face.setTranslateX(largeur_scene / 1.8);
            main[k].face.setTranslateY(hauteur_scene / 1.5);
            main[k].face.toFront();
        } else if (m.config.mode == 1 && m.config.donneur == 2) {
            J2_carte_jouee = main[k];
            main[k].face.setTranslateX(largeur_scene / 1.8);
            main[k].face.setTranslateY(hauteur_scene / 1.5);
            main[k].face.toFront();
        } else if (m.config.mode == 2) {
            J1_carte_jouee = main[k];
            main[k].face.setTranslateX(largeur_scene / 1.8);
            main[k].face.setTranslateY(hauteur_scene / 1.5);
            main[k].face.toFront();
        }

        init_main(main, m.config.donneur);

        if (m.config.mode == 1) {
            pause = 1;
            affichage_face_main(main, m.config.donneur);
            temps = System.currentTimeMillis();
        } else if (m.config.mode == 2) {
            init_main(main, J1);
            affichage_face_main(j1main, J1);
            if (m.config.donneur == J1) {
                //pause = 10;

                bandeau.tourJ(IA);
                tour_joueur = IA;
                pause = 0;
                temps = System.currentTimeMillis();

            } else {
                tour_joueur = J1;
                bandeau.tourJ(J1);
                affichage_face_main(j1main, J1);
            }
            carte_jouee = 0;
        }
        //carte_jouee = 0;      Enlevé pour le gérer dans les messages en joueur contre joueur
    }

    public void carte_select_S(Carte[] main, int k) {

        //
        System.out.println("  Avant  jouerCoupSecond       ---------------J1---------------");
        for (int i = 0; i < m.j1.main.pile.size(); i++) {
            m.afficherCarte(m.j1.main.pile.get(i));
        }
        System.out.println("  Avant   jouerCoupSecond       --------------J2---------------");
        for (int i = 0; i < m.j2.main.pile.size(); i++) {
            m.afficherCarte(m.j2.main.pile.get(i));
        }
        System.out.println();
        //

        Carte card = m.jouerCoupSecond(main[k]);

        if (m.config.mode == 1 && m.config.receveur == 1) {
            J1_carte_jouee = main[k];
        } else if (m.config.mode == 1 && m.config.receveur == 2) {
            J2_carte_jouee = main[k];
        } else if (m.config.mode == 2) {
            J1_carte_jouee = main[k];
        }

        //
        System.out.println(" Apres  jouerCoupSecond       ----------------J1---------------");
        for (int i = 0; i < m.j1.main.pile.size(); i++) {
            m.afficherCarte(m.j1.main.pile.get(i));
        }
        System.out.println("   Apres  jouerCoupSecond       --------------J2---------------");
        for (int i = 0; i < m.j2.main.pile.size(); i++) {
            m.afficherCarte(m.j2.main.pile.get(i));
        }
        System.out.println();
        //

        if (card != null) {

            carte_jouee = 1;

            main[k].face.setTranslateX(largeur_scene / 1.8);
            main[k].face.setTranslateY(hauteur_scene / 1.5);
            main[k].face.toFront();

            init_main(j1main, J1);

            if (m.config.mode == 1) {
                init_main(j2main, J2);
                for (int i = 0; i < main.length; i++) {
                    if (main[i] != card) {
                        main[i].face.setVisible(false);
                    }
                }
            }

            m.config.taille--;
            System.out.println("taille = " + m.config.taille);

            m.config.gagnantPli();
            System.out.println("Le joueur " + m.config.gagnant + " gagne le pli");
            m.config.donneur = m.config.gagnant;
            m.config.receveur = m.config.perdant;
            System.out.println();
            m.rangerPli();

            if (m.config.mode == 1) {
                messagePioche=true;
                pause = 2;
                affichage_face_main(main, m.config.receveur);
                temps = System.currentTimeMillis();

            } else if (m.config.mode == 2) {
                affichage_face_main(j1main, J1);

                if (m.config.gagnant == J1) {
                    bandeau.tourJ(J1);
                    m.config.perdant = IA;
                } else {
                    bandeau.tourJ(IA);
                    m.config.perdant = J1;
                    temps = System.currentTimeMillis();
                }
            }

            if (!m.config.piochable()) {
                carte_jouee = 0;
                tour_pioche = 0;
                init_main(j1main, J1);
                if (m.config.mode == 2) {
                    for (int i = 0; i < 11; i++) {
                        j2main[i].dos.setVisible(false);
                    }
                }
                init_main(j2main, J2);
                maj_handler_main();
                maj_handler_pile();
                if (m.config.mode == 2) {
                    clean = 1;
                    affichage_dos_main(j2main, IA);
                }
                if (m.config.mode == 1) {
                    J1_carte_jouee.face.setVisible(true);
                    J2_carte_jouee.face.setVisible(true);
                    temps = System.currentTimeMillis();
                    pause = 5;
                } else {
                    J1_carte_jouee.face.setVisible(true);
                    J2_carte_jouee.face.setVisible(true);
                    temps = System.currentTimeMillis();
                    if (m.config.donneur == J1) {
                        tour_joueur = J1;
                        bandeau.tourJ(J1);
                        affichage_face_main(j1main, J1);
                    } else {
                        tour_joueur = IA;
                        temps = System.currentTimeMillis();
                        affichage_face_main(j1main, J1);
                    }
                }
            } else {
                tour_pioche = 1;
            }
        }
    }

    public void fin_manche() {
        System.out.println();
        if (m.j1.tas.taille() > m.j2.tas.taille()) {
            System.out.println("LE JOUEUR 1 GAGNE");
        } else if (m.j1.tas.taille() < m.j2.tas.taille()) {
            System.out.println("LE JOUEUR 2 GAGNE");
        } else {
            System.out.println("EGALITE");
        }
        System.out.println();
        m.maj_score();
    }

    public void score() {
        //m.j1.score = m.j1.tas.taille() / 2;
        //m.j2.score = m.j2.tas.taille() / 2;
        System.out.println("Manche: " + m.config.manche);
        System.out.println("Score joueur 1: " + (m.j1.score));
        System.out.println("Score joueur 2: " + (m.j2.score));
        System.out.println();
        System.out.println("Tas joueur 1:");
        Iterator<Carte> it = m.j1.tas.iterateur();
        while (it.hasNext()) {
            m.afficherCarte(it.next());
        }

        System.out.println();
        System.out.println("Tas joueur 2:");
        it = m.j2.tas.iterateur();
        while (it.hasNext()) {
            m.afficherCarte(it.next());
        }

        //m.j1.scoreTotal = m.j1.scoreTotal + m.j1.score;
        //m.j2.scoreTotal = m.j2.scoreTotal + m.j2.score;
    }

    public boolean carte_jouable(Carte c, int j) {
        if ((m.config.donneur == J1 && j == J1) || (m.config.donneur == J2 && j == J2)) {
            return true;
        } else if (m.config.donneur == J1 && j == J2) {
            Carte[] same_color = m.j2.jouables(J1_carte_jouee.couleur);
            if (same_color == null) {
                return true;
            }
            for (int i = 0; i < same_color.length; i++) {
                if (same_color[i] == c) {
                    return true;
                }
            }
        } else if (m.config.donneur == J2 && j == J1) {
            Carte[] same_color = m.j1.jouables(J2_carte_jouee.couleur);
            if (same_color == null) {
                return true;
            }
            for (int i = 0; i < same_color.length; i++) {
                if (same_color[i] == c) {
                    return true;
                }
            }
        }
        return false;
    }

    public void maj_handler_unitMain(int n, Carte[] main, int j) {
        main[n].face.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j))) {
                    main[n].face.setTranslateY(souris_carte);
                }
            }
        });

        main[n].face.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j))) {
                    main[n].face.setTranslateY(hauteur_scene - main[n].hauteur_carte * 0.75);
                }
            }
        });
        
        main[n].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                
                if (m.config.mode == 2 && cheat == 1 && main!=j1main) {
                    for(int i=0;i<j2main.length;i++){                        
                        j2main[i].face.setVisible(false);
                        j2main[i].dos.setVisible(true);
                        j2main[i].dos.toFront();
                        cheat = 0;
                    }
                }
                
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j))) {
                    if (m.config.donneur == j) {
                        carte_select_P(main, n);
                    } else {
                        carte_select_S(main, n);
                    }
                }
                
            }
        });    
        
        /*j2main[n].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (m.config.mode == 2 && cheat == 1) {
                    for(int i=0;i<j2main.length;i++){
                        j2main[i].face.setVisible(false);
                        j2main[i].dos.setVisible(true);
                        j2main[i].dos.toFront();
                        cheat = 0;
                    }
                }
            }
        });*/
        
        j2main[n].dos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (m.config.mode == 2 && cheat == 0) {
                    for(int i=0;i<j2main.length;i++){                       
                        j2main[i].dos.setVisible(false);
                        j2main[i].face.setVisible(true);
                        j2main[i].face.toFront();
                        cheat = 1;
                    }
                }
            }
        });
    }

    public void maj_handler_main() {
        for (k = 0; k < j1main.length; k++) {
            maj_handler_unitMain(k, j1main, J1);
        }
        for (k = 0; k < j2main.length; k++) {
            maj_handler_unitMain(k, j2main, J2);
        }      
    }

    public void maj_handler_unitPile(int n) {
        pile[n][0].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (tour_pioche == 2 && (m.config.mode == 1 || m.config.perdant != IA) && pause == 0) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.perdant, pile[n][0], n);
                        System.out.println();
                        init_pile(pile);
                        affichage_dos_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                    }

                    m.config.donneur = m.config.gagnant;
                    m.config.receveur = m.config.perdant;

                    if (m.config.mode == 2) {
                        tour_joueur = m.config.gagnant;
                        bandeau.tourJ(m.config.receveur);
                        init_main(j1main, J1);
                        affichage_face_main(j1main, J1);
                        maj_handler_main();
                        //maj_handler_pile();
                        temps = System.currentTimeMillis();
                        clean = 1;
                        carte_jouee = 0;
                        tour_pioche = 0;
                    } else if (m.config.mode == 1) {
                        bandeau.tourJ(m.config.receveur);
                        temps = System.currentTimeMillis();
                        messagePioche = true;
                        finTour=true;
                        //Déplacé dans AnimationTimer
                        /*pause = 4;

                        if (m.config.perdant == J1) {
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        } else {
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }*/

                        temps2 = System.currentTimeMillis();
                        clean = 1;
                    }
                    //carte_jouee = 0;
                    //tour_pioche = 0;
                    if (m.config.mode == 2 && m.config.gagnant == IA) {
                        temps = System.currentTimeMillis();
                    }
                    System.out.println();
                }

                if (tour_pioche == 1 && (m.config.mode == 1 || m.config.gagnant != IA) && pause == 0) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.gagnant, pile[n][0], n);
                        System.out.println();
                        init_pile(pile);
                        affichage_dos_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        m.config.taille++;

                    }
                    if (m.config.mode == 1) {
                        //Déplacé dans AnimationTimer
                        /*if (m.config.gagnant == J1) {
                            bandeau.tourJ(J1);
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        } else {
                            bandeau.tourJ(J2);
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }*/
                        pause=-1;
                        messagePioche=true;
                        temps = System.currentTimeMillis();
                        //pause = 3;
                        //tour_pioche = 2;
                    } else if (m.config.mode == 2) {
                        if (m.config.gagnant == J1) {
                            /*bandeau.tourJ(IA);
                             init_main(j1main,J1);
                             maj_handler_main();
                            
                            
                            
                            
                             //pause = 12;
                            
                            
                            
                            
                             temps = System.currentTimeMillis();*/

                            bandeau.tourJ(IA);
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                            maj_handler_main();
                            temps = System.currentTimeMillis();

                        } else {
                            bandeau.tourJ(J1);
                        }
                        tour_pioche = 2;
                    }

                }

            }
        });
    }

    public void maj_handler_pile() {
        for (k = 0; k < m.config.pioche.length; k++) {
            if (pile[k][0] != null) {
                maj_handler_unitPile(k);
            }
        }
    }
    public boolean launchedmenu = false;
    public boolean launchedjeu = false;

    @Override
    public void start(Stage primaryStage) {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (launchedmenu == false) {
                    firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
                    launchedmenu = true;
                } else if (launchedjeu == true) {

                    if (m.finManche() && temps + 2000 < System.currentTimeMillis()) {    //Si la manche est finie
                        if (m.config.taille == 0) {
                            Boolean V1 = m.config.conditionVictoire == 1 && m.config.manche < m.config.mancheMax;
                            Boolean V2 = m.config.conditionVictoire == 2 && (m.j1.scoreTotal < m.config.scoreMax && m.j2.scoreTotal < m.config.scoreMax);
                            fin_manche();
                            score();
                            if (V1 || V2) {
                                System.out.println("Manche SUIVANTE !");
                                init_manche();
                            } else {
                                System.out.println();
                                System.out.println("Score Total joueur 1: " + (m.j1.scoreTotal));
                                System.out.println("Score Total joueur 2: " + (m.j2.scoreTotal));
                                if (m.j1.scoreTotal > m.j2.scoreTotal) {
                                    System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
                                } else if (m.j1.scoreTotal < m.j2.scoreTotal) {
                                    System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
                                } else {
                                    System.out.println("EGALITE");
                                }
                                System.exit(0);
                            }
                        }
                    }
                    
                    //Au tour de l'IA de jouer sa carte
                    if ((((m.config.mode == 2 && tour_joueur == IA) || m.config.mode == 3) && carte_jouee == 0) && clean == 0 && m.config.taille > 0 && temps + 1000 < System.currentTimeMillis()) { //Cas où c'est au tour d'une IA de jouer
                        bandeau.tourJ(IA);
                        carte_jouee = 1;
                        System.out.println("IA joue sa carte");
                        J2_carte_jouee = m.jouerCoupIA(tour_joueur);
                        for (int i = 0; i < 11; i++) {
                            j2main[i].dos.setVisible(false);
                        }
                        init_main(j2main, IA);
                        affichage_dos_main(j2main, IA);
                        J2_carte_jouee.face.setVisible(true);
                        //Affichage de sa carte à l'IA
                        J2_carte_jouee.face.setTranslateX(largeur_scene / 1.8);
                        J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte/1.3);
                        J2_carte_jouee.face.toFront();

                        if (m.config.carteS == null) {
                            //pause = 11;

                            bandeau.tourJ(J1);
                            tour_joueur = J1;
                            carte_jouee = 0;
                            pause = 0;
                            temps = System.currentTimeMillis();

                            temps = System.currentTimeMillis();
                        } else {
                            m.config.taille--;
                            System.out.println("taille = " + m.config.taille);
                            temps = System.currentTimeMillis();
                        }
                    }

                    if (m.config.mode == 2 && pause == 11 && temps + 1000 < System.currentTimeMillis()) {
                        bandeau.tourJ(J1);
                        tour_joueur = J1;
                        carte_jouee = 0;
                        pause = 0;
                        temps = System.currentTimeMillis();
                    }
                    
                    //Si l'IA est le gagnant, il pioche une carte en 1er
                    if (m.config.mode == 2 && tour_pioche == 1 && m.config.gagnant == IA && temps + 500 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                        bandeau.tourJ(J1);
                        if (m.config.piochable()) {
                            m.config.afficherPioche();
                            System.out.println("IA a gagné et prend une carte en 1er");
                            Carte c = m.piocheIA(IA);
                            m.config.afficherPioche();
                            System.out.println();
                            for (int i = 0; i < 11; i++) {
                                j2main[i].dos.setVisible(false);
                            }
                            init_main(j2main, IA);
                            affichage_dos_main(j2main, IA);
                            init_pile(pile);
                            affichage_dos_pile(pile);
                            affichage_face_pile(pile);
                            maj_handler_pile();
                            System.out.println("Main J2");

                            for (int i = 0; i < j2main.length; i++) {
                                if (j2main[i] != null) {
                                    m.afficherCarte(j2main[i]);
                                }
                            }

                            System.out.println();
                            m.config.taille++;
                            tour_pioche = 2;
                        }
                    }
                    
                    //Si l'IA est le perdant, il pioche une carte en 1nd
                    if (m.config.mode == 2 && tour_pioche == 2 && m.config.perdant == IA && temps + 500 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                        bandeau.tourJ(IA);
                        if (m.config.piochable()) {
                            m.config.afficherPioche();
                            System.out.println("IA a perdu et prend une carte en 2nd");
                            Carte c = m.piocheIA(IA);
                            m.config.afficherPioche();
                            System.out.println();
                            for (int i = 0; i < 11; i++) {
                                j2main[i].dos.setVisible(false);
                            }
                            init_main(j2main, IA);
                            affichage_dos_main(j2main, IA);
                            init_pile(pile);
                            affichage_dos_pile(pile);
                            affichage_face_pile(pile);
                            maj_handler_pile();
                            System.out.println("Main J2");

                            for (int i = 0; i < j2main.length; i++) {
                                if (j2main[i] != null) {
                                    m.afficherCarte(j2main[i]);
                                }
                            }

                            temps = System.currentTimeMillis();
                            clean = 1;

                            System.out.println();
                            //init_main(j2main, IA);
                            m.config.donneur = m.config.gagnant;
                            m.config.receveur = m.config.perdant;

                            tour_joueur = m.config.gagnant;
                            bandeau.tourJ(m.config.gagnant);
                            maj_handler_main();
                            maj_handler_pile();
                            carte_jouee = 0;
                            tour_pioche = 0;

                            if (m.config.gagnant == IA) {
                                temps = System.currentTimeMillis();
                            }

                            System.out.println();
                        }
                    }
                    
                    //On met à jour les plis et retire les cartes jouées en JcIA
                    if (m.config.mode == 2 && clean == 1 && temps + 1000 < System.currentTimeMillis()) {
                        bandeau.tourJ(m.config.gagnant);
                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);
                        if (J1_carte_jouee != null && J2_carte_jouee != null) {
                            root.getChildren().remove(J1_carte_jouee);
                            root.getChildren().remove(J2_carte_jouee);
                        }

                        if (m.j1.tas.taille() == 2) {
                            maj_plis(j1plis, J1);
                            affichage_dos_plis(j1plis, J1);
                        }

                        if (m.j2.tas.taille() == 2) {
                            maj_plis(j2plis, IA);
                            affichage_dos_plis(j2plis, IA);
                        }

                        clean = 0;
                        temps = System.currentTimeMillis();
                    }
                    
                    //On met à jour les plis et retire les cartes jouées en JcJ
                    if (m.config.mode == 1 && clean == 1 && temps2 + 1000 < System.currentTimeMillis()) {
                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);
                        if (J1_carte_jouee != null && J2_carte_jouee != null) {
                            root.getChildren().remove(J1_carte_jouee);
                            root.getChildren().remove(J2_carte_jouee);
                        }

                        if (m.j1.tas.taille() == 2 && j1_lock == 0) {
                            maj_plis(j1plis, J1);
                            affichage_dos_plis(j1plis, J1);
                            j1_lock = 1;
                        }

                        if (m.j2.tas.taille() == 2 && j2_lock == 0) {
                            maj_plis(j2plis, J2);
                            affichage_dos_plis(j2plis, J2);
                            j2_lock = 1;
                        }
                        clean = 0;
                    }

                    if (m.config.mode == 2 && pause == 10 && temps + 1000 < System.currentTimeMillis()) {
                        bandeau.tourJ(IA);
                        tour_joueur = IA;
                        pause = 0;
                        temps = System.currentTimeMillis();
                    }
                    
                    //On y rentre lorsque que c'est l'IA qui est receveur et qui a joué sa carte juste avant
                    if (m.config.mode == 2 && m.config.receveur == IA && m.config.carteP != null && m.config.carteS != null && temps + 1000 < System.currentTimeMillis()) {
                        System.out.println("Fin tour  - resultat !");
                        m.config.gagnantPli();
                        System.out.println("Le joueur " + m.config.gagnant + " gagne le pli");
                        m.config.donneur = m.config.gagnant;
                        m.config.receveur = m.config.perdant;
                        bandeau.tourJ(m.config.gagnant);
                        System.out.println();
                        m.rangerPli();
                        if (m.finManche()) {
                            temps = System.currentTimeMillis();
                        }
                        affichage_face_main(j1main, J1);
                        m.config.afficherPioche();

                        maj_plis(j1plis, J1);
                        maj_plis(j2plis, IA);
                        if (m.config.gagnant == J1) {
                            m.config.perdant = IA;
                        } else {
                            m.config.perdant = J1;
                            temps = System.currentTimeMillis();
                        }

                        //Temporairement//
                        if (!m.config.piochable()) {
                            init_main(j1main, J1);
                            for (int i = 0; i < 11; i++) {
                                j2main[i].dos.setVisible(false);
                            }
                            init_main(j2main, IA);
                            affichage_dos_main(j2main, IA);
                            clean = 1;
                            maj_handler_main();
                            maj_handler_pile();
                            carte_jouee = 0;
                            tour_pioche = 0;
                            J1_carte_jouee.face.setVisible(true);
                            J2_carte_jouee.face.setVisible(true);
                            temps = System.currentTimeMillis();
                            if (m.config.donneur == J1) {
                                tour_joueur = J1;
                                bandeau.tourJ(J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                tour_joueur = IA;
                                bandeau.tourJ(IA);
                                temps = System.currentTimeMillis();
                                affichage_face_main(j1main, J1);
                            }
                        } else {
                            tour_pioche = 1;
                        }
                    }

                    if (m.config.mode == 2 && pause == 12 && temps + 1000 < System.currentTimeMillis()) {
                        bandeau.tourJ(IA);
                        init_main(j1main, J1);
                        affichage_face_main(j1main, J1);
                        maj_handler_main();
                        pause = 0;
                        temps = System.currentTimeMillis();
                    }

                    //On rentre ici une fois que le donneur a joué sa carte et on tourne le plateau pour que le jeu du receveur soit en bas
                    if (m.config.mode == 1 && pause == 1 && temps + 1000 < System.currentTimeMillis() && carte_jouee == 0) {
                        if (m.config.donneur == J1) {
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte/1.3);

                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));
                            for (int i = 0; i < j1main.length; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].face.setVisible(false);
                                }
                            }
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J1main
                            affichage_dos_main(j1main, J1);
                            tour_joueur = J2;
                            bandeau.tourJ(J2);
                            affichage_face_main(j2main, J2);
                        } else {
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte/1.3);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);
                            for (int i = 0; i < j2main.length; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].face.setVisible(false);
                                }
                            }
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J2main
                            affichage_dos_main(j2main, J2);
                            tour_joueur = J1;
                            bandeau.tourJ(J1);
                            affichage_face_main(j1main, J1);
                        }
                        pause = 0;
                    }
                    
                    //On rentre ici une fois que le receveur a joué sa carte et on tourne le plateau pour que le jeu du gagnant soit en bas
                    if (m.config.mode == 1 && pause == 2 && temps + 1000 < System.currentTimeMillis() && carte_jouee == 0) {
                        bandeau.tourJ(m.config.gagnant);
                        if (m.config.receveur == J1) {
                            for (int i = 0; i < j1main.length; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].face.setVisible(false);
                                }
                            }
                            for (int i = 0; i < j2main.length; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                        } else {
                            for (int i = 0; i < j2main.length; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].face.setVisible(false);
                                }
                            }
                            for (int i = 0; i < j1main.length; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                        }
                        if (m.config.gagnant == J1) {
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte/1.3);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);
                            affichage_face_main(j1main, J1);
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            affichage_dos_main(j2main, J2);
                            m.config.perdant = J2;
                        } else {
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte/1.3);
                            J2_carte_jouee.face.setTranslateY((hauteur_scene / 1.5));
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));
                            affichage_face_main(j2main, J2);
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            affichage_dos_main(j1main, J1);
                            m.config.perdant = J1;
                        }
                        pause = 0;
                    }
                    
                    //On rentre ici une fois que le gagnant a pioché une carte dans l'une des pioches et on tourne le plateau pour que le jeu du perdant soit en bas
                    if (m.config.mode == 1 && pause == 3 && temps + 1000 < System.currentTimeMillis()) {
                        bandeau.tourJ(m.config.perdant);
                        if (m.config.gagnant == J1) {
                            J2_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte/1.3);
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            for (int i = 0; i < j1main.length; i++) {
                                j1main[i].face.setVisible(false);
                            }
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J1main
                            affichage_dos_main(j1main, J1);
                            System.out.println();
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        } else {
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte/1.3);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            for (int i = 0; i < j2main.length; i++) {
                                j2main[i].face.setVisible(false);
                                //m.afficherCarte(j2main[i]);
                            }
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J2main
                            affichage_dos_main(j2main, J2);
                            System.out.println();
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        }
                        pause = 0;
                    }
                    
                    //On rentre ici une fois que le perdant a pioché une carte dans l'une des pioches et on tourne le plateau pour que le jeu du nouveau donneur soit en bas
                    if (m.config.mode == 1 && pause == 4 && temps + 1000 < System.currentTimeMillis()) {
                        if (m.config.donneur == J1) {
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);
                            tour_joueur = J1;
                            bandeau.tourJ(J1);
                            m.config.receveur = J2;

                            init_main(j1main, J1);
                            init_main(j2main, J2);

                            for (int i = 0; i < m.j1.main.pile.size(); i++) {
                                j1main[i].face.setVisible(false);
                            }
                            for (int i = 0; i < m.j2.main.pile.size(); i++) {
                                j2main[i].face.setVisible(false);
                            }

                            maj_handler_main();
                            maj_handler_pile();
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J2main
                            affichage_dos_main(j2main, J2);
                            affichage_face_main(j1main, J1);
                        } else {
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));
                            tour_joueur = J2;
                            bandeau.tourJ(J2);
                            m.config.receveur = J1;

                            init_main(j1main, J1);
                            init_main(j2main, J2);

                            for (int i = 0; i < m.j1.main.pile.size(); i++) {
                                j1main[i].face.setVisible(false);
                            }
                            for (int i = 0; i < m.j2.main.pile.size(); i++) {
                                j2main[i].face.setVisible(false);
                            }

                            maj_handler_main();
                            maj_handler_pile();
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                if (j1main[i] != J1_carte_jouee) {
                                    j1main[i].dos.setVisible(false);
                                }
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                if (j2main[i] != J2_carte_jouee) {
                                    j2main[i].dos.setVisible(false);
                                }
                            }
                            //Afficher dos J1main
                            affichage_dos_main(j1main, J1);
                            affichage_face_main(j2main, J2);
                        }
                        pause = 0;
                    }
                    
                    //Affichage Message transition CoupPremier CoupSecond Joueur Contre Joueur
                    if(m.config.mode == 1 && m.config.carteP!=null && carte_jouee == 1){
                        if(messageActif==0){
                            if(m.config.receveur==1){
                                mt = new MessageTransition(1, m.j1.nom,largeur_scene);
                            }else{
                                mt = new MessageTransition(1, m.j2.nom,largeur_scene);
                            }
                            root.getChildren().add(mt);
                            messageActif=1;
                        }
                        
                        if(!mt.isVisible()){
                            carte_jouee=0;
                            messageActif=0;
                        }
                        
                    }
                    
                    //Affichage Message transition CoupSecond Pioche Joueur Contre Joueur
                    if(m.config.mode == 1 && messagePioche==true && carte_jouee == 1 && m.config.piochable()){
                        if(messageActif==0){
                            if(m.config.gagnant==1){
                                mt = new MessageTransition(2, m.j1.nom,largeur_scene);
                            }else{
                                mt = new MessageTransition(2, m.j2.nom,largeur_scene);
                            }
                            root.getChildren().add(mt);
                            messageActif=1;
                        }
                        
                        if(!mt.isVisible()){
                            messagePioche=false;
                            carte_jouee = 0;
                            tour_pioche = 1;
                            messageActif=0;
                        }
                        
                    }
                    
                    //Affichage Message transition PremierePioche DeuxiemePioche Joueur Contre Joueur
                    if(m.config.mode == 1 && messagePioche==true && tour_pioche==1){
                        if(messageActif==0){
                            if(m.config.perdant==1){
                                mt = new MessageTransition(2, m.j1.nom,largeur_scene);
                            }else{
                                mt = new MessageTransition(2, m.j2.nom,largeur_scene);
                            }
                            root.getChildren().add(mt);
                            messageActif=1;
                        }
                        
                        if(!mt.isVisible()){
                            messagePioche=false;
                            if (m.config.gagnant == J1) {
                                bandeau.tourJ(J1);
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                bandeau.tourJ(J2);
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            pause = 3;
                            tour_pioche = 2;
                            finTour=false;
                            messageActif=0;
                        }
                    }
                    
                    //Affichage Message transition FinManche Début Autre Manche Joueur Contre Joueur
                    if(m.config.mode == 1 && messagePioche==true && finTour==true){
                        if(messageActif==0){
                            if(m.config.donneur==1){
                                mt = new MessageTransition(1, m.j1.nom,largeur_scene);
                            }else{
                                mt = new MessageTransition(1, m.j2.nom,largeur_scene);
                            }
                            root.getChildren().add(mt);
                            messageActif=1;
                        }
                        
                        if(!mt.isVisible()){
                            messagePioche=false;
                            pause = 4;
                            if (m.config.perdant == J1) {
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            finTour=false;
                            carte_jouee = 0;
                            tour_pioche = 0;
                            messageActif=0;
                        }
                    }
                    
                    //On rentre ici quand les 2 joueurs ont joué une carte et que la pioche est vide, et on retourne le plateau pour que le gagnant soit en bas
                    if (m.config.mode == 1 && pause == 5 && temps + 1000 < System.currentTimeMillis()) {

                        System.out.println("Entree");

                        init_main(j1main, J1);
                        init_main(j2main, J2);

                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);

                        for (int i = 0; i < m.j1.main.pile.size(); i++) {
                            j1main[i].face.setVisible(false);
                        }
                        for (int i = 0; i < m.j2.main.pile.size(); i++) {
                            j2main[i].face.setVisible(false);
                        }

                        init_main(j1main, J1);
                        init_main(j2main, J2);

                        if (m.config.donneur == J1) {
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);
                            tour_joueur = J1;
                            bandeau.tourJ(J1);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                //if (j1main[i] != J1_carte_jouee) {
                                j1main[i].dos.setVisible(false);
                                //}
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                //if (j2main[i] != J2_carte_jouee) {
                                j2main[i].dos.setVisible(false);
                                //}
                            }
                            //Afficher dos J2main
                            affichage_dos_main(j2main, J2);
                            affichage_face_main(j1main, J1);
                        } else {
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));
                            tour_joueur = J2;
                            bandeau.tourJ(J2);
                            //Cacher dos J1main
                            for (int i = 0; i < 11; i++) {
                                //if (j1main[i] != J1_carte_jouee) {
                                j1main[i].dos.setVisible(false);
                                //}
                            }
                            //Cacher dos J2main
                            for (int i = 0; i < 11; i++) {
                                //if (j2main[i] != J2_carte_jouee) {
                                j2main[i].dos.setVisible(false);
                                //}
                            }
                            //Afficher dos J1main
                            affichage_dos_main(j1main, J1);
                            affichage_face_main(j2main, J2);
                        }

                        pause = 0;

                        System.out.println("Sortie");
                    }
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

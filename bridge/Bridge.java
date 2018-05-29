package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javafx.util.Duration.seconds;

public class Bridge extends Application {

    //Les Variables
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

    //Les Couleurs
    public String couleurDos = ("ROUGE");

    final public static String Bleu = ("042955");
    final public static String Vert = ("274e13");
    final public static String Rouge = ("480c19");
    public String couleurPlateau = Vert;

    public int messageActif = 0;

    MessageTransition mt = null;
    public boolean messagePioche = false;
    public boolean finTour = false;
    public boolean messageFinManche = false;
    public boolean messageFinPartie = false;

    void sauver(Stage primaryStage) {
        Rectangle sauvno = new Rectangle(largeur_scene / 5.7, hauteur_scene / 5, Color.GREY);
        sauvno.setTranslateX(((largeur_scene - largeur_scene / 1.85) / 2.3) + largeur_scene / 5.7);
        sauvno.setTranslateY(hauteur_scene - hauteur_scene / 2.8);
        sauvno.setOpacity(0.75);
        sauvno.toFront();
        Rectangle sauvyes = new Rectangle(largeur_scene / 5.7, hauteur_scene / 5, Color.GREY);
        sauvyes.setTranslateX((largeur_scene - largeur_scene / 1.85) / 2.3);
        sauvyes.setTranslateY(hauteur_scene - hauteur_scene / 2.8);
        sauvyes.setOpacity(0.75);
        sauvyes.toFront();
        Text sauvmessage = new Text("Sauvegarder avant de quitter ?");
        sauvmessage.setFont(new Font(35));
        sauvmessage.setFill(Color.LIGHTGREY);
        sauvmessage.setX(largeur_scene / 4.6);
        sauvmessage.setY(hauteur_scene / 1.325);
        root.getChildren().addAll(sauvno, sauvyes, sauvmessage);
        sauvno.setOnMouseEntered((MouseEvent me) -> {
            sauvno.setFill(Color.RED);
        });
        sauvno.setOnMouseExited((MouseEvent me) -> {
            sauvno.setFill(Color.GREY);
        });
        sauvno.setOnMouseClicked((MouseEvent me) -> {
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
        sauvyes.setOnMouseEntered((MouseEvent me) -> {
            sauvyes.setFill(Color.GREEN);
        });
        sauvyes.setOnMouseExited((MouseEvent me) -> {
            sauvyes.setFill(Color.GREY);
        });
        sauvyes.setOnMouseClicked((MouseEvent me) -> {
            sauvegarder(primaryStage);
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
    }

    void quitter(Stage primaryStage) {
        Rectangle quitno = new Rectangle(largeur_scene / 5.7, hauteur_scene / 5, Color.GREY);
        quitno.setTranslateX(((largeur_scene - largeur_scene / 1.85) / 2.3) + largeur_scene / 5.7);
        quitno.setTranslateY(hauteur_scene - hauteur_scene / 2.8);
        quitno.setOpacity(0.75);
        quitno.toFront();
        Rectangle quityes = new Rectangle(largeur_scene / 5.7, hauteur_scene / 5, Color.GREY);
        quityes.setTranslateX((largeur_scene - largeur_scene / 1.85) / 2.3);
        quityes.setTranslateY(hauteur_scene - hauteur_scene / 2.8);
        quityes.setOpacity(0.75);
        quityes.toFront();
        Text quitmessage = new Text("Voulez vous vraiment quitter ?");
        quitmessage.setFont(new Font(35));
        quitmessage.setFill(Color.LIGHTGREY);
        quitmessage.setX(largeur_scene / 4.6);
        quitmessage.setY(hauteur_scene / 1.325);
        root.getChildren().addAll(quitno, quityes, quitmessage);
        quitno.setOnMouseEntered((MouseEvent me) -> {
            quitno.setFill(Color.RED);
        });
        quitno.setOnMouseExited((MouseEvent me) -> {
            quitno.setFill(Color.GREY);
        });
        quitno.setOnMouseClicked((MouseEvent me) -> {
            root.getChildren().removeAll(quitno, quityes, quitmessage);
        });
        quityes.setOnMouseEntered((MouseEvent me) -> {
            quityes.setFill(Color.GREEN);
        });
        quityes.setOnMouseExited((MouseEvent me) -> {
            quityes.setFill(Color.GREY);
        });
        quityes.setOnMouseClicked((MouseEvent me) -> {
            root.getChildren().removeAll(quitno, quityes, quitmessage);
            sauver(primaryStage);
        });
    }

    void sauvegarder(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.setInitialFileName(new SimpleDateFormat("hh_mm_ss_dd_mm_yyyy").format(new Date()) + ".save");
        File f = fc.showSaveDialog(primaryStage);
        try {
            m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                    J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                    message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                    finTour, messageFinManche, messageFinPartie);
            m.sauvegarder(f.getName());
        } catch (IOException ex) {
            Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void charger(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File f = fc.showOpenDialog(primaryStage); //sauver showSaveDialog
        try {

            //m = new Moteur2();
            m.charger(f.getName());

            temps = m.config.temps;
            temps2 = m.config.temps2;
            carte_jouee = m.config.carte_jouee;
            tour_joueur = m.config.tour_joueur;
            tour_pioche = m.config.tour_pioche;
            k = m.config.k;
            J1_carte_jouee = m.config.J1_carte_jouee;
            J2_carte_jouee = m.config.J2_carte_jouee;
            clean = m.config.clean;
            pause = m.config.pause;
            j1_lock = m.config.j1_lock;
            j2_lock = m.config.j2_lock;
            select = m.config.select;
            cheat = m.config.cheat;
            message_t = m.config.message_t;
            animation_cartePiochee = m.config.animation_cartePiochee;
            animation_t = m.config.animation_t;
            J1_lastCard = m.config.J1_lastCard;
            J2_lastCard = m.config.J2_lastCard;
            affichage_initial_pioche = m.config.affichage_initial_pioche;
            messagePioche = m.config.messagePioche;
            finTour = m.config.finTour;
            messageFinManche = m.config.messageFinManche;
            messageFinPartie = m.config.messageFinPartie;

            initialiserCharger(primaryStage);
        } catch (IOException ex) {
            Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void option() {
        Rectangle optionspace = new Rectangle(largeur_scene / 3.8, hauteur_scene / 5, Color.GREY);
        Rectangle bluebg = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.BLUE);
        Rectangle greenbg = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.GREEN);
        Rectangle redbg = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.RED);

        Rectangle bluedos = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.BLUE);
        Rectangle reddos = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.RED);
        Rectangle greendos = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.GREEN);
        Rectangle golddos = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.GOLD);
        Rectangle blackdos = new Rectangle(hauteur_scene / 24, hauteur_scene / 24, Color.BLACK);
        Text fond = new Text("Fond :");
        Text dos = new Text("Dos :");
        // Text alert = new Text("La couleur s'actualisera au prochain pli");
        optionspace.setArcHeight(15);
        optionspace.setArcWidth(25);
        optionspace.setX(largeur_scene / 1.57);
        optionspace.setY((hauteur_scene - hauteur_scene / 1.2));
        bluebg.setArcHeight(15);
        bluebg.setArcWidth(25);
        bluebg.setX(largeur_scene / 1.18);
        bluebg.setY((hauteur_scene - hauteur_scene / 1.25));
        greenbg.setArcHeight(15);
        greenbg.setArcWidth(25);
        greenbg.setX(largeur_scene / 1.28);
        greenbg.setY((hauteur_scene - hauteur_scene / 1.25));
        redbg.setArcHeight(15);
        redbg.setArcWidth(25);
        redbg.setX(largeur_scene / 1.40);
        redbg.setY((hauteur_scene - hauteur_scene / 1.25));
        fond.setFont(new Font(25));
        fond.setFill(Color.LIGHTGREY);
        fond.setX(largeur_scene / 1.53);
        fond.setY((hauteur_scene - hauteur_scene / 1.29));

        bluedos.setArcHeight(15);
        bluedos.setArcWidth(25);
        bluedos.setX(largeur_scene / 1.44);
        bluedos.setY((hauteur_scene - hauteur_scene / 1.39));
        reddos.setArcHeight(15);
        reddos.setArcWidth(25);
        reddos.setX(largeur_scene / 1.355);
        reddos.setY((hauteur_scene - hauteur_scene / 1.39));
        greendos.setArcHeight(15);
        greendos.setArcWidth(25);
        greendos.setX(largeur_scene / 1.28);
        greendos.setY((hauteur_scene - hauteur_scene / 1.39));
        golddos.setArcHeight(15);
        golddos.setArcWidth(25);
        golddos.setX(largeur_scene / 1.213);
        golddos.setY((hauteur_scene - hauteur_scene / 1.39));
        blackdos.setArcHeight(15);
        blackdos.setArcWidth(25);
        blackdos.setX(largeur_scene / 1.15);
        blackdos.setY((hauteur_scene - hauteur_scene / 1.39));
        dos.setFont(new Font(25));
        dos.setFill(Color.LIGHTGREY);
        dos.setX(largeur_scene / 1.53);
        dos.setY((hauteur_scene - hauteur_scene / 1.45));

        bluebg.setOnMouseClicked((MouseEvent me) -> {
            root.setStyle("-fx-background-color:#042955;");
            couleurPlateau= Bleu;
            bandeau.couleurBandeau(Bleu);
            changerCouleurDos(couleurDos);
            //bandeau.menu.setStyle("-fx-background-color:#"+MenuJeu.Gris+";");
        });
        greenbg.setOnMouseClicked((MouseEvent me) -> {
            root.setStyle("-fx-background-color:#274e13;");
            couleurPlateau= Vert;
            bandeau.couleurBandeau(Vert);
            changerCouleurDos(couleurDos);
            //bandeau.menu.setStyle("-fx-background-color:#"+MenuJeu.Violet+";");
        });
        redbg.setOnMouseClicked((MouseEvent me) -> {
            root.setStyle("-fx-background-color:#480c19;");
            couleurPlateau= Rouge;
            bandeau.couleurBandeau(Rouge);
            changerCouleurDos(couleurDos);
            //bandeau.menu.setStyle("-fx-background-color:#"+MenuJeu.Rouge+";");
        });

        bluedos.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("BLEU");
            changerCouleurDos(couleurDos);
        });
        reddos.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("ROUGE");
            changerCouleurDos(couleurDos);
        });
        greendos.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("VERT");
            changerCouleurDos(couleurDos);
        });
        golddos.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("OR");
            changerCouleurDos(couleurDos);
        });
        blackdos.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("NOIR");
            changerCouleurDos(couleurDos);

        });
        optionspace.setOnMouseClicked((MouseEvent me) -> {
            root.getChildren().removeAll(optionspace, bluebg, greenbg, redbg, fond, dos, bluedos, reddos, greendos, golddos, blackdos);
        });
        fond.setOnMouseClicked((MouseEvent me) -> {
            root.getChildren().removeAll(optionspace, bluebg, greenbg, redbg, fond, dos, bluedos, reddos, greendos, golddos, blackdos);
        });
        dos.setOnMouseClicked((MouseEvent me) -> {
            root.getChildren().removeAll(optionspace, bluebg, greenbg, redbg, fond, dos, bluedos, reddos, greendos, golddos, blackdos);
        });

        root.getChildren().addAll(optionspace, bluebg, greenbg, redbg, fond, dos, bluedos, reddos, greendos, golddos, blackdos);
    }

    public void initialiserCharger(Stage primaryStage) {
        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];

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
            //tour_joueur = J1;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, J2);
        } else if (m.config.mode == 1 && m.config.donneur == J2) {
            //tour_joueur = J2;
            affichage_face_main(j2main, J2);
            affichage_dos_main(j1main, J1);
        } else if (m.config.mode == 2 && m.config.donneur == J1) {
            //tour_joueur = J1;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, IA);
        } else if (m.config.mode == 2 && m.config.donneur == IA) {
            //tour_joueur = IA;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, IA);
            temps = System.currentTimeMillis();
        }

        affichage_dos_pile(pile);
        affichage_face_pile(pile);
        maj_handler_main();
        maj_handler_pile();
        bandeau = new MenuJeu(m, couleurPlateau);
        bandeau.tourJ(tour_joueur);
        bandeau.mode(m.config.mode);

        /*if ((m.config.manche > 1)  && root!=null){
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
         }*/
        root = new AnchorPane();

        if (J1_carte_jouee != null) {
            majCarte(J1_carte_jouee);
            J1_carte_jouee.face.toFront();
            J1_carte_jouee.face.setVisible(true);
            //Affichage de sa carte à l'IA
            J1_carte_jouee.face.setTranslateX(largeur_scene / 1.8);
            J1_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
            root.getChildren().add(J1_carte_jouee.face);
        }

        if (J2_carte_jouee != null) {
            majCarte(J2_carte_jouee);
            J2_carte_jouee.face.toFront();
            J2_carte_jouee.face.setVisible(true);
            //Affichage de sa carte à l'IA
            J2_carte_jouee.face.setTranslateX(largeur_scene / 1.8);
            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte / 1.3);
            root.getChildren().add(J2_carte_jouee.face);
        }

        for (int i = 0; i < m.j1.main.taille(); i++) {
            root.getChildren().add(j1main[i].face);
            root.getChildren().add(j1main[i].dos);
        }
        for (int i = 0; i < m.j2.main.taille(); i++) {
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

        bandeau.load.setOnAction((ActionEvent event) -> {
            charger(primaryStage);
        });

        bandeau.restart.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(false);
            nbpoints.setText("");
            cbhuman2.setSelected(false);
            nouvellePartie(primaryStage, firstmenu, launchgame);
        });
        bandeau.restart.setOnKeyPressed(keyEvent -> {
            KeyCode t = keyEvent.getCode();
            if (t.equals(KeyCode.T)) {
                sauvegarder(primaryStage);
            }
        });

        bandeau.option.setOnAction((ActionEvent event) -> {
            option();
        });
        bandeau.option.setOnKeyPressed(keyEvent -> {
            KeyCode o = keyEvent.getCode();
            if (o.equals(KeyCode.O)) {
                option();
            }
        });

        bandeau.quit.setOnKeyPressed(keyEvent -> {
            KeyCode q = keyEvent.getCode();
            if (q.equals(KeyCode.Q)) {
                quitter(primaryStage);
            }
        });
        bandeau.quit.setOnAction((ActionEvent event) -> {
            quitter(primaryStage);
        });

        bandeau.save.setOnAction((ActionEvent event) -> {
            sauvegarder(primaryStage);
        });
        bandeau.save.setOnKeyPressed(keyEvent -> {
            KeyCode s = keyEvent.getCode();
            if (s.equals(KeyCode.S)) {
                sauvegarder(primaryStage);
            }
        });

        undo = new Button();
        ImageView imgUndo = new ImageView(new Image("images/undo.png"));
        undo.setGraphic(imgUndo);
        undo.setPrefWidth(55);
        undo.setPrefHeight(5);
        undo.setTranslateX(largeur_scene / 1.165);
        undo.setTranslateY(hauteur_scene - hauteur_scene / 14);
        root.getChildren().add(undo);
        undo.setOnMouseClicked((MouseEvent me) -> {
            m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                    J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                    message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                    finTour, messageFinManche, messageFinPartie);
            m.undo();

            temps = m.config.temps;
            temps2 = m.config.temps2;
            carte_jouee = m.config.carte_jouee;
            tour_joueur = m.config.tour_joueur;
            tour_pioche = m.config.tour_pioche;
            k = m.config.k;
            J1_carte_jouee = m.config.J1_carte_jouee;
            J2_carte_jouee = m.config.J2_carte_jouee;
            clean = m.config.clean;
            pause = m.config.pause;
            j1_lock = m.config.j1_lock;
            j2_lock = m.config.j2_lock;
            select = m.config.select;
            cheat = m.config.cheat;
            message_t = m.config.message_t;
            animation_cartePiochee = m.config.animation_cartePiochee;
            animation_t = m.config.animation_t;
            J1_lastCard = m.config.J1_lastCard;
            J2_lastCard = m.config.J2_lastCard;
            affichage_initial_pioche = m.config.affichage_initial_pioche;
            messagePioche = m.config.messagePioche;
            finTour = m.config.finTour;
            messageFinManche = m.config.messageFinManche;
            messageFinPartie = m.config.messageFinPartie;

            init_mainJ1J2();
            init_pile(pile);

            bandeau = new MenuJeu(m, couleurPlateau);

            bandeau.tourJ(tour_joueur);
            bandeau.mode(m.config.mode);

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
        });
        redo = new Button();
        ImageView imgRedo = new ImageView(new Image("images/redo.png"));
        redo.setGraphic(imgRedo);
        redo.setPrefWidth(55);
        redo.setPrefHeight(5);
        redo.setTranslateX(largeur_scene / 1.12);
        redo.setTranslateY(hauteur_scene - hauteur_scene / 14);
        root.getChildren().add(redo);
        redo.setOnMouseClicked((MouseEvent me) -> {
            m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                    J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                    message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                    finTour, messageFinManche, messageFinPartie);
            m.redo();

            temps = m.config.temps;
            temps2 = m.config.temps2;
            carte_jouee = m.config.carte_jouee;
            tour_joueur = m.config.tour_joueur;
            tour_pioche = m.config.tour_pioche;
            k = m.config.k;
            J1_carte_jouee = m.config.J1_carte_jouee;
            J2_carte_jouee = m.config.J2_carte_jouee;
            clean = m.config.clean;
            pause = m.config.pause;
            j1_lock = m.config.j1_lock;
            j2_lock = m.config.j2_lock;
            select = m.config.select;
            cheat = m.config.cheat;
            message_t = m.config.message_t;
            animation_cartePiochee = m.config.animation_cartePiochee;
            animation_t = m.config.animation_t;
            J1_lastCard = m.config.J1_lastCard;
            J2_lastCard = m.config.J2_lastCard;
            affichage_initial_pioche = m.config.affichage_initial_pioche;
            messagePioche = m.config.messagePioche;
            finTour = m.config.finTour;
            messageFinManche = m.config.messageFinManche;
            messageFinPartie = m.config.messageFinPartie;

            init_mainJ1J2();
            init_pile(pile);

            bandeau = new MenuJeu(m, couleurPlateau);
            bandeau.tourJ(tour_joueur);
            bandeau.mode(m.config.mode);
        });
        if (m.config.mode == 2) {
            redo = new Button();
            imgRedo = new ImageView(new Image("images/redo.png"));
            redo.setGraphic(imgRedo);
            redo.setPrefWidth(55);
            redo.setPrefHeight(5);
            redo.setTranslateX(largeur_scene / 1.12);
            redo.setTranslateY(hauteur_scene - hauteur_scene / 14);
            root.getChildren().add(redo);
            redo.setOnMouseClicked((MouseEvent me) -> {
                m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                        J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                        message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                        finTour, messageFinManche, messageFinPartie);
                m.redo();

                temps = m.config.temps;
                temps2 = m.config.temps2;
                carte_jouee = m.config.carte_jouee;
                tour_joueur = m.config.tour_joueur;
                tour_pioche = m.config.tour_pioche;
                k = m.config.k;
                J1_carte_jouee = m.config.J1_carte_jouee;
                J2_carte_jouee = m.config.J2_carte_jouee;
                clean = m.config.clean;
                pause = m.config.pause;
                j1_lock = m.config.j1_lock;
                j2_lock = m.config.j2_lock;
                select = m.config.select;
                cheat = m.config.cheat;
                message_t = m.config.message_t;
                animation_cartePiochee = m.config.animation_cartePiochee;
                animation_t = m.config.animation_t;
                J1_lastCard = m.config.J1_lastCard;
                J2_lastCard = m.config.J2_lastCard;
                affichage_initial_pioche = m.config.affichage_initial_pioche;
                messagePioche = m.config.messagePioche;
                finTour = m.config.finTour;
                messageFinManche = m.config.messageFinManche;
                messageFinPartie = m.config.messageFinPartie;

                init_mainJ1J2();
                init_pile(pile);

                bandeau = new MenuJeu(m, couleurPlateau);
                bandeau.tourJ(tour_joueur);
                bandeau.mode(m.config.mode);

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
            });
        }
        System.out.println(screenSize.getWidth());
        System.out.println(screenSize.getHeight());
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        affichage_face_main(j1main, J1);

        launchedjeu = true;

        affichage_dos_pile(pile);
        affichage_face_pile(pile);
    }

    /**
     * Applique son image à la carte c
     *
     * @param c
     */
    public void majCarte(Carte c) {
        String color;
        String number;
        switch (c.couleur) {
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

        number = Integer.toString(c.valeur);
        String carte = color + "_" + number + ".png";
        ImagePattern img = new ImagePattern(new Image("images/" + carte));
        c.face.setFill(img);
    }

    public void transitionMenuJeu(Stage primaryStage) {
        m = new Moteur2();
        m.initialiser(name1final, name2final, joueur1level, joueur2level, typegame, nbroundsfinal, nbpointsfinal, typemode);
        init_manche(primaryStage);
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

        Scene scene = new Scene(root, largeur_scene, hauteur_scene, Color.web(couleurPlateau));
        root.setStyle("-fx-background-color:#" + couleurPlateau + ";");

        root.getChildren().add(bandeau);

        bandeau.load.setOnAction((ActionEvent event) -> {
            charger(primaryStage);
        });

        bandeau.load.setOnKeyPressed(keyEvent -> {
            KeyCode l = keyEvent.getCode();
            if (l.equals(KeyCode.L)) {
                charger(primaryStage);
            }
        });

        bandeau.option.setOnAction((ActionEvent event) -> {
            option();
        });
        bandeau.option.setOnKeyPressed(keyEvent -> {
            KeyCode o = keyEvent.getCode();
            if (o.equals(KeyCode.O)) {
                option();
            }
        });

        bandeau.restart.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(false);
            nbpoints.setText("");
            cbhuman2.setSelected(false);
            nouvellePartie(primaryStage, firstmenu, launchgame);
        });
        bandeau.restart.setOnKeyPressed(keyEvent -> {
            KeyCode t = keyEvent.getCode();
            if (t.equals(KeyCode.T)) {
                sauvegarder(primaryStage);
            }
        });

        bandeau.quit.setOnKeyPressed(keyEvent -> {
            KeyCode q = keyEvent.getCode();
            if (q.equals(KeyCode.Q)) {
                quitter(primaryStage);
            }
        });
        bandeau.quit.setOnAction((ActionEvent event) -> {
            quitter(primaryStage);
        });

        bandeau.save.setOnAction((ActionEvent event) -> {
            sauvegarder(primaryStage);
        });
        bandeau.save.setOnKeyPressed(keyEvent -> {
            KeyCode s = keyEvent.getCode();
            if (s.equals(KeyCode.S)) {
                sauvegarder(primaryStage);
            }
        });
        if (m.config.mode == 2) {
            undo = new Button();
            ImageView imgUndo = new ImageView(new Image("images/undo.png"));
            undo.setGraphic(imgUndo);
            undo.setPrefWidth(55);
            undo.setPrefHeight(5);
            undo.setTranslateX(largeur_scene / 1.165);
            undo.setTranslateY(hauteur_scene - hauteur_scene / 14);
            root.getChildren().add(undo);
            undo.setOnMouseClicked((MouseEvent me) -> {
                m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                        J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                        message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                        finTour, messageFinManche, messageFinPartie);
                m.undo();

                temps = m.config.temps;
                temps2 = m.config.temps2;
                carte_jouee = m.config.carte_jouee;
                tour_joueur = m.config.tour_joueur;
                tour_pioche = m.config.tour_pioche;
                k = m.config.k;
                J1_carte_jouee = m.config.J1_carte_jouee;
                J2_carte_jouee = m.config.J2_carte_jouee;
                clean = m.config.clean;
                pause = m.config.pause;
                j1_lock = m.config.j1_lock;
                j2_lock = m.config.j2_lock;
                select = m.config.select;
                cheat = m.config.cheat;
                message_t = m.config.message_t;
                animation_cartePiochee = m.config.animation_cartePiochee;
                animation_t = m.config.animation_t;
                J1_lastCard = m.config.J1_lastCard;
                J2_lastCard = m.config.J2_lastCard;
                affichage_initial_pioche = m.config.affichage_initial_pioche;
                messagePioche = m.config.messagePioche;
                finTour = m.config.finTour;
                messageFinManche = m.config.messageFinManche;
                messageFinPartie = m.config.messageFinPartie;

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

            });
        }
        if (m.config.mode == 2) {
            redo = new Button();
            ImageView imgRedo = new ImageView(new Image("images/redo.png"));
            redo.setGraphic(imgRedo);
            redo.setPrefWidth(55);
            redo.setPrefHeight(5);
            redo.setTranslateX(largeur_scene / 1.12);
            redo.setTranslateY(hauteur_scene - hauteur_scene / 14);
            root.getChildren().add(redo);
            redo.setOnMouseClicked((MouseEvent me) -> {
                m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                        J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                        message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                        finTour, messageFinManche, messageFinPartie);
                m.redo();

                temps = m.config.temps;
                temps2 = m.config.temps2;
                carte_jouee = m.config.carte_jouee;
                tour_joueur = m.config.tour_joueur;
                tour_pioche = m.config.tour_pioche;
                k = m.config.k;
                J1_carte_jouee = m.config.J1_carte_jouee;
                J2_carte_jouee = m.config.J2_carte_jouee;
                clean = m.config.clean;
                pause = m.config.pause;
                j1_lock = m.config.j1_lock;
                j2_lock = m.config.j2_lock;
                select = m.config.select;
                cheat = m.config.cheat;
                message_t = m.config.message_t;
                animation_cartePiochee = m.config.animation_cartePiochee;
                animation_t = m.config.animation_t;
                J1_lastCard = m.config.J1_lastCard;
                J2_lastCard = m.config.J2_lastCard;
                affichage_initial_pioche = m.config.affichage_initial_pioche;
                messagePioche = m.config.messagePioche;
                finTour = m.config.finTour;
                messageFinManche = m.config.messageFinManche;
                messageFinPartie = m.config.messageFinPartie;

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
            });
        }
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();
        launchedjeu = true;
    }

    public void regles(Stage primaryStage) {
        primaryStage.setTitle("Regles");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);
        //1ST
        Text principe = new Text("\t\tPRINCIPE DU JEU\n");
        Text principe2 = new Text("Le Bridge chinois est un jeu de cartes qui se joue à deux joueur avec un jeu de 52 cartes.\nLe but du jeu est de marquer un maximum de points en réalisant des plis");
        Text setup = new Text("\t\tMISE EN PLACE\n");
        Text setup2 = new Text("Le joueur désigné comme donneur distribue 11 cartes à chaque joueurs, puis dispose le reste des cartes en 6 piles de \n5 cartes faces cachées au centre du plateau, puis il retourne la carte au sommet de chaque pile.\n\nParmi les 6 cartes visibles, la cartes la plus forte donne la couleur de l'atout.\n\nNote: si toutes les cartes visibles ont une valeur inférieur à 10, la partie se joue sans atout.");
        Button suiv1 = new Button("  Suivant >");
        VBox rules11 = new VBox();
        rules11.getChildren().addAll(principe, principe2);
        VBox rules12 = new VBox();
        rules12.getChildren().addAll(setup, setup2);
        //2ND
        Text but = new Text("\t\tBUT\n");
        Text but2 = new Text("Le but du jeu est de réaliser un plus grand nombre de pli que son adversaire. Un pli\n rapporte un point.\n\nChaque pli consiste en 2 cartes, chacune posée sur le plateau par l'un des joueurs, choisie\nparmi les cartes qu'il a en main. Le pli est rammassé par le joueur qui gagne le pli.");
        Text gagner = new Text("\t\tGAGNER UN PLI\n");
        Text gagner2 = new Text("*   Si les deux cartes sont de même couleur, alors le joueur qui a la carte la plus forte remporte le pli.\n\n*   Si l'un des joueurs n'a pas la couleur demandée, il peut au choix:\n\n\t\t\t\t\t-   jouer une carte de la couleur de l'atout pour couper et remporter le pli\n\n\t\t\t\t\t-   donner une carte de son choix, mais il perdra le pli.");
        Button prev2 = new Button("< Précédent");
        Button suiv2 = new Button("  Suivant >");
        VBox rules21 = new VBox();
        rules21.getChildren().addAll(but, but2);
        VBox rules22 = new VBox();
        rules22.getChildren().addAll(gagner, gagner2);
        //3RD
        Text manche12 = new Text("\t\tDEROULEMENT D'UNE MANCHE - 1/2\n");
        Text manche122 = new Text("Le joueur qui à donné les cartes a initialement la main, par la suite, elle est prise (ou gardée) par le joueur qui a remporté le pli.\n\n*   Le joueur qui à la main choisit une des cartes de sa main et la pose sur le\nplateau\n\n*   L'autre joueur pose à son tour une carte (il doit obligatoirement fournir la couleur\ndemandée par le premier joueur s'il le peut).\n\n*   Le joueur qui a gagné le pli le ramasse et le met de coté dans sa défausse.");
        Button prev3 = new Button("< Précédent");
        Button suiv3 = new Button("  Suivant >");
        VBox rules3 = new VBox();
        rules3.getChildren().addAll(manche12, manche122);

        //4TH
        Text manche22 = new Text("\t\tDEROULEMENT D'UNE MANCHE - 2/2\n");
        Text manche222 = new Text("Les joueurs vont maintenant piocher une carte parmi les cartes révélées sur le\nplateur:\n\n*   le joueur qui vient de gagner le pli choisi sa carte en premier et la place dans sa\nmain. Il retourne ensuite la carte qui se trouvait au dessous de la carte qu'il vient\nde piocher.\n\n*   l'autre joueur choisià son tour la carte de son choix de la même manière.\n\nNote: Lorsque toutes les piles sont épuisées, la manche continue selon les mêmes\nrègles, en supprimant la phase de choix/découverte des cartes sur le plateau.");
        Button prev4 = new Button("< Précédent");
        Button suiv4 = new Button("  Suivant >");
        VBox rules4 = new VBox();
        rules4.getChildren().addAll(manche22, manche222);
        //5TH
        Text end = new Text("\t\tFIN D'UNE MANCHE\n\n");
        Text end2 = new Text("La manche se termine lorsque les 2 joueurs n'ont plus de carte en main.\n\nOn compte alors les plis de chaque joueur et chacun se voit attribuer le\nnombre de points correspondants.\n\nOn peut alors démarrer une nouvelle manche.\n\nAu cours des manches successives, chaque joueur donne tour à tour.");
        Button prev5 = new Button("< Précédent");
        VBox rules5 = new VBox();
        rules5.getChildren().addAll(end, end2);

        Button mp = new Button("Menu Principal ");
        Button np = new Button("Nouvelle Partie");

        if (jour == 1) {
            pane.setStyle("-fx-background-color: #274e13;");
            principe.setFill(Color.BLACK);
            principe2.setFill(Color.BLACK);
            setup.setFill(Color.BLACK);
            setup2.setFill(Color.BLACK);
            but.setFill(Color.BLACK);
            but2.setFill(Color.BLACK);
            gagner.setFill(Color.BLACK);
            gagner2.setFill(Color.BLACK);
            manche12.setFill(Color.BLACK);
            manche122.setFill(Color.BLACK);
            manche22.setFill(Color.BLACK);
            manche222.setFill(Color.BLACK);
            end.setFill(Color.BLACK);
            end2.setFill(Color.BLACK);
            suiv1.setTextFill(Color.BLACK);
            prev2.setTextFill(Color.BLACK);
            suiv2.setTextFill(Color.BLACK);
            prev3.setTextFill(Color.BLACK);
            suiv3.setTextFill(Color.BLACK);
            prev4.setTextFill(Color.BLACK);
            suiv4.setTextFill(Color.BLACK);
            prev5.setTextFill(Color.BLACK);
            mp.setTextFill(Color.BLACK);

        } else {
            pane.setStyle("-fx-color: black; -fx-background-color: #3b3f42;");
            principe.setFill(Color.WHITE);
            principe2.setFill(Color.WHITE);
            setup.setFill(Color.WHITE);
            setup2.setFill(Color.WHITE);
            but.setFill(Color.WHITE);
            but2.setFill(Color.WHITE);
            gagner.setFill(Color.WHITE);
            gagner2.setFill(Color.WHITE);
            manche12.setFill(Color.WHITE);
            manche122.setFill(Color.WHITE);
            manche22.setFill(Color.WHITE);
            manche222.setFill(Color.WHITE);
            end.setFill(Color.WHITE);
            end2.setFill(Color.WHITE);
            suiv1.setTextFill(Color.WHITE);
            prev2.setTextFill(Color.WHITE);
            suiv2.setTextFill(Color.WHITE);
            prev3.setTextFill(Color.WHITE);
            suiv3.setTextFill(Color.WHITE);
            prev4.setTextFill(Color.WHITE);
            suiv4.setTextFill(Color.WHITE);
            prev5.setTextFill(Color.WHITE);
            mp.setTextFill(Color.WHITE);
            suiv1.setOnMouseEntered((MouseEvent me) -> {
                suiv1.setTextFill(Color.BLACK);
            });
            suiv1.setOnMouseExited((MouseEvent me) -> {
                suiv1.setTextFill(Color.WHITE);
            });
            prev2.setOnMouseEntered((MouseEvent me) -> {
                prev2.setTextFill(Color.BLACK);
            });
            prev2.setOnMouseExited((MouseEvent me) -> {
                prev2.setTextFill(Color.WHITE);
            });
            suiv2.setOnMouseEntered((MouseEvent me) -> {
                suiv2.setTextFill(Color.BLACK);
            });
            suiv2.setOnMouseExited((MouseEvent me) -> {
                suiv2.setTextFill(Color.WHITE);
            });
            prev3.setOnMouseEntered((MouseEvent me) -> {
                prev3.setTextFill(Color.BLACK);
            });
            prev3.setOnMouseExited((MouseEvent me) -> {
                prev3.setTextFill(Color.WHITE);
            });
            suiv3.setOnMouseEntered((MouseEvent me) -> {
                suiv3.setTextFill(Color.BLACK);
            });
            suiv3.setOnMouseExited((MouseEvent me) -> {
                suiv3.setTextFill(Color.WHITE);
            });
            prev4.setOnMouseEntered((MouseEvent me) -> {
                prev4.setTextFill(Color.BLACK);
            });
            prev4.setOnMouseExited((MouseEvent me) -> {
                prev4.setTextFill(Color.WHITE);
            });
            suiv4.setOnMouseEntered((MouseEvent me) -> {
                suiv4.setTextFill(Color.BLACK);
            });
            suiv4.setOnMouseExited((MouseEvent me) -> {
                suiv4.setTextFill(Color.WHITE);
            });
            prev5.setOnMouseEntered((MouseEvent me) -> {
                prev5.setTextFill(Color.BLACK);
            });
            prev5.setOnMouseExited((MouseEvent me) -> {
                prev5.setTextFill(Color.WHITE);
            });
            mp.setOnMouseEntered((MouseEvent me) -> {
                mp.setTextFill(Color.BLACK);
            });
            mp.setOnMouseExited((MouseEvent me) -> {
                mp.setTextFill(Color.WHITE);
            });

        }
        suiv1.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(true);
            rules22.setVisible(true);
            rules3.setVisible(false);
            rules4.setVisible(false);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(true);
            suiv2.setVisible(true);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(false);
        });
        prev2.setOnAction((ActionEvent event) -> {
            rules11.setVisible(true);
            rules12.setVisible(true);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(false);
            rules4.setVisible(false);
            rules5.setVisible(false);
            suiv1.setVisible(true);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(false);
        });
        suiv2.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(true);
            rules4.setVisible(false);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(true);
            suiv3.setVisible(true);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(false);
        });
        prev3.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(true);
            rules22.setVisible(true);
            rules3.setVisible(false);
            rules4.setVisible(false);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(true);
            suiv2.setVisible(true);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(false);
        });
        suiv3.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(false);
            rules4.setVisible(true);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(true);
            suiv4.setVisible(true);
            prev5.setVisible(false);
        });
        prev4.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(true);
            rules4.setVisible(false);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(true);
            suiv3.setVisible(true);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(false);
        });
        suiv4.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(false);
            rules4.setVisible(false);
            rules5.setVisible(true);
            suiv1.setVisible(false);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(false);
            suiv4.setVisible(false);
            prev5.setVisible(true);
        });
        prev5.setOnAction((ActionEvent event) -> {
            rules11.setVisible(false);
            rules12.setVisible(false);
            rules21.setVisible(false);
            rules22.setVisible(false);
            rules3.setVisible(false);
            rules4.setVisible(true);
            rules5.setVisible(false);
            suiv1.setVisible(false);
            prev2.setVisible(false);
            suiv2.setVisible(false);
            prev3.setVisible(false);
            suiv3.setVisible(false);
            prev4.setVisible(true);
            suiv4.setVisible(true);
            prev5.setVisible(false);
        });
        mp.setOnAction((ActionEvent event) -> {
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
        np.setOnAction((ActionEvent event) -> {
            nouvellePartie(primaryStage, firstmenu, launchgame);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });

        pane.setHgap(largeur_scene / 18);
        pane.setVgap(hauteur_scene / 50);

        principe.setFont(new Font(35));
        principe2.setFont(new Font(20));
        setup.setFont(new Font(35));
        setup2.setFont(new Font(20));
        but.setFont(new Font(35));
        but2.setFont(new Font(20));
        gagner.setFont(new Font(35));
        gagner2.setFont(new Font(20));
        manche12.setFont(new Font(35));
        manche122.setFont(new Font(20));
        manche22.setFont(new Font(35));
        manche222.setFont(new Font(20));
        end.setFont(new Font(35));
        end2.setFont(new Font(20));

        rules11.setVisible(true);
        rules12.setVisible(true);
        rules21.setVisible(false);
        rules22.setVisible(false);
        rules3.setVisible(false);
        rules4.setVisible(false);
        rules5.setVisible(false);
        suiv1.setVisible(true);
        suiv1.setStyle("-fx-alignment: center;");
        prev2.setVisible(false);
        prev2.setStyle("-fx-alignment: center;");
        suiv2.setVisible(false);
        suiv2.setStyle("-fx-alignment: center;");
        prev3.setVisible(false);
        prev3.setStyle("-fx-alignment: center;");
        suiv3.setVisible(false);
        suiv3.setStyle("-fx-alignment: center;");
        prev4.setVisible(false);
        prev4.setStyle("-fx-alignment: center;");
        suiv4.setVisible(false);
        suiv4.setStyle("-fx-alignment: center;");
        prev5.setVisible(false);
        prev5.setStyle("-fx-alignment: center;");
        pane.add(rules11, 3, 2);
        pane.add(rules12, 3, 3);
        pane.add(rules21, 3, 2);
        pane.add(rules22, 3, 3);
        pane.add(rules3, 3, 2);
        pane.add(rules4, 3, 2);
        pane.add(rules5, 3, 2);
        pane.add(suiv1, 4, 6);
        pane.add(prev2, 1, 6);
        pane.add(suiv2, 4, 6);
        pane.add(prev3, 1, 6);
        pane.add(suiv3, 4, 6);
        pane.add(prev4, 1, 6);
        pane.add(suiv4, 4, 6);
        pane.add(prev5, 1, 6);
        HBox sortie = new HBox();
        sortie.setStyle("-fx-alignment: center;");
        sortie.getChildren().addAll(mp, np);
        pane.add(sortie, 3, 7);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void menuoptions(Stage primaryStage) {
        primaryStage.setTitle("Options");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);

        pane.setHgap(largeur_scene / 16);
        pane.setVgap(hauteur_scene / 18);

        Text fond = new Text("Couleur du plateau :");
        Text dos = new Text("Couleur des dos de cartes :");
        if (jour == 1) {
            pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            fond.setFill(Color.BLACK);
            dos.setFill(Color.BLACK);
        } else {
            pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            fond.setFill(Color.WHITE);
            dos.setFill(Color.WHITE);
        }
        Button menu = new Button("Menu Principal ");
        Button nouvellegame = new Button("Nouvelle Partie");
        Rectangle bluebg = new Rectangle(hauteur_scene / 10, hauteur_scene / 10, Color.BLUE);
        Rectangle greenbg = new Rectangle(hauteur_scene / 10, hauteur_scene / 10, Color.GREEN);
        Rectangle redbg = new Rectangle(hauteur_scene / 10, hauteur_scene / 10, Color.RED);

        ImageView goldc = new ImageView(new Image("images/DEFAUSSE_OR.png"));
        ImageView greenc = new ImageView(new Image("images/DEFAUSSE_VERT.png"));
        ImageView redc = new ImageView(new Image("images/DEFAUSSE_ROUGE.png"));
        ImageView bluec = new ImageView(new Image("images/DEFAUSSE_BLEU.png"));
        ImageView blackc = new ImageView(new Image("images/DEFAUSSE_NOIR.png"));
        Circle goldcheck = new Circle(largeur_scene / 20, Color.GOLD);
        Circle greencheck = new Circle(largeur_scene / 20, Color.GOLD);
        Circle redcheck = new Circle(largeur_scene / 20, Color.GOLD);
        Circle bluecheck = new Circle(largeur_scene / 20, Color.GOLD);
        Circle blackcheck = new Circle(largeur_scene / 20, Color.GOLD);

        bluebg.setArcHeight(15);
        bluebg.setArcWidth(25);
        bluebg.setStroke(Color.BLACK);
        bluebg.setStrokeWidth(largeur_scene / 500);

        greenbg.setArcHeight(15);
        greenbg.setArcWidth(25);
        greenbg.setStroke(Color.BLACK);
        greenbg.setStrokeWidth(largeur_scene / 500);

        redbg.setArcHeight(15);
        redbg.setArcWidth(25);
        redbg.setStroke(Color.BLACK);
        redbg.setStrokeWidth(largeur_scene / 500);

        fond.setFont(new Font(25));
        dos.setFont(new Font(25));
        bluec.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("BLEU");

            goldcheck.setVisible(false);
            redcheck.setVisible(false);
            greencheck.setVisible(false);
            bluecheck.setVisible(true);
            blackcheck.setVisible(false);

        });
        redc.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("ROUGE");

            goldcheck.setVisible(false);
            redcheck.setVisible(true);
            greencheck.setVisible(false);
            bluecheck.setVisible(false);
            blackcheck.setVisible(false);
        });
        greenc.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("VERT");

            goldcheck.setVisible(false);
            redcheck.setVisible(false);
            greencheck.setVisible(true);
            bluecheck.setVisible(false);
            blackcheck.setVisible(false);
        });
        goldc.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("OR");

            goldcheck.setVisible(true);
            redcheck.setVisible(false);
            greencheck.setVisible(false);
            bluecheck.setVisible(false);
            blackcheck.setVisible(false);
        });
        blackc.setOnMouseClicked((MouseEvent me) -> {
            couleurDos = ("NOIR");

            goldcheck.setVisible(false);
            redcheck.setVisible(false);
            greencheck.setVisible(false);
            bluecheck.setVisible(false);
            blackcheck.setVisible(true);
        });
        bluebg.setOnMouseClicked((MouseEvent me) -> {
            bluebg.setStroke(Color.GOLD);
            redbg.setStroke(Color.BLACK);
            greenbg.setStroke(Color.BLACK);
            couleurPlateau = (Bleu);

        });
        greenbg.setOnMouseClicked((MouseEvent me) -> {
            greenbg.setStroke(Color.GOLD);
            redbg.setStroke(Color.BLACK);
            bluebg.setStroke(Color.BLACK);
            couleurPlateau = (Vert);

        });
        redbg.setOnMouseClicked((MouseEvent me) -> {
            redbg.setStroke(Color.GOLD);
            bluebg.setStroke(Color.BLACK);
            greenbg.setStroke(Color.BLACK);
            couleurPlateau = (Rouge);
        });
        menu.setOnAction((ActionEvent) -> {
            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
        nouvellegame.setOnAction((ActionEvent) -> {
            nouvellePartie(primaryStage, firstmenu, launchgame);
            if (jour == 1) {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
            } else {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
            }
        });
        HBox sortie = new HBox();
        sortie.getChildren().addAll(menu, nouvellegame);
        goldcheck.setVisible(false);
        redcheck.setVisible(false);
        greencheck.setVisible(false);
        bluecheck.setVisible(false);
        blackcheck.setVisible(false);
        pane.add(goldcheck, 4, 3);
        pane.add(redcheck, 1, 3);
        pane.add(greencheck, 3, 3);
        pane.add(bluecheck, 2, 3);
        pane.add(blackcheck, 5, 3);
        pane.add(fond, 2, 0);
        pane.add(dos, 2, 2);
        pane.add(greenbg, 2, 1);
        pane.add(bluebg, 3, 1);
        pane.add(redbg, 4, 1);
        pane.add(redc, 1, 3);
        pane.add(bluec, 2, 3);
        pane.add(greenc, 3, 3);
        pane.add(goldc, 4, 3);
        pane.add(blackc, 5, 3);
        pane.add(sortie, 3, 5);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void firstMenu(Stage primaryStage, Button newgame, Button loadgame, Button rules, Button options, Button quit) {
        primaryStage.setTitle("Menu Principal");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);
        bridgechinois.setTextFill(Color.BLACK);
        pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");

        //Espace entre les cases du GridPane
        pane.setHgap(l_scene / 15.36);
        pane.setVgap(h_scene / 18);

        //Espace boutons Menu principal
        VBox menu1buttons = new VBox();
        menu1buttons.setSpacing(h_scene / 12);
        newgame.setMaxWidth(l_scene / 4);
        loadgame.setMaxWidth(l_scene / 4);
        rules.setMaxWidth(l_scene / 4);
        options.setMaxWidth(l_scene / 4);
        menu1buttons.getChildren().addAll(newgame, loadgame, rules, options, daynight);
        pane.add(menu1buttons, 5, 3);

        bridgechinois.setFont(new Font(75));

        pane.add(quit, 0, 0);
        pane.add(bridgechinois, 5, 1);

        newgame.setOnAction((ActionEvent event) -> {
            nouvellePartie(primaryStage, firstmenu, launchgame);
        });

        rules.setOnAction((ActionEvent event) -> {
            regles(primaryStage);
        });

        loadgame.setOnAction((ActionEvent event) -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(System.getProperty("user.dir")));
            File f = fc.showOpenDialog(primaryStage); //sauver showSaveDialog
            try {
                m = new Moteur2();
                m.charger(f.getName());

                temps = m.config.temps;
                temps2 = m.config.temps2;
                carte_jouee = m.config.carte_jouee;
                tour_joueur = m.config.tour_joueur;
                tour_pioche = m.config.tour_pioche;
                k = m.config.k;
                J1_carte_jouee = m.config.J1_carte_jouee;
                J2_carte_jouee = m.config.J2_carte_jouee;
                clean = m.config.clean;
                pause = m.config.pause;
                j1_lock = m.config.j1_lock;
                j2_lock = m.config.j2_lock;
                select = m.config.select;
                cheat = m.config.cheat;
                message_t = m.config.message_t;
                animation_cartePiochee = m.config.animation_cartePiochee;
                animation_t = m.config.animation_t;
                J1_lastCard = m.config.J1_lastCard;
                J2_lastCard = m.config.J2_lastCard;
                affichage_initial_pioche = m.config.affichage_initial_pioche;
                messagePioche = m.config.messagePioche;
                finTour = m.config.finTour;
                messageFinManche = m.config.messageFinManche;
                messageFinPartie = m.config.messageFinPartie;

                initialiserCharger(primaryStage);

            } catch (IOException ex) {
                Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Bridge.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        options.setOnAction((ActionEvent event) -> {
            menuoptions(primaryStage);
        });

        quit.setOnAction((ActionEvent event) -> {
            exit(0);
        });

        daynight.setOnAction((ActionEvent event) -> {
            if (jour == 1) {
                pane.setStyle("-fx-color : black; -fx-background-color: #3b3f42;");
                bridgechinois.setStyle("-fx-text-fill: white;");
                quit.setStyle("-fx-text-fill: white;");
                newgame.setStyle("-fx-text-fill: white;");
                loadgame.setStyle("-fx-text-fill: white;");
                rules.setStyle("-fx-text-fill: white;");
                options.setStyle("-fx-text-fill: white;");
                daynight.setTextFill(Color.WHITE);
                quit.setOnMouseEntered((MouseEvent me) -> {
                    quit.setStyle("-fx-text-fill: black;");
                });
                quit.setOnMouseExited((MouseEvent me) -> {
                    quit.setStyle("-fx-text-fill: white;");
                });
                newgame.setOnMouseEntered((MouseEvent me) -> {
                    newgame.setStyle("-fx-text-fill: black;");
                });
                newgame.setOnMouseExited((MouseEvent me) -> {
                    newgame.setStyle("-fx-text-fill: white;");
                });
                loadgame.setOnMouseEntered((MouseEvent me) -> {
                    loadgame.setStyle("-fx-text-fill: black;");
                });
                loadgame.setOnMouseExited((MouseEvent me) -> {
                    loadgame.setStyle("-fx-text-fill: white;");
                
                });
                rules.setOnMouseEntered((MouseEvent me) -> {
                    rules.setStyle("-fx-text-fill: black;");
                });
                rules.setOnMouseExited((MouseEvent me) -> {
                    rules.setStyle("-fx-text-fill: white;");
                });
                options.setOnMouseEntered((MouseEvent me) -> {
                    options.setStyle("-fx-text-fill: black;");
                });
                options.setOnMouseExited((MouseEvent me) -> {
                    options.setStyle("-fx-text-fill: white;");
                });
                daynight.setOnMouseEntered((MouseEvent me) -> {
                    daynight.setTextFill(Color.BLACK);
                });
                daynight.setOnMouseExited((MouseEvent me) -> {
                    daynight.setTextFill(Color.WHITE);
                });
                jour = 0;
            } else {
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
                bridgechinois.setTextFill(Color.BLACK);
                quit.setTextFill(Color.BLACK);
                newgame.setTextFill(Color.BLACK);
                loadgame.setTextFill(Color.BLACK);
                rules.setTextFill(Color.BLACK);
                options.setTextFill(Color.BLACK);
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
            case "Moyen":
                return 3;
            case "Avancé":
                return 4;
            case "Difficile":
                return 5;
            case "Expert":
                return 6;
        }
        return -1;
    }

    public void nouvellePartie(Stage primaryStage, Button firstmenu, Button launchgame) {

        nbpointsfinal = 0;
        nbroundsfinal = 0;
        joueur1level = 0;
        joueur2level = 0;

        primaryStage.setTitle("Nouvelle Partie");
        pane = new GridPane();
        scene = new Scene(pane, l_scene, h_scene);
        if (jour == 1) {
            pane.setStyle("-fx-background-color: #274e13;");
            bridgechinois2.setStyle("-fx-text-fill: black");
            configgame.setStyle("-fx-text-fill: black");
            choiceplayer.setStyle("-fx-text-fill: black");
            player1title.setStyle("-fx-text-fill: black");
            player2title.setStyle("-fx-text-fill: black");
            victorysetup.setStyle("-fx-text-fill: black");
            p1human.setStyle("-fx-text-fill: black");
            cbhuman2.setStyle("-fx-text-fill: black");
            cbcomputer2.setStyle("-fx-text-fill: black");
            cbpoints.setStyle("-fx-text-fill: black");
            cbrounds.setStyle("-fx-text-fill: black");
            firstmenu.setTextFill(Color.BLACK);
            launchgame.setTextFill(Color.BLACK);

        } else {
            pane.setStyle("-fx-color: black; -fx-background-color: #3b3f42;");
            bridgechinois2.setTextFill(Color.WHITE);
            configgame.setTextFill(Color.WHITE);
            choiceplayer.setTextFill(Color.WHITE);
            player1title.setTextFill(Color.WHITE);
            player2title.setTextFill(Color.WHITE);
            victorysetup.setTextFill(Color.WHITE);
            p1human.setTextFill(Color.WHITE);
            cbhuman2.setTextFill(Color.WHITE);
            cbcomputer2.setTextFill(Color.WHITE);
            cbpoints.setTextFill(Color.WHITE);
            cbrounds.setTextFill(Color.WHITE);
            firstmenu.setTextFill(Color.WHITE);
            launchgame.setTextFill(Color.WHITE);
            firstmenu.setOnMouseEntered((MouseEvent me) -> {
                firstmenu.setTextFill(Color.BLACK);
            });
            firstmenu.setOnMouseExited((MouseEvent me) -> {
                firstmenu.setTextFill(Color.WHITE);
            });
            launchgame.setOnMouseEntered((MouseEvent me) -> {
                launchgame.setTextFill(Color.BLACK);
            });
            launchgame.setOnMouseExited((MouseEvent me) -> {
                launchgame.setTextFill(Color.WHITE);
            });
        }
        //Espace entre les cases du GridPane
        pane.setHgap(l_scene / 12);
        pane.setVgap(h_scene / 13.5);

        //Espace Joueur 1
        //box globale
        VBox player1 = new VBox();
        player1.setSpacing(h_scene / 36);
        player1.getChildren().addAll(player1title, player1name);
        pane.add(player1, 1, 2);

        //Espace Joueur 2
        //Menu Deroulant
        if (combobox2set == 0) {
            iaLevel2.getItems().addAll(
                    "Novice",
                    "Facile",
                    "Moyen",
                    "Avancé",
                    "Difficile",
                    "Expert"
            );
            iaLevel2.setValue("Novice");
            combobox2set = 1;
        }
        //box human
        HBox secondhuman = new HBox();
        secondhuman.setSpacing(l_scene / 192);
        secondhuman.getChildren().addAll(cbhuman2);
        //box niveau de l'ordinateur
        HBox computer2 = new HBox();
        computer2.setSpacing(l_scene / 192);
        computer2.getChildren().addAll(cbcomputer2, iaLevel2);
        //box globale
        VBox player2 = new VBox();
        player2.setSpacing(h_scene / 36);
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

        //Espace titre choix joueur
        pane.add(choiceplayer, 2, 2);

        VBox victorycond = new VBox();
        victorycond.setSpacing(h_scene / 36);
        //Espace points
        HBox pointscond = new HBox();

        pointscond.setSpacing(20);
        nbpoints.setPromptText("Nombre de points");
        pointscond.getChildren().addAll(cbpoints, /*points,*/ nbpoints);
        //Espace manches
        HBox roundscond = new HBox();
        roundscond.setSpacing(20);
        nbrounds.setPromptText("Nombre de manches");
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
        cbhuman2.setFont(new Font(20));
        cbcomputer2.setFont(new Font(20));
        cbpoints.setFont(new Font(20));
        cbrounds.setFont(new Font(20));

        player1name.setPrefColumnCount(l_scene / 76);
        player2name.setPrefColumnCount(l_scene / 76);
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
        //selection des radio buttons quand on active la combo box
        iaLevel2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cbhuman2.setSelected(false);
                cbcomputer2.setSelected(true);
            }
        });
        //selection des radiobuttons quand on selectionne les conditions de victoire + selection des textfields
        cbpoints.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(true);
        });
        nbpoints.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            cbpoints.setSelected(true);
        });
        //checkbox manches
        cbrounds.setOnAction((ActionEvent event) -> {
            cbpoints.setSelected(false);
            nbpoints.setText("");
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
                pane.setStyle("-fx-color : white; -fx-background-color: #274e13;");
                bridgechinois.setTextFill(Color.BLACK);
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
            System.out.println("Mode de la partie : " + typemode);
            System.out.println("Type de la partie : " + typegame);
            //validation des parametres
            if ((cbhuman2.isSelected() || cbcomputer2.isSelected()) && (cbpoints.isSelected() || cbrounds.isSelected()) && (cbcomputer2.isSelected() || cbhuman2.isSelected())) {
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

    public int message_t = 0;

    int animation_cartePiochee = 0;

    int animation_t = 0;

    public Carte J1_lastCard;
    public Carte J2_lastCard;

    Timeline timeline2;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    private final double souris_carte = hauteur_scene - (hauteur_scene / 5.45) + 1;

    private int affichage_initial_pioche = 0;

    MenuJeu bandeau;

    Button undo;
    Button redo;

    public void init_manche(Stage primaryStage) {
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
        affichage_initial_pioche = 0;
        message_t = 0;
        animation_cartePiochee = 0;
        animation_t = 0;

        m.config.carteP = null;
        m.config.carteS = null;
        J1_carte_jouee = null;
        J2_carte_jouee = null;
        J1_lastCard = null;
        J2_lastCard = null;

        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];

        switch (m.config.donneurInitial) {
            case 0:
                Random r = new Random();
                m.config.donneurInitial = r.nextInt(2) + 1;
                if (m.config.donneurInitial == 1) {
                    m.config.donneur = 1;
                    m.config.receveur = 2;
                } else {
                    m.config.donneur = 2;
                    m.config.receveur = 1;
                }
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

        m.j1.main.trier();
        m.j2.main.trier();

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

        m.j1.score = 0;
        m.j2.score = 0;

        bandeau = new MenuJeu(m, couleurPlateau);
        bandeau.tourJ(tour_joueur);
        bandeau.mode(m.config.mode);

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
        if (m.config.mode == 1) {
            message_t = -2;
        }
    }

    public void majBandeau(Stage primaryStage) {
        bandeau = new MenuJeu(m, couleurPlateau);
        bandeau.tourJ(tour_joueur);
        bandeau.mode(m.config.mode);

        if (root != null) {
            root.getChildren().add(bandeau);
        }

        bandeau.load.setOnAction((ActionEvent event) -> {
            charger(primaryStage);
        });
        bandeau.load.setOnKeyPressed(keyEvent -> {
            KeyCode l = keyEvent.getCode();
            if (l.equals(KeyCode.L)) {
                charger(primaryStage);
            }
        });

        bandeau.option.setOnAction((ActionEvent event) -> {
            option();
        });
        bandeau.option.setOnKeyPressed(keyEvent -> {
            KeyCode o = keyEvent.getCode();
            if (o.equals(KeyCode.O)) {
                option();
            }
        });

        bandeau.restart.setOnAction((ActionEvent event) -> {
            cbrounds.setSelected(false);
            nbrounds.setText("");
            cbpoints.setSelected(false);
            nbpoints.setText("");
            cbhuman2.setSelected(false);
            nouvellePartie(primaryStage, firstmenu, launchgame);
        });
        bandeau.restart.setOnKeyPressed(keyEvent -> {
            KeyCode t = keyEvent.getCode();
            if (t.equals(KeyCode.T)) {
                sauvegarder(primaryStage);
            }
        });

        bandeau.quit.setOnKeyPressed(keyEvent -> {
            KeyCode q = keyEvent.getCode();
            if (q.equals(KeyCode.Q)) {
                quitter(primaryStage);
            }
        });
        bandeau.quit.setOnAction((ActionEvent event) -> {
            quitter(primaryStage);
        });

        bandeau.save.setOnAction((ActionEvent event) -> {
            sauvegarder(primaryStage);
        });
        bandeau.save.setOnKeyPressed(keyEvent -> {
            KeyCode s = keyEvent.getCode();
            if (s.equals(KeyCode.S)) {
                sauvegarder(primaryStage);
            }
        });
    }

    public void init_main(Carte[] main, int j) {
        String color;
        String number;
        int taille;

        if (j == J1) {
            for (int i = 0; i < m.j1.main.pile.size(); i++) {
                main[i] = m.j1.main.pile.get(i);
            }
        } else {
            for (int i = 0; i < m.j2.main.pile.size(); i++) {
                main[i] = m.j2.main.pile.get(i);
            }
        }

        if (j == J1) {
            taille = m.j1.main.taille();
        } else {
            taille = m.j2.main.taille();
        }

        for (int i = 0; i < taille; i++) {
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

        Moteur test = new Moteur();

        for (int j = 0; j < m.config.pioche.length; j++) {
            System.out.println(m.config.pioche[j].taille());
            for (int i = 0; i < m.config.pioche[j].pile.size(); i++) {
                pile[j][i] = m.config.pioche[j].pile.get(i);
            }
            if (m.config.pioche[j].pile.size() == 0) {
                pile[j][0] = null;
            }
        }

        for (int j = 0; j < pile.length; j++) {
            int taille;
            switch (j) {
                case 1:
                    taille = m.config.pile1.taille();
                    break;
                case 2:
                    taille = m.config.pile2.taille();
                    break;
                case 3:
                    taille = m.config.pile3.taille();
                    break;
                case 4:
                    taille = m.config.pile4.taille();
                    break;
                case 5:
                    taille = m.config.pile5.taille();
                    break;
                case 6:
                    taille = m.config.pile6.taille();
                    break;
            }
            for (int i = 0; i < m.config.pioche[j].taille(); i++) {
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
        ImagePattern imgPli = new ImagePattern(new Image("images/DEFAUSSE_" + couleurDos + ".png"));

        for (int i = 0; i < t; i++) {
            plis[i].dos.setFill(imgPli);
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
        ImagePattern imgPli = new ImagePattern(new Image("images/DEFAUSSE_" + couleurDos + ".png")); //DEFAUSEE_BLEU, DEFAUSEE_ROUGE, DEFAUSEE_OR, DEFAUSEE_NOIR, DEFAUSEE_VERT
        for (int i = 0; i < t; i++) {

            if (plis[i] != null) {
                plis[i].dos.setFill(imgPli);
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

    public void affichage_dos_main_bas(Carte[] main, int j, int display) {
        int t;
        if (j == 1) {
            t = m.j1.main.pile.size();
        } else {
            t = m.j2.main.pile.size();
        }
        for (int i = 0; i < t; i++) {
            ImagePattern img2 = new ImagePattern(new Image("images/DOS_" + couleurDos + ".png"));
            main[i].dos.setFill(img2);
            main[i].dos.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
            main[i].dos.setTranslateY(hauteur_scene - main[i].hauteur_carte * 0.75);
            if (display == 1) {
                main[i].dos.setVisible(true);
                main[i].face.setVisible(false);
            } else {
                main[i].dos.setVisible(false);
                main[i].face.setVisible(true);
            }
        }
    }

    public void cacher_face_main(Carte[] main, int j) {
        int t;
        if (j == 1) {
            t = m.j1.main.pile.size();
        } else {
            t = m.j2.main.pile.size();
        }
        for (int i = 0; i < t; i++) {
            main[i].face.setVisible(false);
        }
    }

    public void affichage_face_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            if (pile[j][0] != null) {
                pile[j][0].face.setTranslateX(largeur_scene / 3 + (pile[j][0].largeur_carte * 1.4 * j));
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

        if (m.config.mode == 1) {
            for (int i = 0; i < t; i++) {
                main[i].face.setVisible(false);

                main[i].dos.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].dos.setTranslateY(-(main[i].hauteur_carte * 0.25));

                ImagePattern img = new ImagePattern(new Image("images/DOS_" + couleurDos + ".png"));
                main[i].dos.setFill(img);
                main[i].dos.setVisible(true);
                main[i].dos.toFront();
            }
        } else if (m.config.mode == 2) {
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

                ImagePattern img2 = new ImagePattern(new Image("images/DOS_" + couleurDos + ".png"));
                main[i].dos.setFill(img2);

                main[i].face.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].face.setTranslateY(0);

                main[i].dos.setTranslateX(largeur_scene / 2.5 + ((main[i].largeur_carte / 2) * i));
                main[i].dos.setTranslateY(-(main[i].hauteur_carte * 0.25));

                if (cheat == 0) {
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

    public void changerCouleurDos(String color) {
        String card;
        ImagePattern img = new ImagePattern(new Image("images/DOS_" + color + ".png"));

        for (int i = 0; i < m.j1.main.taille(); i++) {
            j1main[i].dos.setFill(img);
        }
        for (int i = 0; i < m.j2.main.taille(); i++) {
            j2main[i].dos.setFill(img);
        }
        for (int j = 0; j < pile.length; j++) {
            if (m.config.pioche[j].pile.size() > 0 && m.config.pioche[j] != null) {

                switch (m.config.pioche[j].pile.size()) {
                    case 5:
                        card = "pile_5_" + color + ".png";
                        break;
                    case 4:
                        card = "pile_4_" + color + ".png";
                        break;
                    case 3:
                        card = "pile_3_" + color + ".png";
                        break;
                    case 2:
                        card = "pile_2_" + color + ".png";
                        break;
                    default:
                        card = couleurPlateau;
                        break;
                }
                if (m.config.pioche[j].pile.size() > 1) {
                    ImagePattern img2 = new ImagePattern(new Image("images/" + card));
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setFill(img2);
                } else {
                    //if(pile[j][0] != null){
                        pile[j][0].dos.setFill(Color.web(couleurPlateau));
                    //}
                }

            }
        }
        if (j1plis != null && j1plis.length != 0) {
            for (int i = 0; i < m.j1.tas.taille(); i++) {
                ImagePattern img2 = new ImagePattern(new Image("images/DEFAUSSE_" + color + ".png"));
                j1plis[i].dos.setFill(img2);
            }
        }
        if (j2plis != null && j2plis.length != 0) {
            for (int i = 0; i < m.j2.tas.taille(); i++) {
                ImagePattern img2 = new ImagePattern(new Image("images/DEFAUSSE_" + color + ".png"));
                j2plis[i].dos.setFill(img2);
            }
        }
    }

    public void affichage_dos_pile(Carte[][] pile) {
        String card;
        ImagePattern img;
        for (int j = 0; j < pile.length; j++) {
            if (m.config.pioche[j].pile.size() > 0 && m.config.pioche[j] != null) {
                switch (m.config.pioche[j].pile.size()) {
                    case 5:
                        card = "pile_5_" + couleurDos + ".png";
                        break;
                    case 4:
                        card = "pile_4_" + couleurDos + ".png";
                        break;
                    case 3:
                        card = "pile_3_" + couleurDos + ".png";
                        break;
                    case 2:
                        card = "pile_2_" + couleurDos + ".png";
                        break;
                    default:
                        card = couleurPlateau;
                        break;
                }
                if (m.config.pioche[j].pile.size() > 1) {
                    img = new ImagePattern(new Image("images/" + card));
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setFill(img);
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setVisible(true);
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setTranslateX(largeur_scene / 2.9 + (pile[j][0].largeur_carte * 1.4 * j));
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setTranslateY((hauteur_scene / 2) - (pile[j][0].hauteur_carte / 2));
                } else if (m.config.pioche[j].pile.size() == 1) {
                    pile[j][m.config.pioche[j].pile.size() - 1].dos.setFill(Color.web(couleurPlateau));
                }
            }
        }
    }

    public void carte_select_P(Carte[] main, int k) {
        carte_jouee = 1;

        m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                finTour, messageFinManche, messageFinPartie);
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

        m.maj(temps, temps2, carte_jouee, tour_joueur, tour_pioche, k,
                J1_carte_jouee, J2_carte_jouee, clean, pause, j1_lock, j2_lock, select, cheat,
                message_t, animation_cartePiochee, animation_t, J1_lastCard, J2_lastCard, affichage_initial_pioche, messagePioche,
                finTour, messageFinManche, messageFinPartie);
        Carte card = m.jouerCoupSecond(main[k]);

        if (m.config.mode == 1 && m.config.receveur == 1) {
            J1_carte_jouee = main[k];
        } else if (m.config.mode == 1 && m.config.receveur == 2) {
            J2_carte_jouee = main[k];
        } else if (m.config.mode == 2) {
            J1_carte_jouee = main[k];
        }

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
                messagePioche = true;
                affichage_face_main(main, m.config.receveur);
                temps = System.currentTimeMillis();
                pause = 2;

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
        if (main[n] != null) {
            main[n].face.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j)) && message_t == 0) {
                        main[n].face.setTranslateY(souris_carte);
                    }
                }
            });

            main[n].face.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j)) && message_t == 0) {
                        main[n].face.setTranslateY(hauteur_scene - main[n].hauteur_carte * 0.75);
                    }
                }
            });

            main[n].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {

                    if (m.config.mode == 2 && cheat == 1 && main != j1main) {
                        for (int i = 0; i < m.j2.main.taille(); i++) {
                            j2main[i].face.setVisible(false);
                            j2main[i].dos.setVisible(true);
                            j2main[i].dos.toFront();
                            cheat = 0;
                        }
                    }

                    if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0 && (carte_jouable(main[n], j)) && message_t == 0) {
                        if (m.config.donneur == j) {
                            carte_select_P(main, n);
                        } else {
                            carte_select_S(main, n);
                        }
                    }

                }
            });
        }

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
        if (j2main[n] != null) {
            j2main[n].dos.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (m.config.mode == 2 && cheat == 0) {
                        for (int i = 0; i < m.j2.main.taille(); i++) {
                            j2main[i].dos.setVisible(false);
                            j2main[i].face.setVisible(true);
                            j2main[i].face.toFront();
                            cheat = 1;
                        }
                    }
                }
            });
        }
    }

    public void maj_handler_main() {
        for (k = 0; k < m.j1.main.taille(); k++) {
            maj_handler_unitMain(k, j1main, J1);
        }
        for (k = 0; k < m.j2.main.taille(); k++) {
            maj_handler_unitMain(k, j2main, J2);
        }
    }

    public void maj_handler_unitPile(int n) {

        pile[n][0].face.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if ((tour_pioche == 2 && (m.config.mode == 1 || m.config.perdant != IA) && pause == 0 && message_t == 0) || (tour_pioche == 1 && (m.config.mode == 1 || m.config.gagnant != IA) && pause == 0 && message_t == 0)) {
                    pile[n][0].face.setScaleX(1.2);
                    pile[n][0].face.setScaleY(1.2);
                }
            }
        });

        pile[n][0].face.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if ((tour_pioche == 2 && (m.config.mode == 1 || m.config.perdant != IA) && pause == 0 && message_t == 0) || (tour_pioche == 1 && (m.config.mode == 1 || m.config.gagnant != IA) && pause == 0 && message_t == 0)) {
                    pile[n][0].face.setTranslateY((hauteur_scene / 2) - (pile[n][0].hauteur_carte / 2));
                    pile[n][0].face.setScaleX(1);
                    pile[n][0].face.setScaleY(1);
                }
            }
        });

        pile[n][0].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                pile[n][0].face.setScaleX(1);
                pile[n][0].face.setScaleY(1);
                if (tour_pioche == 2 && (m.config.mode == 1 || m.config.perdant != IA) && pause == 0 && message_t == 0) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.perdant, pile[n][0], n);
                        m.j1.main.trier();
                        m.j2.main.trier();
                        System.out.println();
                        init_pile(pile);
                        affichage_dos_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                    }

                    m.config.donneur = m.config.gagnant;
                    m.config.receveur = m.config.perdant;

                    if (m.config.mode == 2) {
                        //tour_joueur = m.config.gagnant;
                        bandeau.tourJ(m.config.receveur);
                        init_main(j1main, J1);
                        affichage_face_main(j1main, J1);
                        maj_handler_main();
                        //maj_handler_pile();
                        temps = System.currentTimeMillis();
                        clean = 1;
                        carte_jouee = 0;
                    } else if (m.config.mode == 1) {
                        bandeau.tourJ(m.config.receveur);
                        temps = System.currentTimeMillis();
                        messagePioche = true;
                        finTour = true;
                        pause = 4;

                        if (m.config.perdant == J1) {
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        } else {
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }

                        temps2 = System.currentTimeMillis();
                        clean = 1;
                    }
                    //carte_jouee = 0;
                    tour_pioche = 0;
                    if (m.config.mode == 2 && m.config.gagnant == IA) {
                        temps = System.currentTimeMillis();
                    }
                    System.out.println();
                }

                if (tour_pioche == 1 && (m.config.mode == 1 || m.config.gagnant != IA) && pause == 0 && message_t == 0) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.gagnant, pile[n][0], n);
                        m.j1.main.trier();
                        m.j2.main.trier();
                        System.out.println();
                        init_pile(pile);
                        affichage_dos_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        m.config.taille++;

                    }
                    if (m.config.mode == 1) {
                        //Déplacé dans AnimationTimer
                        if (m.config.gagnant == J1) {
                            bandeau.tourJ(J1);
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        } else {
                            bandeau.tourJ(J2);
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }
                        //pause=-1;
                        messagePioche = true;
                        pause = 3;
                        tour_pioche = 2;
                        temps = System.currentTimeMillis();
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

    private void animationTimeline(Carte c) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(seconds(.6), new KeyValue(c.face.translateYProperty(), 0)),
                new KeyFrame(seconds(.6), new KeyValue(c.face.translateXProperty(), largeur_scene / 1.4 + c.largeur_carte / 2))
        );
        timeline.play();
        temps = System.currentTimeMillis();
        animation_t = 1;
    }

    @Override
    public void start(Stage primaryStage) {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (launchedmenu == false) {
                    firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
                    launchedmenu = true;
                } else if (launchedjeu == true) {
                    bandeau.tourP(tour_pioche, m.config.gagnant);
                    if (affichage_initial_pioche == 0) {
                        affichage_dos_pile(pile);
                        affichage_face_pile(pile);
                        affichage_initial_pioche = 1;
                    }

                    if (m.finManche() && temps + 2000 < System.currentTimeMillis()) {    //Si la manche est finie
                        if (m.config.taille == 0) {
                            Boolean V1 = m.config.conditionVictoire == 1 && m.config.manche < m.config.mancheMax;
                            Boolean V2 = m.config.conditionVictoire == 2 && (m.j1.scoreTotal < m.config.scoreMax && m.j2.scoreTotal < m.config.scoreMax);
                            //fin_manche();
                            //score();
                            if (V1 || V2) {
                                messageFinManche = true;
                                /*System.out.println("Manche SUIVANTE !");
                                 init_manche();*/
                            } else {
                                score();
                                fin_manche();
                                messageFinPartie = true;
                                /*System.out.println();
                                 System.out.println("Score Total joueur 1: " + (m.j1.scoreTotal));
                                 System.out.println("Score Total joueur 2: " + (m.j2.scoreTotal));
                                 if (m.j1.scoreTotal > m.j2.scoreTotal) {
                                 System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
                                 } else if (m.j1.scoreTotal < m.j2.scoreTotal) {
                                 System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
                                 } else {
                                 System.out.println("EGALITE");
                                 }
                                 System.exit(0);*/
                            }
                        }
                    }

                    //Affichage Bandeau en Fin Manche mais pas en fin de partie
                    if (m.finManche() && !m.finPartie() && messageFinManche) {
                        if (messageActif == 0) {
                            fin_manche();
                            score();
                            mt = new MessageTransition(m, largeur_scene, hauteur_scene);
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            System.out.println("Manche SUIVANTE !");
                            init_manche(primaryStage);
                            messageFinManche = false;
                            messageActif = 0;
                        }
                    }

                    //Affichage Bandeau en Fin Partie
                    if (m.finManche() && m.finPartie() && messageFinPartie) {
                        if (messageActif == 0) {
                            mt = new MessageTransition(m, largeur_scene, hauteur_scene);
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            messageFinPartie = false;
                            messageActif = 0;
                            firstMenu(primaryStage, newgame, loadgame, rules, options, quit);
                        }
                    }

                    //Au tour de l'IA de jouer sa carte
                    if ((((m.config.mode == 2 && tour_joueur == IA) || m.config.mode == 3) && carte_jouee == 0) && clean == 0 && m.config.taille > 0 && temps + 1000 < System.currentTimeMillis()) { //Cas où c'est au tour d'une IA de jouer
                        bandeau.tourJ(IA);
                        carte_jouee = 1;
                        System.out.println("IA joue sa carte");
                        J2_carte_jouee = m.jouerCoupIA(tour_joueur);
                        for (int i = 0; i < m.j2.main.taille(); i++) {
                            j2main[i].dos.setVisible(false);
                        }
                        init_main(j2main, IA);
                        affichage_dos_main(j2main, IA);
                        J2_carte_jouee.face.setVisible(true);
                        //Affichage de sa carte à l'IA
                        J2_carte_jouee.face.setTranslateX(largeur_scene / 1.8);
                        J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte / 1.3);
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
                    if (m.config.mode == 2 && tour_pioche == 1 && m.config.gagnant == IA && animation_t == 0 && temps + 800 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                        bandeau.tourJ(J1);
                        if (m.config.piochable()) {
                            m.config.afficherPioche();
                            System.out.println("IA a gagné et prend une carte en 1er");
                            Carte c = m.piocheIA(IA);
                            c.face.toFront();

                            animationTimeline(c);

                        }
                    }

                    if (m.config.mode == 2 && tour_pioche == 1 && m.config.gagnant == IA && animation_t == 1 && temps + 1500 < System.currentTimeMillis()) {
                        m.j1.main.trier();
                        m.j2.main.trier();
                        m.config.afficherPioche();
                        init_mainJ1J2();
                        System.out.println();
                        for (int i = 0; i < m.j2.main.taille(); i++) {
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
                        animation_t = 0;
                    }

                    //Si l'IA est le perdant, il pioche une carte en 2nd
                    if (m.config.mode == 2 && tour_pioche == 2 && m.config.perdant == IA && animation_t == 0 && temps + 800 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                        bandeau.tourJ(IA);
                        if (m.config.piochable()) {
                            m.config.afficherPioche();
                            System.out.println("IA a perdu et prend une carte en 2nd");
                            Carte c = m.piocheIA(IA);
                            c.face.toFront();

                            animationTimeline(c);

                        }
                    }

                    if (m.config.mode == 2 && tour_pioche == 2 && m.config.perdant == IA && animation_t == 1 && temps + 1500 < System.currentTimeMillis()) {
                        m.j1.main.trier();
                        m.j2.main.trier();
                        m.config.afficherPioche();
                        init_mainJ1J2();
                        System.out.println();
                        for (int i = 0; i < m.j2.main.taille(); i++) {
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
                        animation_t = 0;
                    }

                    //On met à jour les plis et retire les cartes jouées en JcIA
                    if (m.config.mode == 2 && clean == 1 && temps + 360 < System.currentTimeMillis()) {
                        tour_joueur = m.config.gagnant;
                        bandeau.tourJ(m.config.gagnant);

                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);

                        bandeau.plisJ1.setText(String.valueOf("Plis : " + m.j1.score));
                        bandeau.plisJ2.setText(String.valueOf("Plis : " + m.j2.score));

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
                        bandeau.plisJ1.setText(String.valueOf("Plis : " + m.j1.score));
                        bandeau.plisJ2.setText(String.valueOf("Plis : " + m.j2.score));
                        clean = 0;
                        temps = System.currentTimeMillis();
                    }

                    //On met à jour les plis et retire les cartes jouées en JcJ
                    if (m.config.mode == 1 && clean == 1 && temps2 + 1000 < System.currentTimeMillis()) {
                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);

                        bandeau.plisJ1.setText(String.valueOf("Plis : " + m.j1.score));
                        bandeau.plisJ2.setText(String.valueOf("Plis : " + m.j2.score));

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
                    if (m.config.mode == 1 && pause == 1 && m.config.carteP != null && temps + 1000 < System.currentTimeMillis()) {
                        System.out.println("PAUSE 1 !");
                        if (m.config.donneur == J1) {
                            init_main(j1main, J1);
                            init_main(j2main, J2);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte / 1.3);

                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));

                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 10.5);

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
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte / 1.3);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);

                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 10.5);

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
                        message_t = 1;
                        System.out.println("FIN PAUSE 1");
                    }

                    //On rentre ici une fois que le receveur a joué sa carte et on tourne le plateau pour que le jeu du gagnant soit en bas
                    if (m.config.mode == 1 && pause == 2 && temps + 1000 < System.currentTimeMillis()) {
                        System.out.println("PAUSE 2 !");
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
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte / 1.3);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);

                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 10.5);

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
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte / 1.3);
                            J2_carte_jouee.face.setTranslateY((hauteur_scene / 1.5));
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));

                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 10.5);

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
                        message_t = 3;
                        System.out.println("FIN PAUSE 2");
                    }

                    //On rentre ici une fois que le gagnant a pioché une carte dans l'une des pioches et on tourne le plateau pour que le jeu du perdant soit en bas
                    if (m.config.mode == 1 && pause == 3 && temps + 1000 < System.currentTimeMillis()) {
                        System.out.println("PAUSE 3 !");
                        bandeau.tourJ(m.config.perdant);
                        if (m.config.gagnant == J1) {
                            J2_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte / 1.3);
                            affichage_dos_plis(j1plis, J1, hauteur_scene - (hauteur_scene / 1.5) - J1_carte_jouee.hauteur_carte);
                            affichage_dos_plis(j2plis, J2, (hauteur_scene / 1.5));

                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 10.5);

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
                            J2_carte_jouee.face.setTranslateY(hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte / 1.3);
                            J1_carte_jouee.face.setTranslateY(hauteur_scene / 1.5);
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);

                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 10.5);

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
                        message_t = 5;
                        System.out.println("FIN PAUSE 3");
                    }

                    //On rentre ici une fois que le perdant a pioché une carte dans l'une des pioches et on tourne le plateau pour que le jeu du nouveau donneur soit en bas
                    if (m.config.mode == 1 && pause == 4 && temps + 1000 < System.currentTimeMillis()) {
                        System.out.println("PAUSE 4 !");
                        if (m.config.donneur == J1) {
                            affichage_dos_plis(j1plis, J1, (hauteur_scene / 1.5));
                            affichage_dos_plis(j2plis, J2, hauteur_scene - (hauteur_scene / 1.5) - J2_carte_jouee.hauteur_carte);

                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 10.5);

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

                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 10.5);

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
                        message_t = 7;
                        System.out.println("FIN PAUSE 4");
                    }

                    //Début du 1er tour de la partie
                    if (m.config.mode == 1 && (message_t == -2 || message_t == -1)) {
                        if (messageActif == 0 && (message_t != -1)) {
                            if (m.config.donneur == J1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                            }
                            if (m.config.donneur == 1) {
                                mt = new MessageTransition(1, m.j1.nom, largeur_scene, hauteur_scene);
                                message_t = -1;
                                carte_jouee = 1;
                            } else {
                                mt = new MessageTransition(1, m.j2.nom, largeur_scene, hauteur_scene);
                                message_t = -1;
                                carte_jouee = 1;
                            }
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            if (m.config.donneur == J1) {
                                bandeau.tourJ(J1);
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                bandeau.tourJ(J2);
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            if (m.config.donneur == J1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            message_t = 0;
                            messageActif = 0;
                            carte_jouee = 0;
                        }
                    }

                    //Début de chaque tour sauf le tout premier
                    if (m.config.mode == 1 && messagePioche == true && (message_t == 7 || message_t == 8)) {
                        if (messageActif == 0 && (message_t != 8)) {
                            if (m.config.donneur == J1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                            }
                            if (m.config.gagnant == 1) {
                                mt = new MessageTransition(1, m.j1.nom, largeur_scene, hauteur_scene);
                                message_t = 8;
                            } else {
                                mt = new MessageTransition(1, m.j2.nom, largeur_scene, hauteur_scene);
                                message_t = 8;
                            }
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            messagePioche = false;
                            if (m.config.gagnant == J1) {
                                bandeau.tourJ(J1);
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                bandeau.tourJ(J2);
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            if (m.config.donneur == J1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            tour_pioche = 0;
                            message_t = 0;
                            finTour = false;
                            messageActif = 0;
                        }
                    }

                    //Affichage Message transition CoupPremier CoupSecond Joueur Contre Joueur
                    if (m.config.mode == 1 && m.config.carteP != null && carte_jouee == 1 && pause == 0 && (message_t == 1 || message_t == 2)) {
                        if (messageActif == 0 && message_t != 2) {
                            if (m.config.receveur == J1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                            }
                            if (m.config.receveur == 1) {
                                mt = new MessageTransition(1, m.j1.nom, largeur_scene, hauteur_scene);
                                message_t = 2;
                            } else {
                                mt = new MessageTransition(1, m.j2.nom, largeur_scene, hauteur_scene);
                                message_t = 2;
                            }
                            root.getChildren().add(mt);
                        }

                        if (!mt.isVisible()) {
                            if (m.config.receveur == J1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            carte_jouee = 0;
                            messageActif = 0;
                            message_t = 0;
                        }
                    }

                    //Affichage Message transition CoupSecond Pioche Joueur Contre Joueur
                    if (m.config.mode == 1 && messagePioche == true && carte_jouee == 1 && m.config.piochable() && pause == 0 && (message_t == 3 || message_t == 4)) {
                        if (messageActif == 0 && (message_t != 4)) {
                            if (m.config.gagnant == J1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                            }
                            if (m.config.gagnant == 1) {
                                mt = new MessageTransition(2, m.j1.nom, largeur_scene, hauteur_scene);
                                message_t = 4;
                            } else {
                                mt = new MessageTransition(2, m.j2.nom, largeur_scene, hauteur_scene);
                                message_t = 4;
                            }
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            if (m.config.gagnant == J1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            messagePioche = false;
                            carte_jouee = 0;
                            tour_pioche = 1;
                            messageActif = 0;
                            message_t = 0;
                        }
                    }

                    //Affichage Message transition PremierePioche DeuxiemePioche Joueur Contre Joueur
                    if (m.config.mode == 1 && messagePioche == true && tour_pioche == 2 && (message_t == 5 || message_t == 6)) {
                        if (messageActif == 0 && (message_t != 6)) {
                            if (m.config.perdant == J1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                                cacher_face_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                                cacher_face_main(j1main, J1);
                            }
                            if (m.config.perdant == 1) {
                                mt = new MessageTransition(2, m.j1.nom, largeur_scene, hauteur_scene);
                                message_t = 6;
                            } else {
                                mt = new MessageTransition(2, m.j2.nom, largeur_scene, hauteur_scene);
                                message_t = 6;
                            }
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            if (m.config.perdant == J1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            messagePioche = false;
                            if (m.config.perdant == J1) {
                                bandeau.tourJ(J1);
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                bandeau.tourJ(J2);
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            tour_pioche = 2;
                            message_t = 0;
                            finTour = false;
                            messageActif = 0;
                        }
                    }

                    //Affichage Message transition FinManche Début Autre Manche Joueur Contre Joueur
                    if (m.config.mode == 1 && messagePioche == true && finTour == true && message_t == 100) {
                        if (messageActif == 0) {
                            if (tour_joueur == 1) {
                                affichage_dos_main_bas(j1main, J1, 1);
                                affichage_dos_main(j2main, J2);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 1);
                                affichage_dos_main(j1main, J1);
                            }
                            if (m.config.donneur == 1) {
                                mt = new MessageTransition(1, m.j1.nom, largeur_scene, hauteur_scene);
                            } else {
                                mt = new MessageTransition(1, m.j2.nom, largeur_scene, hauteur_scene);
                            }
                            root.getChildren().add(mt);
                            messageActif = 1;
                        }

                        if (!mt.isVisible()) {
                            if (tour_joueur == 1) {
                                affichage_dos_main_bas(j1main, J1, 0);
                            } else {
                                affichage_dos_main_bas(j2main, J2, 0);
                            }
                            messagePioche = false;
                            pause = 4;
                            if (m.config.perdant == J1) {
                                init_main(j1main, J1);
                                affichage_face_main(j1main, J1);
                            } else {
                                init_main(j2main, J2);
                                affichage_face_main(j2main, J2);
                            }
                            finTour = false;
                            carte_jouee = 0;
                            tour_pioche = 0;
                            messageActif = 0;
                        }
                    }

                    //On rentre ici quand les 2 joueurs ont joué une carte et que la pioche est vide, et on retourne le plateau pour que le gagnant soit en bas
                    if (!m.finManche() && m.config.mode == 1 && pause == 5 && temps + 1000 < System.currentTimeMillis()) {

                        System.out.println("PAUSE 5 !");

                        init_main(j1main, J1);
                        init_main(j2main, J2);

                        J1_carte_jouee.face.setVisible(false);
                        J2_carte_jouee.face.setVisible(false);

                        bandeau.plisJ1.setText(String.valueOf("Plis : " + m.j1.score));
                        bandeau.plisJ2.setText(String.valueOf("Plis : " + m.j2.score));

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

                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 10.5);

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

                            bandeau.nom_plisJ2.setTranslateY(hauteur_scene / 1.18);
                            bandeau.nom_plisJ1.setTranslateY(hauteur_scene / 14);
                            bandeau.plisJ2.setTranslateY(hauteur_scene / 1.15);
                            bandeau.plisJ1.setTranslateY(hauteur_scene / 10.5);

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
                        message_t = 7;
                        System.out.println("FIN PAUSE 5");
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

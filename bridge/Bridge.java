/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Florent
 */
public class Bridge extends Application {

    private final int hauteur_scene = 720;
    private final int largeur_scene = 1280;

    //private final int hauteur_carte = 110;
    //private final int largeur_carte = 90;
    private final int souris_carte = 610;

    public Moteur2 m;

    AnchorPane root;

    public Carte[] j1main;
    public Carte[] j2main;
    public Carte[][] pile;
    public Carte[] j1plis;
    public Carte[] j2plis;

    int temps = 0;
    int carte_jouee = 0;
    int tour_joueur = 1;
    int tour_pioche = 0;
    int k = 0;

    //MessageTransition MessageT;
    public void init_main(Carte[] main, int j) {
        String color;
        String number;

        if (j == 1) {
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
            String carte = color + "_" + number + ".PNG";
            ImagePattern img = new ImagePattern(new Image("images/" + carte));
            main[i].face.setFill(img);
            main[i].face.toFront();

            //main[i].face.setTranslateX(440+(60*i));
            //main[i].face.setTranslateY(642);
        }
    }

    public void init_mainJ1J2() {
        init_main(j1main, 1);
        init_main(j2main, 2);
    }

    public void init_pile(Carte[][] pile) {
        String color;
        String number;

        for (int j = 0; j < m.config.pioche.length; j++) {
            for (int i = 0; i < m.config.pioche[j].pile.size(); i++) {
                pile[j][i] = m.config.pioche[j].pile.get(i);
            }
        }
        for (int j = 0; j < m.config.pioche.length; j++) {
            for (int i = 0; i < m.config.pioche[j].pile.size(); i++) {
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
                String carte = color + "_" + number + ".PNG";
                ImagePattern img = new ImagePattern(new Image("images/" + carte));
                pile[j][i].face.setFill(img);
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
            m.afficherCarte(plis[i]);
            i++;
        }
    }

    public void affichage_dos_plisJ1(Carte[] j1plis) {
        for (int i = 0; i < m.j1.tas.pile.size(); i++) {
            j1plis[i].face.setVisible(false);
            j1plis[i].dos.setTranslateX(1150);
            j1plis[i].dos.setTranslateY(480);
            j1plis[i].dos.setVisible(true);
        }
    }

    public void affichage_dos_plisJ2(Carte[] j2plis) {
        for (int i = 0; i < m.j2.tas.pile.size(); i++) {
            j2plis[i].face.setVisible(false);
            j2plis[i].dos.setTranslateX(1150);
            j2plis[i].dos.setTranslateY(150);
            j2plis[i].dos.setVisible(true);
        }
    }

    public void affichage_face_mainJ1(Carte[] j1main) {
        for (int i = 0; i < m.j1.main.pile.size(); i++) {
            j1main[i].face.setTranslateX(440 + (60 * i));
            j1main[i].face.setTranslateY(642);
            j1main[i].face.setVisible(true);
        }
    }

    public void affichage_face_mainJ2(Carte[] j2main) {
        for (int i = 0; i < m.j2.main.pile.size(); i++) {
            j2main[i].face.setTranslateX(440 + (60 * i));
            j2main[i].face.setTranslateY(642);
            j2main[i].face.setVisible(true);
        }
    }

    public void affichage_face_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            pile[j][0].face.setTranslateX(430 + (110 * j));
            pile[j][0].face.setTranslateY(315);
            pile[j][0].face.toFront();
            pile[j][0].face.setVisible(true);
        }
    }

    public void affichage_dos_mainJ1(Carte[] j1main) {
        for (int i = 0; i < j1main.length; i++) {
            j1main[i].dos.setTranslateX(440 + (60 * i));
            j1main[i].dos.setTranslateY(-17);
            ImagePattern img = new ImagePattern(new Image("images/DOS_1.PNG"));
            j1main[i].dos.setFill(img);
        }
    }

    public void affichage_dos_mainJ2(Carte[] j2main) {
        for (int i = 0; i < j2main.length; i++) {
            j2main[i].dos.setTranslateX(440 + (60 * i));
            j2main[i].dos.setTranslateY(-17);
            ImagePattern img = new ImagePattern(new Image("images/DOS_1.PNG"));
            j2main[i].dos.setFill(img);
        }
    }

    public void affichage_dos_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            for (int i = 1; i < pile[j].length; i++) {
                pile[j][i].dos.setTranslateX(435 + (110 * j - 1 * i));
                pile[j][i].dos.setTranslateY(315);
                ImagePattern img = new ImagePattern(new Image("images/DOS_1.PNG"));
                pile[j][i].dos.setFill(img);
            }
        }
    }

    public void carte_select_P(Carte[] main, int k) {
        carte_jouee = 1;
        Carte card = m.jouerCoupPremier(main[k]);
        
        m.afficherCarte(card);

        main[k].face.setTranslateX(710);
        main[k].face.setTranslateY(150);
        main[k].face.toFront();

        //System.out.println("Carte jouée");
        //actualiser_main(j1main, k);
        //affichage_dos_mainJ1(j1main);
        
        init_main(main, m.config.donneur);

        for (int i = 0; i < main.length; i++) {
            if (main[i] != card) {
                main[i].face.setVisible(false);
            }
        }
        
        if (m.config.donneur == 1) {
            tour_joueur = 2;
            affichage_face_mainJ2(j2main);
        } else {
            tour_joueur = 1;
            affichage_face_mainJ1(j1main);
        }
        
        carte_jouee = 0;
    }

    public void carte_select_S(int k) {
        carte_jouee = 1;
        Carte card;

        if (m.config.receveur == 2) {

            //Boolean carte_correcte = false;
            //while (!carte_correcte) {
            //card = m.jouerCoupSecond(j2main[k]);
            //}
            card = m.jouerCoupSecond(j2main[k]);
            m.afficherCarte(card);

            j2main[k].face.setTranslateX(710);
            j2main[k].face.setTranslateY(480);
            j2main[k].face.toFront();
            tour_joueur = 1;
            carte_jouee = 0;

        } else {
            //Boolean carte_correcte = false;
            //while (!carte_correcte) {
            //card = m.jouerCoupSecond(j2main[k]);
            //}

            card = m.jouerCoupSecond(j1main[k]);
            m.afficherCarte(card);

            j1main[k].face.setTranslateX(710);
            j1main[k].face.setTranslateY(480);
            j1main[k].face.toFront();
            tour_joueur = 2;
            carte_jouee = 0;
        }
        System.out.println("Carte jouée");

        //actualiser_main(j2main, k);
        //affichage_dos_mainJ2(j2main);
        //affichage_face_mainJ1(j1main);
        init_main(j1main, 1);
        init_main(j2main, 2);

        for (int i = 0; i < j1main.length; i++) {
            if (j1main[i] != card) {
                j1main[i].face.setVisible(false);
            }
        }

        for (int i = 0; i < j2main.length; i++) {
            if (j2main[i] != card) {
                j2main[i].face.setVisible(false);
            }
        }
        //affichage_face_mainJ2(j2main);
        m.config.taille--;
        m.config.gagnantPli();
        System.out.println("Le joueur " + m.config.gagnant + " gagne le pli");
        m.config.donneur = m.config.gagnant;
        m.config.receveur = m.config.perdant;
        System.out.println();
        m.rangerPli();

        maj_plis(j1plis, 1);
        maj_plis(j2plis, 2);
        affichage_dos_plisJ1(j1plis);
        affichage_dos_plisJ2(j2plis);

        if (m.config.gagnant == 1) {
            affichage_face_mainJ1(j1main);
            m.config.perdant = 2;
        } else {
            affichage_face_mainJ2(j2main);
            m.config.perdant = 1;
        }
        if (!m.config.piochable()) {
            init_main(j1main, 1);
            init_main(j2main, 2);
            for (int i = 0; i < m.j1.main.pile.size(); i++) {
                j1main[i].face.setVisible(false);
            }
            for (int i = 0; i < m.j2.main.pile.size(); i++) {
                m.afficherCarte(j2main[i]);
                j2main[i].face.setVisible(false);
            }
            maj_handler_main();
            maj_handler_pile();
            carte_jouee = 0;
            tour_pioche = 0;
            if (m.config.donneur == 1) {
                tour_joueur = 1;
                affichage_face_mainJ1(j1main);
            } else {
                tour_joueur = 2;
                affichage_face_mainJ2(j2main);
            }
        } else {
            tour_pioche = 1;
        }
    }

    //Logger.getLogger(this.getClass().getName()).log(Level.DEBUG, "TOUR 1");
    public void maj_handler_unitMain(int n, Carte[] main, int j) {
        main[n].face.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0) {
                    main[n].face.setTranslateY(souris_carte);
                    //j1main[0].toFront();
                }
            }
        });

        main[n].face.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0) {
                    main[n].face.setTranslateY(642);
                }
            }
        });

        main[n].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0) {
                    if (m.config.donneur == j) {
                        carte_select_P(main,n);
                    } else {
                        carte_select_S(n);
                    }
                }
            }
        });
    }

    public void maj_handler_main() {
        for (k = 0; k < j1main.length; k++) {
            maj_handler_unitMain(k, j1main, 1);
        }
        for (k = 0; k < j2main.length; k++) {
            maj_handler_unitMain(k, j2main, 2);
        }
    }

    public void maj_handler_unitPile(int n) {
        pile[n][0].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (tour_pioche == 2) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.perdant, pile[n][0], n);
                        System.out.println();
                        m.config.taille++;
                    }
                    init_pile(pile);
                    affichage_face_pile(pile);
                    m.config.donneur = m.config.gagnant;

                    if (m.config.donneur == 1 && j1main[0] != null) {
                        tour_joueur = 1;
                        m.config.receveur = 2;
                        init_main(j1main, 1);
                        init_main(j2main, 2);
                        for (int i = 0; i < m.j1.main.pile.size(); i++) {
                            j1main[i].face.setVisible(false);
                        }
                        for (int i = 0; i < m.j2.main.pile.size(); i++) {
                            m.afficherCarte(j2main[i]);
                            j2main[i].face.setVisible(false);
                        }
                        maj_handler_main();
                        maj_handler_pile();
                        affichage_face_mainJ1(j1main);
                    } else {
                        tour_joueur = 2;
                        m.config.receveur = 1;
                        init_main(j1main, 1);
                        init_main(j2main, 2);
                        for (int i = 0; i < m.j1.main.pile.size(); i++) {
                            m.afficherCarte(j1main[i]);
                            j1main[i].face.setVisible(false);
                        }
                        for (int i = 0; i < m.j2.main.pile.size(); i++) {
                            j2main[i].face.setVisible(false);
                        }
                        maj_handler_main();
                        maj_handler_pile();
                        affichage_face_mainJ2(j2main);
                    }
                    carte_jouee = 0;
                    tour_pioche = 0;
                }

                if (tour_pioche == 1) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.gagnant, pile[n][0], n);
                        System.out.println();
                        init_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        tour_pioche = 2;
                    }

                    if (m.config.gagnant == 1) {
                        init_main(j1main, 1);
                        for (int i = 0; i < m.j1.main.pile.size(); i++) {
                            j1main[i].face.setVisible(false);
                            //m.afficherCarte(m.j1.main.pile.get(i));
                            m.afficherCarte(j1main[i]);
                        }
                        init_main(j2main, 2);
                        affichage_face_mainJ2(j2main);
                    } else {
                        init_main(j2main, 2);
                        for (int i = 0; i < m.j2.main.pile.size(); i++) {
                            j2main[i].face.setVisible(false);
                            //m.afficherCarte(m.j2.main.pile.get(i));
                            m.afficherCarte(j2main[i]);
                        }
                        init_main(j1main, 1);
                        affichage_face_mainJ1(j1main);
                    }
                }
            }
        });
    }

    public void maj_handler_pile() {
        for (k = 0; k < m.config.pioche.length; k++) {
            maj_handler_unitPile(k);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        m = new Moteur2();

        m.initialiser();

        switch (m.config.donneurInitial) {
            case 0:
                m.config.donneurInitial = 1;
                m.config.donneur = 1;
                break;
            case 1:
                m.config.donneurInitial = 2;
                m.config.donneur = 2;
                break;
            default:
                m.config.donneurInitial = 1;
                m.config.donneur = 1;
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

        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];

        init_mainJ1J2();
        init_pile(pile);

        affichage_face_mainJ1(j1main);
        affichage_dos_mainJ2(j2main);
        affichage_dos_pile(pile);
        affichage_face_pile(pile);

        Rectangle menu = new Rectangle(260, 720, Color.PERU);

        /*
         debut initialisation cartes Rectangle j1plis = new Rectangle(largeur_carte, hauteur_carte, Color.BLACK);
         j1plis.setTranslateX(1150);
         j1plis.setTranslateY(480);

         Rectangle j2carte_select = new Rectangle(largeur_carte, hauteur_carte, Color.PURPLE);
         j2carte_select.setTranslateX(710);
         j2carte_select.setTranslateY(150);

         Rectangle j2plis = new Rectangle(largeur_carte, hauteur_carte, Color.BLACK);
         j2plis.setTranslateX(1150);
         j2plis.setTranslateY(150);
         fin initialisation cartes MessageT = new MessageTransition();
         MessageT.setVisible(false);
         */
        /*
         MessageT.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent me) {
         MessageT.setVisible(false);
         if (tour_joueur == 1) {
         affichage_face_mainJ1(j1main);
         } else {
         affichage_face_mainJ2(j2main);
         }
         }
         });
         */

        /*
         AnimationTimer timer = new AnimationTimer() {
         @Override
         public void handle(long now) {
         if (temps + 2000 < System.currentTimeMillis()) {

         }
         }
         };
         timer.start();
         */
        maj_handler_main();
        maj_handler_pile();

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
            for (int i = 1; i < pile[j].length; i++) {
                root.getChildren().add(pile[j][i].face);
                root.getChildren().add(pile[j][i].dos);
            }
        }

        for (int j = 0; j < pile.length; j++) {
            root.getChildren().add(pile[j][0].face);
            root.getChildren().add(pile[j][0].dos);
        }

        /*
         root.getChildren().add(j1plis);
         root.getChildren().add(j2carte_select);
         root.getChildren().add(j2plis);
         root.getChildren().add(MessageT);
         */
        root.getChildren().add(menu);

        Scene scene = new Scene(root, largeur_scene, hauteur_scene, Color.MEDIUMAQUAMARINE);
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

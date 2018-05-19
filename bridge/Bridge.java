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

    private final int souris_carte = 610;

    private final int J1 = 1;
    private final int J2 = 2;
    private final int IA = 2;

    public Moteur2 m;

    AnchorPane root;
    Rectangle menu;

    public Carte[] j1main;
    public Carte[] j2main;
    public Carte[][] pile;
    public Carte[] j1plis;
    public Carte[] j2plis;

    int temps = 0;
    int carte_jouee = 0;
    int tour_joueur = J1;
    int tour_pioche = 0;
    int k = 0;
    
    Carte J1_carte_jouee;
    Carte J2_carte_jouee;

    //MessageTransition MessageT;
    public void init_manche() {
        carte_jouee = 0;
        tour_pioche = 0;
        tour_joueur = J1;
        temps = 0;
        k = 0;

        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];

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

        init_mainJ1J2();
        init_pile(pile);

        System.out.println();
        System.out.println("Pile ");

        for (int j = 0; j < pile.length; j++) {
            for (int i = 0; i < pile[j].length; i++) {
                if (pile[j][i] != null) {
                    m.afficherCarte(pile[j][i]);
                    //pile[j][i].dos.setVisible(false);
                    //pile[j][i].face.setVisible(true);
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Main J1");

        for (int i = 0; i < j1main.length; i++) {
            if (j1main[i] != null) {
                m.afficherCarte(j1main[i]);
                //j1main[i].dos.setVisible(false);
                //j1main[i].face.setVisible(true);
            }
        }

        System.out.println();
        System.out.println("Main J2");

        for (int i = 0; i < j2main.length; i++) {
            if (j2main[i] != null) {
                m.afficherCarte(j2main[i]);
                //j2main[i].dos.setVisible(false);
                //j2main[i].face.setVisible(true);
            }
        }

        System.out.println();

        if (m.config.donneur == J1) {
            tour_joueur = J1;
            affichage_face_main(j1main, J1);
            affichage_dos_main(j2main, J2);
        } else {
            tour_joueur = J2;
            affichage_face_main(j2main, J2);
            affichage_dos_main(j1main, J1);
        }

        //affichage_dos_pile(pile);
        affichage_face_pile(pile);

        maj_handler_main();
        maj_handler_pile();

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
            root.getChildren().add(menu);
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
            String carte = color + "_" + number + ".PNG";
            ImagePattern img = new ImagePattern(new Image("images/" + carte));
            main[i].face.setFill(img);
            main[i].face.toFront();

            //main[i].face.setTranslateX(440+(60*i));
            //main[i].face.setTranslateY(642);
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
                    String carte = color + "_" + number + ".PNG";
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
            //m.afficherCarte(plis[i]);
            i++;
        }
    }

    public void affichage_dos_plis(Carte[] plis, int j) {
        int t;
        int posY;
        if (j == J1) {
            t = m.j1.tas.pile.size();
            posY = 480;
        } else {
            t = m.j2.tas.pile.size();
            posY = 150;
        }
        for (int i = 0; i < t; i++) {
            plis[i].face.setVisible(false);
            plis[i].dos.setTranslateX(1150);
            plis[i].dos.setTranslateY(posY);
            plis[i].dos.setVisible(true);
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
            main[i].face.setTranslateX(440 + (60 * i));
            main[i].face.setTranslateY(642);
            main[i].face.setVisible(true);
        }
    }

    public void affichage_face_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            if (pile[j][0] != null) {
                pile[j][0].face.setTranslateX(460 + (110 * j));
                pile[j][0].face.setTranslateY(315);
                pile[j][0].face.toFront();
                pile[j][0].face.setVisible(true);
            }
        }
    }

    public void affichage_dos_main(Carte[] main, int j) {
        int t;
        if (j == J1) {
            t = j1main.length;
        } else {
            t = j2main.length;
        }
        for (int i = 0; i < t; i++) {
            main[i].dos.setTranslateX(440 + (60 * i));
            main[i].dos.setTranslateY(-17);
            ImagePattern img = new ImagePattern(new Image("images/DOS_1.PNG"));
            main[i].dos.setFill(img);
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

        //m.afficherCarte(card);
        if (m.config.mode == 1) {
            main[k].face.setTranslateX(710);
            main[k].face.setTranslateY(150);
            main[k].face.toFront();
        } else if (m.config.mode == 2) {
            J1_carte_jouee = main[k];
            main[k].face.setTranslateX(710);
            main[k].face.setTranslateY(480);
            main[k].face.toFront();
        }

        //System.out.println("Carte jouée");
        //actualiser_main(j1main, k);
        //affichage_dos_main(j1main, J1);
        init_main(main, m.config.donneur);

        if (m.config.mode == 1) {
            for (int i = 0; i < main.length; i++) {
                if (main[i] != card) {
                    main[i].face.setVisible(false);
                }
            }
        } else if (m.config.mode == 2) {
            affichage_face_main(j1main, J1);
        }

        if (m.config.mode == 1) {
            if (m.config.donneur == J1) {
                tour_joueur = J2;
                affichage_face_main(j2main, J2);
            } else {
                tour_joueur = J1;
                affichage_face_main(j1main, J1);
            }
        } else if (m.config.mode == 2) {
            if (m.config.donneur == J1) {
                tour_joueur = IA;
            } else {
                tour_joueur = J1;
                affichage_face_main(j1main, J1);
            }
        }
        carte_jouee = 0;
    }

    public void carte_select_S(Carte[] main, int k) {

        //
        System.out.println("  Avant  joeurCoupSecond       ---------------J1---------------");
        for (int i = 0; i < m.j1.main.pile.size(); i++) {
            m.afficherCarte(m.j1.main.pile.get(i));
        }
        System.out.println("  Avant   joeurCoupSecond       --------------J2---------------");
        for (int i = 0; i < m.j2.main.pile.size(); i++) {
            m.afficherCarte(m.j2.main.pile.get(i));
        }
        System.out.println();
        //

        Carte card = m.jouerCoupSecond(main[k]);

        //
        System.out.println(" Apres  joeurCoupSecond       ----------------J1---------------");
        for (int i = 0; i < m.j1.main.pile.size(); i++) {
            m.afficherCarte(m.j1.main.pile.get(i));
        }
        System.out.println("   Apres  joeurCoupSecond       --------------J2---------------");
        for (int i = 0; i < m.j2.main.pile.size(); i++) {
            m.afficherCarte(m.j2.main.pile.get(i));
        }
        System.out.println();
        //

        if (card != null) {

            carte_jouee = 1;

            //m.afficherCarte(card);
            main[k].face.setTranslateX(710);
            main[k].face.setTranslateY(480);
            main[k].face.toFront();

            //System.out.println("Carte jouée");
            //actualiser_main(j2main, k);
            //affichage_dos_main(j2main, J2);
            init_main(j1main, J1);
            init_main(j2main, J2);

            for (int i = 0; i < main.length; i++) {
                if (main[i] != card) {
                    main[i].face.setVisible(false);
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

            maj_plis(j1plis, J1);
            maj_plis(j2plis, J2);

            affichage_dos_plis(j1plis, J1);
            affichage_dos_plis(j2plis, J2);

            if (m.config.gagnant == J1) {
                affichage_face_main(j1main, J1);
                m.config.perdant = J2;
            } else {
                affichage_face_main(j2main, J2);
                m.config.perdant = J1;
            }

            if (!m.config.piochable()) {
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
                carte_jouee = 0;
                tour_pioche = 0;
                if (m.config.donneur == J1) {
                    tour_joueur = J1;
                    affichage_face_main(j1main, J1);
                } else {
                    tour_joueur = J2;
                    affichage_face_main(j2main, J2);
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
    }

    public void score() {
        m.j1.score = m.j1.tas.taille() / 2;
        m.j2.score = m.j2.tas.taille() / 2;
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

        m.j1.scoreTotal = m.j1.scoreTotal + m.j1.score;
        m.j2.scoreTotal = m.j2.scoreTotal + m.j2.score;
    }

    //Logger.getLogger(this.getClass().getName()).log(Level.DEBUG, "TOUR 1");
    public void maj_handler_unitMain(int n, Carte[] main, int j) {
        main[n].face.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0) {
                    main[n].face.setTranslateY(souris_carte);
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
                        carte_select_P(main, n);
                    } else {
                        carte_select_S(main, n);
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
                if (tour_pioche == 2) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.perdant, pile[n][0], n);
                        System.out.println();
                        init_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                    }

                    m.config.donneur = m.config.gagnant;
                    m.config.receveur = m.config.perdant;

                    if (m.config.donneur == J1) {
                        tour_joueur = J1;
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
                        affichage_face_main(j1main, J1);
                    } else {
                        tour_joueur = J2;
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
                        affichage_face_main(j2main, J2);
                    }
                    carte_jouee = 0;
                    tour_pioche = 0;

                    System.out.println();
                }

                if (tour_pioche == 1) {
                    if (m.config.piochable()) {
                        m.config.afficherPioche();
                        m.pioche(m.config.gagnant, pile[n][0], n);
                        System.out.println();
                        init_pile(pile);
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        m.config.taille++;

                    }
                    if (m.config.mode == 1) {
                        if (m.config.gagnant == J1) {
                            init_main(j1main, J1);
                            for (int i = 0; i < j1main.length; i++) {
                                j1main[i].face.setVisible(false);
                                //m.afficherCarte(j1main[i]);
                            }
                            System.out.println();
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        } else {
                            init_main(j2main, J2);
                            for (int i = 0; i < j2main.length; i++) {
                                j2main[i].face.setVisible(false);
                                //m.afficherCarte(j2main[i]);
                            }
                            System.out.println();
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        }
                        tour_pioche = 2;
                    } else if (m.config.mode == 2) {
                        if (m.config.gagnant == J1) {
                            System.out.println("YATA");
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
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

    @Override
    public void start(Stage primaryStage) {

        m = new Moteur2();

        m.initialiser();

        init_manche();

        /*
         MessageT.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent me) {
         MessageT.setVisible(false);
         if (tour_joueur == J1) {
         affichage_face_main(j1main,1);
         } else {
         affichage_face_main(j2main,2);
         }
         }
         });
         */
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                /*if (temps + 2000 < System.currentTimeMillis()) {

                }*/

                if (m.finPartie()) {
                    //Afficher le score de chaque joueur et une fenêtre pour recommencer, voir IHM

                } else if (m.finManche()) {    //Si la manche est finie
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
                        }
                    }
                }

                if (((m.config.mode == 2 && tour_joueur == 2) || m.config.mode == 3 && tour_joueur > 0) && carte_jouee == 0 && !m.finManche()) { //Cas où c'est au tour d'une IA de jouer
                    carte_jouee = 1;
                    System.out.println("IA joue sa carte");
                    J2_carte_jouee = m.jouerCoupIA(tour_joueur);
                    //Affichage de sa carte à l'IA
                    J2_carte_jouee.face.setTranslateX(710);
                    J2_carte_jouee.face.setTranslateY(150);
                    J2_carte_jouee.face.toFront();

                    if (m.config.carteS == null) {  //Si la deuxieme carte n'est pas jouée, l'autre joueur devient le joueur courant AFFICHAGE DES MAINS EN CONSEQUENCE A FAIRE, PENSER A UTILISER UNE METHODE QUI AFFICHE LA MAIN DU JOUEUR PASSE EN PARAMETRE
                        if (tour_joueur == J1) {
                            tour_joueur = J2;
                        } else {
                            tour_joueur = J1;
                        }
                    } else {
                        tour_joueur = 0;
                    }
                    carte_jouee = 0;
                }

                if (m.config.mode == 2 && tour_pioche == 1 && m.config.gagnant == IA ) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                    m.config.afficherPioche();
                    Carte c = m.piocheIA(tour_pioche);
                    System.out.println();
                    init_pile(pile);
                    affichage_face_pile(pile);
                    maj_handler_pile();
                    init_main(j2main, IA);
                    System.out.println("boucle ?");
                    m.config.taille++;
                    tour_pioche = 2;

                    //Si le joueur courant était gagnant, l'autre joueur devient le piocheur courant AFFICHAGE DES MAINS EN CONSEQUENCE A FAIRE, PENSER A UTILISER UNE METHODE QUI AFFICHE LA MAIN DU JOUEUR PASSE EN PARAMETRE
                    /*if (tour_pioche == m.config.gagnant) {
                        if (tour_pioche == 1) {
                            tour_pioche = 2;
                        } else {
                            tour_pioche = 1;
                        }
                    } else {
                        tour_pioche = 0;
                    }*/
                }

                if (m.config.mode == 2 && tour_pioche == 2 && m.config.perdant == IA) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
                    m.config.afficherPioche();
                    Carte c = m.piocheIA(tour_pioche);
                    System.out.println();
                    init_pile(pile);
                    affichage_face_pile(pile);
                    maj_handler_pile();
                    //init_main(j2main, IA);
                    
                    m.config.donneur = m.config.gagnant;
                    m.config.receveur = m.config.perdant;

                    if (m.config.donneur == J1) {
                        tour_joueur = J1;
                        m.config.receveur = IA;
                    } else {
                        tour_joueur = IA;
                        m.config.receveur = J1;
                    }
                    
                    J1_carte_jouee.face.setVisible(false);
                    J2_carte_jouee.face.setVisible(false);
                    
                    maj_handler_main();
                    maj_handler_pile();
                    carte_jouee = 0;
                    tour_pioche = 0;

                    System.out.println();
                }

                if (m.config.mode == 2 && m.config.carteP != null && m.config.carteS != null) {
                    m.config.gagnantPli();
                    System.out.println("Le joueur " + m.config.gagnant + " gagne le pli");
                    m.config.donneur = m.config.gagnant;
                    m.config.receveur = m.config.perdant;
                    System.out.println();
                    m.rangerPli();

                    //maj_plis(j1plis, J1);
                    //maj_plis(j2plis, IA);

                    //affichage_dos_plis(j1plis, J1);
                    //affichage_dos_plis(j2plis, IA);
                    if (m.config.gagnant == J1) {
                        m.config.perdant = IA;
                    } else {
                        m.config.perdant = J1;
                    }
                    //Temporairement
                    if(m.config.piochable()){
                        tour_pioche = 1;
                    }
                }

                //A adapter !
                if (m.config.mode == 2 && carte_jouee == 1 && !m.config.piochable()) {
                    init_main(j1main, J1);
                    init_main(j2main, IA);
                    maj_handler_main();
                    maj_handler_pile();
                    carte_jouee = 0;
                    tour_pioche = 0;
                    if (m.config.donneur == J1) {
                        tour_joueur = J1;
                        affichage_face_main(j1main, J1);
                    } else {
                        tour_joueur = IA;
                    }
                }
            }
        };
        timer.start();

        menu = new Rectangle(260, 720, Color.PERU);

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

        root.getChildren().add(menu);

        //root.getChildren().add(MessageT);
        Scene scene = new Scene(root, largeur_scene, hauteur_scene, Color.MEDIUMAQUAMARINE);
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

/**
 *
 * @author Florent
 */
public class Bridge extends Application {

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
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private final double largeur_scene = screenSize.getWidth();
    private final double hauteur_scene = screenSize.getHeight();
    private final double souris_carte = hauteur_scene - (hauteur_scene/5.45) + 1;

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

        m.config.carteP = null;
        m.config.carteS = null;
        J1_carte_jouee = null;
        J2_carte_jouee = null;

        j1main = new Carte[11];
        j2main = new Carte[11];
        pile = new Carte[6][5];
        j1plis = new Carte[52];
        j2plis = new Carte[52];

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
            posY = hauteur_scene/1.5;
        } else {
            t = m.j2.tas.pile.size();
            posY = hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte;
        }
        for (int i = 0; i < t; i++) {
            plis[i].face.setVisible(false);
            plis[i].dos.setTranslateX(largeur_scene-plis[i].largeur_carte*1.25);
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
            if(plis[i]!=null){
                plis[i].face.setVisible(false);
                plis[i].dos.setTranslateX(largeur_scene-plis[i].largeur_carte*1.25);
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
            main[i].face.setTranslateX(largeur_scene/2.5 + ((main[i].largeur_carte/2) * i));
            main[i].face.setTranslateY(hauteur_scene-main[i].hauteur_carte*0.75);
            main[i].face.setVisible(true);
        }
    }

    public void affichage_face_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            if (pile[j][0] != null) {
                pile[j][0].face.setTranslateX(largeur_scene/2.75 + (pile[j][0].largeur_carte*1.25 * j));
                pile[j][0].face.setTranslateY((hauteur_scene/2)-(pile[j][0].hauteur_carte/2));
                pile[j][0].face.toFront();
                pile[j][0].face.setVisible(true);
            }
        }
    }
    
    public void affichage_dos_main(Carte[] main, int j) {
        int t;
        
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
              
        for (int i = 0; i < t; i++) {
            main[i].face.setVisible(false);
            main[i].dos.setTranslateX(largeur_scene/2.5 + ((main[i].largeur_carte/2) * i));
            main[i].dos.setTranslateY(-(main [i].hauteur_carte*0.25));
            ImagePattern img = new ImagePattern(new Image("images/DOS_1.png"));
            main[i].dos.setFill(img);
            main[i].dos.setVisible(true);
            main[i].dos.toFront();
        }
    }

    /*public void affichage_dos_main(Carte[] main, int j) {
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
            main[i].face.setVisible(true);
            main[i].face.setTranslateX(largeur_scene/2.5 + ((main[i].largeur_carte/2) * i));
            main[i].face.setTranslateY(0);
            main[i].face.toFront();
        }
    }*/

    /*
    public void affichage_dos_pile(Carte[][] pile) {
        for (int j = 0; j < pile.length; j++) {
            for (int i = 1; i < pile[j].length; i++) {
                pile[j][i].dos.setTranslateX(435 + (110 * j - 1 * i));
                pile[j][i].dos.setTranslateY(315);
                ImagePattern img = new ImagePattern(new Image("images/DOS_1.png"));
                pile[j][i].dos.setFill(img);
            }
        }
    }
     */
        
    public void carte_select_P(Carte[] main, int k) {
        carte_jouee = 1;

        Carte card = m.jouerCoupPremier(main[k]);

        if (m.config.mode == 1 && m.config.donneur == 1) {
            J1_carte_jouee = main[k];
            //main[k].face.setTranslateX(710);
            //main[k].face.setTranslateY(480);
            main[k].face.setTranslateX(largeur_scene/1.8);
            main[k].face.setTranslateY(hauteur_scene/1.5);
            main[k].face.toFront();
        } else if (m.config.mode == 1 && m.config.donneur == 2) {
            J2_carte_jouee = main[k];
            //main[k].face.setTranslateX(710);
            //main[k].face.setTranslateY(480);
            main[k].face.setTranslateX(largeur_scene/1.8);
            main[k].face.setTranslateY(hauteur_scene/1.5);
            main[k].face.toFront();
        } else if (m.config.mode == 2) {
            J1_carte_jouee = main[k];
            //main[k].face.setTranslateX(710);
            //main[k].face.setTranslateY(480);
            main[k].face.setTranslateX(largeur_scene/1.8);
            main[k].face.setTranslateY(hauteur_scene/1.5);
            main[k].face.toFront();
        }

        //System.out.println("Carte jouée");
        //actualiser_main(j1main, k);
        //affichage_dos_main(j1main, J1);
        
        init_main(main, m.config.donneur);

        if (m.config.mode == 1) {
            pause = 1;
            affichage_face_main(main, m.config.donneur);
            temps = System.currentTimeMillis();

            /*for (int i = 0; i < main.length; i++) {
                if (main[i] != card) {
                    main[i].face.setVisible(false);
                }
            }
            if (m.config.donneur == J1) {
                tour_joueur = J2;
                affichage_face_main(j2main, J2);
            } else {
                tour_joueur = J1;
                affichage_face_main(j1main, J1);
            }*/
        } else if (m.config.mode == 2) {
            init_main(main,J1);
            affichage_face_main(j1main, J1);
            if (m.config.donneur == J1) {
                tour_joueur = IA;
                //bandeau.tourJ(IA);
                temps = System.currentTimeMillis();
            } else {
                tour_joueur = J1;
                bandeau.tourJ(J1);
                affichage_face_main(j1main, J1);
            }
        }
        carte_jouee = 0;
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

            //m.afficherCarte(card);
            main[k].face.setTranslateX(largeur_scene/1.8);
            main[k].face.setTranslateY(hauteur_scene/1.5);
            main[k].face.toFront();

            //System.out.println("Carte jouée");
            //actualiser_main(j2main, k);
            //affichage_dos_main(j2main, J2);
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
                //maj_plis(j1plis, J1);
                //maj_plis(j2plis, J2);

                //affichage_dos_plis(j1plis, J1);
                //affichage_dos_plis(j2plis, J2);
                pause = 2;
                affichage_face_main(main, m.config.receveur);
                temps = System.currentTimeMillis();

                /*if (m.config.gagnant == J1) {
                    affichage_face_main(j1main, J1);
                    m.config.perdant = J2;
                } else {
                    affichage_face_main(j2main, J2);
                    m.config.perdant = J1;
                }*/
            } else if (m.config.mode == 2) {
                affichage_face_main(j1main, J1);

                if (m.config.gagnant == J1) {
                    m.config.perdant = IA;
                } else {
                    m.config.perdant = J1;
                    temps = System.currentTimeMillis();
                }
            }

            if (!m.config.piochable()) {
                init_main(j1main, J1);
                if (m.config.mode == 2) {
                    for (int i = 0; i < 11; i++) {
                        j2main[i].dos.setVisible(false);
                    }
                }
                init_main(j2main, J2);
                /*for (int i = 0; i < m.j1.main.pile.size(); i++) {
                    j1main[i].face.setVisible(false);
                }
                for (int i = 0; i < m.j2.main.pile.size(); i++) {
                    j2main[i].face.setVisible(false);
                }*/
                maj_handler_main();
                maj_handler_pile();
                if (m.config.mode == 2) {
                    clean = 1;
                    affichage_dos_main(j2main, IA);
                }
                carte_jouee = 0;
                tour_pioche = 0;
                if (m.config.mode == 1) {
                    J1_carte_jouee.face.setVisible(true);
                    J2_carte_jouee.face.setVisible(true);
                    temps = System.currentTimeMillis();
                    pause = 5;
                    /*if (m.config.donneur == J1) {
                        tour_joueur = J1;
                        affichage_face_main(j1main, J1);
                    } else {
                        tour_joueur = J2;
                        affichage_face_main(j2main, J2);
                    }*/
                } else {
                    J1_carte_jouee.face.setVisible(true);
                    J2_carte_jouee.face.setVisible(true);
                    temps = System.currentTimeMillis();
                    if (m.config.donneur == J1) {
                        tour_joueur = J1;
                        bandeau.tourJ(J1);
                        affichage_face_main(j1main, J1);
                    } else {
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
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0) {
                    main[n].face.setTranslateY(souris_carte);
                }
            }
        });

        main[n].face.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0) {
                    main[n].face.setTranslateY(hauteur_scene-main[n].hauteur_carte*0.75);
                }
            }
        });

        main[n].face.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (carte_jouee == 0 && tour_joueur == j && tour_pioche == 0 && clean == 0 && pause == 0) {
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
                if (tour_pioche == 2 && (m.config.mode == 1 || m.config.perdant != IA) && pause == 0) {
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

                    if (m.config.mode == 2) {
                        tour_joueur = m.config.gagnant;
                        bandeau.tourJ(m.config.receveur);
                        init_main(j1main, J1);
                        affichage_face_main(j1main, J1);
                        maj_handler_main();
                        //maj_handler_pile();
                        temps = System.currentTimeMillis();
                        clean = 1;
                    } else if (m.config.mode == 1) {
                        bandeau.tourJ(m.config.receveur);
                        temps = System.currentTimeMillis();
                        pause = 4;
                        
                        if(m.config.perdant == J1){
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        }
                        else{
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }
                        
                        temps2 = System.currentTimeMillis();
                        clean = 1;
                    }
                    carte_jouee = 0;
                    tour_pioche = 0;
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
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        m.config.taille++;

                    }
                    if (m.config.mode == 1) {
                        if (m.config.gagnant == J1) {
                            bandeau.tourJ(J1);
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                        } else {
                            bandeau.tourJ(J2);
                            init_main(j2main, J2);
                            affichage_face_main(j2main, J2);
                        }
                        temps = System.currentTimeMillis();
                        pause = 3;
                        tour_pioche = 2;
                    } else if (m.config.mode == 2) {
                        if (m.config.gagnant == J1) {
                            init_main(j1main, J1);
                            affichage_face_main(j1main, J1);
                            temps = System.currentTimeMillis();
                        } else{
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

                /*if (m.finPartie()) {
                    //Afficher le score de chaque joueur et une fenêtre pour recommencer, voir IHM
                }*/
                
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
                    J2_carte_jouee.face.setTranslateX(largeur_scene/1.8);
                    J2_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                    J2_carte_jouee.face.toFront();

                    //Si la deuxieme carte n'est pas jouée, l'autre joueur devient le joueur courant AFFICHAGE DES MAINS EN CONSEQUENCE A FAIRE, PENSER A UTILISER UNE METHODE QUI AFFICHE LA MAIN DU JOUEUR PASSE EN PARAMETRE
                    /*if (m.config.carteS == null) {  
                        if (tour_joueur == J1) {
                            tour_joueur = J2;
                        } else {
                            tour_joueur = J1;
                        }
                    } else {
                        tour_joueur = 0;
                    }*/
                    
                    if (m.config.carteS == null) {
                        tour_joueur = J1;
                        bandeau.tourJ(J1);
                        carte_jouee = 0;
                    } else {
                        m.config.taille--;
                        System.out.println("taille = " + m.config.taille);
                    }
                }

                if (m.config.mode == 2 && tour_pioche == 1 && m.config.gagnant == IA && temps + 1000 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
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
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        System.out.println("Main J2");

                        for (int i = 0; i < j2main.length; i++) {
                            if (j2main[i] != null) {
                                m.afficherCarte(j2main[i]);
                                //j2main[i].dos.setVisible(false);
                                //j2main[i].face.setVisible(true);
                            }
                        }

                        System.out.println();
                        m.config.taille++;
                        tour_pioche = 2;
                    }
                }

                if (m.config.mode == 2 && tour_pioche == 2 && m.config.perdant == IA && temps + 1000 < System.currentTimeMillis()) { //Cas où c'est à l'ia de piocher A VOIR POUR IA CONTRE IA
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
                        affichage_face_pile(pile);
                        maj_handler_pile();
                        System.out.println("Main J2");

                        for (int i = 0; i < j2main.length; i++) {
                            if (j2main[i] != null) {
                                m.afficherCarte(j2main[i]);
                                //j2main[i].dos.setVisible(false);
                                //j2main[i].face.setVisible(true);
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

                if (m.config.mode == 2 && m.config.receveur == IA && m.config.carteP != null && m.config.carteS != null) {
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
                    //temps = System.currentTimeMillis();
                    //clean = 1;
                    m.config.afficherPioche();

                    maj_plis(j1plis, J1);
                    maj_plis(j2plis, IA);

                    //affichage_dos_plis(j1plis, J1);
                    //affichage_dos_plis(j2plis, IA);
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

                if (m.config.mode == 1 && pause == 1 && temps + 1000 < System.currentTimeMillis()) {
                    if (m.config.donneur == J1) {
                        init_main(j1main,J1);
                        init_main(j2main,J2);
                        J1_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        
                        affichage_dos_plis(j1plis, J1, hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j2plis, J2, (hauteur_scene/1.5));
                        for (int i = 0; i < j1main.length; i++) {
                            if (j1main[i] != J1_carte_jouee) {
                                j1main[i].face.setVisible(false);
                            }
                        }
                        init_main(j1main,J1);
                        init_main(j2main,J2);
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
                        affichage_dos_main(j1main,J1);
                        tour_joueur = J2;
                        bandeau.tourJ(J2);
                        affichage_face_main(j2main, J2);
                    } else {
                        init_main(j1main,J1);
                        init_main(j2main,J2);
                        J2_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j1plis, J1, (hauteur_scene/1.5));
                        affichage_dos_plis(j2plis, J2, hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        for (int i = 0; i < j2main.length; i++) {
                            if (j2main[i] != J2_carte_jouee) {
                                j2main[i].face.setVisible(false);
                            }
                        }
                        init_main(j1main,J1);
                        init_main(j2main,J2);
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
                        affichage_dos_main(j2main,J2);
                        tour_joueur = J1;
                        bandeau.tourJ(J1);
                        affichage_face_main(j1main, J1);
                    }
                    pause = 0;
                }

                if (m.config.mode == 1 && pause == 2 && temps + 1000 < System.currentTimeMillis()) {
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
                        init_main(j1main,J1);
                        init_main(j2main,J2);
                        J1_carte_jouee.face.setTranslateY(hauteur_scene/1.5);
                        J2_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j1plis, J1, (hauteur_scene/1.5));
                        affichage_dos_plis(j2plis, J2, hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        affichage_face_main(j1main, J1);
                        init_main(j1main,J1);
                        init_main(j2main,J2);
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
                        affichage_dos_main(j2main,J2);
                        m.config.perdant = J2;
                    } else {
                        init_main(j1main,J1);
                        init_main(j2main,J2);
                        J1_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        J2_carte_jouee.face.setTranslateY((hauteur_scene/1.5));
                        affichage_dos_plis(j1plis, J1, hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j2plis, J2, (hauteur_scene/1.5));
                        affichage_face_main(j2main, J2);
                        init_main(j1main,J1);
                        init_main(j2main,J2);
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
                        affichage_dos_main(j1main,J1);
                        m.config.perdant = J1;
                    }
                    pause = 0;
                }
                if (m.config.mode == 1 && pause == 3 && temps + 1000 < System.currentTimeMillis()) {
                    bandeau.tourJ(m.config.perdant);
                    if (m.config.gagnant == J1) {
                        J2_carte_jouee.face.setTranslateY(hauteur_scene/1.5);
                        J1_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j1plis, J1, hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j2plis, J2, (hauteur_scene/1.5));
                        init_main(j1main,J1);
                        init_main(j2main,J2);
                        for (int i = 0; i < j1main.length; i++) {
                            j1main[i].face.setVisible(false);
                            //m.afficherCarte(j1main[i]);
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
                        affichage_dos_main(j1main,J1);
                        System.out.println();
                        init_main(j2main, J2);
                        affichage_face_main(j2main, J2);
                    } else {
                        J2_carte_jouee.face.setTranslateY(hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        J1_carte_jouee.face.setTranslateY(hauteur_scene/1.5);
                        affichage_dos_plis(j1plis, J1, (hauteur_scene/1.5));
                        affichage_dos_plis(j2plis, J2, hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
                        init_main(j1main,J1);
                        init_main(j2main,J2);
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
                        affichage_dos_main(j2main,J2);
                        System.out.println();
                        init_main(j1main, J1);
                        affichage_face_main(j1main, J1);
                    }
                    pause = 0;
                }

                if (m.config.mode == 1 && pause == 4 && temps + 1000 < System.currentTimeMillis()) {
                    if (m.config.donneur == J1) {
                        affichage_dos_plis(j1plis, J1, (hauteur_scene/1.5));
                        affichage_dos_plis(j2plis, J2, hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
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
                        affichage_dos_main(j2main,J2);
                        affichage_face_main(j1main, J1);
                    } else {
                        affichage_dos_plis(j1plis, J1, hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j2plis, J2, (hauteur_scene/1.5));
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
                        affichage_dos_main(j1main,J1);
                        affichage_face_main(j2main, J2);
                    }
                    pause = 0;
                }
                
                if (m.config.mode == 1 && pause == 5 && temps + 1000 < System.currentTimeMillis()) {
                    
                    System.out.println("Entree");
                    
                    init_main(j1main,J1);
                    init_main(j2main,J2);
                    
                    J1_carte_jouee.face.setVisible(false);
                    J2_carte_jouee.face.setVisible(false);
                    
                    /*if (J1_carte_jouee != null && J2_carte_jouee != null) {
                        root.getChildren().remove(J1_carte_jouee);
                        root.getChildren().remove(J2_carte_jouee);
                    }*/
                    
                    for (int i = 0; i < m.j1.main.pile.size(); i++) {
                        j1main[i].face.setVisible(false);
                    }
                    for (int i = 0; i < m.j2.main.pile.size(); i++) {
                        j2main[i].face.setVisible(false);
                    }
                    
                    init_main(j1main,J1);
                    init_main(j2main,J2);
                    
                    if (m.config.donneur == J1) {
                        affichage_dos_plis(j1plis, J1, (hauteur_scene/1.5));
                        affichage_dos_plis(j2plis, J2, hauteur_scene-(hauteur_scene/1.5)-J2_carte_jouee.hauteur_carte);
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
                        affichage_dos_main(j2main,J2);
                        affichage_face_main(j1main, J1);
                    } else {
                        affichage_dos_plis(j1plis, J1, hauteur_scene-(hauteur_scene/1.5)-J1_carte_jouee.hauteur_carte);
                        affichage_dos_plis(j2plis, J2, (hauteur_scene/1.5));
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
                        affichage_dos_main(j1main,J1);
                        affichage_face_main(j2main, J2);
                    }
                    
                    pause = 0;
                    
                    System.out.println("Sortie");
                }               
            }
        };
        timer.start();

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

        //root.getChildren().add(MessageT);
        
        primaryStage.setFullScreen(true);       
        
        Scene scene = new Scene(root, largeur_scene, hauteur_scene, Color.web("274e13"));
        root.setStyle("-fx-background-color:#274e13;");
        
        root.getChildren().add(bandeau);
        
        undo=new Button();
        ImageView imgUndo = new ImageView(new Image("images/undo.png"));
	undo.setGraphic(imgUndo);
        undo.setPrefWidth(85);
        undo.setPrefHeight(25);
        undo.setTranslateX(largeur_scene/3.9);
        undo.setTranslateY(hauteur_scene-hauteur_scene/11);
        root.getChildren().add(undo);

        redo=new Button();
        ImageView imgRedo = new ImageView(new Image("images/redo.png"));
	redo.setGraphic(imgRedo);
        redo.setPrefWidth(85);
        redo.setPrefHeight(25);
        redo.setTranslateX(largeur_scene/3.3);
        redo.setTranslateY(hauteur_scene-hauteur_scene/11);
        root.getChildren().add(redo);
        
        System.out.println(screenSize.getWidth());
        System.out.println(screenSize.getHeight());
        
        primaryStage.setTitle("Bridge Chinois");
        primaryStage.setScene(scene);  
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

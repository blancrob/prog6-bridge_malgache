package bridge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Moteur {
    Configuration config;
    Joueur j1, j2;
    
    int joueur1donneur, joueur2donneur; //Utile uniquement pour l'affichage, à ne pas mettre dans Configuration donc
    
    public Moteur(){    //Voir si on peut pas mettre un truc dedans quand même
        
    }
    
    /**
     * Initialise le début de jeu et les structures Configuration et Joueur 
     */
    public void initialiser(){
        
        //Initialisation des structures
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();
        
        //Utile juste pour voir en terminal si le joueur Donneur est celui qui gagne le pli
        joueur1donneur=0;
        joueur2donneur=0;
        
        //On initialise les valeurs de la structure
        config.conditionVictoire=0;
        config.mancheMax=0;
        config.scoreMax=0;
        
        j1.manchesGagnees=0;
        j2.manchesGagnees=0;
        
        Scanner sc = new Scanner(System.in);
        
        //Choix des conditions de victoire
        System.out.println("Choix de la condition de victoire:\n1: Nombre de Manches || 2: Score");
        String str = sc.nextLine();
        config.conditionVictoire = Integer.parseInt(str);
        
        if(config.conditionVictoire==1){
            System.out.println("Entrez le nombre de manches:");
            str = sc.nextLine();
            config.mancheMax = Integer.parseInt(str);
        }else{
            System.out.println("Entrez le score maximum:");
            str = sc.nextLine();
            config.scoreMax = Integer.parseInt(str);
        }
        
        //Choix du mode de jeu
        System.out.println("1: Joueur Contre Joueur || 2: Joueur Contre Ordinateur || 3: Ordinateur Contre Ordinateur");
        str = sc.nextLine();
        config.mode = Integer.parseInt(str);
        
        //En cas de mode de jeu impliquant des IA, choix de la difficulté des IA
        if(config.mode == 2){System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
        }else if(config.mode == 3){
            System.out.println("Difficulté Joueur 1:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j1.difficulte = Integer.parseInt(str);
            
            System.out.println("Difficulté Joueur 2:");
            System.out.println("1: Novice || 2: Facile || 3: Moyen || 4: Avancé || 5: Difficile || 6: Expert");
            str = sc.nextLine();
            j2.difficulte = Integer.parseInt(str);
        }
    }
    
    /**
     * Initialise le début de la manche, distribue les cartes aux joueurs
     */
    public void initialiserManche(){
        j1.main = new PileCartes();
        j2.main = new PileCartes();
        j1.tas = new PileCartes();
        j2.tas = new PileCartes();
        j1.score=0;
        j2.score=0;
        distribuer();
        config.taille=11;
        config.set_atout();
    }
    
    /**
     * Sauvegarder la configuration courante dans un fichier dont le nom est demandé 
     * @throws IOException 
     */
    public void sauvegarder() throws IOException{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Entrez le nom du fichier de sauvegarde:");
        String str = sc.nextLine();
        File fichier =  new File(str) ;

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));

        oos.writeObject(copieEtat());

        oos.close();
    }
    
    /**
     * Charger la configuration contenue dans un fichier dont le nom est demandé
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void charger() throws IOException, ClassNotFoundException{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Entrez le nom du fichier à charger:");
        String str = sc.nextLine();
        File fichier =  new File(str) ;

        // ouverture d'un flux sur un fichier
       // ouverture d'un flux sur un fichier
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));

        // désérialization de l'objet
        EtatGlobal e = (EtatGlobal) ois.readObject();
        
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();

        config = e.config;
        j1 = e.j1;
        j2 = e.j2;

        ois.close();
    }
    
    /**
     * Renvoie la liste des fichiers contenus dans le dossier sauvegardes
     * @return un tableau de String contenant les noms des fichiers contenus dans le dossier sauvegardes
     */
    public String[] afficheSauvegardes(){
        File dossier=new File("sauvegardes");
        if (dossier.exists() && dossier.isDirectory()){ //Si le dossier sauvegarde existe
            File[] files=dossier.listFiles();
            String[] res = new String[files.length];
            for(int i=0; i<files.length;i++){
                res[i] = files[i].getName();
            }
            return res;
        }else{
            System.out.println("Le dossier \"sauvegardes\" n'existe pas");
            return null;
        }
    }
    
    /**
     * Annule le coup précédent et retourne dans la dernière configuration enregistrée dans la pile Undo
     * Permet de refaire le coup en stockant la configuration dans la pile Redo
     */
    public void undo(){
        if(!config.estVideUndo()){
            EtatGlobal e1 = copieEtat();
            EtatGlobal e = config.getUndo();
            config = e.config;
            j1 = e.j1;
            j2 = e.j2;
            config.addRedo(e1);
        }
    }
    
    /**
     * Refait le dernier coup enregistré dans la pile Redo
     * Permet d'annuler de nouveau le coup en stockant la configuration dans la pile Undo
     */
    public void redo(){
        if(!config.estVideRedo()){
            EtatGlobal e1 = copieEtat();
            EtatGlobal e = config.getRedo();
            config = e.config;
            j1 = e.j1;
            j2 = e.j2;
            config.addUndo(e1);
        }
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public void jouerCoupPremier(){
        Iterator<Carte> it;
        Carte[] main = new Carte[20];
        
        //Début affichage de la main du joueur
        if(config.donneur==1){
            it = j1.main.iterateur();
        }
        else{
            it = j2.main.iterateur();
        }

        int i=0;
        do{
           main[i]=it.next();
           i++;
        }while(i<20 && it.hasNext());

        if(config.donneur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        //Fin affichage de la main du joueur
        
        if(config.mode == 1 || (config.mode == 2 && config.donneur==1)){    //Cas où le joueur est un humain
            
            //Le joueur entre le numéro de la carte à jouer, ou bien choisit de sauvegarde, charger, annuler ou refaire
            System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger, -3 pour annuler, -4 pour refaire:");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            while(choix<0){
                if(choix==-1){  //Choix Sauvegarder
                    try {
                        sauvegarder();
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-2){    //Choix Charger
                   
                    try {
                        charger();
                        
                        //Après avoir chargé la partie on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                        main = new Carte[20];
                        if(config.donneur==1){
                            it = j1.main.iterateur();
                        }
                        else{
                            it = j2.main.iterateur();
                        }
                        i=0;
                        do{
                           main[i]=it.next();
                           i++;
                        }while(i<20 && it.hasNext());

                        if(config.donneur==1){
                            System.out.println("Main Joueur 1");
                        }
                        else{
                            System.out.println("Main Joueur 2");
                        }
                        for(i=0; i<20 && main[i]!=null; i++){
                            System.out.print("["+i+"]:");
                            afficherCarte(main[i]);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-3){    //Choix Annuler
                    undo();
                    
                    //Après avoir annulé le coup on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                    main = new Carte[20];
                    if(config.donneur==1){
                        it = j1.main.iterateur();
                    }
                    else{
                        it = j2.main.iterateur();
                    }
                    i=0;
                    do{
                       main[i]=it.next();
                       i++;
                    }while(i<20 && it.hasNext());

                    if(config.donneur==1){
                        System.out.println("Main Joueur 1");
                    }
                    else{
                        System.out.println("Main Joueur 2");
                    }
                    for(i=0; i<20 && main[i]!=null; i++){
                        System.out.print("["+i+"]:");
                        afficherCarte(main[i]);
                    }
                }else if(choix==-4){    //Choix Refaire
                    redo();
                    //Après avoir refait le coup on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                    main = new Carte[20];
                    if(config.donneur==1){
                        it = j1.main.iterateur();
                    }
                    else{
                        it = j2.main.iterateur();
                    }
                    i=0;
                    do{
                       main[i]=it.next();
                       i++;
                    }while(i<20 && it.hasNext());

                    if(config.donneur==1){
                        System.out.println("Main Joueur 1");
                    }
                    else{
                        System.out.println("Main Joueur 2");
                    }
                    for(i=0; i<20 && main[i]!=null; i++){
                        System.out.print("["+i+"]:");
                        afficherCarte(main[i]);
                    }
                }
                //Une fois qu'une option spéciale a été sélectionnée, on redemande au joueur de choisir une carte ou refaire une autre action
                System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger, -3 pour annuler, -4 pour refaire:");
                sc = new Scanner(System.in);
                str = sc.nextLine();
                choix = Integer.parseInt(str);
            }

            config.addUndo(copieEtat());    //Copie de l'état courant dans la pile undo pour y revenir en cas d'annulation du coup courant
            
            //La carte est jouée
            Carte c = main[choix];
            if (config.donneur==1){ //On enlève la carte choisie de la main du donneur
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteP = c;  //La carte est placée sur le terrain
            
            if(config.joueur == 1){//On change de joueur
                config.joueur = 2;
            }else{
                config.joueur = 1;
            }
              
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            jouerCoupIA(config.donneur); 
        }
    }
    
    /**
     * Le joueur qui n'a pas le statut de donneur ou qui a perdu joue en deuxième
     */
    public void jouerCoupSecond(){
        Iterator<Carte> it;
        PileCartes mainjoueur;
        Carte[] main = new Carte[20];
        
        //Début affichage de la main du joueur
        if(config.receveur==1){
            mainjoueur= j1.main;
        }
        else{
            mainjoueur = j2.main;
        }
        it = mainjoueur.iterateur();

        int i=0;
        do{
           main[i]=it.next();
           i++;
        }while(it.hasNext());

        if(config.receveur==1){
            System.out.println("Main Joueur 1");
        }
        else{
            System.out.println("Main Joueur 2");
        }
        for(i=0; i<20 && main[i]!=null; i++){
            System.out.print("["+i+"]:");
            afficherCarte(main[i]);
        }
        //Fin affichage de la main du joueur
        
        if(config.mode == 1 || (config.mode == 2 && config.donneur==2)){    //Affichage de la couleur à fournir si besoin
            switch (config.carteP.couleur){
                case 1:
                    System.out.println("Fournir du TREFLE si vous en avez");
                    break;
                case 2:
                    System.out.println("Fournir du CARREAU si vous en avez");
                    break;
                case 3:
                    System.out.println("Fournir du COEUR si vous en avez");
                    break;
                case 4:
                    System.out.println("Fournir du PIQUE si vous en avez");
                    break;
            }
            
            //Le joueur entre le numéro de la carte à jouer, ou bien choisit de sauvegarde, charger, annuler ou refaire
            boolean condition = false;
            System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger, -3 pour annuler, -4 pour refaire:");   //Choix entre jouer une carte et sauvegarder ou charger
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            while(choix<0){
                if(choix==-1){  //Choix Sauvegarder
                    try {
                        sauvegarder();
                    } catch (IOException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-2){    //Choix Charger
                   
                    try {
                        charger();
                        //Après avoir chargé la partie on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                        main = new Carte[20];
                        if(config.receveur==1){
                            it = j1.main.iterateur();
                        }
                        else{
                            it = j2.main.iterateur();
                        }
                        i=0;
                        do{
                           main[i]=it.next();
                           i++;
                        }while(i<20 && it.hasNext());

                        if(config.receveur==1){
                            System.out.println("Main Joueur 1");
                        }
                        else{
                            System.out.println("Main Joueur 2");
                        }
                        for(i=0; i<20 && main[i]!=null; i++){
                            System.out.print("["+i+"]:");
                            afficherCarte(main[i]);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(choix==-3){    //Choix Annuler
                    undo();
                    //Après avoir annulé le coup on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                    main = new Carte[20];
                    if(config.donneur==1){
                        it = j1.main.iterateur();
                    }
                    else{
                        it = j2.main.iterateur();
                    }
                    i=0;
                    do{
                       main[i]=it.next();
                       i++;
                    }while(i<20 && it.hasNext());

                    if(config.donneur==1){
                        System.out.println("Main Joueur 1");
                    }
                    else{
                        System.out.println("Main Joueur 2");
                    }
                    for(i=0; i<20 && main[i]!=null; i++){
                        System.out.print("["+i+"]:");
                        afficherCarte(main[i]);
                    }
                }else if(choix==-4){    //Choix Refaire
                    redo();
                    //Après avoir refiat le coup on réaffiche la main du Joueur   PEUT S'EXPORTER DANS UNE METHODE CAR UTILISE PLUSIEURS FOIS
                    main = new Carte[20];
                    if(config.donneur==1){
                        it = j1.main.iterateur();
                    }
                    else{
                        it = j2.main.iterateur();
                    }
                    i=0;
                    do{
                       main[i]=it.next();
                       i++;
                    }while(i<20 && it.hasNext());

                    if(config.donneur==1){
                        System.out.println("Main Joueur 1");
                    }
                    else{
                        System.out.println("Main Joueur 2");
                    }
                    for(i=0; i<20 && main[i]!=null; i++){
                        System.out.print("["+i+"]:");
                        afficherCarte(main[i]);
                    }
                }
                //Une fois qu'une option spéciale a été sélectionnée, on redemande au joueur de choisir une carte ou refaire une autre action
                System.out.println("Donnez le numéro de la carte à jouer, ou -1 pour sauvegarder, -2 pour charger, -3 pour annuler, -4 pour refaire:");
                sc = new Scanner(System.in);
                str = sc.nextLine();
                choix = Integer.parseInt(str);
            }
            
            //Si la carte choisie n'est pas de la couleur qu'il fallait fournir, on demande de rejouer une carte appropriée
            if(config.receveur == 1){
                if (main[choix].couleur != config.carteP.couleur && j1.main.contient(config.carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != config.carteP.couleur && j1.main.contient(config.carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }else{
                if (main[choix].couleur != config.carteP.couleur && j2.main.contient(config.carteP.couleur)){
                    while(!condition){
                        System.out.println("Jouez une carte de la couleur demandée");
                        str = sc.nextLine();
                        choix = Integer.parseInt(str);
                        if (!(main[choix].couleur != config.carteP.couleur && j2.main.contient(config.carteP.couleur))){
                            condition = true;
                        }
                    }
                }
            }

            //La carte est jouée
            Carte c = main[choix];
            if(config.receveur==1){ //On enlève la carte choisie de la main du receveur
                j1.main.retirer(c);
            }else{
                j2.main.retirer(c);
            }
            config.carteS = c;  //La carte est placée sur le terrain

            if(config.joueur == 1){ //On change de joueur
                config.joueur = 2;
            }else{
                config.joueur = 1;
            }
            System.out.println();
        }
        else{   //Cas où l'ordinateur joue c;
            jouerCoupIA(config.receveur); 
        }
        //j1.main.trier();
        //j2.main.trier();
    }
    
    /**
     * Renvoie une carte jouée par une IA
     * La difficulté de l'IA est choisie par rapport à la difficulté présente dans la
     * structure Joueur passée en paramètre
     * @param joueur joueur auquel doit être attribué le coup joué par l'IA
     * @return la carte jouée par l'IA
     */
    public Carte jouerCoupIA(int joueur){
        
        j1.score=j1.tas.taille()/2;
        j2.score=j2.tas.taille()/2;
        
        Carte c = null;
        Carte[] piocheIA = new Carte[6];
        int[] nbCartes = new int[6];
        int lg = 0;
        
        //Création d'un tableau de cartes PiocheIA représentant le dessus de la pioche
        //Et d'un tableau d'entiers représentant la taille de chaque tas de la pioche
        //Nécessaires au constructeur de l'IA
        for(int i=1; i<6; i++){
            if(config.pioche[i].premiere()!=null){
                piocheIA[lg]=config.pioche[i].premiere();
                nbCartes[lg]=config.pioche[i].taille();
                lg++;
            }
        }

        //Création d'une PileCartes contenant les cartes déjà jouées nécessaire dans le constructeur de l'IA
        PileCartes cartesDejaJouees = new PileCartes();
        cartesDejaJouees.pile.addAll(j1.tas.pile);
        cartesDejaJouees.pile.addAll(j2.tas.pile);

        int difficulte;
        IA ia = null;
        
        //Création du constructeur de l'IA selon la difficulté A FACTORISER
        if(joueur==1){
            difficulte = j1.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 2:
                    ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 3:
                    ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 4:
                    ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score, config.conditionVictoire);
                    break;
                case 5:
                    ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j1.score, j2.score, config.conditionVictoire);
                    break;
                case 6:
                    ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score, config.conditionVictoire, j2.main, config.pioche);
                    break;
            }
        }else{
            difficulte = j2.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 2:
                    ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 3:
                    ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP);
                    break;
                case 4:
                    ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score, config.conditionVictoire);
                    break;
                case 5:
                    ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score, config.conditionVictoire);
                    break;
                case 6:
                    ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, config.carteP, nbCartes, false, j2.score, j1.score, config.conditionVictoire, j1.main, config.pioche);
                    break;
            }
        }

        //On joue le coup
        c = ia.jouer(); 
        if (joueur==1){ //on retire la carte de la main du joueur
            j1.main.retirer(c);
        }else{
            j2.main.retirer(c);
        }

        if(config.carteP==null){    //On met la carte sur le plateau de jeu à la place appropriée
            config.carteP=c;
        }else{
            config.carteS=c;
        }
        
        System.out.println("Le joueur " + joueur + " a joué:");
            afficherCarte(c);
        
        return c;
    }
    
    /**
     * Enlève les deux cartes jouées de la table pour les ranger dans le tas du gagnant
     */
    public void rangerPli(){
        /*config.piochees.retirer(config.carteP);
        config.piochees.retirer(config.carteS);*/
        if(config.gagnant==1){
            j1.tas.ajouter(config.carteP);
            j1.tas.ajouter(config.carteS);
            config.carteP=null;
            config.carteS=null;
        }else{
            j2.tas.ajouter(config.carteP);
            j2.tas.ajouter(config.carteS);
            config.carteP=null;
            config.carteS=null;
        }
        j1.score = j1.tas.taille()/2;
        j2.score = j2.tas.taille()/2;
    }
    
    /**
     * Mets à jour les scores des deux joueurs, à utiliser à la fin d'une manche
     */
    public void maj_score(){
        j1.score=j1.tas.taille()/2;
        j2.score=j2.tas.taille()/2;
        
        if(finManche()){
            if(j1.score>j2.score){
                j1.manchesGagnees++;
            }else if(j1.score<j2.score){
                j2.manchesGagnees++;
            }
        }
        
        j1.scoreTotal=j1.scoreTotal+j1.score;
        j2.scoreTotal=j2.scoreTotal+j2.score;
        
        System.out.println("manches j1:"+j1.manchesGagnees);
        System.out.println("manches j2:"+j2.manchesGagnees);
        System.out.println("score j1:"+j1.score);
        System.out.println("score j2:"+j2.score);
        System.out.println("scoreTotal j1:"+j1.scoreTotal);
        System.out.println("scoreTotal j2:"+j2.scoreTotal);
    }
    
    /**
     * Permet au joueur désigné en paramètre de piocher
     * @param piocheur Le numéro du joueur qui va piocher
     */
    public void pioche(int piocheur){
        Carte c=null;
        
        if(config.mode == 1 || (config.mode == 2 && piocheur==1)){ //Si l'on est en mode joueur contre joueur ou alors que c'est le tour du joueur 1
        
            System.out.println("Joueur "+ piocheur +", choisissez une carte:");

            Scanner sc = new Scanner(System.in);    //on lit le choix du joueur
            String str = sc.nextLine();
            int choix = Integer.parseInt(str);
            
            for(int i=0; i<6; i++){ //On ajoute à la main du joueur la carte choisie
                if(choix==i+1){
                    c = config.pioche[i].premiere();
                    if(piocheur == 1){
                        j1.main.ajouter(config.pioche[i].retirer());
                    }else{
                        j2.main.ajouter(config.pioche[i].retirer());
                    }
                }
            }
            
            config.piochees.ajouter(c);
            
        }else{  //Si c'est à l'ordinateur de jouer
            piocheIA(piocheur);
        }
        //j1.main.trier();
        //j2.main.trier();
    }
    
    public Carte piocheIA(int piocheur){
        Carte c = null;
        Carte[] piocheIA = new Carte[6];
        int[] nbCartes = new int[6];
        int lg = 0;
        
        //Création d'un tableau de cartes PiocheIA représentant le dessus de la pioche
        //Et d'un tableau d'entiers représentant la taille de chaque tas de la pioche
        //Nécessaires au constructeur de l'IA
        for(int i=0; i<6; i++){
            if(config.pioche[i].premiere()!=null){
                piocheIA[lg]=config.pioche[i].premiere();
                nbCartes[lg]=config.pioche[i].taille();
                lg++;
            }
        }

        //Création d'une PileCartes contenant les cartes déjà jouées nécessaire dans le constructeur de l'IA
        PileCartes cartesDejaJouees = new PileCartes();
        cartesDejaJouees.pile.addAll(j1.tas.pile);
        cartesDejaJouees.pile.addAll(j2.tas.pile);

        int difficulte;
        IA ia = null;
        
        //Création du constructeur de l'IA selon la difficulté A FACTORISER
        if(piocheur==1){
            difficulte = j1.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 2:
                    ia = new IaFacile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 3:
                    ia = new IaMoyenne(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 4:
                    ia = new IaAvancee(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j1.score, j2.score, config.conditionVictoire);
                    break;
                case 5:
                    ia = new IaDifficile(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j1.score, j2.score, config.conditionVictoire);
                    break;
                case 6:
                    ia = new IaExperte(j1.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j2.score, j1.score, config.conditionVictoire, j2.main, config.pioche);
                    break;
            }
        }else{
            difficulte = j2.difficulte;
            switch(difficulte){
                case 1:
                    ia = new IaNovice(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 2:
                    ia = new IaFacile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 3:
                    ia = new IaMoyenne(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null);
                    break;
                case 4:
                    ia = new IaAvancee(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j2.score, j1.score, config.conditionVictoire);
                    break;
                case 5:
                    ia = new IaDifficile(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j2.score, j1.score, config.conditionVictoire);
                    break;
                case 6:
                    ia = new IaExperte(j2.main, cartesDejaJouees, config.piochees, piocheIA, lg, config.atout, null, nbCartes, config.gagnant==piocheur, j2.score, j1.score, config.conditionVictoire, j1.main, config.pioche);
                    break;
            }
        }

        //On pioche la carte
        c = ia.piocher();
        for(int i=0; i<6; i++){ //On ajoute la carte piochée à la main du joueur et on l'enlève de la pioche
            if(c == config.pioche[i].premiere()){
                if (piocheur==1){   
                    j1.main.ajouter(config.pioche[i].retirer());
                }else{
                    j2.main.ajouter(config.pioche[i].retirer());
                }
            }
        }

        System.out.println("Le joueur " + piocheur + " a pioché:");
        afficherCarte(c);

        //On ajoute à la structure des cartes piochées la car te c
        config.piochees.ajouter(c);
        
        //j1.main.trier();
        //j2.main.trier();
        
        return c;
    }
    
    /**
     * Affiche la carte passée en paramètre et va à la ligne
     * @param c La carte à afficher
     */
    public void afficherCarte(Carte c){
        switch(c.couleur){
                case 1:
                    System.out.print("TREFLE");
                    break;
                case 2:
                    System.out.print("CARREAU");
                    break;
                case 3:
                    System.out.print("COEUR");
                    break;
                case 4:
                    System.out.print("PIQUE");
                    break;
            }
        System.out.print("|");
        switch(c.valeur){
            case 11:
                System.out.println("VALET");
                break;
            case 12:
                System.out.println("DAME");
                break;
            case 13:
                System.out.println("ROI");
                break;
            case 14:
                System.out.println("AS");
                break;
            default:
                System.out.println(c.valeur);
        }
    }
    
    /**
     * Crée un paquet de 52 cartes de base et les distribue dans les piles et aux joueurs
     */
    public void distribuer(){
        PileCartes paquet = new PileCartes();
        paquet.paquet();
        
        for(int i=0;i<5;i++){
            config.pile1.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile2.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile3.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile4.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile5.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<5;i++){
            config.pile6.ajouter(paquet.aleatoire(true));
        }
        for(int i=0;i<11;i++){
            j1.main.ajouter(paquet.aleatoire(false));
        }
        for(int i=0;i<11;i++){
            j2.main.ajouter(paquet.aleatoire(false));
        }
    }
    
    /**
     * @return true si la manche est terminée, false sinon
     */
    public boolean finManche(){
        return j1.main.vide() && j2.main.vide();
    }
    
    /**
     * @return true si la partie est terminée, false sinon
     */
    public boolean finPartie(){
        return ((config.conditionVictoire==1 && config.manche>=config.mancheMax) || (config.conditionVictoire==2 && (j1.scoreTotal>=config.scoreMax || j2.scoreTotal>=config.scoreMax)));
    }
    
    public EtatGlobal copieEtat(){
        EtatGlobal e = new EtatGlobal();
        //Copie Joueur 1
        e.j1.main = j1.main.clone();
        e.j1.tas = j1.tas.clone();
        e.j1.cartesPiochees = j1.cartesPiochees.clone();
        e.j1.score = j1.score;
        e.j1.difficulte = j1.difficulte;
        e.j1.scoreTotal = j1.scoreTotal;
        e.j1.manchesGagnees = j1.manchesGagnees;
        e.j1.lg = j1.lg;
        e.j1.nom = j1.nom;
        //Copie Joueur 2
        e.j2.main = j2.main.clone();
        e.j2.tas = j2.tas.clone();
        e.j2.cartesPiochees = j2.cartesPiochees.clone();
        e.j2.score = j2.score;
        e.j2.difficulte = j2.difficulte;
        e.j2.scoreTotal = j2.scoreTotal;
        e.j2.manchesGagnees = j2.manchesGagnees;
        e.j2.lg = j2.lg;
        e.j2.nom = j2.nom;
        //Copie Configuration
        e.config.pile1 = config.pile1.clone();
        e.config.pile2 = config.pile2.clone();
        e.config.pile3 = config.pile3.clone();
        e.config.pile4 = config.pile4.clone();
        e.config.pile5 = config.pile5.clone();
        e.config.pile6 = config.pile6.clone();
        e.config.piochees = config.piochees.clone();
        e.config.pioche[0] = e.config.pile1;
        e.config.pioche[1] = e.config.pile2;
        e.config.pioche[2] = e.config.pile3;
        e.config.pioche[3] = e.config.pile4;
        e.config.pioche[4] = e.config.pile5;
        e.config.pioche[5] = e.config.pile6;
        e.config.carteP = config.carteP;
        e.config.carteS = config.carteS;
        
        e.config.undo = (Stack<EtatGlobal>) config.undo.clone();
        e.config.redo = (Stack<EtatGlobal>) config.redo.clone();
        
        e.config.conditionVictoire=config.conditionVictoire;
        e.config.mode = config.mode;
        e.config.mancheMax=config.mancheMax;
        e.config.scoreMax=config.scoreMax;
        e.config.atout=config.atout;
        e.config.joueur=config.joueur;
        e.config.donneur=config.donneur;
        e.config.donneurInitial=config.donneurInitial;
        e.config.receveur=config.receveur;
        e.config.manche = config.manche;
        e.config.gagnant=config.gagnant;
        e.config.perdant=config.perdant;
        e.config.taille=config.taille;
        
        e.config.temps = config.temps;
        e.config.temps2 = config.temps2;
        e.config.carte_jouee = config.carte_jouee;
        e.config.tour_joueur = config.tour_joueur;
        e.config.tour_pioche = config.tour_pioche;
        e.config.k = config.k;
        e.config.messagePioche = config.messagePioche;
        e.config.finTour = config.finTour;
        e.config.messageFinManche = config.messageFinManche;
        e.config.messageFinPartie = config.messageFinPartie;
        e.config.J1_carte_jouee = config.J1_carte_jouee;
        e.config.J2_carte_jouee = config.J2_carte_jouee;
        e.config.clean = config.clean;
        e.config.pause = config.pause;
        e.config.j1_lock = config.j1_lock;
        e.config.j2_lock = config.j2_lock;
        e.config.select = config.select;
        e.config.cheat = config.cheat;
        e.config.message_t = config.message_t;
        e.config.animation_cartePiochee = config.animation_cartePiochee;
        e.config.animation_t = config.animation_t;
        e.config.J1_lastCard = config.J1_lastCard;
        e.config.J2_lastCard = config.J2_lastCard;
        e.config.affichage_initial_pioche = config.affichage_initial_pioche;
        
        return e;
    }
    
    /**
     * Lis les valeurs des cartes à utiliser pour la partie dans un fichier texte, chaque pileCartes séparée par un tiret -, dans cet ordre:
     * mainj1, mainj2, pile1, pile2, pile3, pile4, pile5, pile6, tasj1, tasj2
     * Puis trois ints, un par ligne: taille, atout, doneur, completer
     * Taille est la taille courante de la main
     * Atout est la couleur d'atout de la manche
     * Donneur le donneur de la manche
     * Si completer vaut 1: les cartes manquantes sont ditribuées dans les mains des joueurs et dans la pioche
     * Si completer vaut 2: les cartes manquantes sont ditribuées dans les tas des joueurs
     * @throws IOException 
     */
    public void forcer() throws IOException{
        Scanner sc = new Scanner(System.in);
        String[] c;
        int completer=0;
        
        System.out.println("Entrez le nom du fichier à charger:");
        String str = sc.nextLine();
        
        File f = new File(str);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        
        config.pile1 = new PileCartes();
        config.pile2 = new PileCartes();
        config.pile3 = new PileCartes();
        config.pile4 = new PileCartes();
        config.pile5 = new PileCartes();
        config.pile6 = new PileCartes();
        j1.main = new PileCartes();
        j1.tas = new PileCartes();
        j2.main = new PileCartes();
        j2.tas = new PileCartes();
        
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            j1.main.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            j2.main.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile1.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile2.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile3.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile4.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile5.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.pile6.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            j1.tas.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            j2.tas.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        line = br.readLine();
        while(!line.equals("-")){
            c = line.split(" ");
            config.piochees.ajouter(new Carte(Integer.parseInt(c[0]), Integer.parseInt(c[1]), false));
            line = br.readLine();
        }
        config.taille =Integer.parseInt(br.readLine());
        config.atout = Integer.parseInt(br.readLine());
        config.donneur = Integer.parseInt(br.readLine());
        if(config.donneur==1){
            config.donneurInitial=1;
            config.receveur=2;
        }else{
            config.donneurInitial=2;
            config.receveur=1;
        }
        completer = Integer.parseInt(br.readLine());
        
        config.pioche = new PileCartes[6];
        config.pioche[0] = config.pile1;
        config.pioche[1] = config.pile2;
        config.pioche[2] = config.pile3;
        config.pioche[3] = config.pile4;
        config.pioche[4] = config.pile5;
        config.pioche[5] = config.pile6;
        
        
        br.close();
        
        //On regarde si toutes les cartes ont été dstribuées
        PileCartes distribuees = new PileCartes();
        distribuees.pile.addAll(j1.main.pile);
        distribuees.pile.addAll(j2.main.pile);
        distribuees.pile.addAll(j1.tas.pile);
        distribuees.pile.addAll(j2.tas.pile);
        for(int i=0; i<6; i++){
            distribuees.pile.addAll(config.pioche[i].pile);
        }
        
        if (distribuees.taille()<52){   //Si toutes les cartes n'ont pas été distribuées, on les distribue aléatoirement
            Carte carte;
            PileCartes paquet = new PileCartes();
            paquet.paquet();
            
            Iterator<Carte> it = distribuees.iterateur();
            
            while(it.hasNext()){    //On enlève les cartes distribuées au paquet de cartes entier
                paquet.retirer(it.next());
            }
            
            
            if(completer==1){
                while(j1.main.taille()<11 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    j1.main.ajouter(carte);
                }while(j2.main.taille()<11 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    j2.main.ajouter(carte);
                }while(config.pile1.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile1.ajouter(carte);
                }while(config.pile2.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile2.ajouter(carte);
                }while(config.pile3.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile3.ajouter(carte);
                }while(config.pile4.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile4.ajouter(carte);
                }while(config.pile5.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile5.ajouter(carte);
                }while(config.pile6.taille()<5 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    config.pile6.ajouter(carte);
                }
            }else{
                while(j1.tas.taille()<26 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    j1.tas.ajouter(carte);
                }while(j2.tas.taille()<26 && !paquet.vide()){
                    carte = paquet.aleatoire(true);
                    paquet.retirer(carte);
                    j2.tas.ajouter(carte);
                }
            }
        }
}
    
    public void moteur() throws ClassNotFoundException{
        
        initialiser();
        
        /*try {
            forcer();
        } catch (IOException ex) {
            Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        while((config.conditionVictoire==1 && config.manche<config.mancheMax) || (config.conditionVictoire==2 && (j1.scoreTotal<config.scoreMax && j2.scoreTotal<config.scoreMax))){
            
            /*
            if(config.donneurInitial == 0){
                config.donneurInitial=1;
                config.donneur = 1 ;
            }
            else if (config.donneurInitial == 1){
                config.donneurInitial = 2;
                config.donneur = 2;
            }else{
                config.donneurInitial = 1;
                config.donneur = 1;
            }*/
            
            config.manche++;
            initialiserManche(); 
            
            System.out.println("MANCHE "+ config.manche);
            System.out.println();

            switch(config.atout){
                case 0:
                    System.out.println("SANS ATOUT");
                    break;
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

            while(!finManche()){
                if(config.piochable()){
                    config.afficherPioche();
                }
                System.out.println("Tour joueur "+ config.donneur);
                System.out.println();
                jouerCoupPremier();
                System.out.println("Tour Joueur "+ config.receveur);
                System.out.println();
                jouerCoupSecond();
                config.taille--;
                config.gagnantPli();
                System.out.println("Le joueur "+ config.gagnant +" gagne le pli");
                System.out.println();
                rangerPli();
                if(config.piochable()){
                    config.afficherPioche();
                    pioche(config.gagnant);
                    System.out.println();
                    config.afficherPioche();
                    pioche(config.perdant);
                    System.out.println();
                    config.taille++;
                }
                config.donneur = config.gagnant;
                if(config.donneur==1){
                    config.receveur = 2;
                }else{
                    config.receveur = 1;
                }
                System.out.println("-----------------------------------------");
                System.out.println("-----------------------------------------");
                System.out.println("-----------------------------------------");
            }
            System.out.println();
            if(j1.tas.taille()>j2.tas.taille()){
                System.out.println("LE JOUEUR 1 GAGNE");
                if(config.donneurInitial==1){
                    joueur1donneur++;
                }
            }else if(j1.tas.taille()<j2.tas.taille()){
                System.out.println("LE JOUEUR 2 GAGNE");
                if(config.donneurInitial==2){
                    joueur2donneur++;
                }
            }
            else{
                System.out.println("EGALITE");
            }
            System.out.println();
            
            //maj_score();
            
            maj_score();
            
            System.out.println("Manche: "+ config.manche);
            System.out.println("Score joueur 1: "+ (j1.score));
            System.out.println("Score joueur 2: "+ (j2.score));
            System.out.println();
            System.out.println("Tas joueur 1:");
            Iterator<Carte> it = j1.tas.iterateur();
            while(it.hasNext()){
                afficherCarte(it.next());
            }

            System.out.println();
            System.out.println("Tas joueur 2:");
            it = j2.tas.iterateur();
            while(it.hasNext()){
                afficherCarte(it.next());
            }
        }
        
        System.out.println();
        System.out.println("Score Total joueur 1: "+ (j1.scoreTotal));
        System.out.println("Score Total joueur 2: "+ (j2.scoreTotal));
        System.out.println("Manches Gagnées joueur 1: "+ (j1.manchesGagnees));
        System.out.println("Manches Gagnées joueur 2: "+ (j2.manchesGagnees));
        if(config.conditionVictoire==1){
            if(j1.manchesGagnees>j2.manchesGagnees){
                System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
            }else if(j1.manchesGagnees<j2.manchesGagnees){
                System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
            }
            else{
                if(j1.scoreTotal>j2.scoreTotal){
                    System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
                }else if(j1.scoreTotal<j2.scoreTotal){
                    System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
                }
                else{
                    System.out.println("EGALITE");
                }
            }
        }else if(config.conditionVictoire==2){
            if(j1.scoreTotal>j2.scoreTotal){
                System.out.println("LE JOUEUR 1 GAGNE LA PARTIE");
            }else if(j1.scoreTotal<j2.scoreTotal){
                System.out.println("LE JOUEUR 2 GAGNE LA PARTIE");
            }
            else{
                System.out.println("EGALITE");
            }
        }
        System.out.println("Nombre de victoires du joueur 1 en commençant la manche donneur: " + joueur1donneur);
        System.out.println("Nombre de victoires du joueur 2 en commençant la manche donneur: " + joueur2donneur);
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        Moteur m = new Moteur();
        m.moteur();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;

public class MoteurIA extends Moteur{
    
    public int gagnant; //Vaut 1 si joueur 1 gagnant, vaut 2 si joueur 2 gagnant, vaut 3 si égalité
    
    public MoteurIA(Configuration config, Joueur j1, Joueur j2){    //Voir si on peut pas mettre un truc dedans quand même
        this.config = config;
        this.j1 = j1;
        this.j2=j2;
        gagnant=0;
    }
    
    /**
     * Initialise le début de la manche, distribue les cartes aux joueurs
     */
    public void initialiserManche(){
        j1.main = new PileCartes();
        j2.main = new PileCartes();
        j1.tas = new PileCartes();
        j2.tas = new PileCartes();
        distribuer();
        config.taille=11;
        config.set_atout();
    }
    
    /**
     * Le joueur qui a le statut de donneur ou bien est gagnant joue en premier
     */
    public void jouerCoupPremier(){
        jouerCoupIA(config.donneur); 
        j1.main.trier();
        j2.main.trier();
    }
    
    /**
     * Le joueur qui n'a pas le statut de donneur ou qui a perdu joue en deuxième
     */
    public void jouerCoupSecond(){
        jouerCoupIA(config.receveur);
        j1.main.trier();
        j2.main.trier();
    }
    
    /**
     * Renvoie une carte jouée par une IA
     * La difficulté de l'IA est choisie par rapport à la difficulté présente dans la
     * structure Joueur passée en paramètre
     * @param joueur joueur auquel doit être attribué le coup joué par l'IA
     * @return la carte jouée par l'IA
     */
    public Carte jouerCoupIA(int joueur){
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
     * Permet au joueur désigné en paramètre de piocher
     * @param piocheur Le numéro du joueur qui va piocher
     */
    public void pioche(int piocheur){
        piocheIA(piocheur);
        j1.main.trier();
        j2.main.trier();
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

        //On ajoute à la structure des cartes piochées la carte c
        config.piochees.ajouter(c);
        
        j1.main.trier();
        j2.main.trier();
        
        return c;
    }
    
    public void moteur() throws ClassNotFoundException{
        
        while((config.conditionVictoire==1 && config.manche<config.mancheMax) || (config.conditionVictoire==2 && (j1.scoreTotal<config.scoreMax && j2.scoreTotal<config.scoreMax))){
        
            
            if(config.donneurInitial==0){
                config.donneurInitial=1;
                config.donneur=1;
            }else if(config.donneurInitial==1){
                config.donneurInitial=2;
                config.donneur=2;
            }else{
               config.donneurInitial=1;
               config.donneur=1; 
            }
            
            config.manche++;
            initialiserManche();

            while(!finManche()){
                j1.main.trier();
                j2.main.trier();
                jouerCoupPremier();
                jouerCoupSecond();
                config.taille--;
                config.gagnantPli();
                rangerPli();
                if(config.piochable()){
                    pioche(config.gagnant);
                    pioche(config.perdant);
                    config.taille++;
                }
                config.donneur = config.gagnant;
                if(config.donneur==1){
                    config.receveur = 2;
                }else{
                    config.receveur = 1;
                }
            }
            if(j1.tas.taille()>j2.tas.taille()){
                if(config.donneurInitial==1){
                    joueur1donneur++;
                }
            }else if(j1.tas.taille()<j2.tas.taille()){
                if(config.donneurInitial==2){
                    joueur2donneur++;
                }
            }
            j1.score=j1.tas.taille()/2;
            j2.score=j2.tas.taille()/2;
            
            j1.scoreTotal=j1.scoreTotal+j1.score;
            j2.scoreTotal=j2.scoreTotal+j2.score;
            if(j1.scoreTotal<j2.scoreTotal){
                gagnant = 2;
            }else if(j2.scoreTotal<j1.scoreTotal){
                gagnant = 1;
            }else{
                gagnant = 3;
            }
        }
    }
    
}

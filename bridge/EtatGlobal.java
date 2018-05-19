package bridge;

import java.io.Serializable;
import java.util.Stack;

public class EtatGlobal implements Serializable{
    Configuration config;
    Joueur j1, j2;
    
    public EtatGlobal(Configuration config, Joueur j1, Joueur j2){
        this.config = config;
        this.j1 = j1;
        this.j2 = j2;
    }
    
    public EtatGlobal(){
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();
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
        //Copie Joueur 2
        e.j2.main = j2.main.clone();
        e.j2.tas = j2.tas.clone();
        e.j2.cartesPiochees = j2.cartesPiochees.clone();
        e.j2.score = j2.score;
        e.j2.difficulte = j2.difficulte;
        e.j2.scoreTotal = j2.scoreTotal;
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
        
        e.config.undo = (Stack<EtatGlobal>) config.undo.clone();
        e.config.redo = (Stack<EtatGlobal>) config.redo.clone();
        
        e.config.conditionVictoire=config.conditionVictoire;
        e.config.mancheMax=config.mancheMax;
        e.config.scoreMax=config.scoreMax;
        e.config.mode = config.mode;
        e.config.manche = config.manche;
        e.config.joueur=config.joueur;
        e.config.donneur=config.donneur;
        e.config.donneurInitial=config.donneurInitial;
        e.config.receveur=config.receveur;
        e.config.gagnant=config.gagnant;
        e.config.perdant=config.perdant;
        e.config.taille=config.taille;
        
        return e;
    }
}

package bridge;

import java.io.Serializable;
import java.util.Stack;

public class Configuration implements Serializable {
    
    PileCartes pile1, pile2, pile3, pile4, pile5, pile6, piochees;
    PileCartes[] pioche;
    Carte carteP, carteS;
    int conditionVictoire, mancheMax, scoreMax, atout, joueur, donneur, donneurInitial, receveur, manche, gagnant, perdant, taille, mode;
    
    Stack<EtatGlobal> undo, redo;
    
    public Configuration(){
        pile1 = new PileCartes();
        pile2 = new PileCartes();
        pile3 = new PileCartes();
        pile4 = new PileCartes();
        pile5 = new PileCartes();
        pile6 = new PileCartes();
        
        piochees = new PileCartes();
        
        pioche = new PileCartes[6];
        pioche[0] = pile1;
        pioche[1] = pile2;
        pioche[2] = pile3;
        pioche[3] = pile4;
        pioche[4] = pile5;
        pioche[5] = pile6;
        
        undo = new Stack();
        redo = new Stack();
        
        conditionVictoire=0;
        mancheMax=0;
        scoreMax=0;
        mode = 0;
        manche = 0;
        joueur=1;
        donneur=1;
        donneurInitial=0;
        receveur=2;
        gagnant=0;
        perdant=0;
        taille=11;
    }
    
    /**
     * Met à jour la variable gagnant, qui indique qui est le gagnant du dernier pli joué
     */
    public void gagnantPli(){
        if(carteP.couleur == carteS.couleur){
            if(carteP.valeur>carteS.valeur){
                gagnant = donneur;
                perdant = receveur;
            }
            else{
                gagnant = receveur;
                perdant = donneur;
            }
        }
        else if(carteS.couleur==atout){
            gagnant = receveur;
            perdant = donneur;
        }
        else{
            gagnant = donneur;
            perdant = receveur;
        }
    }
    
    /**
     * Vérifie si une pioche n'est pas vide
     * @return true si il est encore possible de piocher, false sinon
     */
    public boolean piochable(){
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Affiche le contenu de la pioche
     */
    public void afficherPioche(){
        System.out.println("Pioche:");
        Moteur m = new Moteur();
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                System.out.print(pioche[i].taille() +"||");
                System.out.print("["+(i+1)+"]:");
            m.afficherCarte(pioche[i].premiere());
            }
        }
        System.out.println();
    }
    
    /**
     * Regarde la première carte de chaque pile pour définir l'atout
     */    
    public void set_atout(){
        Carte res = pile1.premiere();
        
        for(int i=1; i<6; i++){
            if(res.valeur < pioche[i].premiere().valeur && pioche[i].premiere().valeur>10){   //si la carte courante a une plus grande valeur que la carte résultat, elle deviens la carte résultat
                res = pioche[i].premiere();
            }
            else if(res.valeur == pioche[i].premiere().valeur){ //si les valeurs des cartes sont égales, si la couleur de la carte courante est plus forte, elle deviens la carte résultat
                if(res.couleur < pioche[i].premiere().couleur){
                    res = pioche[i].premiere();
                }
            }
        }
        
        if(res.valeur>=10){
            atout = res.couleur;
        }else{
            atout = 0;
        }
       
    }
    
    /**
     * Ajoute un EtatGlobal à la pile des undo
     * @param e EtatGlobal qu'on ajoute
     */
    public void addUndo(EtatGlobal e){
        undo.push(e);
    }
    
    /**
     * Donne l'etat global au sommet de la pile des undo, et l'y enlève
     * @return le dernier coup placé en undo
     */
    public EtatGlobal getUndo(){
        return undo.pop();
    }
    
    /**
     * Test si undo est vide
     * @return true si undo est vide, false sinon
     */
    public boolean estVideUndo(){
        return undo.empty();
    }
    
    /**
     * Ajoute un EtatGlobal à la pile des redo
     * @param e EtatGlobal qu'on ajoute
     */
    public void addRedo(EtatGlobal e){
        redo.push(e);
    }
    
    /**
     * Donne l'etat global au sommet de la pile des redo, et l'y enlève
     * @return le dernier coup placé en redo
     */
    public EtatGlobal getRedo(){
        return redo.pop();
    }
    
    /**
     * Test si redo est vide
     * @return true si redo est vide, false sinon
     */
    public boolean estVideRedo(){
        return redo.empty();
    }
    
}

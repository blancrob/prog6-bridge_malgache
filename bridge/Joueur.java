package bridge;

import java.io.Serializable;

public class Joueur implements Serializable{
    PileCartes main, tas;
    int score, difficulte, scoreTotal;
    
    public Joueur(){
        main = new PileCartes();
        tas = new PileCartes();
        score=0;
        scoreTotal=0;
        difficulte=0;
    }
    
}

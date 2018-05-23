package bridge;

import java.io.Serializable;

public class Joueur implements Serializable{
    PileCartes main, tas, cartesPiochees;
    int score, difficulte, scoreTotal, manchesGagnees;
    
    public Joueur(){
        main = new PileCartes();
        tas = new PileCartes();
        cartesPiochees = new PileCartes();
        score=0;
        scoreTotal=0;
        difficulte=0;
        manchesGagnees=0;
    }
    
}

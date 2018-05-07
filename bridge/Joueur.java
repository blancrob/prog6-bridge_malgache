package bridge;

public class Joueur {
    PileCartes main, tas;
    int score, difficulte;
    
    public Joueur(){
        main = new PileCartes();
        tas = new PileCartes();
        score=0;
        difficulte=0;
    }
    
}

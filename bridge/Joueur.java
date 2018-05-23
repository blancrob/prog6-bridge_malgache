package bridge;

import java.io.Serializable;
import java.util.Iterator;

public class Joueur implements Serializable{
    PileCartes main, tas, cartesPiochees;
    int score, difficulte, scoreTotal, manchesGagnees;
    String nom;
    
    public Joueur(){
        main = new PileCartes();
        tas = new PileCartes();
        cartesPiochees = new PileCartes();
        score=0;
        scoreTotal=0;
        difficulte=0;
        manchesGagnees=0;
        nom = "";
    }
    
    /**
     * Renvoie un tableau des cartes de la main du joueur qu'il a le droit de jouer
     * @param couleur la couleur de la carte posée par le premier joueur
     * @return Tableau des cartes jouables
     */
    public Carte[] jouables(int couleur){
        Iterator<Carte> it = main.iterateur();
        Boolean fournir = false;
        int lg = 0;
        Carte c;
        
        while(it.hasNext()){
            c = it.next();
            if(c.couleur==couleur){
                fournir = true;
                lg++;
            }
        }
        
        Carte[] res = new Carte[lg];
        
        it = main.iterateur();
        lg = 0;
        
        while(it.hasNext()){
            c = it.next();
            if(fournir){
                if(c.couleur==couleur){
                    res[lg]=c;
                    lg++;
                }
            }else{
                res[lg]=c;
                lg++;
            }
        }
        
        return res;
    }
}

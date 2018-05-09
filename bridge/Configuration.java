package bridge;

import static bridge.Moteur.afficherCarte;

public class Configuration {
    
    PileCartes pile1, pile2, pile3, pile4, pile5, pile6;
    PileCartes[] pioche;
    Carte carteP, carteS;
    int conditionVictoire, mancheMax, scoreMax, atout, joueur, donneur, donneurInitial, receveur, scorej1, scorej2, manche, gagnant, perdant, taille, mode;
    
    public Configuration(){
        pile1 = new PileCartes();
        pile2 = new PileCartes();
        pile3 = new PileCartes();
        pile4 = new PileCartes();
        pile5 = new PileCartes();
        pile6 = new PileCartes();
        
        pioche = new PileCartes[6];
        pioche[0] = pile1;
        pioche[1] = pile2;
        pioche[2] = pile3;
        pioche[3] = pile4;
        pioche[4] = pile5;
        pioche[5] = pile6;
        
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
        for(int i=0; i<6; i++){
            if(pioche[i].premiere()!=null){
                System.out.print(pioche[i].taille() +"||");
                System.out.print("["+(i+1)+"]:");
            afficherCarte(pioche[i].premiere());
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
            if(res.valeur < pioche[i].premiere().valeur){   //si la carte courante a une plus grande valeur que la carte résultat, elle deviens la carte résultat
                res = pioche[i].premiere();
            }
            else if(res.valeur == pioche[i].premiere().valeur){ //si les valeurs des cartes sont égales, si la couleur de la carte courante est plus forte, elle deviens la carte résultat
                if(res.couleur < pioche[i].premiere().valeur){
                    res = pioche[i].premiere();
                }
            }
        }
        
       atout = res.couleur;
       
    }
    
}
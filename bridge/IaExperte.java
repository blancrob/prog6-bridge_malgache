/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;


/**
 *
 * Ia Experte.
 */
public class IaExperte implements IA{
    PileCartes main;
    PileCartes cartesDejaJouees;
    PileCartes cartesPiochees;
    Carte[] pioche;
    int lg;
    int atout;
    Carte courante;
    int[] nbCartes;
    boolean gagnant;
    PileCartes adverse;
    PileCartes[] piocheEntiere;
    int plisAdv;
    int plisIA;
    int condVic;
    
    
   public IaExperte(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour, int[] n, boolean g,int nbPlisIA, int nbPlisAdv, int conditionVictoire, PileCartes j2, PileCartes[] pe) {
        main = m;
        cartesDejaJouees = c;
        cartesPiochees = pi;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
        nbCartes = n;
        gagnant = g;
        plisAdv = nbPlisAdv;
        plisIA = nbPlisIA;
        condVic = conditionVictoire;
        adverse = j2;
        piocheEntiere = pe;
    }
    
    /**
     * Jouer Experte. Elle connait la disposition de toutes les cartes après la distribution 
     * 1ERE A JOUER
     * Si pioche cool || pioche nulle et que toutes les cartes juste en dessous sont aussi nulles || pioche nulle et un des tas de la pioche a une seule carte
     *      Jouer la carte la plus interressante connaissant la main de l'adversaire (voir spécif meilleurCoupCommenceExperte dans IA_Util)
     * Si pioche nulle && que 4/6 cartes de la pioche dévoileront une carte de meilleure heuristique && que tous les tas ont plus d'une carte 
     *      Jouer minimum pour perdre le pli 
     * 
     * 2EME A JOUER
     *  Jouer la carte la plus interressante en fonction de la pioche (voir specif meilleurCoupTermineExperte dans IA_Util)
     *   
     * @return une carte à jouer
     */
    @Override
    public Carte jouer(){
        Carte res;
         // On regarde à quoi ressemble la pioche
         int i = 0;
        double hCartesSurPioche = 0;
        while (i<lg){
            if(IA_Util.heuristiqueExperte(main,adverse,pioche[i],atout,cartesDejaJouees) > hCartesSurPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSurPioche = IA_Util.heuristiqueExperte(main,adverse,pioche[i],atout,cartesDejaJouees);
            }
            i++;
        }
        double hCartesSousPioche = 0;
        Carte[] piocheDessous = new Carte[6]; // tableau des cartes placées en 2eme dans chaque tas de la pioche
        for (int j=0; j<6; j++){
            if(piocheEntiere[j].taille() > 1){
               piocheDessous[j]= piocheEntiere[j].pile.get(1); 
            }
        }
        
        i=0;
        //On regarde à quoi ressemble les cartes juste en dessous dans les tas de la pioche 
        while (i<piocheDessous.length){
            if(piocheDessous[i] != null && IA_Util.heuristiqueExperte(main,adverse,piocheDessous[i],atout,cartesDejaJouees) > hCartesSousPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSousPioche = IA_Util.heuristiqueExperte(main,adverse,piocheDessous[i],atout,cartesDejaJouees);
            }
            i++;
        }
        
        if(courante == null){ // 1ERE A JOUER
            if (!(IA_Util.ImtheBest(plisIA,plisAdv,condVic) && pioche[IA_Util.positionMeilleurCartePioche(pioche,lg)] != null && IA_Util.heuristiqueExperte(main,adverse,pioche[IA_Util.positionMeilleurCartePioche(pioche,lg)],atout,cartesDejaJouees)>hCartesSurPioche) &&((hCartesSurPioche>0.4)||((hCartesSurPioche<0.4)&& (hCartesSousPioche<0.4))|| //Si pioche cool ou pioche nulle et que toutes les cartes juste en dessous sont aussi nulles 
               ((hCartesSurPioche<0.4)&&(IA_Util.plusPetitePile(nbCartes, lg)==1)) || // ou pioche nulle et un des tas de la pioche a une seule carte
                    ((hCartesSurPioche>0.4)|| IA_Util.gagnerCommence(piocheEntiere, nbCartes, adverse, atout) || (IA_Util.plusPetitePile(nbCartes, lg) == 1)))){ // Si pioche cool || interessant de gagner (voir gagnerCommence) ||un tas de la pioche à une carte 
                    
                return IA_Util.meilleurCoupCommenceExperte(adverse, main, atout); //jouer une carte qui va gagner si possible 
        
            }
            else{
                res = IA_Util.plusPetitePerdante(main, adverse, atout,cartesDejaJouees); //jouer la carte de plus faible heuristique contre son jeu.
            }
        }    
        else{ // 2EME A JOUER
            res = IA_Util.meilleurCoupTermineExperte(main,adverse, atout, courante,pioche,lg, nbCartes, piocheEntiere,plisIA,plisAdv,condVic,cartesDejaJouees);
        }
        return res;
    }
    
    /**
     * pioche Experte. 
     * Elle connait toute la pioche
     * Si l'IA est la 2ème à piocher et que la pioche est pas très cool, 
     * elle choisit de prendre une carte qui va entrainer la découverte d'une carte d'heuristique inférieure à celle qui vient d'être piochée
     * @return la carte à piocher 
     */
    @Override
     public Carte piocher(){
        Carte res = IA_Util.choisirMeilleureCartePioche(atout, pioche, lg); // Choisir le meilleur atout de la pioche 
        int pos = 0;
        Carte[] piocheDessous = new Carte[6]; // tableau des cartes placées en 2eme dans chaque tas de la pioche
        for (int j=0; j<6; j++){
            if(piocheEntiere[j].taille() > 1){
                piocheDessous[j]= piocheEntiere[j].pile.get(1); 
            }
        }
        if(res == null){ //si pas d'atout 
            Carte[] piocheDessus = new Carte[6]; // tableau des cartes placées en 2eme dans chaque tas de la pioche
            for (int j=0; j<6; j++){
                if(!piocheEntiere[j].vide()){
                    piocheDessus[j]= piocheEntiere[j].pile.get(0); 
                }
            }
            int i = 0;
            double h = 0;
            while (i<6){ 
                if(piocheDessus[i]!= null && IA_Util.heuristiqueExperte(main,adverse,piocheDessus[i], atout, cartesDejaJouees) >= h){ // prendre la carte avec la meilleure heuristique 
                    res = piocheDessus[i];
                    h = IA_Util.heuristiqueExperte(main,adverse,piocheDessus[i], atout,cartesDejaJouees);
                    pos = i;
                }
                i++;
            }
            if (h<0.4 ||    
                (piocheDessous[pos] != null && 
                (gagnant || !IA_Util.gagneProchainPli(main, adverse, piocheDessus[pos], atout)) && 
                ((IA_Util.carteMaitre(piocheDessous[pos], cartesDejaJouees) && !IA_Util.carteMaitre(piocheDessus[pos], cartesDejaJouees)) ||
                (main.minGagnant(piocheDessous[pos].couleur, piocheDessous[pos].valeur)==null) ||
                (!IA_Util.avantageAtout(main,adverse,atout) && piocheDessous[pos].couleur == atout)
                ))){ 
                /*si la carte n'est pas interessant || on est dans une situation ou l'on doit faire attention à la carte qu'on retourne et que
                             *on retourne une carte que l'on ne peut pas gagner.
                             *ou on retourne un atout alors qu'on a pas plus d'atout que lui
                             *ou on retourne une carte maitre dans une couleur sans en prendre une maitre nous même.
                on cherche s'il n'y a pas une meilleur situation de pioche
                        */
                int j = 0;
                double heur = 1000000;
                Carte temp = null;
                while(j<piocheEntiere.length){
                    if(piocheDessous[j]==null && piocheDessus[j]!= null && (temp == null || (temp.valeur<piocheDessus[j].valeur))){
                        //Si il y a une pioche avec une seule carte on la prend.
                       temp = piocheDessus[j];
                    }
                    j++;
                }
                j=0;
                if (temp == null){
                    while(j<piocheEntiere.length){      
                        if(piocheDessous[j] != null && IA_Util.heuristiqueExperte(adverse,main, piocheDessous[j], atout,cartesDejaJouees)<heur && piocheDessous[j].couleur != atout){
                            //sinon on pioche la carte retournant la moins mauvaise contre nous.
                            temp = piocheDessus[j];
                            heur = IA_Util.heuristiqueExperte(adverse,main, piocheDessous[j], atout,cartesDejaJouees);
                        }
                        j++;
                    }
                }
                if(temp != null){
                    res = temp;
                }
            }
        }
        
        //}
        return res;            
    }
  
}    
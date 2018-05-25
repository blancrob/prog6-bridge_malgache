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
            if(IA_Util.heuristiqueExperte(main,adverse,pioche[i],atout) > hCartesSurPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSurPioche = IA_Util.heuristiqueExperte(main,adverse,pioche[i],atout);
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
            if(piocheDessous[i] != null && IA_Util.heuristiqueExperte(main,adverse,piocheDessous[i],atout) > hCartesSousPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSousPioche = IA_Util.heuristiqueExperte(main,adverse,piocheDessous[i],atout);
            }
            i++;
        }
        
        if(courante == null){ // 1ERE A JOUER
            if (!(IA_Util.ImtheBest(plisIA,plisAdv) && pioche[IA_Util.positionMeilleurCartePioche(pioche,lg)] != null && IA_Util.heuristiqueExperte(main,adverse,pioche[IA_Util.positionMeilleurCartePioche(pioche,lg)],atout)>hCartesSurPioche) &&((hCartesSurPioche>0.4)||((hCartesSurPioche<0.4)&& (hCartesSousPioche<0.4))|| //Si pioche cool ou pioche nulle et que toutes les cartes juste en dessous sont aussi nulles 
               ((hCartesSurPioche<0.4)&&(IA_Util.plusPetitePile(nbCartes, lg)==1)) || // ou pioche nulle et un des tas de la pioche a une seule carte
                    ((hCartesSurPioche>0.4)|| IA_Util.gagnerCommence(piocheEntiere, nbCartes, adverse, atout) || (IA_Util.plusPetitePile(nbCartes, lg) == 1)))){ // Si pioche cool || interessant de gagner (voir gagnerCommence) ||un tas de la pioche à une carte 
                    
                return IA_Util.meilleurCoupCommenceExperte(adverse, main, atout); //jouer une carte qui va gagner si possible 
        
            }
            else{
                res = IA_Util.plusPetitePerdante(main, adverse, atout);
            }
        }    
        else{ // 2EME A JOUER
            res = IA_Util.meilleurCoupTermineExperte(main,adverse, atout, courante,pioche,lg, nbCartes, piocheEntiere,plisIA,plisAdv);
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
            int i = 0;
            double h = 0;
            while (i<lg){ // pour chaque pile de la pioche 
                /*if(gagnant){ // si on est gagnant donc 1er à piocher
                    if(IA_Util.heuristiqueExperte(adverse,pioche[i], atout) >= h){ // Trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueExperte(adverse,pioche[i], atout);
                    }
                }
                else{ // Si on est le 2ème à piocher 
                    if(IA_Util.heuristiqueExperte(adverse,pioche[i], atout) >= h){ // prendre la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueExperte(adverse,pioche[i], atout);
                    }
                }*/
                if(IA_Util.heuristiqueExperte(main,adverse,pioche[i], atout) >= h){ // prendre la carte avec la meilleure heuristique 
                    res = pioche[i];
                    h = IA_Util.heuristiqueExperte(main,adverse,pioche[i], atout);
                    pos = i;
                }
                i++;
            }
           /*if(gagnant){ //Si on est gagnant  */
                if (h<0.4 || 
                        (piocheDessous[pos] != null && (
                        (main.minGagnant(piocheDessous[pos].couleur, piocheDessous[pos].valeur)==null) ||
                        (!IA_Util.avantageAtout(main,adverse,atout) && piocheDessous[pos].couleur == atout)
                        ))){ //mais que la pioche est pas très cool                             
                    //Choisir une carte
                    int j = 0;
                    double heur = 1000000;
                    Carte temp = null;
                    while(j<piocheEntiere.length){
                        if(piocheDessous[j]==null && pioche[j]!= null && (temp == null || (temp.valeur<pioche[j].valeur))){
                           temp = pioche[j];
                        }
                        j++;
                    }
                    j=0;
                    if (temp == null){
                        while(j<piocheEntiere.length){      
                            if(piocheDessous[j] != null && IA_Util.heuristiqueExperte(adverse,main, piocheDessous[j], atout)<heur && piocheDessous[j].couleur != atout){
                                temp = pioche[j];
                                heur = IA_Util.heuristiqueExperte(adverse,main, piocheDessous[j], atout);
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
  
    /**
     * Pioche Experte. Connait la pioche
     * 1ERE A PIOCHER
     *  1)Choisir le plus grand atout : a. qui retourne une carte que l'on peut battre
     *                                   b. qui retourne une carte que l'on peut pas battre
     *  2)Trouver la plus grande carte : a. qui retourne une carte que l'on peut battre et la choisir 
     *                                   b. qui retourne une carte que l'on ne peut pas battre (la garder en mémoire)
     *  3)Si la carte gardée en mémoire a pas une heuristique géniale et si il y a une pioche avec 1 seule carte piocher cette carte,
     *                                                                   sinon choisir carte gardée en mémoire 
     *  
     * 2EME A PIOCHER
     * Si on est sûr de gagner le coup suivant quelque soit la couleur demandée par l'adversaire
     *   1) Choisir le plus grand atout
     *   2) trouver la carte de plus grosse heuristique
     *             si elle est pas cool regarder en dessous et si une carte géniale en dessous piocher la carte qui va la découvrir 
     * 
     * Si on est pas sûr de gagner le coup suivant 
     *   1) Choisir le plus grand atout : a. qui retourne une carte à faible heuristique 
     *                                    b. qui retourne une carte à grande heuristique
     *   2)Trouver la plus grande carte : a. qui retourne une carte à faible heuristique et la choisir 
     *                                    b. qui retourne une carte à grande heuristique et la garder en mémoire 
     *   3) si il y a une pioche avec 1 seule carte piocher cette carte,
     *      sinon choisir carte gardée en mémoire 
     * 
     * @return la carte à piocher
     */
    public Carte piocherv2(){
       Carte res = null;
       if(gagnant){ //1ERE A PIOCHER
           res = IA_Util.piocheCommenceExperte(atout,piocheEntiere,pioche,lg,main,adverse,nbCartes);
       }
       else{ //2EME A PIOCHER
           res = IA_Util.piocherMeilleurAtout(piocheEntiere,main,adverse,atout);
           if (res == null){
               res = IA_Util.piocherMeilleurCarte(piocheEntiere,main,adverse,atout);
           }if(res == null){
               res = IA_Util.piocherAtoutPerdant(piocheEntiere,main,adverse,atout);
           }if(res == null){
               res = IA_Util.piocherCartePerdante(piocheEntiere,main,adverse,atout);
           }
       }
       return res;
    }
}

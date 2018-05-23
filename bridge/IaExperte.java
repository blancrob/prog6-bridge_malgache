/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import static bridge.IA_Util.plusPetitePile;

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
    
    
   public IaExperte(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour, int[] n, boolean g, PileCartes j2, PileCartes[] pe) {
        main = m;
        cartesDejaJouees = c;
        cartesPiochees = pi;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
        nbCartes = n;
        gagnant = g;
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
            if(IA_Util.heuristiqueExperte(adverse,pioche[i],atout) > hCartesSurPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSurPioche = IA_Util.heuristiqueExperte(adverse,pioche[i],atout);
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
        while (i<piocheDessous.length && piocheDessous[i] != null){
            if(IA_Util.heuristiqueExperte(adverse,piocheDessous[i],atout) > hCartesSousPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSousPioche = IA_Util.heuristiqueExperte(adverse,piocheDessous[i],atout);
            }
            i++;
        }
        
        if(courante == null){ // 1ERE A JOUER
            if ((hCartesSurPioche>0.4)||((hCartesSurPioche<0.4)&& (hCartesSousPioche<0.4))|| //Si pioche cool ou pioche nulle et que toutes les cartes juste en dessous sont aussi nulles 
               ((hCartesSurPioche<0.4)&&(plusPetitePile(nbCartes, lg)==1)) || // ou pioche nulle et un des tas de la pioche a une seule carte
                    ((hCartesSurPioche>0.4)|| IA_Util.gagnerCommence(piocheEntiere, nbCartes, adverse, atout) || (IA_Util.plusPetitePile(nbCartes, lg) == 1))){ // Si pioche cool || interessant de gagner (voir gagnerCommence) ||un tas de la pioche à une carte 
                    
                return IA_Util.meilleurCoupCommenceExperte(adverse, main, atout); //jouer une carte qui va gagner si possible 
        
            }
            else{
                res = main.min();
            }
        }    
        else{ // 2EME A JOUER
            res = IA_Util.meilleurCoupTermineExperte(main,adverse, atout, courante,pioche,lg, nbCartes, piocheEntiere);
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
        if(res == null){ //si pas d'atout 
            int i = 0;
            double h = 0;
            while (i<lg){ // pour chaque pile de la pioche 
                if(gagnant){ // si on est gagnant donc 1er à piocher
                    if(IA_Util.heuristiqueCommence(pioche[i], atout, main, cartesDejaJouees, pioche, lg) >= h){ // Trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueCommence(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                }
                else{ // Si on est le 2ème à piocher 
                    if(IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg) >= h){ // prendre la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                }
                i++;
            }
            if(gagnant){ //Si on est gagnant  
                if (h<0.4){ //mais que la pioche est pas très cool                             
                    //Choisir une carte
                    int j = 0;
                    double heur = 1;
                    int k = 0;
                    while(j<piocheEntiere.length){      
                        if(IA_Util.heuristiqueTermine(piocheEntiere[j].pile.get(1), atout, main, cartesDejaJouees, pioche, lg)<heur){
                            heur = IA_Util.heuristiqueTermine(piocheEntiere[j].pile.get(1), atout, main, cartesDejaJouees, pioche, lg);
                            k = j;
                        }
                        j++;
                    }
                    res = piocheEntiere[k].premiere();
                }
            }
        
        }
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
        
        if(gagnant){ // Si on est le premier à piocher/jouer le prochain tour.
            int i = IA_Util.positionMeilleurCartePioche(atout, pioche, lg);
            PileCartes temp = main.clone();
            temp.ajouter(IA_Util.choisirMeilleureCartePioche(atout, pioche, lg));
            if(temp.minGagnant(piocheEntiere[i].pile.get(1).couleur, piocheEntiere[i].pile.get(1).valeur)!=null){ // Si la carte en dessous de celle testé nous empêche pas de gagner le prochain pli.
               res = IA_Util.choisirMeilleureCartePioche(atout, pioche, lg); // on prend la meilleur carte disponible.
            }
            else{ // Si la carte en dessous permet à l'adversaire de gagner, on regarde les autres tas.
                for(int j=0; j<pioche.length; j++){ 
                    if(j != i){                          //Pour chaque autre tas.
                        PileCartes test = main.clone();
                        test.ajouter(pioche[j]);
                        if(temp.minGagnant(piocheEntiere[j].pile.get(1).couleur, piocheEntiere[j].pile.get(1).valeur) != null){   //si la carte en dessous nous empêche pas de gagner.
                            if(res == null){
                                res = pioche[j];
                            }
                            if((pioche[j].couleur == atout && res.couleur != atout) || (pioche[j].valeur > res.valeur)){ // on prend la meilleur des cartes dont celle en dessous n'est pas trop forte.
                                res = pioche[j];
                            }
                        }
                    }
                }
            }
            if(res == null){ //Si toujours pas de pioche idéale on prend le meilleur atout
                res = IA_Util.choisirMeilleureCartePioche(atout, pioche, lg);
            }
            if(res == null){ // si pas d'atout on prend la meilleure carte
                for(int k = 0; k < pioche.length; k++){
                    if(res == null){
                        res = pioche[k];
                    }
                    if(pioche[k].valeur > res.valeur){
                        res = pioche[k];
                    }
                }
            }
            if(IA_Util.heuristiqueCommence(res, atout, main, cartesDejaJouees, pioche, lg) < 0.4){
                if(IA_Util.plusGrossePile(nbCartes, lg)>1){
                    for(int k=0; k<piocheEntiere.length; k++){
                        if(piocheEntiere[k].taille() == 1){
                            res = pioche[k];
                        }
                    }
                }
            }
        }
        else{ // Si on pioche en second.
            for(int p = 0 ; p < pioche.length; p++){
               if(adverse.minGagnant(pioche[p].couleur,pioche[p].valeur) == null){
                    if(res == null){
                        res = pioche[p];
                    }
                    if((res.couleur != atout && pioche[p].couleur == atout) || (pioche[p].valeur > res.valeur)){ //commentaire
                        res = pioche[p];
                    }
                }
            }
            if (res == null){
                
            }
            
        }
        return res;
    }
}

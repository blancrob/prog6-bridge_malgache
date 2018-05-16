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
public class IaExperte extends IaDifficile{

    public IaExperte(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour) {
        super(m, c, pi, p, l, at, cour);
    }
    
    /**
     * Elle connait la disposition de toutes les cartes après la distribution 
     * @param nbCartes
     * @return une carte à jouer
     */
    public Carte jouer(int[] nbCartes, PileCartes adverse){
        if(courante == null){ // si l'IA commence
            return IA_Util.tricheCommence(adverse, main, atout); //jouer une carte qui va gagner si possible 
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            
            if (IA_Util.fournir(courante.couleur, main)){ // si on a la couleur demandée 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la plus petite carte gagnante
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ // si on a pas la couleur demandée
                
                int i = 0;
                double h = 0;
                while (i<lg){ // On regarde à quoi ressemble la pioche
                    if(IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg) > h){ // trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                    i++;
                }
                if ((h<0.4) && (IA_Util.plusGrossePile(nbCartes, lg)>1)){ //Si la pioche est pas très cool et qu'il reste au moins une pile avec plus d'une carte
                    res = main.min(); // on donne la plus petite carte de la main pour perdre le pli
                }
                
                else{ // Si la pioche est cool on essaye de gagner le pli
                    if (IA_Util.fournir(atout, main)){ // on joue de l'atout si possible
                        res = main.min(atout); // on joue le plus petit atout 
                    }
                    else{ // si on a pas d'atout 
                        res = main.min(); // on donne la plus petite carte de la main 
                    }
                }
            }
            return res;
        }
    }
    
    /****************************************************************************************************************************
     * pioche Experte.
     * Elle connait toute la pioche
     * Si l'IA est la 2ème à piocher et que la pioche est pas très cool, 
     * elle choisit de prendre une carte qui va entrainer la découverte d'une carte d'heuristique inférieure à celle qui vient d'être piochée
     * @param gagnant
     * @param nbCartes
     * @return la carte à piocher 
     */
    public Carte piocher(boolean gagnant, int[] nbCartes, PileCartes[] piocheEntiere){
        Carte res = IA_Util.choisirMeilleureCarte(atout, pioche, lg); // Choisir le meilleur atout de la pioche 
        if(res == null){ // si pas d'atout 
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
}

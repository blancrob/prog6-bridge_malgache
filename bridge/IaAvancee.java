/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

/**
 *
 * Ia Avancée.
 */
public class IaAvancee extends IaMoyenne {

    public IaAvancee(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour) {
        super(m, c, pi, p, l, at, cour);
    }
    
    
    /** 
    * Si IA commence 
    *       Jouer carte de meilleure heuristique 
    * si Adversaire a déjà joué 
    *       Si on a la couleur demandée
    *           Si on peut gagner : mettre la plus petite carte gagnante 
    *           Sinon : mettre la plus petite carte 
    * 
    *       Si on a pas la couleur 
    *           Regarder la pioche 
    *           Si pioche cool : jouer le plus petit atout possible si il y a 
    *           Si pioche pas cool : mettre la plus petite carte 
    * 
    * @param nbCartes
    * @return une carte à jouer
    */
    public Carte jouer(int[] nbCartes){
        if(courante == null){ // si l'IA commence elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (IA_Util.fournir(courante.couleur,main)){ // si on a la couleur demandée 
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
                    if (IA_Util.fournir(atout,main)){ // on joue de l'atout si possible
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
        
    /**
     * Choisir plus gros atout si il y en a
     * Choisir la carte d'une autre couleur qui a la meilleure heuristique 
     * Si pioche pas cool : - Si on est le gagnant alors préférer une carte d'une pile ou il reste le moins de cartes (proba plus faible qu'en dessous il y ait une carte cool que l'adversaire pourra prendre)
     *                      - Si on est perdant : prendre la carte qui a la meilleure heuristique 
     * 
     * @param gagnant : vrai ssi on pioche apres avoir gagné le pli.
     * @param nbCartes : contient le nombre de cartes dans les 6 piles de la pioche.
     * @return la carte à jouer 
     */

    public Carte piocher(boolean gagnant, int[] nbCartes){
        Carte res = IA_Util.choisirMeilleureCarte(atout, pioche, lg); // Choisir le meilleur atout de la pioche 
        if(res == null){ // si pas d'atout 
            int i = 0;
            double h = 0;
            while (i<lg){ // pour chaque pile de la pioche 
                if(gagnant){ // si on est gagnant donc 1er à piocher
                    if(IA_Util.heuristiqueCommence(pioche[i], atout, main, cartesDejaJouees, pioche, lg)>= h){ // Trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueCommence(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                }
                else{ // Si on est le 2ème à piocher 
                    if(IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg)>= h){ // prendre la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                }
                i++;
            }
            if(gagnant){ //Si on est gagnant  
                if (h<0.4){ //mais que la pioche est pas très cool                             
                    res = IA_Util.meilleurPioche(nbCartes, lg, pioche);  //Choisir la carte de la pile qui a le moins de cartes
                }
            }
        }
        return res;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;

/**
 *
 * Ia Difficile. 
 * Même pioche que IA avancée.
 */
public class IaDifficile extends IaAvancee {

    public IaDifficile(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour, int[] n, boolean g, int nbPlisIA, int nbPlisAdv) {
        super(m, c, pi, p, l, at, cour, n, g, nbPlisIA, nbPlisAdv);
    }

    
    
    
   /**
    * Comme IA avancée, mis à part qu'elle peut déduire une partie de la main adverse car elle connait les cartes piochées et les cartes jouées.
    * @return une carte à jouer
    */
    @Override
    public Carte jouer(){
        if(courante == null){ // si l'IA commence elle joue la plus grosse carte
            PileCartes test = IA_Util.adversaire(main, cartesDejaJouees, cartesPiochees);
            Iterator<Carte> it2= test.iterateur();
            return IA_Util.meilleurCoupCommence(main, cartesDejaJouees, cartesPiochees, atout, pioche, lg);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;

/**
 * Ia Moyenne.
 */
public class IaMoyenne extends IaNovice {

    public IaMoyenne(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour) {
        super(m, c, pi, p, l, at, cour);
    }
    
    /**
     * Si IA commence : joue la plus grande carte 
     * SI IA termine :
        * 1) Joue la plus petite carte gagnante de la bonne couleur
        * 2) Joue la plus petite carte perdante de la bonne couleur
        * 3) Joue le plus petit atout 
        * 4) Joue la plus petite carte d'une autre couleur
     * @return une carte à jouer
     */
    @Override
     public Carte jouer(){
        if(courante == null){ // si l'IA commence elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (IA_Util.fournir(courante.couleur, main)){ // si on a la couleur demandée 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la plus petite carte gagnante
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ // si on a pas la couleur demandée
                if (IA_Util.fournir(atout,main)){ // on joue de l'atout si possible
                    res = main.min(atout); // on joue le plus petit atout 
                }
                else{ // si on a pas d'atout 
                    res = main.min(); // on donne la plus petite carte de la main 
                }
            }
            return res;
        }
       
    }
    
    
     /**
     * pioche une carte , le plus grand atout possible, si pas d'atout la plus grande carte d'une autre couleur 
     * @return une carte parmis les cartes de la pioche. 
     */
    @Override
    public Carte piocher(){
        int i = 0;
        Carte res = IA_Util.choisirMeilleureCarte(atout, pioche, lg); // Si on a des atout dans la picohe on prend le plus grand 
    
        if (res == null){ // si pas d'atout dans la pioche 
            res = pioche[0];
            while(i<lg){
                if (pioche[i].valeur>res.valeur){ // Recherche de la carte de valeur maximale 
                    res = pioche[i];
                }
                i++;
            }
        }   
        return res;
    }     
     
}

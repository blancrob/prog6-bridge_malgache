/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

/**
 * IA Facile.
 * Même façon de piocher que IA Novice
 */

public class IaFacile extends IaNovice {
   

    public IaFacile(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour) {
        super(m, c, pi, p, l, at, cour);
    }
    
    /**
     * Jouer.
     * Si IA commence : Aléatoire 
     * SI IA termine :
        * 1)Joue un coup gagnant aléatoire dans la bonne couleur, perdant sinon
        * 2)Joue un atout aléatoire
        * 3)Joue une carte aléatoire
     * @return Carte à jouer
     */
    @Override
    public Carte jouer(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return IA_Util.aleatoire(main);
        }
        else{ // si l'adversaire a joué 
            int couleur = courante.couleur; // on récupère la couleur jouée par l'adversaire
            Carte res = IA_Util.aleatoireGagnant(main,courante);
            if (res == null){
                res = IA_Util.aleatoire(main,courante);
            }
            if (res == null){
                res = IA_Util.aleatoire(main,atout);
            }
            if (res == null){
               res = IA_Util.aleatoire(main);
            }
            return res;
        }
    }
    
}

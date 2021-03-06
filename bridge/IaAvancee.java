/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

/**
 * Ia Avancée.
 */
public class IaAvancee implements IA {
    PileCartes main;
    PileCartes cartesDejaJouees;
    PileCartes cartesPiochees;
    Carte[] pioche;
    int lg;
    int atout;
    Carte courante;
    int[] nbCartes;
    boolean gagnant;
    int nbPlisIA;
    int nbPlisAdv;
    int conditionVictoire;
    
    
    public IaAvancee(PileCartes m, PileCartes c, PileCartes pi, Carte[] p, int l, int at, Carte cour, int[] n, boolean g, int nbPlisIA, int nbPlisAdv, int conditionVictoire) {
        main = m;
        cartesDejaJouees = c;
        cartesPiochees = pi;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
        nbCartes = n;
        gagnant = g;
        this.nbPlisIA = nbPlisIA;
        this.nbPlisAdv = nbPlisAdv;
        this.conditionVictoire = conditionVictoire;
    }
    
    
    /** 
    * Jouer.
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
    * @return une carte à jouer
    */
    @Override
    public Carte jouer(){
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
                        h = IA_Util.heuristiqueTermine(pioche[i], atout, main, cartesDejaJouees, pioche, lg);
                    }
                    i++;
                }
                if ((h<0.4) && (IA_Util.plusGrossePile(nbCartes, lg)>1) && ((conditionVictoire == 1) && IA_Util.ImtheBest(nbPlisIA,nbPlisAdv,conditionVictoire))){ //Si la pioche est pas cool et qu'il reste au moins 1 pile avec plus d'1 carte et qu'on est pas "obligé" de gagner ce plis en mode nb de manches
                    res = IA_Util.minHorsAtout(atout, main); // on donne la plus petite carte de la main (hors atout) pour perdre le pli
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
     * Piocher.
     * Choisir plus gros atout si il y en a
     * Choisir la carte d'une autre couleur qui a la meilleure heuristique 
     * Si pioche pas cool : - Si on est le gagnant alors préférer une carte d'une pile ou il reste le moins de cartes (proba plus faible qu'en dessous il y ait une carte cool que l'adversaire pourra prendre)
     *                      - Si on est perdant : prendre la carte qui a la meilleure heuristique 
     * 
     * @return la carte à jouer 
     */
    @Override
    public Carte piocher(){
        Carte res = IA_Util.choisirMeilleureCartePioche(atout, pioche, lg); // Choisir le meilleur atout de la pioche 
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
            if(IA_Util.ImtheBest(nbPlisIA, nbPlisAdv, conditionVictoire)){ //Si on est gagnant  
                if (h<0.4){ //mais que la pioche est pas très cool                             
                    if(IA_Util.meilleurPioche(nbCartes, lg, pioche) != null ){
                        res = IA_Util.meilleurPioche(nbCartes, lg, pioche);  //Choisir la carte de la pile qui a le moins de cartes
                    }
                }
            }
        }
        return res;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;
import java.util.Random;

/**
 * Contient toutes les méthodes utiles aux IA.
 */

public class IA_Util {
    
    /**
     * @param couleur demandée
     * @param main main de l'IA
     * @return true si on a une carte de la couleur passée en paramètre
     */
    public static boolean fournir(int couleur, PileCartes main){
        Carte res = null;
        Carte tmp;
        Iterator<Carte> it = main.iterateur();
        do{
            tmp = it.next();
            if (tmp.couleur == couleur){
                res = tmp;
            }
        }while(res == null && it.hasNext());
        
        return res != null;
    }
    
    /**
     * @param couleur
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @param lg nombre de piles de la pioche 
     * @return la carte de plus grande valeur de la couleur passée en paramètre présente dans la pioche
     */
    public static Carte choisirMeilleureCartePioche(int couleur, Carte[] pioche, int lg){
        Carte res = null;
        for(int i = 0; i < lg; i++){
           if (pioche[i].couleur == couleur){
               if(res == null){
                   res = pioche[i];
               }
               else{
                   if(res.valeur < pioche[i].valeur){
                       res = pioche[i];
                   }
               }
           } 
        }
        return res;
    }
    
    
    public static int positionMeilleurCartePioche(int couleur, Carte[] pioche, int lg){
        Carte res = null;
        int j = 0;
        for(int i = 0; i < lg; i++){
           if (pioche[i].couleur == couleur){
               if(res == null){
                   res = pioche[i];
                   j=i;
               }
               else{
                   if(res.valeur < pioche[i].valeur){
                       res = pioche[i];
                       j=i;
                   }
               }
           } 
        }
        return j;
    }
    
       
    /**
     * Contruit la main adverse connue.
     * @param main main de l'IA
     * @param cartesDejaJouees 
     * @param cartesPiochees
     * @return une PileCartes contenant les cartes connu de la main adverse.
     */
    public static PileCartes adversaire(PileCartes main, PileCartes cartesDejaJouees, PileCartes cartesPiochees){
        PileCartes res = new PileCartes();
        res.pile = cartesPiochees.pile;
        Iterator<Carte> m = main.iterateur();
        Iterator<Carte> j = cartesDejaJouees.iterateur();
        while(m.hasNext()){
            res.retirer(m.next());
        }
        while(j.hasNext()){
            res.retirer(j.next());
        }
        return res;
    }
    
    /**
     * 
     * @param c une carte à tester
     * @param p la main de l'adversaire
     * @return vrai ssi l'adversaire a une carte connu de la même couleur que celle de c.
     */
    public static boolean aCouleur(Carte c, PileCartes p){
        boolean res = false;
        Iterator<Carte> it = p.iterateur();
        Carte tmp;
        while(it.hasNext() && !res){
            tmp = it.next();
            res = tmp.couleur == c.couleur;
        }
        return res;
    }
    
    /**
     * 
     * @param c une Carte
     * @param main main de l'IA
     * @param cartesDejaJouees
     * @param cartesPiochees
     * @return vrai ssi la carte c peut être battu par une carte connu de l'adversaire.
     */
    public static boolean battu(Carte c, PileCartes main, PileCartes cartesDejaJouees, PileCartes cartesPiochees){
        boolean res = false;
        boolean fournir = aCouleur(c,adversaire(main,cartesDejaJouees,cartesPiochees));
        Iterator<Carte> it = adversaire(main,cartesDejaJouees,cartesPiochees).iterateur();
        Carte tmp;
        while(it.hasNext() && !res){
            tmp = it.next();
            res = (tmp.couleur==c.couleur && tmp.valeur>c.valeur);
        }
        return res;
    }
    /**
     * Cherche la meilleur carte a jouer
     * @param main main de l'IA
     * @param cartesDejaJouees
     * @param cartesPiochees
     * @param atout
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @param lg nombre de piles de la pioche
     * @return Une carte étant la meilleur a jouer
     */
    public static Carte meilleurCoupCommence(PileCartes main, PileCartes cartesDejaJouees, PileCartes cartesPiochees, int atout, Carte[] pioche, int lg){
        Carte res = null;
        double h = 0;
        Iterator<Carte> it = main.iterateur();
        Carte tmp;
        while(it.hasNext()){
            tmp = it.next();
            if(battu(tmp, main, cartesDejaJouees, cartesPiochees)){
                if(h == 0){
                    res = tmp;
                }
            }
            else{
                if(h<heuristiqueCommence(tmp, atout ,main ,cartesDejaJouees ,pioche ,lg)){
                    h = heuristiqueCommence(tmp, atout ,main ,cartesDejaJouees ,pioche ,lg);
                    res = tmp;
                }
            }
        }
        if(h==0){
            return main.min();
        }else{
            return res;
        }
    }
      
    /**
     * Retourne la taille de la pile la plus grande
     * @param nbCartes tableau avec le nombre de cartes dans chaque pile de la pioche
     * @param lg nombre de piles de la pioche
     * @return 
     */
    public static int plusGrossePile(int[] nbCartes, int lg){
        int i = 0;
        int max = 0;
        while(i<lg){
            if(nbCartes[i] > max){
                max = nbCartes[i];
            }
            i++;
        }
        return max;
    }
    
    /**
     * Retourne la taille de la pile la plus petite
     * @param nbCartes tableau avec le nombre de cartes dans chaque pile de la pioche
     * @param lg nombre de piles de la pioche
     * @return 
     */
    public static int plusPetitePile(int[] nbCartes, int lg){
        int i = 0;
        int min = 10;
        while(i<lg){
            if(nbCartes[i] < min){
                min = nbCartes[i];
            }
            i++;
        }
        return min;
    }
    
    /**
     * Choisit la carte de la pile qui a le moins de cartes 
     * @param nbCartes tableau avec le nombre de cartes dans chaque pile de la pioche
     * @param lg nombre de piles de la pioche 
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @return la carte à choisir 
     */
    public static Carte meilleurPioche(int[] nbCartes, int lg, Carte[] pioche){
        int i = 0;
        int taille = 10;
        int res = 0;
        while(i<lg){
            if(nbCartes[i] < taille){
                res = i;
                taille = nbCartes[i];
            }
            i++;
        }
        return pioche[res];
    }
    
    /**
     * On enlève de toutes les cartes les cartes de la main, les cartes déjà jouées et les cartes qui sont découvertes dans les piles de la pioche.
     * @param main main de l'IA
     * @param cartesDejaJouees
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @param lg nombre de piles de la pioche
     * @return la liste de cartes qui ne sont pas encore connues (qui sont dans la pioche ou dans la main de l'adversaire)  
     */
    public static PileCartes cartesInconnues(PileCartes main, PileCartes cartesDejaJouees, Carte[] pioche, int lg){
        PileCartes res = new PileCartes();
        res.paquet(); // Toutes les cartes d'un paquet de cartes
        Iterator<Carte> it = main.iterateur(); 
        Carte tmp;
        while(it.hasNext()){ // Enlever les cartes qui sont dans la main
            tmp = it.next();
            res.retirer(tmp);
        }
        it = cartesDejaJouees.iterateur();
        while(it.hasNext()){ // Enlever les cartes qui ont déjà été jouées
            tmp = it.next();
            res.retirer(tmp);
        }
        int i = 0;
        while (i<lg){
            res.retirer(pioche[i]); // Enlever les cartes visibles sur les piles de la pioche
            i++;
        }
        return res; 
    }
    
    /**
     * HeuristiqueCommence. Cas ou on est le 1er a joué (cela détermine la couleur du pli)
     * Correspond aux nb de cartes que la carte en paramètre peut battre / nb de cartes restantes (encore non jouées)
     * @param c la carte dont on veut analyser l'heuristique
     * @param atout 
     * @param main main de l'IA
     * @param cartesDejaJouees 
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @param lg nombre de piles de la pioche 
     * @return l'heuristique de la carte (Pourcentage de victoire du pli si on utilise cette carte) 
     */
    public static double heuristiqueCommence(Carte c , int atout , PileCartes main , PileCartes cartesDejaJouees , Carte[] pioche , int lg){
        PileCartes cartesNonJouees = cartesInconnues(main,cartesDejaJouees,pioche,lg);
        Carte tmp;
        Iterator<Carte> it = cartesNonJouees.iterateur();
        int taille = 0;
        int res = 0;
        if(c.couleur == atout){ // Si la carte est 1 atout, elle battra toutes les cartes sauf les atouts plus grands qu'elle 
            do{ 
                tmp = it.next();
                taille++;
                if (tmp.couleur != atout || tmp.valeur < c.valeur){
                    res++;
                }
            }while(it.hasNext());
        }
        else{ // Si la carte est une couleur autre, elle battra toutes les cartes d'une autre couleurs (hors atouts) et les cartes inférieures de même couleur
            do{
                tmp = it.next();
                taille++;
                if (tmp.couleur != atout && (tmp.couleur != c.couleur || tmp.valeur < c.valeur)){
                    res++;
                }
            }while(it.hasNext());
        }
        return (double)res/(double)taille;
    }
    
    /**
     * HeuristiqueTermine. Cas ou on est le 2ème a joué (la couleur du pli a déjà été décidée par l'adversaire)
     * Correspond aux nb de cartes que la carte en paramètre peut battre / nb de cartes restantes (encore non jouées)
     * @param c la carte dont on veut calculer l'heuristique
     * @param atout
     * @param main main de l'IA
     * @param cartesDejaJouees
     * @param pioche tableau contenant les cartes retournées de la pioche 
     * @param lg nombre de piles de la pioches
     * @return l'heuristique de la carte (Pourcentage de victoire du pli si on utilise cette carte) 
     */
    public static double heuristiqueTermine(Carte c , int atout , PileCartes main , PileCartes cartesDejaJouees , Carte[] pioche , int lg){
        PileCartes cartesNonJouees = cartesInconnues(main,cartesDejaJouees,pioche,lg);
        Carte tmp;
        Iterator<Carte> it = cartesNonJouees.iterateur();
        int res = 0;
        int taille = 0;
        if(c.couleur == atout){ // Si la carte est 1 atout, elle battra toutes les cartes sauf les atouts plus grands qu'elle 
            do{
                tmp = it.next();
                taille++;
                if(tmp.couleur != atout || tmp.valeur < c.valeur){
                    res++;
                }
            }while(it.hasNext());
        }
        else{// Si la carte est une couleur autre, elle battra les cartes de la couleur demandée de valeur inférieure
            do{
                tmp = it.next();
                taille++;
                if(tmp.couleur != atout && (tmp.couleur == c.couleur && tmp.valeur < c.valeur)){
                    res++;
                }
            }while(it.hasNext());
        }
        return (double)res/(double)taille;
    }
    
    /**
     * meilleurCoupCommenceExperte.
     * Retourne la meilleure carte a jouer quand on commence (En connaissant la main de l'adversaire)
     * @param adverse la main de l'adversaire
     * @param main main de l'IA
     * @param atout 
     * @return une carte intéressante
     */
    public static Carte meilleurCoupCommenceExperte(PileCartes adverse, PileCartes main, int atout){   
        
        // Victoire sûre à 100%
        if(!adverse.contient(atout)){ // Si l'adversaire n'a pas d'atout 
            if (minGagnantIAExperte(true,atout,main, adverse)!=null){ 
                return minGagnantIAExperte(true,atout,main, adverse); // carte min gagnante d'une couleur que l'adversaire n'a pas (hors atout)
            }
        }
        if(minGagnantIAExperte(false,atout,main, adverse)!=null){
            return minGagnantIAExperte(false,atout,main, adverse); // carte min gagnante d'une couleur que l'adversaire a
        }
        // Victoire dépend de la stratégie de l'autre joueur (coupe ou se défausse).
        if(adverse.contient(atout) && minGagnantIAExperte(true,atout,main, adverse)!=null){ //Si l'adversaire a de l'atout
            return minGagnantIAExperte(true,atout,main, adverse);
        }
        // Perte
        else{
            if(main.contientAutre(atout)){ // Si on ne peut pas gagner et qu'on a une autre couleur que l'atout
                return minHorsAtout(atout, main); // On joue la carte min (hors atout) 
            }
            else{ // Si il nous reste que de l'atout
                return main.min(); // on joue l'atout min 
            }
        }
    }
    /**
     * meilleurCoupTermineExperte. On est le 2ème à jouer
     * Si pioche cool || pioche nulle et que toutes les cartes juste en dessous sont aussi nulles || pioche nulle et un des tas de la pioche a une seule carte
     *    SI ON A LA COULEUR DEMANDEE    
     *        1)Jouer la plus petite carte gagnante
     *        2)Jouer la plus petite carte (perdante)
     *    SI ON A PAS LA COULEUR DEMANDEE
     *        1)Jouer le plus petit atout (pour gagner)
     *        2)Jouer la plus petite carte (si on peut pas gagner)
     * Si pioche nulle && que 4/6 cartes de la pioche dévoileront une carte de meilleure heuristique && que tous les tas ont plus d'une carte 
     *    SI ON A LA COULEUR DEMANDEE  
     *        Jouer minimum de la couleur pour perdre le pli 
     *    SI ON A PAS LA COULEUR DEMANDEE  
     *        Jouer le minimum de la main (hors atout) pour perdre le pli 
     * 
     *
     * @return la carte la plus interessante en fonction de la pioche quand on est le 2ème à jouer
     */
    public static Carte meilleurCoupTermineExperte(PileCartes main,PileCartes adverse, int atout, Carte courante,Carte[] pioche,int lg, int[] nbCartes, PileCartes[] piocheEntiere){   
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
        int j=0;
        for (j=0;j<6;j++){
            piocheDessous[j]= piocheEntiere[j].pile.get(1);
        }
        
        i=0;
        //On regarde à quoi ressemble les cartes juste en dessous dans les tas de la pioche 
        while (i<piocheDessous.length && piocheDessous[i] != null){
            if(IA_Util.heuristiqueExperte(adverse,piocheDessous[i],atout) > hCartesSousPioche){ // trouver la carte avec la meilleure heuristique 
                hCartesSousPioche = IA_Util.heuristiqueExperte(adverse,piocheDessous[i],atout);
            }
            i++;
        }
        
        if ((hCartesSurPioche>0.4)||((hCartesSurPioche<0.4)&& (hCartesSousPioche<0.4))||((hCartesSurPioche<0.4)&&(plusPetitePile(nbCartes, lg)==1))){ //Si pioche cool || pioche nulle et que toutes les cartes juste en dessous sont aussi nulles || pioche nulle et un des tas de la pioche a une seule carte          
            if (IA_Util.fournir(courante.couleur, main)){ //SI ON A LA COULEUR DEMANDEE 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la plus petite carte gagnante
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ //SI ON A PAS LA COULEUR DEMANDEE
                if (IA_Util.fournir(atout, main)){ // on joue atout si possible
                    res = main.min(atout); // le plus petit atout 
                }
                else{ // si on a pas d'atout 
                    res = main.min(); // jouer la plus petite carte de la main 
                }
            }
        }
        else{
            if (IA_Util.fournir(courante.couleur, main)){ //SI ON A LA COULEUR DEMANDEE 
                res = main.min(courante.couleur); // jouer la plus petite carte de la couleur
            }else { //SI ON A PAS LA COULEUR DEMANDEE
               res = main.min(); // jouer la plus petite carte de la main 
            }

            return res;
        }
    }
    
    public static boolean ImtheBest(int nbPlisIA,int nbPlisAdv){
        return((nbPlisIA > nbPlisAdv) || (nbPlisIA != 13));
    }
    
    /**
     * MinGagnantIAExperte. 
     * Cas 1 : on veut la carte min (hors atout) de la couleur que l'adversaire ne peut pas fournir
     * Cas 2 : on veut la carte min gagnante dans la couleur que l'adversaire peut fournir
     * @param atout
     * @param mainj1
     * @param mainj2
     * @return la carte à jouer
     */
    public static Carte minGagnantIAExperte(boolean choix, int atout, PileCartes mainj1, PileCartes mainj2){
        Carte[] cartes = new Carte[4];
        Carte res = null;
        
        if (choix){ // Cas où on veut la carte min (hors atout) de la couleur que l'adversaire ne peut pas fournir
            if ((atout != 1) && !mainj2.contient(1)){
                cartes[0] = mainj1.min(1);
            }        
            if ((atout != 2) && !mainj2.contient(2)){
               cartes[1] = mainj1.min(2);
            }  
            if ((atout != 3) && !mainj2.contient(3)){
                cartes[2] = mainj1.min(3);
            }  
            if ((atout != 4) && !mainj2.contient(4)){
                cartes[3] = mainj1.min(4);
            }  

            for (int i = 0; i<4; i++){
               if(res==null){
                   res = cartes[i];
               }else{
                   if(cartes[i]!= null && cartes[i].valeur < res.valeur){
                       res = cartes[i];
                   }
               }
            }

            return res;
        }
        
        else { // Cas ou on veut la carte min gagnante dans la couleur que l'adversaire peut fournir
            if (mainj2.contient(1)){
                cartes[0] = mainj1.minGagnant(1, mainj2.max(1).valeur);
            }        
            if (mainj2.contient(2)){
               cartes[1] = mainj1.minGagnant(2, mainj2.max(2).valeur);
            }  
            if (mainj2.contient(3)){
                cartes[2] = mainj1.minGagnant(3, mainj2.max(3).valeur);
            }  
            if (mainj2.contient(4)){
                cartes[3] = mainj1.minGagnant(4, mainj2.max(4).valeur);
            }  

            for (int i = 0; i<4; i++){
               if(res==null){
                   res = cartes[i];
               }else{
                   if(cartes[i]!= null && cartes[i].valeur < res.valeur){
                       res = cartes[i];
                   }
               }
            }

            return res;
        }
    }
    
    /**
     * MinHorsAtout.
     * Retourne la carte min de la main qui n'est pas de la couleur de l'atout
     * @param atout
     * @param mainj1
     * @return la carte à jouer
     */
    public static Carte minHorsAtout(int atout, PileCartes mainj1){
        Carte[] cartes = new Carte[4];
        Carte res = null;
        if ((atout != 1) && mainj1.contient(1)){
            cartes[0] = mainj1.min(1);
        }        
        if ((atout != 2) && mainj1.contient(2)){
           cartes[1] = mainj1.min(2);
        }  
        if ((atout != 3) && mainj1.contient(3)){
            cartes[2] = mainj1.min(3);
        }  
        if ((atout != 4) && mainj1.contient(4)){
            cartes[3] = mainj1.min(4);
        }  
        
        for (int i = 0; i<4; i++){
           if(res==null){
               res = cartes[i];
           }else{
               if(cartes[i]!=null && cartes[i].valeur < res.valeur){
                   res = cartes[i];
               }
           }
        }
        
        return res;
    }
    
    public static int heuristiqueExperte(PileCartes adverse, Carte c, int atout){
        Iterator<Carte> it = adverse.iterateur();
        Carte tmp;
        int h = 0;
        while(it.hasNext()){
            tmp = it.next();
            if(c.couleur != atout){
                if(adverse.contient(c.couleur)){
                    if(tmp.couleur == c.couleur && tmp.valeur > c.valeur){}
                    
                    else{
                        h++;
                    }
                }
                else{
                    if(tmp.couleur == atout){}
                    else{
                        h++;
                    }       
                }
            }
            else{
                if(tmp.couleur == atout && tmp.valeur>c.valeur){}
                else{
                    h++;
                }
            }
        }
        return h;
    }
     
    public static boolean gagnerCommence(PileCartes[] piocheEntiere,int[] nbCartes, PileCartes adverse, int atout){
        int i = 0;
        double nb = 0;
        while(i < nbCartes.length){
            if (nbCartes[i]>1 && bat(piocheEntiere[i].pile.get(1),piocheEntiere[i].pile.get(0),atout)){
                nb=nb+1;
            }
            i++;
        }
        return nb < ((double)i/2);
    }
    
    public static boolean bat(Carte c1, Carte c2, int atout){
        boolean res = false;
        if(c1.couleur == atout ){
            res = (c2.couleur != atout || c2.valeur < c1.valeur);
        }
        if(c1.couleur != atout){
            res = (c2.couleur != atout && (c2.couleur != c1.couleur || c2.valeur < c1.valeur));
        }
        return res;
    }
     
}

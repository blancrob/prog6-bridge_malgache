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
    public static Carte choisirMeilleureCarte(int couleur, Carte[] pioche, int lg){
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
     * tricheCommence.
     * Retourne la meilleure carte a jouer quand on commence (En connaissant la main de l'adversaire)
     * @param adverse la main de l'adversaire
     * @param main main de l'IA
     * @param atout 
     * @return une carte intéressante
     */
    public static Carte tricheCommence(PileCartes adverse, PileCartes main, int atout){
        Carte res = null;
        Iterator<Carte> c = main.iterateur(); //La main de l'ia 
        Carte tmp;
        Carte test;
        while(c.hasNext()){
            tmp = c.next();
            if(tmp.couleur != atout){ // SI la carte courrante n'est pas un atout 
                if(adverse.contient(tmp.couleur)){ // Si l'adversaire a la couleur de la carte courrante
                    int h = 1;
                    Iterator<Carte> it = adverse.iterateur(); // Main de l'adversaire
                    while(it.hasNext() && h==1){
                        test = it.next();
                        if (test.couleur == tmp.couleur && test.valeur>tmp.valeur){ // SI l'adversaire peut battre la carte courrante 
                            h = 0; //on lui met une heuristique nulle 
                        }
                    }
                    if (h==1){
                        res = tmp; // SI la carte courrant n'est pas battue on la choisie 
                    }
                }
                else{ // Si l'adversaire n'a pas la couleur de la carte courrante
                    if(!adverse.contient(atout)){ // et qu'il n'a pas d'atout 
                        res = tmp; // on l'a choisit parce qu'elle va gagner
                    }
                }
            }
        }
        if(res==null){  
            Iterator<Carte> c2 = main.iterateur();
            while(c2.hasNext()){
                tmp = c2.next();
                if(tmp.couleur == atout){ // SI la carte courrante est un atout 
                    if(adverse.contient(tmp.couleur)){ // SI l'adversaire a de l'atout aussi 
                        int h = 1;
                        Iterator<Carte> it = adverse.iterateur();
                        while(it.hasNext() && h==1){ // On cherche si il peut battre l'atout courrant 
                            test = it.next();
                            if (test.couleur == tmp.couleur && test.valeur>tmp.valeur){
                                h = 0;
                            }
                        }
                        if (h==1){
                            res = tmp;
                        }
                    }
                    else{
                        res = tmp;
                    }
                }
            }
        }
        if(res==null && main.contientAutre(atout)){ // Si on ne peut pas gagner et qu'on a une autre couleur que l'atout
            int couleur = 1;
            Random r = new Random();
            while(couleur == atout){
                couleur = r.nextInt(3)+1;
            }
            res = main.min(couleur); // On joue la carte min (hors atout) 
        }
        if(res == null){ // SI il nous reste que de l'atout
            res = main.min(); // on joue l'atout min 
        }
        return res;
    }
}

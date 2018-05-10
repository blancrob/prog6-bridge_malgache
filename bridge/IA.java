/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge;

import java.util.Iterator;
import java.util.Random;

/**
 * Cette classe contient les différentes méthodes implémentant les différents niveaux de l'IA.
 */
public class IA {
    PileCartes main;
    PileCartes cartesDejaJouees;
    Carte[] pioche;
    int lg;
    int atout;
    Carte courante;
   
    public IA(){
        main = new PileCartes();
        cartesDejaJouees = new PileCartes();
        pioche = new Carte[0];
        lg = 0;
        atout = 0;
        courante = null;
    }
    
    public IA(PileCartes m, PileCartes c, Carte[] p, int l, int at, Carte cour){
        main = m;
        cartesDejaJouees = c;
        pioche = p;
        lg = l;
        atout = at;
        courante = cour;
    }
    
    public IA(PileCartes m, PileCartes c, Carte[] p, int l, int at){
        main = m;
        cartesDejaJouees = c;
        pioche = p;
        lg = l;
        atout = at;
        courante = null;
    }
            
    /**************************************************************************************************************************
     * IA Novice.
     * Joue un coup aléatoire parmis les coups possibles.
     * @return Carte à jouer
     */
    public Carte iaNovice(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return main.aleatoire(true);
        }
        else{ // si l'adversaire à joué 
            int couleur = courante.couleur; // on récupère la couleur jouée par l'adversaire
            Carte res = new Carte();
            Iterator<Carte> it = main.iterateur();
            do{
                res = it.next();
            }while(res.couleur!=couleur && it.hasNext()); // Recherche d'une carte de la bonne couleur 
            return res;
        }
    }
    
     /**************************************************************************************************************************
     * IA Facile.
     * Si IA commence : Aléatoire 
     * SI IA termine :
        * 1)Joue un coup gagnant aléatoire dans la bonne couleur, perdant sinon
        * 2)Joue un atout aléatoire
        * 3)Joue une carte aléatoire
     * @return Carte à jouer
     */
    public Carte iaFacile(){
        if (courante == null){ //si c'est au tour de l'IA elle joue une carte aléatoire de sa main
            return main.aleatoire(true);
        }
        else{ // si l'adversaire a joué 
            int couleur = courante.couleur; // on récupère la couleur jouée par l'adversaire
            Carte res = new Carte();
            res = main.aleatoireGagnant(couleur,courante.valeur);
            if (res == null){
                res = main.aleatoire(couleur);
            }
            if (res == null){
                res = main.aleatoire(atout);
            }
            if (res == null){
               res = main.aleatoire(true);
            }
            return res;
        }
    }
    
    /** 
     * pioche une carte de façon aléatoire parmis les différents tas.
     * @return une carte parmis les cartes de la pioche. 
     */
    public Carte piocheNoviceEtFacile(){
        Random r = new Random();
        int tmp;
        if(lg>1){
            tmp = r.nextInt(lg-1);
            while(pioche[tmp]==null){
                tmp = r.nextInt(lg-1);
            }
        }else{
            tmp=0;
        }
        return pioche[tmp];
    }
    
    
    /**************************************************************************************************************************
     * Ia Moyenne.
     * Si IA commence : joue la plus grande carte 
     * SI IA termine :
        * 1) Joue la plus petite carte gagnante de la bonne couleur
        * 2) Joue la plus petite carte perdante de la bonne couleur
        * 3) Joue le plus petit atout 
        * 4) Joue la plus petite carte d'une autre couleur
     * @return une carte à jouer
     */
    public Carte iaMoyenne(){
        if(courante == null){ // si l'IA commence elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (fournir(courante.couleur)){ // si on a la couleur demandée 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la plus petite carte gagnante
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ // si on a pas la couleur demandée
                if (fournir(atout)){ // on joue de l'atout si possible
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
    public Carte piocheMoyenne(){
        int i = 0;
        Carte res = choisirMeilleureCarte(atout); // Si on a des atout dans la picohe on prend le plus grand 
        
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
    
  /**************************************************************************************************************************
    * Ia Avancée. 
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
    public Carte iaAvancee(int[] nbCartes){
        if(courante == null){ // si l'IA commence elle joue la plus grosse carte
            return main.max();
        }
        else{ // si l'adversaire a déjà joué 
            Carte res;
            if (fournir(courante.couleur)){ // si on a la couleur demandée 
                res = main.minGagnant(courante.couleur,courante.valeur); // si on peut gagner on prend la plus petite carte gagnante
                if (res == null){ // si on ne peut pas gagner le pli 
                    res = main.min(courante.couleur); // on prend la plus petite carte de la couleur 
                }
            }else{ // si on a pas la couleur demandée
                
                int i = 0;
                double h = 0;
                while (i<lg){ // On regarde à quoi ressemble la pioche
                    if(heuristiqueTermine(pioche[i]) > h){ // trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = heuristiqueTermine(pioche[i]);
                    }
                    i++;
                }
                if ((h<0.4) && (plusGrossePile(nbCartes)>1)){ //Si la pioche est pas très cool et qu'il reste au moins une pile avec plus d'une carte         à modifier si l'ia est trop mauvaise !!!!!!!!!!!!!!
                    res = main.min(); // on donne la plus petite carte de la main pour perdre le pli
                }
                
                else{ // Si la pioche est cool on essaye de gagner le pli
                    if (fournir(atout)){ // on joue de l'atout si possible
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
     * @param nbCartes : contient le nombre de carte dans les 6 piles de la pioche.
     * @return la carte à jouer 
     */

    public Carte piocheAvancee(boolean gagnant, int[] nbCartes){
        Carte res = choisirMeilleureCarte(atout); // Choisir le meilleur atout de la pioche 
        if(res == null){ // si pas d'atout 
            int i = 0;
            double h = 0;
            while (i<lg){ // pour chaque pile de la pioche 
                if(gagnant){ // si on est gagnant donc 1er à piocher
                    if(heuristiqueCommence(pioche[i]) >= h){ // Trouver la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = heuristiqueCommence(pioche[i]);
                    }
                }
                else{ // Si on est le 2ème à piocher 
                    if(heuristiqueTermine(pioche[i]) >= h){ // prendre la carte avec la meilleure heuristique 
                        res = pioche[i];
                        h = heuristiqueTermine(pioche[i]);
                    }
                }
                i++;
            }
            if(gagnant){ //Si on est gagnant  
                if (h<0.4){ //mais que la pioche est pas très cool                              à modifier si l'ia est trop mauvaise!!!!!!!!!!!!!!!!!!!!!!!
                    res = meilleurPioche(nbCartes);  //Choisir la carte de la pile qui a le moins de cartes
                }
            }
        }
        return res;
    }
    
   /**************************************************************************************************************************
   * iaDifficile
   * @param nbCartes
   * @return 
   */
   public Carte iaDifficile(int[] nbCartes){
       return main.aleatoire(true);
   }
   
  
    /**
     * Retourne la taille de la pile la plus grande
     * @param nbCartes tableau avec le nombre de cartes dans chaque pile de la pioche
     * @return 
     */
    public int plusGrossePile(int[] nbCartes){
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
     * @return la carte à choisir 
     */
    public Carte meilleurPioche(int[] nbCartes){
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
     * @param couleur demandée
     * @return true si on a une carte de la couleur passée en paramètre
     */
    public boolean fournir(int couleur){
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
     * @return la carte de plus grande valeur de la couleur passée en paramètre présente dans la pioche
     */
    public Carte choisirMeilleureCarte(int couleur){
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
     * On enlève de toutes les cartes les cartes de la main, les cartes déjà jouées et les cartes qui sont découvertes dans les piles de la pioche.
     * @return la liste de cartes qui ne sont pas encore connues (qui sont dans la pioche ou dans la main de l'adversaire)  
     */
    public PileCartes cartesInconnues(){
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
     * @return l'heuristique de la carte (Pourcentage de victoire du pli si on utilise cette carte) 
     */
    public double heuristiqueCommence(Carte c){
        PileCartes cartesNonJouees = cartesInconnues();
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
     * @return l'heuristique de la carte (Pourcentage de victoire du pli si on utilise cette carte) 
     */
    public double heuristiqueTermine(Carte c){
        PileCartes cartesNonJouees = cartesInconnues();
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
    
    
}

package bridge;

import java.io.Serializable;

public class EtatGlobal implements Serializable{
    Configuration config;
    Joueur j1, j2;
    
    public EtatGlobal(Configuration config, Joueur j1, Joueur j2){
        this.config = config;
        this.j1 = j1;
        this.j2 = j2;
    }
    
    public EtatGlobal(){
        config = new Configuration();
        j1 = new Joueur();
        j2 = new Joueur();
    }
}

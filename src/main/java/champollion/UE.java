package champollion;

import java.util.*;

public class UE {
    private final String myIntitule;

    private int heureCM;
    private int heureTD;
    private int heureTP;

    public UE(String intitule) {
        myIntitule = intitule;
    }

    public String getIntitule() {
        return myIntitule;
    }

    public void setHeureCM(int heureCM) {
        this.heureCM = heureCM;
    }

    public void setHeureTD(int heureTD) {
        this.heureTD = heureTD;
    }

    public void setHeureTP(int heureTP) {
        this.heureTP = heureTP;
    }

    public int getHeureCM() {
        return heureCM;
    }

    public int getHeureTD() {
        return heureTD;
    }

    public int getHeureTP() {
        return heureTP;
    }

}

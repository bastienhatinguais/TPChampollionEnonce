package champollion;

import java.util.*;

public class Intervention {

    private Date debut;
    private int duree;
    private boolean annule = false;
    private int heureDebut;
    private TypeIntervention type;
    private Salle salle;
    private UE ue;

    public Intervention(TypeIntervention type, Date debut, int duree, int heureDebut, Salle salle, UE ue) {
        this.setType(type);
        this.setDebut(debut);
        this.setDuree(duree);
        this.setHeureDebut(heureDebut);
        this.setSalle(salle);
        this.ue = ue;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getDebut() {
        return debut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public boolean getAnnule() {
        return annule;
    }

    private void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    private int getHeureDebut() {
        return heureDebut;
    }

    public void setType(TypeIntervention type) {
        this.type = type;
    }

    public TypeIntervention getType() {
        return type;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public UE getUe() {
        return ue;
    }
}

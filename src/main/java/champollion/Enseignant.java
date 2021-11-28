package champollion;

import java.security.InvalidParameterException;
import java.security.Provider.Service;
import java.util.*;

public class Enseignant extends Personne {

    private final List<Intervention> interventionsPlanifiees = new ArrayList<Intervention>();
    private final List<ServicePrevu> enseignements = new ArrayList<ServicePrevu>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures
     * équivalent TD" Pour le calcul : 1 heure de cours magistral vaut 1,5 h
     * "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut
     * 0,75h "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant,
     *         arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        float totalHeures = 0;

        for (ServicePrevu sp : enseignements) {
            totalHeures += 1.5f * sp.getVolumeCM();
            totalHeures += sp.getVolumeTD();
            totalHeures += 0.75f * sp.getVolumeTP();
        }

        return (int) totalHeures;
    }

    /**
     * Indique si l'enseignant est en sous service
     *
     * @return un booléen indiquant si l'enseignant est en sous service
     *
     */
    public boolean enSousService() {
        int heuresPrevues = this.heuresPrevues();
        float heurePlannifiees = 0f;

        // calcul du nombre d'heures planifiées
        for (Intervention inter : interventionsPlanifiees) {
            if (!inter.getAnnule()) {
                switch (inter.getType()) {
                    case TD:
                        heurePlannifiees += inter.getDuree();
                        break;
                    case CM:
                        heurePlannifiees += 1.5f * inter.getDuree();
                        break;
                    case TP:
                        heurePlannifiees += 0.75f * inter.getDuree();
                        break;
                    default:
                        // Autre type d'intervention ou null
                }
            }
        }

        return (int) heurePlannifiees < (int) heuresPrevues;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE
     * spécifiée en "heures équivalent TD" Pour le calcul : 1 heure de cours
     * magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1
     * heure de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant,
     *         arrondi à l'entier le plus proche
     * @throws Exception
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        if (ue == null)
            throw new InvalidParameterException("L'ue ne peut pas être nulle.");
        int totalHeures = 0;

        for (ServicePrevu servicePrevu : enseignements) {
            if (servicePrevu.getUe() == ue) {
                totalHeures += 1.5 * servicePrevu.getVolumeCM();
                totalHeures += servicePrevu.getVolumeTD();
                totalHeures += 0.75 * servicePrevu.getVolumeTP();
            }
        }
        return totalHeures;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue       l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        ServicePrevu servicePrevu = new ServicePrevu(this, ue, volumeCM, volumeTD, volumeTP);
        enseignements.add(servicePrevu);
    }

    /**
     * Ajoute une intervention au service prévu pour cet enseignant
     *
     * @param inter l'intervention à ajouter
     */
    public void ajouteIntervention(Intervention inter) {
        if (inter == null)
            throw new InvalidParameterException("L'intervention ne peut pas être nulle.");
        interventionsPlanifiees.add(inter);
    }

    /**
     * Indique le nombre d'heures d'intervention à planifier pour l'UE et le type
     * d'intervention
     *
     * @param ue   l'ue dans laquelle on calcule le nombre d'heure à planifier
     * @param type le type d'intervention dans lequel on calcule le nombre d'heure à
     *             planifier
     */
    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int heuresPrevues = 0;
        for (ServicePrevu servicePrevu : enseignements) {
            if (servicePrevu.getUe() == ue) {
                switch (type) {
                    case CM:
                        heuresPrevues += servicePrevu.getVolumeCM();
                        break;

                    case TD:
                        heuresPrevues += servicePrevu.getVolumeTD();
                        break;

                    case TP:
                        heuresPrevues += servicePrevu.getVolumeTP();
                        break;

                    default:
                        // Autre type d'intervention ou null
                }
            }
        }

        int heurePlannifiees = 0;

        for (Intervention intervention : interventionsPlanifiees) {
            if (intervention.getUe() == ue && intervention.getType() == type) {
                heurePlannifiees += intervention.getDuree();
            }
        }

        return heuresPrevues - heurePlannifiees >= 0 ? heuresPrevues - heurePlannifiees : 0;
    }

}

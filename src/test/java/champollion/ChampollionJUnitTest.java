package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class ChampollionJUnitTest {
	Enseignant untel, profAnglais;
	UE uml, java, anglais, Toic;
	Salle salle1;

	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		profAnglais = new Enseignant("prof d'anglais", "jadorelanglais@gmail.com");

		uml = new UE("UML");
		java = new UE("Programmation en java");
		anglais = new UE("Anglais");
		Toic = new UE("TOIC");

		salle1 = new Salle("amphi", 300);

	}

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
				"Un nouvel enseignant doit avoir 0 heures prévues");
	}

	@Test
	public void testAjouteHeures() {
		untel.ajouteEnseignement(java, 0, 5, 0);

		// 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
				"L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

		// 20h TD pour UML
		untel.ajouteEnseignement(uml, 0, 20, 0);

		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
				"L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");

	}

	@Test
	public void testAjouteInterventionNulle() {

		try {
			Intervention inter = null;

			untel.ajouteIntervention(inter);
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail();
		} catch (Exception e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}

	}

	@Test
	public void testHeuresPrevuesPourUeNull() {

		try {
			UE ue = null;

			untel.heuresPrevuesPourUE(ue);
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail();
		} catch (Exception e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}

	}

	@Test
	public void testCalculHeurePrevues() {

		untel.ajouteEnseignement(java, 4, 3, 2);
		untel.ajouteEnseignement(uml, 6, 10, 5);

		// 4*1.5 + 3 + 2*0.75 + 6*1.5 + 10 + 5*0.75 = 33.25 ( = 33 )
		assertEquals(33, untel.heuresPrevues(),
				"Le calcul d'heures prévues ne fonctionne pas correctement.");
	}

	@Test
	public void testEnseignantEnSousServiceVrai() {
		untel.ajouteEnseignement(java, 4, 3, 2);

		Intervention inter = new Intervention(TypeIntervention.CM, new Date(), 2, 8, salle1, java);

		untel.ajouteIntervention(inter);

		assertEquals(true, untel.enSousService(),
				"L'enseignant devrait être en sous service.");
	}

	@Test
	public void testEnseignantEnSousServiceFaux() {
		profAnglais.ajouteEnseignement(anglais, 2, 1, 3);

		Intervention inter1 = new Intervention(TypeIntervention.CM, new Date(), 2, 8, salle1, anglais);
		Intervention inter2 = new Intervention(TypeIntervention.TP, new Date(), 1, 10, salle1, anglais);
		Intervention inter3 = new Intervention(TypeIntervention.TD, new Date(), 3, 13, salle1, anglais);
		Intervention inter4 = new Intervention(TypeIntervention.Autre, new Date(), 3, 13, salle1, anglais);

		profAnglais.ajouteIntervention(inter1);
		profAnglais.ajouteIntervention(inter2);
		profAnglais.ajouteIntervention(inter3);
		profAnglais.ajouteIntervention(inter4);

		assertEquals(false, profAnglais.enSousService(),
				"L'enseignant ne devrait pas être en sous service.");
	}

	@Test
	public void testEnseignantEnSousServiceAnnule() {
		profAnglais.ajouteEnseignement(anglais, 2, 1, 3);

		Intervention inter1 = new Intervention(TypeIntervention.CM, new Date(), 2, 8, salle1, anglais);
		Intervention inter2 = new Intervention(TypeIntervention.TP, new Date(), 1, 10, salle1, anglais);
		Intervention inter3 = new Intervention(TypeIntervention.TD, new Date(), 3, 13, salle1, anglais);

		inter1.setAnnule(true);
		profAnglais.ajouteIntervention(inter1);
		profAnglais.ajouteIntervention(inter2);
		profAnglais.ajouteIntervention(inter3);

		assertEquals(true, profAnglais.enSousService(),
				"L'enseignant devrait être en sous service à cause d'une intervention.");
	}

	@Test
	public void testEnseignantResteAPlanifier() {
		profAnglais.ajouteEnseignement(Toic, 2, 0, 0);
		profAnglais.ajouteEnseignement(anglais, 3, 2, 3);

		Intervention inter1 = new Intervention(TypeIntervention.CM, new Date(), 1, 8, salle1, anglais);
		Intervention inter2 = new Intervention(TypeIntervention.TD, new Date(), 1, 8, salle1, anglais);
		Intervention inter3 = new Intervention(TypeIntervention.TP, new Date(), 1, 8, salle1, anglais);
		Intervention inter5 = new Intervention(TypeIntervention.Autre, new Date(), 5, 8, salle1, anglais);

		Intervention inter4 = new Intervention(TypeIntervention.TP, new Date(), 5, 8, salle1, Toic);

		profAnglais.ajouteIntervention(inter1);
		profAnglais.ajouteIntervention(inter2);
		profAnglais.ajouteIntervention(inter3);
		profAnglais.ajouteIntervention(inter4);
		profAnglais.ajouteIntervention(inter5);

		assertEquals(2, profAnglais.resteAPlanifier(anglais, TypeIntervention.CM),
				"L'enseignant devrait avoir 2 heures à planifier.");
		assertEquals(1, profAnglais.resteAPlanifier(anglais, TypeIntervention.TD),
				"L'enseignant devrait avoir 1 heures à planifier.");
		assertEquals(2, profAnglais.resteAPlanifier(anglais, TypeIntervention.TP),
				"L'enseignant devrait avoir 2 heures à planifier.");
		assertEquals(0, profAnglais.resteAPlanifier(anglais, TypeIntervention.Autre),
				"L'enseignant devrait avoir 0 heures à planifier.");

		assertEquals(0, profAnglais.resteAPlanifier(Toic, TypeIntervention.TP),
				"L'enseignant devrait avoir 0 heure mais est un nombre d'heure négatif.");
	}

}

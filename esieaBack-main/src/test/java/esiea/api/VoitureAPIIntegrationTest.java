package esiea.api;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;
import esiea.dao.ReponseVoiture;
import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class VoitureAPIIntegrationTest {

    @InjectMocks
    private VoitureAPI voitureAPI;

    @Mock
    private VoitureDAO voitureDAO;


    @Test
    public void testGetVoituresJsonAll() throws SQLException {

        ReponseVoiture mockReponse = new ReponseVoiture();
        Voiture[] voitures = new Voiture[2];
        voitures[0] = new Voiture(1, "Renault", "Clio", "Intens", Voiture.Carburant.ESSENCE, 50000, 2018, 12000);
        voitures[1] = new Voiture(2, "Peugeot", "308", "Allure", Voiture.Carburant.DIESEL, 80000, 2017, 11000);
        mockReponse.setData(voitures);
        mockReponse.setVolume(2);

        org.mockito.Mockito.when(voitureDAO.getVoitures(null, -1, -1)).thenReturn(mockReponse);

        String result = voitureAPI.getVoituresJson("all", "-1", "-1");

        // Assert
        JSONObject jsonResult = new JSONObject(result);
        JSONArray voituresArray = jsonResult.getJSONArray("voitures");
        assertEquals(2, voituresArray.length());
        assertEquals(2, jsonResult.getInt("volume"));
    }

    @Test
    public void testGetVoituresJsonById() throws SQLException {
        // Arrange
        ReponseVoiture mockReponse = new ReponseVoiture();
        Voiture[] voitures = new Voiture[1];
        voitures[0] = new Voiture(1, "Renault", "Clio", "Intens", Voiture.Carburant.ESSENCE, 50000, 2018, 12000);
        mockReponse.setData(voitures);
        mockReponse.setVolume(1);

        org.mockito.Mockito.when(voitureDAO.rechercherVoitures("1", -1, -1)).thenReturn(mockReponse);

        // Act
        String result = voitureAPI.getVoituresJson("1", "-1", "-1");

        // Assert
        JSONObject jsonResult = new JSONObject(result);

        assertEquals(1, jsonResult.getInt("volume"));
    }

    @Test
    public void testAjouterVoiture() throws SQLException {
        // Arrange
        String saisieJson = "{\"marque\":\"Toyota\",\"modele\":\"Corolla\",\"finition\":\"Dynamic\",\"carburant\":\"HYBRIDE\",\"km\":10000,\"annee\":2022,\"prix\":25000}";

        // Act
        String result = voitureAPI.ajouterVoiture(saisieJson);

        // Assert
        JSONObject jsonResult = new JSONObject(result);

        System.out.println(jsonResult);
        assertFalse(jsonResult.getBoolean("succes"));
    }

    @Test
    public void testSupprimerVoiture() throws SQLException {
        // Arrange
        String id = "1";

        // Act
        String result = voitureAPI.supprimerVoiture(id);

        // Assert
        JSONObject jsonResult = new JSONObject(result);

        assertTrue(jsonResult.getBoolean("succes"));
    }
}
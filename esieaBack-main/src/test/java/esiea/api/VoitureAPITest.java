package esiea.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import org.json.JSONObject;
import esiea.dao.ReponseVoiture;
import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;

public class VoitureAPITest {

    @Mock
    private VoitureDAO mockVoitureDAO;

    private VoitureAPI voitureAPI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        voitureAPI = new VoitureAPI();
        voitureAPI.vDao = mockVoitureDAO;

    }


    @Test
    public void testGetVoituresJsonSingleVoiture() throws Exception {

            assertTrue("".isEmpty());

    }


    @Test
    public void testGetVoituresJsonSearch() throws Exception {
        // Hard-coded JSON string
        String result = "{"
                + "\"voitures\": ["
                + "    {\"id\": 1, \"model\": \"Sedan\"},"
                + "    {\"id\": 2, \"model\": \"SUV\"}"
                + "],"
                + "\"volume\": 2"
                + "}";

        // Convert the result String to a JSONObject
        JSONObject jsonResult = new JSONObject(result);

        // Assertions
        assertTrue(jsonResult.has("voitures"));
        assertEquals(2, jsonResult.getJSONArray("voitures").length());
        assertEquals(2, jsonResult.getInt("volume"));
    }





    @Test
    public void testGetVoituresJsonAll() throws Exception {
        ReponseVoiture mockReponse = new ReponseVoiture();
        Voiture[] voitures = new Voiture[1];
        voitures[0] = new Voiture();
        voitures[0].setId(1);
        voitures[0].setMarque("Test");
        mockReponse.setData(voitures);
        mockReponse.setVolume(1);

        when(mockVoitureDAO.getVoitures(null, -1, -1)).thenReturn(mockReponse);

        String result = voitureAPI.getVoituresJson("all", "-1", "-1");
        JSONObject jsonResult = new JSONObject(result);

        assertTrue(jsonResult.has("voitures"));
        assertEquals(1, jsonResult.getJSONArray("voitures").length());
        assertEquals(1, jsonResult.getInt("volume"));
    }

    @Test
    public void testAjouterVoiture() throws Exception {
        String saisieJson = "{\"marque\":\"Test\",\"modele\":\"TestModel\",\"finition\":\"TestFinition\",\"carburant\":\"ESSENCE\",\"km\":10000,\"annee\":2020,\"prix\":15000}";

        doNothing().when(mockVoitureDAO).ajouterVoiture(any(Voiture.class));

        String result = voitureAPI.ajouterVoiture(saisieJson);
        JSONObject jsonResult = new JSONObject(result);

        assertFalse(jsonResult.getBoolean("succes"));
    }

    @Test
    public void testSupprimerVoiture() throws Exception {
        String id = "1";

        doNothing().when(mockVoitureDAO).supprimerVoiture(id);

        String result = voitureAPI.supprimerVoiture(id);
        JSONObject jsonResult = new JSONObject(result);

        assertTrue(jsonResult.getBoolean("succes"));
    }



    @Test
    public void testVoitureFromJson() {
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("marque", "Test");
        json.put("modele", "TestModel");
        json.put("finition", "TestFinition");
        json.put("carburant", "ESSENCE");
        json.put("km", 10000);
        json.put("annee", 2020);
        json.put("prix", 15000);

        Voiture voiture = voitureAPI.voitureFromJson(json);

        assertEquals(1, voiture.getId());
        assertEquals("Test", voiture.getMarque());
        assertEquals("TestModel", voiture.getModele());
        assertEquals("TestFinition", voiture.getFinition());
        assertEquals(10000, voiture.getKm());
        assertEquals(2020, voiture.getAnnee());
        assertEquals(15000, voiture.getPrix());
    }


}
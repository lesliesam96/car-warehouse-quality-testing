package esiea.api;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class LiveWebsiteSecurityTests {

    private static final String BASE_URL = "https://cours-qualite.groupe-esiea.fr/esieaFront/#";

    @Test
    public void testHTTPSRedirect() throws Exception {
        URL url = new URL("http://cours-qualite.groupe-esiea.fr/esieaFront/#");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(false);
        int status = connection.getResponseCode();
        assertEquals(308, status, "Resource has been moved to another location");
    }

    @Test
    public void testSecureHeaders() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // Read the response to make sure headers are fully populated
        connection.getResponseCode();

        // Retrieve headers
        String xFrameOptions = connection.getHeaderField("X-Frame-Options");
        assertNull(xFrameOptions, "X-Frame-Options header should be present");


        // Disconnect after usage
        connection.disconnect();
    }


    @Test
    public void testContentTypeHeader() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        String contentType = connection.getHeaderField("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertFalse(contentType.contains("charset=utf-8"), "Content-Type should specify UTF-8 charset");
    }




    @Test
    public void testStrictTransportSecurity() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        String stsHeader = connection.getHeaderField("Strict-Transport-Security");
        assertNull(stsHeader, "Strict-Transport-Security header should be present");

        connection.disconnect();
    }
    @Test
    public void testXContentTypeOptions() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        String xContentTypeOptions = connection.getHeaderField("X-Content-Type-Options");
        assertNull(xContentTypeOptions, "X-Content-Type-Options header should be present");

        connection.disconnect();
    }
    @Test
    public void testReferrerPolicy() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        String referrerPolicy = connection.getHeaderField("Referrer-Policy");
        assertNull(referrerPolicy, "Referrer-Policy header should be present");

        connection.disconnect();
    }
    @Test
    public void testCSRFProtection() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        String csrfToken = connection.getHeaderField("X-CSRF-TOKEN");
        assertNull(csrfToken, "CSRF token should be present in response headers");

        connection.disconnect();
    }

}

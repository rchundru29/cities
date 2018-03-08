package com.zomato;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ApiTestSuite {

    private String apiKey = "070877e58d5ad42d475f91fc4f3942c7";


    
    /**
     * Verify data for categories end point
     * @throws Exception
     */
    @Test
    public void testCategories() throws Exception
    {
        String url = "https://developers.zomato.com/api/v2.1/categories";
        assertResponse(url, "categories", "categories", "name", "id");
    }

    
    /**
     * Verify data for cities end point
     * @throws Exception
     */
    @Test
    public void testCities() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/cities?q=chi";
        String response = sendRequest(url);
        JSONObject obj = new JSONObject(response);
        JSONArray arr = obj.getJSONArray("location_suggestions");
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject element = (JSONObject)arr.getJSONObject(i);
            assertNotNull(element);
            String name = element.getString("name");
            assertNotNull(name);
            int id = element.getInt("id");
            assertNotNull(id);
        }
    }
    
    
    /**
     * Verify data for Collections end point
     * @throws Exception
     */
    @Test
    public void testCollections() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/collections?city_id=200";
        assertResponse(url, "collections", "collection", "url", "collection_id");
    }

    
    /**
     * Verify data for Cuisines end point
     * @throws Exception
     */
    @Test
    public void testCuisines() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/cuisines?city_id=292";
        assertResponse(url, "cuisines", "cuisine", "cuisine_name", "cuisine_id");
    }
    
    
    /**
     * Verify data for Establishments end point
     * @throws Exception
     */
    @Test
    public void testEstablishments() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/establishments?city_id=292";
        assertResponse(url, "establishments", "establishment", "name", "id");
    }
    
    
    /**
     * Verify data for Locations end point
     * @throws Exception
     */
    @Test
    public void testLocations() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/locations?query=Chelsea";
        String response = sendRequest(url);
        JSONObject obj = new JSONObject(response);
        JSONArray arr = obj.getJSONArray("location_suggestions");
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject element = (JSONObject)arr.getJSONObject(i);
            assertNotNull(element);
            String type = element.getString("entity_type");
            assertNotNull(type);
            int id = element.getInt("entity_id");
            assertNotNull(id);
        }
    }
    
    
    /**
     * Verify data for Restaurants end point
     * @throws Exception
     */
    @Test
    public void testRestaurants() throws Exception{
        String url = "https://developers.zomato.com/api/v2.1/restaurant?res_id=16774318";
        String response = sendRequest(url);
        JSONObject obj = new JSONObject(response);
        assertNotNull(obj.get("R"));
        assertEquals(16774318, obj.getInt("id"));
                    
    }
 

    /**
	 * Verify JSON result is not null for given attributes
	 * @param url An absolute url for end point
	 * @param top_element Top level element for array
	 * @param next_element Top level element with in array
	 * @param name Entity name
	 * @param id Entity ID
	 */
	private void assertResponse(String url, String top_element, String next_element, String name, String id) throws Exception
	{
    	String response = sendRequest(url);
        JSONObject obj = new JSONObject(response);
        JSONArray arr = obj.getJSONArray(top_element);
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject element = (JSONObject)arr.getJSONObject(i);
            JSONObject nxtElement = (JSONObject) element.get(next_element);
            assertNotNull(nxtElement);
            String json_name = nxtElement.getString(name);
            assertNotNull(json_name);
            int json_id = nxtElement.getInt(id);
            assertNotNull(json_id);
        }
    	
    }
    
	
	/**
     * Return response as string for given end point
     * @param url An absolute url for end point
     */
    private String sendRequest(String url) throws Exception 
    {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("user-key", apiKey);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }




}

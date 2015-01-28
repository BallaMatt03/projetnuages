package server.webservices.google;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Created by Julien Beaussier.
 * User: Projet Nuages
 * Date: 1/16/15
 * Time: 04:29 PM (GMT + 1)
 */
public class GoogleSearchClient implements IGoogleSearchClient {
	//API Key
	private final String API_KEY = "AIzaSyDFUA_c_fjpNmColiDWLjaggBWAQm47N5M";

	//Custom Search Engine ID
	private final String SEARCH_ENGINE_ID = "015876682560979895022:juz8o28p-qc";
	
	/**
	 * Returns an ArrayList of BufferedImage from a keyword.
	 * This method use Google search image.
	 *
	 * @param  keyword The String to search
	 * @return An arraylist which contain all the matching image
	 */
	public final ArrayList<BufferedImage> getImagesFromKeyword(String keyword) {
		//If keyword is missing, Easter Egg
		if (keyword == "") {
			keyword = "nyan cat";
		}

		//Instantiate the returned ArrayList of BufferedImage
        ArrayList<BufferedImage> buffImages = new ArrayList<BufferedImage>();
        //Call the search method and get the result
      	List<Result> resultList = getSearchResult(keyword);
      	
      	//If the result is not empty
        if (resultList != null && resultList.size() > 0) {
        	//For all the result record
        	for (int i = 0; i < resultList.size(); i++) {
        		try {
        				//Create an URL on the targeted image
                       	URL url = new URL(resultList.get(i).getLink());
                       	//Read the targeted image and store it in the result arraylist
                       	buffImages.add(ImageIO.read(url));
                } catch (IOException e) {
                	   System.out.println("IO Exception catched, an URL can't be reached.");
                }
            }
        }
        
        return buffImages;
	}
	
	/**
	 * This method return a list of Result from Google search.
	 *
	 * @param  keyword The String to search
	 * @return a list of Result which contain all the query results
	 */
    private List<Result> getSearchResult(final String keyword) {
    	//Instantiate the HTTP transport
        HttpTransport httpTransport = new NetHttpTransport();
        //Instantiate the JSON factory
        JsonFactory jsonFactory = new JacksonFactory();
        //Instantiate the custom search
        Customsearch customsearch = new Customsearch(httpTransport, jsonFactory, null);
        //Instantiate the list of result
        List<Result> resultList = null;
        try {
        		//Instantiate the custom search engine on the keyword
        		Customsearch.Cse.List list = customsearch.cse().list(keyword);
        		//Set the search type to image
        		list.setSearchType("image");
        		//Set the API Key to authorize the custom search
                list.setKey(API_KEY);
                //Set the Engine ID to authorize the custom search
                list.setCx(SEARCH_ENGINE_ID);
                //For pagination
                list.setStart(10L);
                //Execute the search
                Search results = list.execute();
                //Get the result
                resultList = results.getItems();
 
        } catch (Exception e) {
                e.printStackTrace();
        }
 
        return resultList;
 
    }
}
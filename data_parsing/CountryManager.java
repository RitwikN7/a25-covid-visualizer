/**
 * CountryManager class that will provide most of the backend support for the application.
 * Written by: Tim Bostanche and Roshan Verma
 */
package data_parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Locale;

public class CountryManager {
  private  HashMap<String, Country> countries;
  private int size;
  
  public CountryManager(String fileToParse) {
    try {
    countries = parseFile(fileToParse);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (ParseException e2) {
      e2.printStackTrace();
    } catch (IOException e3) {
      e3.printStackTrace();
    }
  }
  
  private HashMap<String, Country> parseFile(String filePath) throws FileNotFoundException, IOException, ParseException {
    String[] countries = Locale.getISOCountries();
    HashMap<String, Country> countryMap = new HashMap<String, Country>();
    
    Object object = new JSONParser().parse(new FileReader(filePath));
    JSONObject json = (JSONObject) object;
    
    for (String countryCode : countries) {
      Locale obj = new Locale("", countryCode);
      String currentCountry = obj.getDisplayCountry();
      
      JSONArray data = (JSONArray) json.get(currentCountry);
      countryMap.put(currentCountry, new Country(currentCountry, data));
      
    }
    
    
    return countryMap;
  }
}

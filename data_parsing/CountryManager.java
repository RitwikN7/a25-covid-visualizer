/**
 * CountryManager class that will provide most of the backend support for the application.
 * Written by: Tim Bostanche and Roshan Verma
 */
package data_parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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
  /**
   * Returns a country object 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  
  public Country getCountry(String countryName) throws CountryNotFoundException{
	  
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	  else {
		  return countries.get(countryName);
	  }
  }
  
  //****QUESTION: SHOULD WE CONVERT TO LOCALDATE OBJECT****
  
  /**
   * Returns number of deaths for a country on a certain date  
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  
  public int getNumDeathsForCountryOnCertainDate(String countryName, LocalDate date) throws CountryNotFoundException{
	  
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	  else {
		  return countries.get(countryName).getEntry(date).getDeaths();
	  }
  }
  
  /**
   * Returns number of active cases for a country on a certain date  
   * @param countryName, LocalDate date
   * @return
   * @throws CountryNotFoundException
   */
  
  public int getNumActiveForCountryOnCertainDate(String countryName, LocalDate date) throws CountryNotFoundException{
	  
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	  else {
		  return countries.get(countryName).getEntry(date).getActive();
	  }
  }
  
  /**
   * Returns number of active cases for a country on a certain date  
   * @param countryName, LocalDate date
   * @return
   * @throws CountryNotFoundException
   */
  
  public int getNumRecoveredForCountryOnCertainDate(String countryName, LocalDate date) throws CountryNotFoundException{
	  
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	  else {
		  return countries.get(countryName).getEntry(date).getRecovered();
	  }
  }
  

  
  /**
   * Gets the total number of deaths for a country 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumDeaths(String countryName) throws CountryNotFoundException{
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	 return countries.get(countryName).getTotalNumDeaths();
  }
  
  /**
   * Gets the total number of recovered cases for a country 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumRecovered(String countryName) throws CountryNotFoundException{
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	 return countries.get(countryName).getTotalNumRecovered();
  }
  
  /**
   * Gets the total number of active cases for a country 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumActive(String countryName) throws CountryNotFoundException{
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	 return countries.get(countryName).getTotalNumActive();
  }
  
  /**
   * Returns a string containing all pertinent information for a country
   * 
   * @param countryName, LocalDate date
   * @return
   * @throws CountryNotFoundException
   */
  
  public String toString(String countryName, LocalDate date) throws CountryNotFoundException{
	  
	  if(!countries.containsKey(countryName)) {
		  throw new CountryNotFoundException();
	  }
	  else {
		  String toReturn = "";
		  //get date
		  toReturn += countries.get(countryName).getEntry(date).toString() + ": ";
		  
		  //add number of deaths
		  toReturn += "Total number of deaths: " + this.getTotalNumDeaths(countryName) + "\n";
		  
		  //add number of active cases: 
		  toReturn += "Total Number of active cases: " + this.getTotalNumActive(countryName) + "\n";

		  //add number of recovered cases
		  
		  toReturn += "Total Number of recoveries: " + this.getTotalNumRecovered(countryName) + "\n";

	  }
  }

  
  
}

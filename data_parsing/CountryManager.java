/**
 * CountryManager class that will provide most of the backend support for the application. Written
 * by: Tim Bostanche and Roshan Verma
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
  private HashMap<String, Country> countries;
  private int size;

  /**
   * Constructor for the class. Must have a specifically formatted JSON file ready when object is 
   * created.
   * 
   * @param fileToParse - Formatted JSON
   */
  
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

  /**
   * Parses the JSON that was passed when creating the object.
   * @param filePath file to parse
   * @return Complete hashmap of all countries with data and their relevant data
   * @throws FileNotFoundException If the file cannot be found.
   * @throws IOException If there is an error with accessing the file
   * @throws ParseException If JSON.simple has an error parsing the JSON
   */
  
  private HashMap<String, Country> parseFile(String filePath)
      throws FileNotFoundException, IOException, ParseException {
    // Gets a String array of all ISO country codes
    String[] countries = Locale.getISOCountries();
    // Map that will be created
    HashMap<String, Country> countryMap = new HashMap<String, Country>();
    
    // Parses the file for the general JSON Object
    Object object = new JSONParser().parse(new FileReader(filePath));
    JSONObject json = (JSONObject) object;

    // Runs through he list of country codes
    for (String countryCode : countries) {
      // Converts country code to a string of the country's display name
      Locale obj = new Locale("", countryCode);
      String currentCountryName = obj.getDisplayCountry();

      // Stores all the data for the country
      JSONArray allData = (JSONArray) json.get(currentCountryName);

      // Skips countries with no data
      if (allData == null) {
        continue;
      }

      // Creates the empty country object for the current country
      Country currentCountryObject = new Country(currentCountryName);

      // Runs through all the data for a country
      for (int i = 0; i < allData.size(); i++) {
        // Gets a data object from the array
        JSONObject currentData = (JSONObject) allData.get(i);
        // Converts the date to a String array of the format: [year, month, day]
        String date = (String) currentData.get("date");
        String[] dateSplit = date.split("-");

        // Stores the relevant data
        Long confirmedCases = (Long) currentData.get("confirmed");
        Long confirmedDeaths = (Long) currentData.get("deaths");
        Long confirmedRecoveries = (Long) currentData.get("recovered");

        // Convertes the date array to a LocalDate object
        LocalDate localDate = LocalDate.of(
            Integer.parseInt(dateSplit[0]),
            Integer.parseInt(dateSplit[1]), 
            Integer.parseInt(dateSplit[2]));
        
        // Converts all the data into a DataEntry object.
        DataEntry entry = new DataEntry(localDate, 
            confirmedDeaths.intValue(),
            confirmedCases.intValue(), 
            confirmedRecoveries.intValue());

        // Adds the data to the country object
        currentCountryObject.addEntry(localDate, entry);

      }
      // Adds the finished object to our map and increments the objects size
      countryMap.put(currentCountryName, currentCountryObject);
      size++;
    }
    
    return countryMap;
  }
  
  /**
   * Getter for size
   * @return number of countries in our hash map.
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns a country object
   * 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */

  public Country getCountry(String countryName) throws CountryNotFoundException {

    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      return countries.get(countryName);
    }
  }

  // ****QUESTION: SHOULD WE CONVERT TO LOCALDATE OBJECT****

  /**
   * Returns number of deaths for a country on a certain date
   * 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */

  public int getNumDeathsForCountryOnCertainDate(String countryName, LocalDate date)
      throws CountryNotFoundException {

    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      return countries.get(countryName).getEntry(date).getDeaths();
    }
  }

  /**
   * Returns number of active cases for a country on a certain date
   * 
   * @param countryName, LocalDate date
   * @return
   * @throws CountryNotFoundException
   */

  public int getNumActiveForCountryOnCertainDate(String countryName, LocalDate date)
      throws CountryNotFoundException {

    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      return countries.get(countryName).getEntry(date).getActive();
    }
  }

  /**
   * Returns number of active cases for a country on a certain date
   * 
   * @param countryName, LocalDate date
   * @return
   * @throws CountryNotFoundException
   */

  public int getNumRecoveredForCountryOnCertainDate(String countryName, LocalDate date)
      throws CountryNotFoundException {

    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      return countries.get(countryName).getEntry(date).getRecovered();
    }
  }



  /**
   * Gets the total number of deaths for a country
   * 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumDeaths(String countryName) throws CountryNotFoundException {
    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    }
    return countries.get(countryName).getTotalNumDeaths();
  }

  /**
   * Gets the total number of recovered cases for a country
   * 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumRecovered(String countryName) throws CountryNotFoundException {
    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    }
    return countries.get(countryName).getTotalNumRecovered();
  }

  /**
   * Gets the total number of active cases for a country
   * 
   * @param countryName
   * @return
   * @throws CountryNotFoundException
   */
  public int getTotalNumActive(String countryName) throws CountryNotFoundException {
    if (!countries.containsKey(countryName)) {
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

  public String toString(String countryName, LocalDate date) throws CountryNotFoundException {
    String toReturn = "";

    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException();
    } else {
      // get date
      toReturn += countries.get(countryName).getEntry(date).toString() + ": ";

      // add number of deaths
      toReturn += "Total number of deaths: " + this.getTotalNumDeaths(countryName) + "\n";

      // add number of active cases:
      toReturn += "Total Number of active cases: " + this.getTotalNumActive(countryName) + "\n";

      // add number of recovered cases

      toReturn += "Total Number of recoveries: " + this.getTotalNumRecovered(countryName) + "\n";

    }
    return toReturn;
  }



}

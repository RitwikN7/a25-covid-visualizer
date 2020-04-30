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
	 * Constructor for the class. Must have a specifically formatted JSON file ready
	 * when object is created.
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
		} catch (NullPointerException e4) {
			e4.printStackTrace();
		}
	}

	/**
	 * Parses the JSON that was passed when creating the object.
	 * 
	 * @param filePath file to parse
	 * @return Complete hashmap of all countries with data and their relevant data
	 * @throws FileNotFoundException If the file cannot be found.
	 * @throws IOException           If there is an error with accessing the file
	 * @throws ParseException        If JSON.simple has an error parsing the JSON
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

		// Runs through the list of country codes
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

				// Converts the date array to a LocalDate object
				LocalDate localDate = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
						Integer.parseInt(dateSplit[2]));

				// Converts all the data into a DataEntry object.
				DataEntry entry = new DataEntry(localDate, confirmedDeaths.intValue(), confirmedCases.intValue(),
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
	 * 
	 * @return number of countries in our hash map.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns a country object based on the parameter passed.
	 * 
	 * @param countryName a String to search for a country in the HashMap
	 * @return a Country object returned from the HashMap by searching for
	 *         countryName String
	 * @throws CountryNotFoundException when the CountryName String was not
	 *                                  associated with a value in the hashmap.
	 */

	public Country getCountry(String countryName) throws CountryNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		} else {
			return countries.get(countryName);
		}
	}

	public HashMap<String, Country> getAllCountries() {
		return countries;
	}

	/**
	 * Returns number of deaths for a country on a certain date
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date        the date that is being searched for
	 * @return an int as the number of deaths
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 */

	public int getNumDeathsForCountryOnCertainDate(String countryName, LocalDate date)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		} else {
			return countries.get(countryName).getEntry(date).getDeaths();
		}
	}

	/**
	 * Returns number of active cases for a country on a certain date
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date        the date that is being searched for
	 * @return int number of active cases for this country on this date.
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */

	public int getNumActiveForCountryOnCertainDate(String countryName, LocalDate date)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		} else {
			return countries.get(countryName).getEntry(date).getActive();
		}
	}

	/**
	 * Returns number of active cases for a country on a certain date
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date        the date that is being searched for
	 * @return int number of recovered cases for this country on this date
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */

	public int getNumRecoveredForCountryOnCertainDate(String countryName, LocalDate date)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		} else {
			return countries.get(countryName).getEntry(date).getRecovered();
		}
	}

	/**
	 * Gets the total number of deaths for a country
	 * 
	 * @param countryName the string name of the country being searched for
	 * @return int number of the total deaths for this country
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 */
	public int getTotalNumDeaths(String countryName) throws CountryNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		}
		return countries.get(countryName).getTotalNumDeaths();
	}

	/**
	 * Gets the total number of recovered cases for a country
	 * 
	 * @param countryName the string name of the country being searched for
	 * @return int number of the total recovered cases for this country
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 */
	public int getTotalNumRecovered(String countryName) throws CountryNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		}
		return countries.get(countryName).getTotalNumRecovered();
	}

	/**
	 * Gets the total number of active cases for a country
	 * 
	 * @param countryName the string name of the country being searched for
	 * @return int number of the total active cases for this country
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 */
	public int getTotalNumActive(String countryName) throws CountryNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		}
		return countries.get(countryName).getTotalNumActive();
	}

	/**
	 * Gets total active cases for a country in a certain date range
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date1       the beginning date in the range
	 * @param date2       the end date in the range
	 * @return a hashmap with dates and ints; the ints are the number of active
	 *         cases for that date
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */
	public HashMap<LocalDate, DataEntry> getNumActiveForDateRange(String countryName, LocalDate date1, LocalDate date2)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		HashMap<LocalDate, DataEntry> toReturn = new HashMap<LocalDate, DataEntry>();
		Country countryToGetStatsFrom = this.getCountry(countryName);

		for (LocalDate start = date1; start.isBefore(date2); start = start.plusDays(1)) {

			toReturn.put(start, countryToGetStatsFrom.getEntry(start));
		}

		return toReturn;

	}

	/**
	 * Gets total deaths for a country in a certain date range
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date1       the beginning date in the range
	 * @param date2       the end date in the range
	 * @return a hashmap with dates and ints; the ints are the number of deaths for
	 *         that date
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */
	public HashMap<LocalDate, Integer> getNumDeathsForDateRange(String countryName, LocalDate date1, LocalDate date2)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		HashMap<LocalDate, Integer> toReturn = new HashMap<LocalDate, Integer>();
		Country countryToGetStatsFrom = this.getCountry(countryName);

		for (LocalDate start = date1; start.isBefore(date2); start = start.plusDays(1)) {
			int numActive = this.getNumDeathsForCountryOnCertainDate(countryName, start);
			toReturn.put(start, numActive);
		}

		return toReturn;
	}

	/**
	 * Gets total recoveries for a country in a certain date range
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date1       the beginning date in the range
	 * @param date2       the end date in the range
	 * @return a hashmap with dates and ints; the ints are the number of recovered
	 *         for that date
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */
	public HashMap<LocalDate, Integer> getNumRecoveredForDateRange(String countryName, LocalDate date1, LocalDate date2)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
		HashMap<LocalDate, Integer> toReturn = new HashMap<LocalDate, Integer>();
		Country countryToGetStatsFrom = this.getCountry(countryName);

		for (LocalDate start = date1; start.isBefore(date2); start = start.plusDays(1)) {
			int numActive = this.getNumRecoveredForCountryOnCertainDate(countryName, start);
			toReturn.put(start, numActive);
		}

		return toReturn;
	}

	/**
	 * Returns a string containing all pertinent information for a country for a
	 * certain date range
	 * 
	 * @param countryName the string name of the country being searched for
	 * @param date1       the beginning date in the range
	 * @param date2       the end date in the range
	 * @return a hashmap with dates and ints; the ints are the number of deaths for
	 *         that date
	 * @throws CountryNotFoundException when Country not found in the hashmap
	 * @throws DateNotFoundException
	 */
	public String toStringForDateRange(String countryName, LocalDate date1, LocalDate date2)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}

		String toReturn = "";

		if (!countries.containsKey(countryName)) {
			throw new CountryNotFoundException();
		} else {
			// get date
			toReturn += "Statistics for " + countries.get(countryName).getEntry(date1).toString() + " until "
					+ countries.get(countryName).getEntry(date2).toString();

			// add number of deaths
			toReturn += "Total number of deaths: " + this.getNumDeathsForDateRange(countryName, date1, date2) + "\n";

			// add number of active cases:
			toReturn += "Total Number of active cases: " + this.getNumActiveForDateRange(countryName, date1, date2)
					+ "\n";

			// add number of recovered cases

			toReturn += "Total Number of recoveries: " + this.getNumRecoveredForDateRange(countryName, date1, date2)
					+ "\n";

		}
		return toReturn;
	}

	/**
	 * Returns a string containing all pertinent information for a country
	 * 
	 * @param countryName the string name of the country being searched for.
	 * @param date        the date that is being searched for.
	 * @return returns a String containing all of the information of the country
	 *         searched for.
	 * @throws CountryNotFoundException when Country not found in the hashmap.
	 * @throws DateNotFoundException
	 */
	public String toString(String countryName, LocalDate date)
			throws CountryNotFoundException, DateNotFoundException, IllegalArgumentException {
		if (countryName == null) {
			throw new IllegalArgumentException();
		}
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

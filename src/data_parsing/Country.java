/**
 * Country.java created by drewhalverson on MacBook Pro 15-inch, 2018 in p2_project.
 *
 * This class titled "Country" represents a Country with a String name and many DataEntry objects
 * that hold data from covid19 json according to date.
 *
 * Author:   Drew Halverson (dhalverson2@wisc.edu)
 * Date:     4/20/20
 *
 * Course:   CS400
 * Semester: Spring 2020
 * Lecture:  001
 *
 * IDE:      Eclipse IDE for Java Developers
 * Version:  2019-12 (4.14.0)
 * Build id: 20191212-1212
 *
 * Device:   Drew's MacBook Pro
 * OS:       macOS Catalina
 * Version   10.15.2
 * OS Build: 19C57
 * 
 * List Collaborators: None
 *
 * Other Credits: None
 *
 * Known Bugs: None
 */

package data_parsing;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import java.util.Map.Entry;;

/**
 * Country - represents a Country with a String name and many DataEntry objects
 * that hold data from covid19 json according to date.
 * 
 * @author halverson (2020)
 */
public class Country {

	private HashMap<Date, DataEntry> dataEntries;
	private String name;

	/**
	 * Simple constructor that accepts a string as a parameter to be the name of
	 * this Country object. Throws IllegalArgumentException if name is null or zero
	 * length string.
	 * 
	 * @param name the name of this Country
	 * @throws IllegalArgumentException if name is null or zero length.
	 */
	public Country(String name, JSONArray data) {
		if (name == null)
			throw new IllegalArgumentException("name parameter is null");
		if (name.length() == 0)
			throw new IllegalArgumentException("name parameter is zero length string.");

		this.name = name;
		this.dataEntries = new HashMap<Date, DataEntry>();
		
		
	}

	/**
	 * Accessor method to return a DataEntry given the passed Date.
	 * 
	 * @param date the date to be searched for in the hash map
	 * @return returns a DataEntry object that is obtained from the hashmap by
	 *         searching for key date
	 */
	public DataEntry getEntry(Date date) {
		return dataEntries.get(date);
	}

	/**
	 * Accessor for the name of this Country.
	 * 
	 * @return returns name instance field.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Simple mutator method for dataEntries that calls put method of the HashMap
	 * and adds this key value pair.
	 * 
	 * @param date  the key, a Date object
	 * @param entry the value, a DataEntry object
	 */
	public void addEntry(Date date, DataEntry entry) {
		this.dataEntries.put(date, entry);
	}

	/**
	 * Returns a Set of all of the entries in the HashMap so that this Country can
	 * be iterated over.
	 * 
	 * @return a Set of all Date, DataEntry entries.
	 */
	public Set<Entry<Date, DataEntry>> getAllEntries() {
		return this.dataEntries.entrySet();
	}
}

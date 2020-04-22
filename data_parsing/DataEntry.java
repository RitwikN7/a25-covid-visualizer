/**
 * DataEntry.java created by drewhalverson on MacBook Pro 15-inch, 2018 in p2_project.
 *
 * This class titled "DataEntry" holds a Date and data recorded from that date about covid19
 * deaths, active cases, and recovered cases.
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

/**
 * DataEntry - holds a Date and data recorded from that date about covid19
 * deaths, active cases, and recovered cases.
 * 
 * @author halverson (2020)
 */
public class DataEntry {

	private final Date date;
	private int deaths;
	private int active;
	private int recovered;

	/**
	 * Public constructor that calls the other overloaded constructor and supplies
	 * default values of 0 for integers.
	 * 
	 * @param date Date object of this entry
	 */
	public DataEntry(Date date) {
		this(date, 0, 0, 0);
	}

	/**
	 * Overloaded constructor that checks to make sure the Date object is valid,
	 * throws Exceptions if not.
	 * 
	 * @param date      the Date of this entry
	 * @param deaths    the number of deaths on this date
	 * @param active    the number of active cases on this date
	 * @param recovered the number of recovered cases on this date
	 * @throws IllegalArgumentException when date is null.
	 */
	public DataEntry(Date date, int deaths, int active, int recovered) {
		if (date == null)
			throw new IllegalArgumentException("date is null");
		this.date = date;
	}

	/**
	 * Public accessor that returns the Date object stored in this entry.
	 * 
	 * @return returns the date of this entry.
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Returns the deaths for this Date
	 * 
	 * @return deaths int
	 */
	public int getDeaths() {
		return deaths;
	}

	/**
	 * Returns the active cases for this Date
	 * 
	 * @return active int
	 */
	public int getActive() {
		return active;
	}

	/**
	 * Returns the recovered cases for this Date
	 * 
	 * @return recovered int
	 */
	public int getRecovered() {
		return recovered;
	}

	/**
	 * toString method that provides a readable string format of the date of this
	 * data entry:
	 * 
	 * @return returns a String in the format: mon dd yyyy - where mon is the month,
	 *         dd is the day, and yyyy is the year.
	 */
	@Override
	public String toString() {
		String result = date.toString();
		result = result.substring(4, 10) + " " + result.substring(result.length() - 4); // takes just month, day, year
		return result;
	}

}

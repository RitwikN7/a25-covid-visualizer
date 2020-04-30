/*
 * CountryTest.java edited by Ritwik Shanker Prasad on Windows 10 PC
 * 
 * Author: Ritwik Shanker Prasad Email: rprasad22@wisc.edu Date: 29th April 2020
 * 
 * Course: COMP SCI 400 Semester: Spring 2020 Lecture: LEC 001
 * 
 * IDE: Eclipse 2019-12
 * 
 */

package data_parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * This class tests the methods and functionality of Country.java.
 */
public class CountryTest {

    private Country country;

    /**
     * Tests whether the constructor throws IllegalArgumentException when expected.
     */
    @Test
    void test000_CountryConstructor() {
        try {
            country = new Country(null);
            fail("Constructor didn't throw IllegalArgumentException as expected.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
        try {
            country = new Country("");
            fail("Constructor didn't throw IllegalArgumentException as expected.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Adds a single DataEntry to a Country object and tests whether the
     * corresponding getter and setter methods work as expected.
     */
    @Test
    void test001_AddOneEntry() {
        DataEntry data = new DataEntry(LocalDate.now(), 1, 2, 3);
        country = new Country("test");
        country.addEntry(data.getDate(), data);
        try {
            country.getEntry(data.getDate().minusDays(2));
            fail("method didn't throw DateNotFoundException as expected.");
        } catch (DateNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
        try {
            if (!country.getEntry(data.getDate()).equals(data)) {
                fail("Getter method for data doesn't return the right data.");
            }
        } catch (DateNotFoundException e) {
            e.printStackTrace();
            fail("Unexpected DateNotFoundException thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Adds multiple DataEntry objects to a Country object and checks whether the
     * returned HashMap and date List are correct.
     */
    @Test
    void test002_AddMultipleEntries() {
        country = new Country("test");
        HashMap<LocalDate, DataEntry> entries = new HashMap<>();
        DataEntry d1 = new DataEntry(LocalDate.now());
        DataEntry d2 = new DataEntry(LocalDate.now().plusDays(1));
        DataEntry d3 = new DataEntry(LocalDate.now().minusDays(1));
        entries.put(d1.getDate(), d1);
        entries.put(d2.getDate(), d2);
        entries.put(d3.getDate(), d3);
        country.addEntry(d1.getDate(), d1);
        country.addEntry(d2.getDate(), d2);
        country.addEntry(d3.getDate(), d3);

        HashMap<LocalDate, DataEntry> allEntries = country.getAllEntries();
        if (!allEntries.equals(entries)) {
            fail("The returned HashMap from Country class is incorrect.");
        }

        List<LocalDate> dates = country.getAllDates();
        if (!(dates.contains(d1.getDate()) && dates.contains(d2.getDate()) && dates.contains(d3.getDate()))) {
            fail("The List of dates returned is incorrect.");
        }
    }
}
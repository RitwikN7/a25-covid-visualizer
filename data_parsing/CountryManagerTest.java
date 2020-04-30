/*
 * CountryManagerTest.java edited by Ritwik Shanker Prasad on Windows 10 PC
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
 * This class tests the methods and functionality of CountryManager.java.
 */
public class CountryManagerTest {

    private CountryManager cm = new CountryManager("timeseries.json");
    private CountryManager test;

    /**
     * Tests whether the constructor throws errors or not when initialized with
     * incorrect and valid parameters.
     */
    @Test
    void test000_Constructor() {
        try {
            test = new CountryManager(null); // testing with null parameter
        } catch (Exception e) {
            e.printStackTrace();
            fail("The Constructor should not throw any exceptions.");
        }
        try {
            test = new CountryManager("timeseries.json"); // testing with valid parameter
        } catch (Exception e) {
            e.printStackTrace();
            fail("The Constructor should not throw any exceptions.");
        }
    }

    /**
     * Tests the getCountry() method with valid and invalid parameters and checks
     * whether it throws the correct exceptions.
     */
    @Test
    void test001_GetOneCountry() {
        try {
            Country c = cm.getCountry(null); // testing with null parameter
            fail("Did not throw IllegalArgumentException as expected.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            Country c = cm.getCountry("aabb"); // testing with non-existent Country
            fail("Did not throw CountryNotFoundException as expected.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            Country c = cm.getCountry("India"); // testing with existent Country
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
            fail("Unexpected CountryNotFoundException thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            fail("Unexpected IllegalArgumentException thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getTotalNumDeaths() method with invalid arguments to check whether
     * it throws expected exceptions.
     */
    @Test
    void test002_GetTotalDeaths() {
        try {
            cm.getTotalNumDeaths(null);
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getTotalNumDeaths("aabb");
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getTotalNumActive() method with invalid arguments to check whether
     * it throws expected exceptions.
     */
    @Test
    void test003_GetTotalActive() {
        try {
            cm.getTotalNumActive(null);
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getTotalNumActive("aabb");
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getTotalNumRecovered() method with invalid arguments to check
     * whether it throws expected exceptions.
     */
    @Test
    void test004_GetTotalRecovered() {
        try {
            cm.getTotalNumRecovered(null);
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getTotalNumRecovered("aabb");
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getNumDeathsForCountryOnCertainDate() method with invalid arguments
     * to check whether it throws expected exceptions.
     */
    @Test
    void test005_GetDeathsCertainDate() {
        try {
            cm.getNumDeathsForCountryOnCertainDate(null, LocalDate.now());
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumDeathsForCountryOnCertainDate("aabb", LocalDate.now());
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumDeathsForCountryOnCertainDate("India", LocalDate.now());
            fail("Expected DateNotFoundException not thrown.");
        } catch (DateNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getNumActiveForCountryOnCertainDate() method with invalid arguments
     * to check whether it throws expected exceptions.
     */
    @Test
    void test006_GetActiveCertainDate() {
        try {
            cm.getNumActiveForCountryOnCertainDate(null, LocalDate.now());
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumActiveForCountryOnCertainDate("aabb", LocalDate.now());
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumActiveForCountryOnCertainDate("India", LocalDate.now());
            fail("Expected DateNotFoundException not thrown.");
        } catch (DateNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Tests the getNumRecoveredForCountryOnCertainDate() method with invalid
     * arguments to check whether it throws expected exceptions.
     */
    @Test
    void test007_GetRecoveredCertainDate() {
        try {
            cm.getNumRecoveredForCountryOnCertainDate(null, LocalDate.now());
            fail("Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumRecoveredForCountryOnCertainDate("aabb", LocalDate.now());
            fail("Expected CountryNotFoundException not thrown.");
        } catch (CountryNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }

        try {
            cm.getNumRecoveredForCountryOnCertainDate("India", LocalDate.now());
            fail("Expected DateNotFoundException not thrown.");
        } catch (DateNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }
}
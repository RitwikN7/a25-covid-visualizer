package data_parsing;

import java.util.Locale;

public class CountryManagerTester {
  
  public static void main(String[] args) throws CountryNotFoundException {
    CountryManager manager = new CountryManager("timeseries.json");
    
    Country unitedStates = manager.getCountry("United States");
    System.out.println(unitedStates.getTotalNumActive());
  }
}

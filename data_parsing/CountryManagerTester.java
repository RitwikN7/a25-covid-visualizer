package data_parsing;

import java.time.LocalDate;
import java.util.Locale;

public class CountryManagerTester {
  
  public static void main(String[] args) throws CountryNotFoundException {
    CountryManager manager = new CountryManager("timeseries.json");
    
    Country andorra = manager.getCountry("Andorra");
    LocalDate date = LocalDate.of(2020, 4, 19);
    DataEntry entry = andorra.getEntry(date);
    
  }
}

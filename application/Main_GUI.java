package application;

import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;


// Unnecessary imports because this is not the final version of the application yet.
// This version is meant for Milestone 2 submission.

import java.io.File;
import com.sun.javafx.css.StyleManager;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javax.imageio.ImageIO;
import com.sun.prism.paint.Color;
import javafx.scene.control.CheckBox;

import data_parsing.Country;
import data_parsing.CountryManager;
import data_parsing.CountryNotFoundException;
import data_parsing.DataEntry;
import data_parsing.DateNotFoundException;
import javafx.scene.control.DatePicker;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main_GUI extends Application {

	private List<String> args;
	private static final int WINDOW_WIDTH = 940;
	private static final int WINDOW_HEIGHT = 700;
	private CountryManager manager = new CountryManager("timeseries.json");
	boolean secondDatePicked = false;

	private static final String APP_TITLE = "aTeam 25 Covid 19 Visualizer Final Project";

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);


		// FIrst Grid Pane
		GridPane topGP = new GridPane();



		//

		HBox box = new HBox();
		box.setSpacing(10);

		Label range = new Label("Select range for Graph");
		range.setFont(Font.font("Serif", FontWeight.BOLD, 18));
		Label one = new Label("From:");
		Label two = new Label("To:");

		DatePicker from = new DatePicker();
		DatePicker to = new DatePicker();

		CheckBox rangeCB = new CheckBox();

		box.getChildren().add(rangeCB);
		box.getChildren().add(range);
		box.getChildren().add(one);
		box.getChildren().add(from);
		box.getChildren().add(two);
		box.getChildren().add(to);


		// Menu section
		HBox menuHB = new HBox();
		ArrayList<String> mainMenu = new ArrayList<String>();

		mainMenu.add("About");
		mainMenu.add("Sources");

		ObservableList<String> ob1 = FXCollections.observableArrayList(mainMenu);
		ComboBox<String> cbMenu = new ComboBox<String>(ob1);
		menuHB.getChildren().add(cbMenu);
		cbMenu.setPromptText("Menu");
		EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				String optionSelected = cbMenu.getValue();

				if (optionSelected == "About") {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("About");
					alert.setHeaderText("About");
					String s =	"This program is a COVID-19 data visualizer with an emphasis on how " +
							"the statistics concerning this global pandemic for a particular country " +
							"have changed over time. Users can use this tool of " +
							"analysis to know daily change in the number of total confirmed cases, total " +
							"deaths and total recovered for a particular country affected by " +
							"COVID-19. Graphical visualisations and tabular data available for the same.\n\n"+
							"Click on 'Take ScreenShot' to save the screenshot of the data displayed on screen.";
					alert.setContentText(s);
					alert.show();
				}
				else if (optionSelected == "Sources")
				{


					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Sources");
					alert.setHeaderText("Sources");
					String s ="COVID-19 data Source: https://pomber.github.io/covid19/timeseries.json\n" ;
					alert.setContentText(s);
					alert.show();
				}
			}
		};

		cbMenu.setOnAction(event3);


		// main Label
		HBox labelHB = new HBox();
		Label mainLabel = new Label();
		mainLabel.setFont(Font.font("Serif", FontWeight.NORMAL, 30));
		mainLabel.setText("     Welcome to aTeam25's COVID-19 Data Visualiser     ");
		labelHB.getChildren().add(mainLabel);

		// screenshot functionality
		HBox scnHB = new HBox();
		Button scn = new Button();
		scn.setText("Take Screenshot");
		scnHB.getChildren().add(scn);
		scn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
				takeSnapShot(mainScene);

			}
		});
		topGP.add(menuHB, 0, 0);
		topGP.add(labelHB, 1, 0);
		topGP.add(scnHB, 2, 0);


		// Second Grid Pane
		GridPane secondGP = new GridPane();

		// Third Grid Pane
		GridPane thirdGP = new GridPane();

		// The date picker functionality will work directly with the
		// JSON parsed data and it displays nothing for the purposes of Milestone#2

		// Date picker
		HBox dateHB = new HBox();

		VBox statTableVB = new VBox();

		DatePicker datePicker = new DatePicker();


		// label to show the date
		Label dateLabel = new Label("Date: no date selected");
		//
		dateLabel.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, 15));

		Label confirmedLabel = new Label("Confirmed:");
		//
		confirmedLabel.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, 15));

		Label deathsLabel = new Label("Deaths:");
		//
		deathsLabel.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, 15));

		Label recoveredLabel = new Label("Recovered:");
		//
		recoveredLabel.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, 15));

		// Country section
		HBox countryHB = new HBox();
		ArrayList<String> countryMenu = new ArrayList<String>();

		for (String countryName : manager.getAllCountries().keySet()) {
			countryMenu.add(countryName);
		}
		Collections.sort(countryMenu);

		ObservableList<String> ob2 = FXCollections.observableArrayList(countryMenu);
		ComboBox<String> ctMenu = new ComboBox<String>(ob2);
		countryHB.getChildren().add(ctMenu);
		ctMenu.setPromptText("---Select Country---");

		// action event
		EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {


				// get the date picker value
				String countryThatWasPicked = ctMenu.getValue();

				if(secondDatePicked) {
					System.out.println("2 dates picked");
					root.setCenter(drawGraph(countryThatWasPicked, from.getValue(),to.getValue()));

				}
				else {
					root.setCenter(drawGraph(countryThatWasPicked,null,null));

				}

				// if there is a date selected, the table information is updated.
				if (!(datePicker.getValue() == null)) {
					fillTable(ctMenu.getValue(), datePicker.getValue(), dateLabel, confirmedLabel, deathsLabel, recoveredLabel);
				}

			}

		};

		// action event to fill table
		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				fillTable(ctMenu.getValue(), datePicker.getValue(), dateLabel, confirmedLabel, deathsLabel, recoveredLabel);
			}
		};


		// action event for when 2 dates are picked
		EventHandler<ActionEvent> rangeCBEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				if (rangeCB.isSelected())
				{
					secondDatePicked = true;
					String countryThatWasPicked = ctMenu.getValue();

					root.setCenter(drawGraph(countryThatWasPicked, from.getValue(),to.getValue()));

				}
				else 
				{
					secondDatePicked = false;
					String countryThatWasPicked = ctMenu.getValue();
					root.setCenter(drawGraph(countryThatWasPicked,null,null));
				}
			}
		};

		rangeCB.setOnAction(rangeCBEvent);
		to.setOnAction(event1);
		ctMenu.setOnAction(event1);
		secondGP.add(countryHB, 0, 0);
		datePicker.setOnAction(event2);
		// to.setOnAction(secondDatePickedEvent);

		// Minor changes made to add padding

		statTableVB.getChildren().add(dateLabel);
		statTableVB.getChildren().add(confirmedLabel);
		statTableVB.getChildren().add(deathsLabel);
		statTableVB.getChildren().add(recoveredLabel);


		dateHB.getChildren().add(datePicker);

		datePicker.setPromptText("---Select Date---");
		from.setPromptText(("\t  ---Start Date---"));
		to.setPromptText("\t  ---End Date---");
		secondGP.add(dateHB, 1, 0);
		secondGP.add(statTableVB, 2, 0);
		// secondGP.add(datePicker2, 3, 0);

		secondGP.setHgap(50); 
		secondGP.setVgap(50);

		// adding to third gp

		thirdGP.add(box, 0, 0);



		VBox top = new VBox(topGP, secondGP);
		top.setSpacing(20);
		root.setTop(top);



		// Part: Adding an Exit button bottom panel
		Button btn = new Button("Close Application");
		btn.setText("Close Application");
		btn.setOnAction(ae -> {
			primaryStage.close();
		});

		VBox bottom = new VBox(thirdGP, btn);
		bottom.setSpacing(20);
		root.setBottom(bottom);

		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	/**
	 * Creates screeenshot functionality
	 * Saves .png file to working directory
	 * @param scene
	 */
	private void takeSnapShot(Scene scene) {
		WritableImage writableImage =
				new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
		scene.snapshot(writableImage);

		File file = new File("snapshot.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
			System.out.println("snapshot saved: " + file.getAbsolutePath());
		} catch (IOException ex) {
			Logger.getLogger(Main_GUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Fills table with the data from a given day within a given country
	 * @param countryName     | Country to select data from
	 * @param date            | Date to find data for
	 * @param dateLabel       | Labels to update
	 * @param confirmedLabel           |||
	 * @param deathsLabel              |||
	 * @param recoveredLabel           VVV
	 */
	private void fillTable(String countryName, LocalDate date, Label dateLabel, Label confirmedLabel, Label deathsLabel, Label recoveredLabel) {
		try {
			// Get country data
			Country selectedCountry = manager.getCountry(countryName);
			DataEntry data = selectedCountry.getEntry(date);

			// Formatting to add commas after every 3 digits for readability
			NumberFormat commasIncluded = NumberFormat.getInstance();
			commasIncluded.setGroupingUsed(true);

			// Update table to show data on a given day
			dateLabel.setText("Date: " + date);
			confirmedLabel.setText("Confirmed: " + commasIncluded.format(data.getActive()));
			deathsLabel.setText("Deaths: " + commasIncluded.format(data.getDeaths()));
			recoveredLabel.setText("Recovered: " + commasIncluded.format(data.getRecovered()));
		} catch (CountryNotFoundException e1) {
			Alert noCountryAlert =
					new Alert(AlertType.ERROR, "Please select a country before selecting a date.");
			noCountryAlert.showAndWait();
		} catch (DateNotFoundException e2) {
			Alert dataNotFound = new Alert(AlertType.ERROR,
					"We could not find any data for this day: "+ date + ", please select another");
			dataNotFound.showAndWait();
		}
	}

	// For the final submission this data would be parsed from a JSON file/API
	// and directly fed into the graph for respective countries and dates
	private LineChart drawGraph(String countryThatWasPicked,LocalDate start, LocalDate end) {
		
		
		ObservableList<String> ob = FXCollections.observableArrayList();

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Date");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Total number of cases");

		LineChart<String, Number> lc = new LineChart<String, Number>(xAxis, yAxis);
		lc.setTitle(countryThatWasPicked);

		XYChart.Series<String, Number> dataConfirmed = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> dataDeaths = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> dataRecovered = new XYChart.Series<String, Number>();


		if(start == null && end == null) {
			try {
				Country countryToGetDataFrom = manager.getCountry(countryThatWasPicked);

				HashMap<LocalDate,DataEntry> returnedCountryData = countryToGetDataFrom.getAllEntries();

				List<LocalDate> dates = countryToGetDataFrom.getAllDates();

				for(LocalDate inOrderDate : dates) {
					DataEntry data = returnedCountryData.get(inOrderDate);
					dataConfirmed.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getActive()));
					dataDeaths.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getDeaths()));
					dataRecovered.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getRecovered()));
				}
			} catch (CountryNotFoundException e) {
				// TODO Auto-generated catch block

				Alert noCountryAlert =
						new Alert(AlertType.ERROR, "Please select a different country");
				noCountryAlert.showAndWait();			
			}

		}
		else {
			
			if(end.isBefore(start)) {
				Alert invalidDateRange =
						new Alert(AlertType.ERROR, "Please make sure that ending date is after start date ");
				invalidDateRange.showAndWait();			
			}
			try {
				Country countryToGetDataFrom = manager.getCountry(countryThatWasPicked);

				HashMap<LocalDate,DataEntry> returnedCountryData = manager.getNumActiveForDateRange(countryThatWasPicked, start, end);

				List<LocalDate> dates = countryToGetDataFrom.getAllDatesInRange(start,end);

				for(LocalDate inOrderDate : dates) {
					DataEntry data = returnedCountryData.get(inOrderDate); 
					dataConfirmed.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getActive()));
					dataDeaths.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getDeaths()));
					dataRecovered.getData().add(new XYChart.Data<String, Number>(data.toString(),data.getRecovered()));

				}


			} catch (CountryNotFoundException | DateNotFoundException e) {
				// TODO Auto-generated catch block

				Alert dataNotFound = new Alert(AlertType.ERROR,
						"We could not find any data for this day, please select another");
				dataNotFound.showAndWait();
			}
		}



		dataConfirmed.setName("Confirmed");
		dataDeaths.setName("Deaths");
		dataRecovered.setName("Recovered");

		lc.getData().add(dataConfirmed);
		lc.getData().add(dataDeaths);
		lc.getData().add(dataRecovered);

		return lc;
	}

	/**
	 * Main method that just calls launch and passes the args
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

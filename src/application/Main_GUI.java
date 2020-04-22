package application;

import java.time.LocalDate;
import java.util.*;

// Unnecessary imports because this is not the final version of the application yet. 
// This version is meant for Milestone 2 submission.

import com.sun.prism.paint.Color;
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

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 700;
	private static final String APP_TITLE = "ateam 25 project Milestone 2";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		
		// FIrst Grid Pane
		GridPane topGP = new GridPane();
		
		
		
		// Menu section
		HBox menuHB = new HBox();
		ArrayList<String> mainMenu = new ArrayList<String>();

		mainMenu.add("About");
		mainMenu.add("Sources");
		
		ObservableList<String> ob1 = FXCollections.observableArrayList(mainMenu);
		ComboBox<String> cbMenu = new ComboBox<String>(ob1);
		menuHB.getChildren().add(cbMenu);
		cbMenu.setPromptText("Menu");
		
		
		// main Label
		HBox labelHB = new HBox() ;
		Label mainLabel = new Label();	
		mainLabel.setFont(Font.font("Serif",FontWeight.NORMAL, 30));
		mainLabel.setText("     Welcome to aTeam25's COVID-19 Data Visualiser     ");
		labelHB.getChildren().add(mainLabel);
		
		topGP.add(menuHB,0,0);
		topGP.add(labelHB,1,0);
		
		
		// Second Grid Pane
		GridPane secondGP = new GridPane();
		
		 
		// Country section
				HBox countryHB = new HBox();
				ArrayList<String> countryMenu = new ArrayList<String>();

				countryMenu.add("USA");// Displaying only USA first 10 days 
				// of March data for Milestone #2
				countryMenu.add("Spain -- Not for Milestone#2");
				countryMenu.add("Italy -- Not for Milestone#2");
				countryMenu.add("France -- Not for Milestone#2");
				countryMenu.add("Germany -- Not for Milestone#2");
				
				
				ObservableList<String> ob2 = FXCollections.observableArrayList(countryMenu);
				ComboBox<String> ctMenu = new ComboBox<String>(ob2);
				countryHB.getChildren().add(ctMenu);
				ctMenu.setPromptText("---Select Country---");
				
				// action event 
		        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() { 
		            public void handle(ActionEvent e) 
		            { 
		            	 
		                
		            	// get the date picker value 
		                String i = ctMenu.getValue(); 
		  
		                if (i == "USA") 
		                {
		                	// Graph in Center
		                    root.setCenter(drawGraph()); 
		            } 
		                else if (i == "") {
		                	
		                	// for other countries later on
		                }
		            }
		        };
		        
		        ctMenu.setOnAction(event1);
				secondGP.add(countryHB,0,0);
		
		// The date picker functionality will work directly with the 
		// JSON parsed data and it displays nothing for the purposes of Milestone#2
				
		// Date picker
				HBox dateHB = new HBox();
				
				VBox statTableVB = new VBox();
				
				DatePicker datePicker = new DatePicker(); 
				
				// label to show the date 
                Label dateLabel = new Label("Date: no date selected");
                Label confirmedLabel = new Label("Confirmed: no data to display for Milestone #2");
                Label deathsLabel = new Label("Deaths: no data to display for Milestone #2");
                Label recoveredLabel = new Label("Recovered: no data to display for Milestone #2");
				// action event 
		        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
		            public void handle(ActionEvent e) 
		            { 
		            	 
		                
		            	// get the date picker value 
		                LocalDate i = datePicker.getValue(); 
		  
		                // get the selected date 
		                dateLabel.setText("Date :" + i); 
		                confirmedLabel.setText("Confirmed: ----");
		                deathsLabel.setText("Deaths: ----");
		                recoveredLabel.setText("Recovered: ----");
		            } 
		        };
		        
		        datePicker.setOnAction(event2);
		        
		        statTableVB.getChildren().add(dateLabel);
		        statTableVB.getChildren().add(confirmedLabel);
		        statTableVB.getChildren().add(deathsLabel);
		        statTableVB.getChildren().add(recoveredLabel);
		        
		        
				dateHB.getChildren().add(datePicker);
				dateHB.getChildren().add(statTableVB);
				
				datePicker.setPromptText("---Select Date---");
				secondGP.add(dateHB, 1, 0);
				
		
		VBox top = new VBox(topGP,secondGP);
		top.setSpacing(20);
        root.setTop(top);
        
        
     // Part: Adding an Exit button bottom panel
     		Button btn = new Button("Close");
     		btn.setText("Close");
     		btn.setOnAction(ae-> {primaryStage.close();});
       root.setBottom(btn);
        
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
		
	}
	
	// For the final submission this data would be parsed from a JSON file/API 
	// and directly fed into the graph for respective countries and dates
	
	private LineChart drawGraph() {
		
		
		
		ObservableList<String> ob = FXCollections.observableArrayList();
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Date");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Total number of additional cases");
		
		LineChart<String, Number> lc = new LineChart<String, Number>(xAxis, yAxis);
		lc.setTitle("USA Cases [Sample data]");
		
	
		XYChart.Series<String, Number> dataConfirmed = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> dataDeaths = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> dataRecovered = new XYChart.Series<String, Number>();

		
		// Confirmed cases
		
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/01/2020",74));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/02/2020",98));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/03/2020",118));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/04/2020",149));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/05/2020",217));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/06/2020",262));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/07/2020",402));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/08/2020",518));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/09/2020",583));
		dataConfirmed.getData().add(new XYChart.Data<String,Number>("03/10/2020",959));
		
		// Deaths
		
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/01/2020",1));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/02/2020",6));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/03/2020",7));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/04/2020",11));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/05/2020",12));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/06/2020",14));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/07/2020",17));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/08/2020",21));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/09/2020",22));
		dataDeaths.getData().add(new XYChart.Data<String,Number>("03/10/2020",28));
		
		// Recovered
		
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/01/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/02/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/03/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/04/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/05/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/06/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/07/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/08/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/09/2020",7));
		dataRecovered.getData().add(new XYChart.Data<String,Number>("03/10/2020",8));

		
		
		dataConfirmed.setName("Confirmed");
		dataDeaths.setName("Deaths");
		dataRecovered.setName("Recovered");

		
		lc.getData().add(dataConfirmed);
		lc.getData().add(dataDeaths);
		lc.getData().add(dataRecovered);

		return lc;
	}
	
	
	
	}
	
	

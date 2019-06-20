package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.joda.time.DateTime;
import org.joda.time.Days;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplementCounterController {
	@FXML
	private DatePicker dateofReturn;
	@FXML
	private Button addtoList;
	@FXML
	private Button deletefromList;
	@FXML
	private ChoiceBox<String> suppChoiceBox;
	
	@FXML
	private ListView listViewSupplement;
	@FXML
	private Button calculateButton;
	@FXML
	private ListView calculateListView;
	@FXML
	private TextField servingSize;
	
	
	
	
	ArrayList<Supplement> supplementList = new ArrayList<Supplement>();
	
	ArrayList<Supplement> patientSupplementList = new ArrayList<Supplement>();
	
	
	class Supplement {
		private String supplementName;
		private Integer amountPills;
		
		public Supplement(String supplementName, Integer amountPills) {
			this.supplementName = supplementName;
			this.amountPills = amountPills;
			
		}
		public Integer getAmount() {
			return this.amountPills;
		}
		public String getName() {
			return this.supplementName;
		}
		
	}
	
	
	public ObservableList<String> alphaSort(ObservableList<String> list) {
		ObservableList<String> nameList = FXCollections.observableArrayList();
		for (int i = 0; i < list.size(); i++) 
        {
            for (int j = i + 1; j < list.size(); j++) 
            {
                if (list.get(i).compareTo(list.get(j)) > 0) 
                {
                		
                    String temp = list.get(i);
                    nameList.add(i, list.get(j));
                    nameList.add(j, temp);
                } else {
                		nameList.add(i, list.get(i));
                    nameList.add(j, list.get(j));
                }
            }
        }
		return nameList;
    }
	
	//add method
	@FXML
	public void add(ActionEvent event) {
		if (!suppChoiceBox.getSelectionModel().isEmpty() && servingSize.getText() != null) {
			listViewSupplement.getItems().add(suppChoiceBox.getSelectionModel().getSelectedItem() + " : " + servingSize.getText());
			Supplement newSupp = new Supplement(suppChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(servingSize.getText()));
			patientSupplementList.add(newSupp);
		}
		
		
	}
	
	//delete method
	@FXML
	public void delete(ActionEvent event) {
		listViewSupplement.getItems().remove(listViewSupplement.getSelectionModel().getSelectedItem());
		patientSupplementList.remove(listViewSupplement.getSelectionModel().getSelectedIndex());
	}
	
	//calculate method
	@FXML
	public void calculateBottles(ActionEvent event) {
		
		Date date = new Date();
		LocalDate CurrentDate = LocalDate.now();
		LocalDate ReturnDate = dateofReturn.getValue();
		DateTime start = new DateTime(CurrentDate.getYear(), CurrentDate.getMonthValue(), CurrentDate.getDayOfMonth(), 0, 0);
		DateTime end = new DateTime(ReturnDate.getYear(), ReturnDate.getMonthValue(), ReturnDate.getDayOfMonth(), 0, 0);
		
		
		int numberDays = Days.daysBetween(start, end).getDays();
		for(int i = 0; i < patientSupplementList.size(); i++) {
			int suppListIndex = 0;
			String spaceString = "";
			for (int z = 0; z < supplementList.size(); z++) {
				if (supplementList.get(z).getName().equals(patientSupplementList.get(i).getName())) {
					suppListIndex = z;
				}
			}
			long numberBottles = Math.round(Double.parseDouble((patientSupplementList.get(i).getAmount().toString())) * numberDays / Double.parseDouble((supplementList.get(suppListIndex).getAmount().toString())));
			
			calculateListView.getItems().add(patientSupplementList.get(i).getName() + "                              " + numberBottles);
			
		}
	}
	
	//start method
	@FXML
	public void initialize() {
		
		String csvFile = "/Users/jacobantoine/Desktop/Supplement List - Sheet1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        calculateListView.getItems().add("Supplement:                                   number of units:");
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] supplementValue = line.split(cvsSplitBy);

                Supplement newSupp = new Supplement(supplementValue[0], Integer.parseInt(supplementValue[1]));
                supplementList.add(newSupp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ObservableList<String> suppNameList = FXCollections.observableArrayList();
        
        
        for (int i = 0; i < supplementList.size(); i++) {
        		suppNameList.add(supplementList.get(i).getName());
        		
        }
        //ObservableList<String> newNameList = alphaSort(suppNameList);
        suppChoiceBox.setItems(suppNameList);
        
        //autoCompleteField = new AutoCompleteTextField((SortedSet<String>) suppNameList);
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}

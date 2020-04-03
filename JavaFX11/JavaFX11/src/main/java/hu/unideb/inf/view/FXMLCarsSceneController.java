/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import hu.unideb.inf.model.Model;
import hu.unideb.inf.model.Cars;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hallgato
 */
public class FXMLCarsSceneController implements Initializable {

    private Model model;

    public void setModel(Model model) 
    {
        this.model = model;
    }
    
    ObservableList<String> CurrencyList = FXCollections.observableArrayList("HUF", "EUR", "USD", "GBP");
    
    @FXML
    private TextField BrandTextTitle;

    @FXML
    private TextField TypeTextTitle;

    @FXML
    private TextField YearTextTitle;

    @FXML
    private TextField MotorTextTitle;

    @FXML
    private TextField ChassisTextTitle;

    @FXML
    private TextField ColorTextTitle;

    @FXML
    private TextField PriceTextTitle;
    
    @FXML
    private ChoiceBox<String> CurrencyChoiceBox;
    
    boolean IsSpecialChars(TextField Special)
    {
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        if (Special.getText().endsWith("-") || Special.getText().startsWith("-")
                || Special.getText().endsWith(" ") || Special.getText().startsWith(" "))
        {
            return true;
        }
        for (char c : Special.getText().toCharArray()) {
            String ch = c + "";
            if (specialChars.contains(ch)) {
                return true;
            }
        }
        return false;
    }
    
    boolean IsDig(TextField Dig)
    {
        for (char c : Dig.getText().toCharArray()) {
            if(Character.isDigit(c))
            {
                return true;
            }
        }
        return false;
    }
    
    boolean IsLet(TextField Let)
    {
        for (char c : Let.getText().toCharArray()) {
            if(Character.isLetter(c))
            {
                return true;
            }
        }
        return false;
    }
    
    boolean IsOnlyDig(TextField Let)
    {
        int count = 0;
        for (char c : Let.getText().toCharArray()) {
            if(Character.isDigit(c))
            {
                count++;
            }
        }
        return Let.getText().length() == count;
    }
    
    boolean IsHandleUploadButtonPushed(Alert alert)
    {
        alert.setTitle("Hibás bemenet!");
        alert.setHeaderText("A feltöltés során hiba történt!");
        if(BrandTextTitle.getText().isBlank() || TypeTextTitle.getText().isBlank()
                || YearTextTitle.getText().isBlank() || MotorTextTitle.getText().isBlank()
                || ChassisTextTitle.getText().isBlank() || ColorTextTitle.getText().isBlank()
                || PriceTextTitle.getText().isBlank())
        {
            alert.setContentText("A bemenetek között nem szerepelhet üres vagy csak whitespace karaktereket tartalmazó karaktersorozat!");
            return false;
        }else if(IsSpecialChars(BrandTextTitle) || IsSpecialChars(TypeTextTitle)
                || IsSpecialChars(YearTextTitle) || IsSpecialChars(MotorTextTitle)
                || IsSpecialChars(ChassisTextTitle) || IsSpecialChars(ColorTextTitle)
                || IsSpecialChars(PriceTextTitle))
        {
            alert.setContentText("A bementek között nem szerepelhet speciális karaktert tartalmazó karaktersorozat!");
            return false;
        }else if(IsDig(MotorTextTitle) || IsDig(BrandTextTitle) || IsDig(ColorTextTitle))
        {
            alert.setContentText("A motor, márka és a szín nem tartalmazhat számjegyet!");
            return false;
        }else if(IsLet(YearTextTitle) || IsLet(PriceTextTitle))
        {
            alert.setContentText("A gyártási év és az ár nem tartalmazhat betűt!");
            return false;
        }else if(YearTextTitle.getText().length() != 4)
        {
            alert.setContentText("A gyártási év pontosan 4 darab számjegyből állhat!");
            return false;
        }else if (ChassisTextTitle.getText().length() != 17)
        {
            alert.setContentText("Az alvázszám pontosan 17 karakterből állhat!");
            return false;
        }else if (IsOnlyDig(ChassisTextTitle) || IsOnlyDig(TypeTextTitle))
        {
            alert.setContentText("Az alvázszám és a típus nem tartalmazhatnak csak számjegyeket!");
            return false;
        }
        return true;
    }
    
    @FXML
    void HandleUploadButtonPushed() throws IOException
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        String newLine = System.getProperty("line.separator");
        if (IsHandleUploadButtonPushed(alert)) {
            model.getCar().setBrand(BrandTextTitle.getText());
            model.getCar().setType(TypeTextTitle.getText());
            model.getCar().setYear(Integer.parseInt(YearTextTitle.getText()));
            model.getCar().setMotor(MotorTextTitle.getText());
            model.getCar().setChassisNumber(ChassisTextTitle.getText());
            model.getCar().setColor(ColorTextTitle.getText());
            model.getCar().setPrice(Integer.parseInt(PriceTextTitle.getText()));
        
            try (FileOutputStream fos = new FileOutputStream("car.ser",true);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) 
            {
                oos.writeObject(model.getCar());
                oos.close();
                fos.close();
            }
            
            alert.setTitle("Siker");
            alert.setHeaderText("A feltöltés sikeresen megtörtént");
            alert.setContentText("Most már az adatbázisban szerepel!");
        }
        alert.showAndWait();
    }
    
    
    @FXML
    void HandleCancleButtonPushed() throws IOException, ClassNotFoundException 
    {
        BrandTextTitle.setText("");
        TypeTextTitle.setText("");
        ColorTextTitle.setText("");
        MotorTextTitle.setText("");
        ChassisTextTitle.setText("");
        YearTextTitle.setText("");
        PriceTextTitle.setText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CurrencyChoiceBox.setValue("HUF");
        CurrencyChoiceBox.setItems(CurrencyList);
    }

}

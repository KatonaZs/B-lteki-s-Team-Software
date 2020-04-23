package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.util.HibernateUtil;
import hu.unideb.inf.model.Cars;
import hu.unideb.inf.model.Model;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
  @author hallgato
 **/

 public class FXMLCarsSceneController implements Initializable {

    private Model model;
    List<Cars> carss = new ArrayList<>();

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
    private TextField LicenseNumberTextTitle;
    
    @FXML
    private ChoiceBox<String> CurrencyChoiceBox;
    
    @FXML
    private ChoiceBox<String> SellPriceCurrencyChoiceBox;
    
    @FXML
    private ChoiceBox<String> SellCarChoiceBox;

    @FXML
    private TextField SellPriceTextField;

    @FXML
    private TextField SellerNameTextField;

    @FXML
    private TextField SellDateTextField;
    
    ObservableList<String> items;
    Transaction transaction = null;
    void SellChoiceBoxUpdate()
    {
        List<String> item = new ArrayList<>();
        item.add("-- Kérem válasszon ki egy autót a következő listából. --");
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
         carss = session.createQuery("from Cars", Cars.class).list();
         for(int i=0;i<carss.size();i++)
         {
           item.add( " Márka: " + carss.get(i).getBrand() + " | Típus: " + carss.get(i).getType() + " | Szín: " + carss.get(i).getColor()+  " | Rendszám: " + carss.get(i).getLicenseNumber());
         }
        items=FXCollections.observableArrayList(item);
        SellCarChoiceBox.setItems(items);
        } 
        catch (Exception e) 
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
 
 
    //A bemenet speciális karaktert tartalmaz-e//
    boolean IsSpecialChars(TextField Special)
    {
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        if (Special.getText().endsWith("-") || Special.getText().startsWith("-")
                || Special.getText().endsWith(" ") || Special.getText().startsWith(" "))
        {
            return true;
        }
        for (char c : Special.getText().toCharArray())
        {
            String ch = c + "";
            if (specialChars.contains(ch)) 
            {
                return true;
            }
        }
        return false;
    }
    
    //A bemenet tartalmaz-e számot//
    boolean IsDig(TextField Dig)
    {
        for (char c : Dig.getText().toCharArray())
        {
            if(Character.isDigit(c))
            {
                return true;
            }
        }
        return false;
    }
    
    //Tartalmaz-e betüt//
    boolean IsLet(TextField Let)
    {
        for (char c : Let.getText().toCharArray())
        {
            if(Character.isLetter(c))
            {
                return true;
            }
        }
        return false;
    }
    
    //Csak számot tartalmaz?//
    boolean IsOnlyDig(TextField Let)
    {
        int count = 0;
        for (char c : Let.getText().toCharArray()) 
        {
            if(Character.isDigit(c))
            {
                count++;
            }
        }
        return Let.getText().length() == count;
    }
    
    //Bemenetek kezelése//
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
                || IsSpecialChars(YearTextTitle) || IsSpecialChars(ChassisTextTitle) || IsSpecialChars(ColorTextTitle))
        {
            alert.setContentText("A bementek között nem szerepelhet speciális karaktert tartalmazó karaktersorozat!");
            return false;
        }else if(IsDig(BrandTextTitle) || IsDig(ColorTextTitle))
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
       
     boolean IsHandleSellButtonPushed(Alert alert)
     {
         alert.setTitle("Hiba!");
         alert.setHeaderText("Hibás kitöltés!");
        if(SellCarChoiceBox.getValue() == "-- Kérem válasszon ki egy autót a következő listából. --")
        {
            alert.setContentText("Kérem válasszon egy autót a listából!");
            return false;
        }
 
        if(SellPriceTextField.getText().isBlank() || SellerNameTextField.getText().isBlank() || SellDateTextField.getText().isBlank())
        {
            alert.setContentText("A bemenetek nem lehetnek üresek!");
            return false;
        }
        
         return true;
     }
    
    //A beállított pénznem szerinti összeg mentés//
    void SetPriceValue()
    {     
        switch (CurrencyChoiceBox.getValue())
        {
            case "GBP":
                model.getCar().setPrice(Double.parseDouble(PriceTextTitle.getText()));
                break;
            case "HUF":
                model.getCar().setPrice(Math.round(Double.parseDouble(PriceTextTitle.getText()) / 400.0));
                break;
            case "EUR":
                model.getCar().setPrice(Math.round(Double.parseDouble(PriceTextTitle.getText())/1.2));
                break;
            case "USD":
                model.getCar().setPrice(Math.round(Double.parseDouble(PriceTextTitle.getText())/1.25));
                break;
            default:
                break;
        }
    }
    
    //Model értékeinek beállítása és azok feltöltése az adatbázisba//
    void SetModelAndUploadToTheDataBase()
    {
         model.getCar().setBrand(BrandTextTitle.getText());
         model.getCar().setType(TypeTextTitle.getText());
         model.getCar().setYear(Integer.parseInt(YearTextTitle.getText()));
         model.getCar().setMotor(MotorTextTitle.getText());
         model.getCar().setChassisNumber(ChassisTextTitle.getText());
         model.getCar().setColor(ColorTextTitle.getText());
         SetPriceValue();
         model.getCar().setCurrency("GBP");
         model.getCar().setLicenseNumber(LicenseNumberTextTitle.getText());
         
         Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession())
            {
            transaction = session.beginTransaction();
            session.save(model.getCar());
            transaction.commit();
            } 
            catch (Exception e)
            {
                if (transaction != null)
                {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }
    
    @FXML
    void HandleUploadButtonPushed() throws IOException
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (IsHandleUploadButtonPushed(alert)) 
        {
            SetModelAndUploadToTheDataBase();
            alert.setTitle("Siker");
            alert.setHeaderText("A feltöltés sikeresen megtörtént");
            alert.setContentText("Most már az adatbázisban szerepel!");
            SellChoiceBoxUpdate();
        }
        alert.showAndWait();
    }
    
    @FXML
    void HandleSellButtonPushed()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String szoveg= SellCarChoiceBox.getValue();
        int idk;
        String idkk="";
        for(int i=0;i<szoveg.length();i++)
        {
            if(szoveg.charAt(i)=='I' && szoveg.charAt(i+1)=='D')
            {
                idkk=szoveg.charAt(i+4)+""+szoveg.charAt(i+5);
            }
        }
        System.out.println(idkk);
        idk=Integer.parseInt(idkk);
	Cars emp = (Cars)session.load(Cars.class,idk);
	session.delete(emp);
		
	tr.commit();
	System.out.println("Data Updated");

        SellChoiceBoxUpdate();
        Alert alert = new Alert(AlertType.INFORMATION);
        if (IsHandleSellButtonPushed(alert)) 
        {
            alert.setTitle("Siker");
            alert.setHeaderText("Az eladás sikeresen megtörtént");
            alert.setContentText("Most már törlödőtt az adatbázisból!");
        }
        alert.showAndWait();
    }

    @FXML
    void HandleSellCancelButtonPushed() 
    {
        SellPriceTextField.clear();
        SellerNameTextField.clear();
        SellDateTextField.clear();
    }
    
    @FXML
    void HandleCancleButtonPushed() throws IOException, ClassNotFoundException 
    {
        BrandTextTitle.clear();
        TypeTextTitle.clear();
        ColorTextTitle.clear();
        MotorTextTitle.clear();
        ChassisTextTitle.clear();
        YearTextTitle.clear();
        PriceTextTitle.clear();
        LicenseNumberTextTitle.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        CurrencyChoiceBox.setValue("GBP");
        CurrencyChoiceBox.setItems(CurrencyList);
        SellPriceCurrencyChoiceBox.setValue("GBP");
        SellPriceCurrencyChoiceBox.setItems(CurrencyList);
        SellChoiceBoxUpdate();
        SellCarChoiceBox.setValue("-- Kérem válasszon ki egy autót a következő listából. --");
    }
}

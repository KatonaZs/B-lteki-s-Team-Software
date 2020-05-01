package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.util.HibernateUtil;
import hu.unideb.inf.model.Cars;
import hu.unideb.inf.model.Model;
import hu.unideb.inf.model.Workers;
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
    List<Workers> workerss = new ArrayList<>();

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
    private TextField LicenseNumberTextTitle;

    @FXML
    private TextField ChassisTextTitle;

    @FXML
    private TextField ColorTextTitle;

    @FXML
    private TextField PriceTextTitle;

    @FXML
    private ChoiceBox<String> BuyCurrencyChoiceBox;
    
    @FXML
    private ChoiceBox<String> EditCurrencyChoiceBox;

    @FXML
    private TextField SellPriceTextField;

    @FXML
    private ChoiceBox<String> SellPriceCurrencyChoiceBox;

    @FXML
    private ChoiceBox<String> SellerNameChoiceBox;

    @FXML
    private TextField SellDateTextField;

    @FXML
    private ChoiceBox<String> EditCarChoiceBox;
    
    @FXML
    private ChoiceBox<String> SellCarChoiceBox;

    @FXML
    private TextField SetBrandText;

    @FXML
    private TextField SetTypeText;

    @FXML
    private TextField SetYearText;

    @FXML
    private TextField SetMotorText;

    @FXML
    private TextField SetLicenseNumberText;

    @FXML
    private TextField SetChassisNumberText;

    @FXML
    private TextField SetColorText;

    @FXML
    private TextField SetPriceText;
    
    @FXML
    private TextField WorkerNameTextField;

    @FXML
    private TextField WorkerBDTextField;

    @FXML
    private TextField WorkerINTextField;

    @FXML
    private ChoiceBox<String> WorkersChoiceBox;

    @FXML
    private TextField WorkerCarCountTextField;

    @FXML
    private TextField WorkerProfitTextField;
    
    ObservableList<String> itemCarss;
    ObservableList<String> itemWorkerss;
    Transaction transaction = null;
    void ChoiceBoxesUpdate()
    {
        List<String> itemCars = new ArrayList<>();
        List<String> itemWorkers = new ArrayList<>();
        itemCars.add("-- Kérem válasszon ki egy autót a következő listából. --");
        itemWorkers.add("-- Kérem válasszon ki egy dolgozót a következő listából. --");
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
         carss = session.createQuery("from Cars", Cars.class).list();
         workerss = session.createQuery("from Workers", Workers.class).list();
         for(int i = 0; i < carss.size(); i++)
         {
           itemCars.add("ID: " + carss.get(i).getId() +" | Márka: " + carss.get(i).getBrand() + " | Típus: " + carss.get(i).getType() + " | Szín: " + carss.get(i).getColor()+ " | Lista ár: " + carss.get(i).getPrice() + " GBP | Rendszám: " + carss.get(i).getLicenseNumber());
         }
         for(int i = 0; i < workerss.size(); i++)
         {
           itemWorkers.add("ID: " + workerss.get(i).getId() + " | Név: " +  workerss.get(i).getName() + " | I.Sz.: " + workerss.get(i).getIN());
         }
        itemCarss = FXCollections.observableArrayList(itemCars);
        itemWorkerss = FXCollections.observableArrayList(itemWorkers);
        SellCarChoiceBox.setItems(itemCarss);
        EditCarChoiceBox.setItems(itemCarss);
        WorkersChoiceBox.setItems(itemWorkerss);
        SellerNameChoiceBox.setItems(itemWorkerss);
        } 
        catch (Exception e) 
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        SellCarChoiceBox.setValue("-- Kérem válasszon ki egy autót a következő listából. --");
        EditCarChoiceBox.setValue("-- Kérem válasszon ki egy autót a következő listából. --");
        WorkersChoiceBox.setValue("-- Kérem válasszon ki egy dolgozót a következő listából. --");
        SellerNameChoiceBox.setValue("-- Kérem válasszon ki egy dolgozót a következő listából. --");
    }
 
 
    //A bemenet speciális karaktert tartalmaz-e//
    boolean IsSpecialChars(TextField Special)
    {
        String specialChars = "=/*!@#$%^&*()\"{}_[]|\\?/<>,.";
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
        if(SellerNameChoiceBox.getValue() == "-- Kérem válasszon ki egy dolgozót a következő listából. --")
        {
            alert.setContentText("Kérem válasszon egy dolgozót a listából!");
            return false;
        }
        if(SellPriceTextField.getText().isBlank() || SellDateTextField.getText().isBlank())
        {
            alert.setContentText("A bemenetek nem lehetnek üresek!");
            return false;
        }
        
        if(!IsOnlyDig(SellPriceTextField))
        {
            alert.setContentText("Az eladási ár csak számot tartalmazhat");
            return false;
        }
        
         return true;
     }
    
    //A beállított pénznem szerinti összeg mentés//
    void SetPriceValue()
    {     
        switch (BuyCurrencyChoiceBox.getValue())
        {
            case "GBP":
                model.getCar().setPrice(Double.parseDouble(PriceTextTitle.getText()) * 1.2);
                break;
            case "HUF":
                model.getCar().setPrice(Math.round((Double.parseDouble(PriceTextTitle.getText()) / 400.0) * 1.2));
                break;
            case "EUR":
                model.getCar().setPrice(Math.round((Double.parseDouble(PriceTextTitle.getText()) / 1.2) * 1.2));
                break;
            case "USD":
                model.getCar().setPrice(Math.round((Double.parseDouble(PriceTextTitle.getText()) / 1.25) * 1.2));
                break;
            default:
                break;
        }
    }
    
    void GetSellerCarFromBuyInputs()
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
    }
    
    //Model értékeinek beállítása és azok feltöltése az adatbázisba//
    void SetModelAndUploadToTheDataBase()
    {
         GetSellerCarFromBuyInputs();
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
    
    
    void ClearBuyTabInputs()
    {
        BrandTextTitle.clear();
        TypeTextTitle.clear();
        ColorTextTitle.clear();
        MotorTextTitle.clear();
        ChassisTextTitle.clear();
        YearTextTitle.clear();
        PriceTextTitle.clear();
        LicenseNumberTextTitle.clear();
        BuyCurrencyChoiceBox.setValue("GBP");
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
            ChoiceBoxesUpdate();
            ClearBuyTabInputs();
        }
        alert.showAndWait();
    }
    
    void ClearSellTabInputs()
    {
        SellPriceTextField.clear();
        SellDateTextField.clear();
        SellPriceCurrencyChoiceBox.setValue("GBP");
        SellCarChoiceBox.setValue("-- Kérem válasszon ki egy autót a következő listából. --");
    }
    
    void AddTheProfitAndTheCarToTheWorkers(double SellPrice, double BuyPrice)
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedWorker= SellerNameChoiceBox.getValue();
        int worker_ID;
        String getWorkerIDString = "";
   
        for(int i=0;i<selectedWorker.length();i++)
        {  
            if(Character.isDigit(selectedWorker.charAt(i)))
            {
               getWorkerIDString += selectedWorker.charAt(i);
               if(selectedWorker.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        worker_ID = Integer.parseInt(getWorkerIDString);
	Workers emp = (Workers)session.load(Workers.class,worker_ID);
        int count = emp.getCarCount() + 1;
	emp.setCarCount(count);
        SellPrice = SellPrice - BuyPrice;
        double Profit = Math.round(emp.getProfit() + SellPrice);
        emp.setProfit(Profit);
        session.update(emp);
	tr.commit();
    }
    
    double GetSellPriceValue()
    {     
        switch (SellPriceCurrencyChoiceBox.getValue())
        {
            case "GBP":
                return Double.parseDouble(SellPriceTextField.getText());
            case "HUF":
                return Math.round(Double.parseDouble(SellPriceTextField.getText()) / 400.0);
            case "EUR":
                return Math.round(Double.parseDouble(SellPriceTextField.getText())/1.2);
            case "USD":
                return Math.round(Double.parseDouble(SellPriceTextField.getText())/1.25);
            default:
                break;
        }
        return 0;
    }
    void DeleteSelectedCarFromDataBaseonTheSellTab()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedCar= SellCarChoiceBox.getValue();
        int car_ID;
        String getCarIDString="";
   
        for(int i=0;i<selectedCar.length();i++)
        {  
            if(Character.isDigit(selectedCar.charAt(i)))
            {
               getCarIDString += selectedCar.charAt(i);
               if(selectedCar.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        car_ID = Integer.parseInt(getCarIDString);
	Cars emp = (Cars)session.load(Cars.class,car_ID);
        double BuyPrice = emp.getPrice() / 1.2;
	session.delete(emp);
	tr.commit();
        double SellPrice = GetSellPriceValue();
        AddTheProfitAndTheCarToTheWorkers(SellPrice, BuyPrice);
	System.out.println("Data Updated");
    }
    
    @FXML
    void HandleSellButtonPushed()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (IsHandleSellButtonPushed(alert)) 
        {
            DeleteSelectedCarFromDataBaseonTheSellTab();
            alert.setTitle("Siker");
            alert.setHeaderText("Az eladás sikeresen megtörtént");
            alert.setContentText("Most már törlödőtt az adatbázisból!");
            ChoiceBoxesUpdate();
            ClearSellTabInputs();
        }
        alert.showAndWait();
    }

    @FXML
    void HandleSellCancelButtonPushed() 
    {
        ClearSellTabInputs();
    }
    
    @FXML
    void HandleCancleButtonPushed() throws IOException, ClassNotFoundException 
    {
         ClearBuyTabInputs();
    }
    
    void ClearEditTabInputs()
    {
       SetBrandText.clear();
       SetTypeText.clear();
       SetYearText.clear();
       SetMotorText.clear();
       SetLicenseNumberText.clear();
       SetChassisNumberText.clear();
       SetColorText.clear();
       SetPriceText.clear();
       EditCurrencyChoiceBox.setValue("GBP");
       EditCarChoiceBox.setValue("-- Kérem válasszon ki egy autót a következő listából. --");
    }
    
    @FXML
    void HandleEditCancleButtonPushod()
    {
       ClearEditTabInputs();
    }
    
    void GetDataToEditTextFields()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedCar= EditCarChoiceBox.getValue();
        int car_ID;
        String getCarIDString="";
   
        for(int i=0;i<selectedCar.length();i++)
        {  
            if(Character.isDigit(selectedCar.charAt(i)))
            {
               getCarIDString += selectedCar.charAt(i);
               if(selectedCar.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        car_ID = Integer.parseInt(getCarIDString);
	Cars emp = (Cars)session.load(Cars.class,car_ID);
		
        System.out.println(emp.getLicenseNumber());
	System.out.println("The data has arrive for edit");
        SetBrandText.setText(emp.getBrand());
        SetTypeText.setText(emp.getType());
        SetYearText.setText (String.valueOf(emp.getYear()));
        SetMotorText.setText(emp.getMotor());
        SetLicenseNumberText.setText(emp.getLicenseNumber());
        SetChassisNumberText.setText(emp.getChassisNumber());
        SetColorText.setText(emp.getColor());
        SetPriceText.setText(String.valueOf(emp.getPrice()));
        tr.commit();
    }
    
    boolean IsHandleEditAutomaticButtonPushed(Alert alert)
    {  
        if(EditCarChoiceBox.getValue() == "-- Kérem válasszon ki egy autót a következő listából. --")
        {
            System.out.println("almafa");
            alert.setTitle("Sikertelen");
            alert.setHeaderText("Az adatok nem kerültek betöltésre");
            alert.setContentText("Kérem válasszon ki egy autót a listából!");
            return false;
        }
        
        return true;
    }
    
     @FXML
     void  HandleEditAutomaticButtonPushed()  throws IOException
     { 
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if (IsHandleEditAutomaticButtonPushed(alert)) 
        {
         GetDataToEditTextFields();
         alert.setTitle("Siker");
         alert.setHeaderText("Az adatok sikeresen betöltésre kerültek");
         alert.setContentText("Most szerkesztheti a bemeneteket!");
        }
         alert.showAndWait();
     }
     
     
    void  DeleteSelectedCarFromDataBaseonTheEditTab()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedCar= EditCarChoiceBox.getValue();
        int car_ID;
        String getCarIDString="";
   
        for(int i=0;i<selectedCar.length();i++)
        {  
            if(Character.isDigit(selectedCar.charAt(i)))
            {
               getCarIDString += selectedCar.charAt(i);
               if(selectedCar.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        car_ID = Integer.parseInt(getCarIDString);
	Cars emp = (Cars)session.load(Cars.class,car_ID);
        session.delete(emp);
		
	tr.commit();
	System.out.println("Data Deleted!");
    }
     
    @FXML
    void HandleEditDeleteButtonPushed() 
    {
        DeleteSelectedCarFromDataBaseonTheEditTab();
        ChoiceBoxesUpdate();
        ClearEditTabInputs();
    }
    
    Cars UpdateDataToTheDataBaseOnTheEditTab(Cars SelectedCar)
    {
        SelectedCar.setBrand(SetBrandText.getText());
        SelectedCar.setChassisNumber(SetChassisNumberText.getText());
        SelectedCar.setColor(SetColorText.getText());
        SelectedCar.setMotor(SetMotorText.getText());
        SelectedCar.setLicenseNumber(SetLicenseNumberText.getText());
        SelectedCar.setPrice(Double.parseDouble(SetPriceText.getText()));
        SelectedCar.setType(SetTypeText.getText());
        SelectedCar.setYear(Integer.parseInt(SetYearText.getText()));
        
        return SelectedCar;
    }
    
    void ChangeUploadedDataFromEditTab()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedCar= EditCarChoiceBox.getValue();
        int car_ID;
        String getCarIDString="";
   
        for(int i=0;i<selectedCar.length();i++)
        {  
            if(Character.isDigit(selectedCar.charAt(i)))
            {
               getCarIDString += selectedCar.charAt(i);
               if(selectedCar.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        car_ID = Integer.parseInt(getCarIDString);
	Cars emp = (Cars)session.load(Cars.class,car_ID);
        emp = UpdateDataToTheDataBaseOnTheEditTab(emp);
        session.update(emp);
		
	tr.commit();
	System.out.println("Data Changed!");
    }
    
    boolean IsHandleEditSaveButtonPushed(Alert alert)
    {
        alert.setTitle("Hibás bemenet!");
        alert.setHeaderText("A feltöltés során hiba történt!");
        if(SetBrandText.getText().isBlank() || SetTypeText.getText().isBlank()
                || SetYearText.getText().isBlank() || SetMotorText.getText().isBlank()
                || SetChassisNumberText.getText().isBlank() || SetColorText.getText().isBlank()
                || SetPriceText.getText().isBlank())
        {
            alert.setContentText("A bemenetek között nem szerepelhet üres vagy csak whitespace karaktereket tartalmazó karaktersorozat!");
            return false;
        }
        else if(IsSpecialChars(SetBrandText) || IsSpecialChars(SetTypeText)
                || IsSpecialChars(SetYearText) || IsSpecialChars(SetChassisNumberText) || IsSpecialChars(SetColorText))
        {
            alert.setContentText("A bementek között nem szerepelhet speciális karaktert tartalmazó karaktersorozat!");
            return false;
        }
        else if(IsDig(SetBrandText) || IsDig(SetColorText))
        {
            alert.setContentText("A motor, márka és a szín nem tartalmazhat számjegyet!");
            return false;
        }
        else if(IsLet(SetYearText) || IsLet(SetPriceText))
        {
            alert.setContentText("A gyártási év és az ár nem tartalmazhat betűt!");
            return false;
        }
        else if(SetYearText.getText().length() != 4)
        {
            alert.setContentText("A gyártási év pontosan 4 darab számjegyből állhat!");
            return false;
        }
        else if (SetChassisNumberText.getText().length() != 17)
        {
            alert.setContentText("Az alvázszám pontosan 17 karakterből állhat!");
            return false;
        }
        else if (IsOnlyDig(SetChassisNumberText) || IsOnlyDig(SetTypeText))
        {
            alert.setContentText("Az alvázszám és a típus nem tartalmazhatnak csak számjegyeket!");
            return false;
        }
        else if(EditCarChoiceBox.getValue() == "-- Kérem válasszon ki egy autót a következő listából. --")
        {
            System.out.println("almafa");
            alert.setTitle("Sikertelen");
            alert.setHeaderText("Az adatok nem kerültek betöltésre");
            alert.setContentText("Kérem válasszon ki egy autót a listából!");
            return false;
        }
        return true;
    }
    
    @FXML
    void HandleEditSaveButtonPushed()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if (IsHandleEditSaveButtonPushed(alert)) 
        {
            alert.setTitle("Siker");
            alert.setHeaderText("Az adatok sikeresen megváltoztak!");
            alert.setContentText("Az új adat mostmár az adatbázisban szerepel!");
            ChangeUploadedDataFromEditTab();
            ChoiceBoxesUpdate();
            ClearEditTabInputs();
        }
        
        alert.showAndWait();
    }
    
    
     void GetWorkerFromCompanyTab()
     {
         model.getWorker().setName(WorkerNameTextField.getText());
         model.getWorker().setBdate(WorkerBDTextField.getText());
         model.getWorker().setCarCount(0);
         model.getWorker().setProfit(0);
         model.getWorker().setIN(WorkerINTextField.getText());
         Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession())
            {
            transaction = session.beginTransaction();
            session.save(model.getWorker());
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
     
     boolean BadDateFormatum(String Text)
     {
         if(Text.length() != 10)
         return true;
         
         if(Text.charAt(4) != '.')
         return true;
         
         if(Text.charAt(7) != '.')
         return true;
         
         for(int i = 0; i < 4; i++)
         {
             if(!(Character.isDigit(Text.charAt(i))))
                 return true;
         }
         
         for(int i = 5; i < 7; i++)
         {
             if(!(Character.isDigit(Text.charAt(i))))
                 return true;
         }
         
         for(int i = 8; i < 10; i++)
         {
             if(!(Character.isDigit(Text.charAt(i))))
                 return true;
         }
         
         return false;
     }
     
     boolean IsHandleApplyButtonPushed(Alert alert)
     {
        alert.setTitle("Hibás bemenet!");
        alert.setHeaderText("A feltöltés során hiba történt!");
        if(WorkerNameTextField.getText().isBlank() || WorkerBDTextField.getText().isBlank()||WorkerINTextField.getText().isBlank())
        {
            alert.setContentText("A bemenetek nem lehetnek üresek se whitespace nem megengedett!");
            return false;
        }
        if(IsDig(WorkerNameTextField))
        {
            alert.setContentText("A név nem tartalmazhat számjegyet!");
            return false;
        }
        if(BadDateFormatum(WorkerBDTextField.getText()))
        {
            alert.setContentText("A születési dátumnot XXXX.XX.XX formátumba kell megadni!");
            return false;
        }
        
         return true;
     }
     
     void ClearApplyWorkerInputs()
     {
         WorkerBDTextField.clear();
         WorkerNameTextField.clear();
         WorkerINTextField.clear();
     }
     
    @FXML
    void WorkerApplyButtonPushed() 
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if (IsHandleApplyButtonPushed(alert)) 
        {
         GetWorkerFromCompanyTab();
         ChoiceBoxesUpdate();
         ClearApplyWorkerInputs();
         alert.setTitle("Siker");
         alert.setHeaderText("Az új dolgozót sikeresen felvette az adatbázisba!");
         alert.setContentText("Most már lekérdezheti ha szeretné!");
         ClearQuiryInputs();
        }
         alert.showAndWait();
    }
    
    void ClearQuiryInputs()
    {
        WorkerProfitTextField.clear();
        WorkerCarCountTextField.clear();
    }
    
    void DeleteWorkerOnTheCompanyTab()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedWorker= WorkersChoiceBox.getValue();
        int worker_ID;
        String getWorkerIDString = "";
   
        for(int i=0;i<selectedWorker.length();i++)
        {  
            if(Character.isDigit(selectedWorker.charAt(i)))
            {
               getWorkerIDString += selectedWorker.charAt(i);
               if(selectedWorker.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        worker_ID = Integer.parseInt(getWorkerIDString);
	Workers emp = (Workers)session.load(Workers.class,worker_ID);
	session.delete(emp);
		
	tr.commit();
	System.out.println("Worker Deleted!");
    }
    
    @FXML
    void DeleteSelectedWorkerButtonPushed()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        DeleteWorkerOnTheCompanyTab();
        ClearQuiryInputs();
        ChoiceBoxesUpdate();  
        alert.setTitle("Siker");
        alert.setHeaderText("A dolgozót sikeresen törölte az adatbázisból!");
        alert.setContentText("Adatbázis frissitésre került!");
        alert.showAndWait();
    }
    
    void GetWorkerData()
    {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
	Session session = sessFact.getCurrentSession();
	Transaction tr = session.beginTransaction();
        
        String selectedWorker= WorkersChoiceBox.getValue();
        int worker_ID;
        String getWorkerIDString = "";
   
        for(int i=0;i<selectedWorker.length();i++)
        {  
            if(Character.isDigit(selectedWorker.charAt(i)))
            {
               getWorkerIDString += selectedWorker.charAt(i);
               if(selectedWorker.charAt(i+1)==' ')
               {
                   break;
               }  
            }
        }
        
        worker_ID = Integer.parseInt(getWorkerIDString);
	Workers emp = (Workers)session.load(Workers.class,worker_ID);
	WorkerProfitTextField.setText(emp.getProfit() + " GBP");
        WorkerCarCountTextField.setText(emp.getCarCount()+ " db");
	tr.commit();
    }
    
    boolean IsHandleGetButtonPushed(Alert alert)
    {
        
        if(WorkersChoiceBox.getValue() == "-- Kérem válasszon ki egy dolgozót a következő listából. --")
        {
            alert.setTitle("Hiba");
            alert.setHeaderText("Sikertelen lekérdezés!");
            alert.setContentText("Kérem válasszon egy dolgozót!");
            return false;
        }
            
     return true;
    }
    
    @FXML
    void GetWorkerDataButtonPushed() 
    {
       Alert alert = new Alert(AlertType.INFORMATION);
       ClearQuiryInputs();
       if (IsHandleGetButtonPushed(alert)) 
        {
            GetWorkerData();
            alert.setTitle("Siker");
            alert.setHeaderText("A dolgozó adatait sikeresen lekérte!");
            alert.setContentText("Most már az adatok a box-okban szerepelnek!");
        }
       alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        BuyCurrencyChoiceBox.setValue("GBP");
        BuyCurrencyChoiceBox.setItems(CurrencyList);
        SellPriceCurrencyChoiceBox.setValue("GBP");
        SellPriceCurrencyChoiceBox.setItems(CurrencyList);
        EditCurrencyChoiceBox.setValue("GBP");
        EditCurrencyChoiceBox.setItems(CurrencyList);
        ChoiceBoxesUpdate();
    }
}
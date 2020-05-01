/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.viewtest;

import hu.unideb.inf.model.Cars;
import hu.unideb.inf.model.Model;
import hu.unideb.inf.model.Workers;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;

/**
 *
 * @author kodis
 */
public class FXMLCarsSceneController1 {
    
    private Model model;
    List<Cars> carss = new ArrayList<>();
    List<Workers> workerss = new ArrayList<>();

    public void setModel(Model model) 
    {
        this.model = model;
    }
    
    //A bemenet speciális karaktert tartalmaz-e//
    boolean IsSpecialChars(String Special)
    {
        String specialChars = "=/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        if (Special.endsWith("-") || Special.startsWith("-")
                || Special.endsWith(" ") || Special.startsWith(" "))
        {
            return true;
        }
        for (char c : Special.toCharArray())
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
    boolean IsDig(String Dig)
    {
        for (char c : Dig.toCharArray())
        {
            if(Character.isDigit(c))
            {
                return true;
            }
        }
        return false;
    }
    
    //Tartalmaz-e betüt//
    boolean IsLet(String Let)
    {
        for (char c : Let.toCharArray())
        {
            if(Character.isLetter(c))
            {
                return true;
            }
        }
        return false;
    }
    
    //Csak számot tartalmaz?//
    boolean IsOnlyDig(String Let)
    {
        int count = 0;
        for (char c : Let.toCharArray()) 
        {
            if(Character.isDigit(c))
            {
                count++;
            }
        }
        return Let.length() == count;
    }
    
    //A beállított pénznem szerinti összeg mentés//
    double SetPriceValue(String BuyCurrency, String PriceTextTitle)
    {     
        double result = 0;
        switch (BuyCurrency)
        {
            case "GBP":
                result = (Double.parseDouble(PriceTextTitle) * 1.2);
                break;
            case "HUF":
                result = (Math.round((Double.parseDouble(PriceTextTitle) / 400.0) * 1.2));
                break;
            case "EUR":
                result = (Math.round((Double.parseDouble(PriceTextTitle) / 1.2) * 1.2));
                break;
            case "USD":
                result = (Math.round((Double.parseDouble(PriceTextTitle) / 1.25) * 1.2));
                break;
            default:
                break;
        }
        return result;
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
    
    //Bemenetek kezelése//
    boolean IsHandleUploadButtonPushed(Model model)
    {
        if(model.getCar().getBrand().isBlank() || model.getCar().getType().isBlank()
                || String.valueOf(model.getCar().getYear()).isBlank() || model.getCar().getMotor().isBlank()
                || model.getCar().getChassisNumber().isBlank() || model.getCar().getColor().isBlank()
                || String.valueOf(model.getCar().getPrice()).isBlank())
        {
            return false;
        }else if(IsSpecialChars(model.getCar().getBrand()) || IsSpecialChars(model.getCar().getType())
                || IsSpecialChars(String.valueOf(model.getCar().getYear())) || IsSpecialChars(model.getCar().getChassisNumber()) || IsSpecialChars(model.getCar().getColor()))
        {
            return false;
        }else if(IsDig(model.getCar().getBrand()) || IsDig(model.getCar().getColor()))
        {
            return false;
        }else if(IsLet(String.valueOf(model.getCar().getYear())) || IsLet(String.valueOf(model.getCar().getPrice())))
        {
            return false;
        }else if(String.valueOf(model.getCar().getYear()).length() != 4)
        {
            return false;
        }else if (model.getCar().getChassisNumber().length() != 17)
        {
            return false;
        }else if (IsOnlyDig(model.getCar().getChassisNumber()) || IsOnlyDig(model.getCar().getType()))
        {
            return false;
        }
        return true;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hallgato
 */
public class Cars implements Serializable 
{

    private StringProperty Brand = new SimpleStringProperty();
    private StringProperty Type = new SimpleStringProperty();
    private int Year;
    private StringProperty Motor = new SimpleStringProperty();
    private StringProperty Color = new SimpleStringProperty();
    private StringProperty ChassisNumber = new SimpleStringProperty();
    private int Price;
    private StringProperty Currency = new SimpleStringProperty();

    public Cars(String Brand,String Type,int Year,String Motor,String Color, String ChassisNumber, int Price, String Currency) 
    {
        this.Brand.setValue(Brand);
        this.Type.setValue(Type);
        this.Year = Year;
        this.Motor.setValue(Motor);
        this.Color.setValue(Color);
        this.ChassisNumber.setValue(ChassisNumber);
        this.Price = Price;
        this.Currency.setValue(Currency);
    }

    public StringProperty BrandProperty()
    {
        return Brand;
    }
    
    public StringProperty TypeProperty()
    {
        return Type;
    }
    
    
    public StringProperty MotorProperty()
    {
        return Motor;
    }
    
    public StringProperty ColorProperty() 
    {
        return Color;
    }
    
    public StringProperty ChassisNumberProperty() 
    {
        return ChassisNumber;
    }
    
    public StringProperty CurrencyProperty() 
    {
        return Currency;
    }
    
    public String getBrand() 
    {
        return Brand.getValue();
    }

    public void setBrand(String Brand)
    {
        this.Brand.setValue(Brand);
    }
    
    public String getType() 
    {
        return Type.getValue();
    }

    public void setType(String Type) 
    {
        this.Type.setValue(Type);
    }
    
    public int getYear() 
    {
        return Year;
    }

    public void setYear(int Year) 
    {
        this.Year = Year;
    }
   
    public String getMotor() 
    {
        return Motor.getValue();
    }

    public void setMotor(String Motor) 
    {
        this.Motor.setValue(Motor);
    }
    
    public String getColor() 
    {
        return Color.getValue();
    }

    public void setColor(String Color) 
    {
        this.Color.setValue(Color);
    }
       
    public String getChassisNumber() 
    {
        return ChassisNumber.getValue();
    }

    public void setChassisNumber(String ChassisNumber) 
    {
        this.ChassisNumber.setValue(ChassisNumber);
    }

    public int getPrice()
    {
        return Price;
    }

    public void setPrice(int Price)
    {
        this.Price = Price;
    }
    
    public String getCurrency() 
    {
        return Currency.getValue();
    }

    public void setCurrency(String Currency) 
    {
        this.Currency.setValue(Currency);
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException 
    {
        s.writeUTF(Brand.getValue());
        s.writeUTF(Type.getValue());
        s.writeInt(Year);
        s.writeUTF(Motor.getValue());
        s.writeUTF(Color.getValue());
        s.writeUTF(ChassisNumber.getValue());
        s.writeInt(Price);
        s.writeUTF(Currency.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        this.Brand = new SimpleStringProperty(s.readUTF());
        this.Type = new SimpleStringProperty(s.readUTF());
        this.Year = s.readInt();
        this.Motor = new SimpleStringProperty(s.readUTF());
        this.Color = new SimpleStringProperty(s.readUTF());
        this.ChassisNumber = new SimpleStringProperty(s.readUTF());
        this.Price = s.readInt();
        this.Currency = new SimpleStringProperty(s.readUTF());
    }
}

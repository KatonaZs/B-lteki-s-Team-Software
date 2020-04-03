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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hallgato
 */
@Entity
@Table(name = "cars")
public class Cars implements Serializable 
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Brand")
    private String Brand ;
    @Column(name = "Type")
    private String Type;
    @Column(name = "Year")
    private int Year;
    @Column(name = "Motor")
    private String Motor;
    @Column(name = "Color")
    private String Color;
    @Column(name = "ChassisNumber")
    private String ChassisNumber;
    @Column(name = "Price")
    private int Price;
    @Column(name = "Currency")
    private String Currency;

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String Motor) {
        this.Motor = Motor;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getChassisNumber() {
        return ChassisNumber;
    }

    public void setChassisNumber(String ChassisNumber) {
        this.ChassisNumber = ChassisNumber;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public Cars(String Brand, String Type, int Year, String Motor, String Color, String ChassisNumber, int Price, String Currency) {
        this.Brand = Brand;
        this.Type = Type;
        this.Year = Year;
        this.Motor = Motor;
        this.Color = Color;
        this.ChassisNumber = ChassisNumber;
        this.Price = Price;
        this.Currency = Currency;
    }

    
    
    private void writeObject(ObjectOutputStream s) throws IOException 
    {
        s.writeUTF(Brand);
        s.writeUTF(Type);
        s.writeInt(Year);
        s.writeUTF(Motor);
        s.writeUTF(Color);
        s.writeUTF(ChassisNumber);
        s.writeInt(Price);
        s.writeUTF(Currency);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        this.Brand = s.readUTF();
        this.Type = s.readUTF();
        this.Year = s.readInt();
        this.Motor = s.readUTF();
        this.Color = s.readUTF();
        this.ChassisNumber =s.readUTF();
        this.Price = s.readInt();
        this.Currency = s.readUTF();
    }
}

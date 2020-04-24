/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Katona Zsombor
 */

@Entity
@Table(name = "workers")
public class Workers 
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    
    @Column(name = "Name")
    private String Name;
    @Column(name = "BDate")
    private String Bdate;
    @Column(name = "IN")
    private String IN;
    @Column(name = "Profit")
    private double Profit;
    @Column(name = "CarCount")
    private int CarCount;
    

    public Workers() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public String getBdate() {
        return Bdate;
    }

    public void setBdate(String Bdate) {
        this.Bdate = Bdate;
    }
    public String getIN() {
        return IN;
    }

    public void setIN(String IN) {
        this.IN = IN;
    }
    
    public double getProfit() {
        return Profit;
    }
     
    public void setProfit(double Profit) {
       this.Profit = Profit;
    }
      
    public int getCarCount() {
       return CarCount;
    }
    
    public void setCarCount(int CarCount) {
       this.CarCount = CarCount;
    }

    public Workers(String Name, String Bdate, String IN, double Profit, int CarCount) {
        this.Name = Name;
        this.Bdate = Bdate;
        this.IN = IN;
        this.Profit = Profit;
        this.CarCount = CarCount;
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException 
    {
        s.writeUTF(Name);
        s.writeUTF(Bdate);
        s.writeUTF(IN);
        s.writeDouble(Profit);
        s.writeInt(CarCount);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        this.Name = s.readUTF();
        this.Bdate = s.readUTF();
        this.IN = s.readUTF();
        this.Profit = s.readDouble();
        this.CarCount = s.readInt();
    }
}

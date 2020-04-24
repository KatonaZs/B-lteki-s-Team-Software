/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;


/**
 *
 * @author hallgato
 */
public class Model
{
    private Cars Car;
    private Workers Worker;
    
    public Cars getCar() 
    {
        return Car;
    }
    
    public Workers getWorker()
    {
        return Worker;
    }
    
    public Model() {
        Car = new Cars("BMW", "M3",2000,"Benzin","Fekete","2001230asd",25000,"GBP","la");
        Worker = new Workers("Lakatos József","1976.05.21","123456MA",0,0);
    }
}

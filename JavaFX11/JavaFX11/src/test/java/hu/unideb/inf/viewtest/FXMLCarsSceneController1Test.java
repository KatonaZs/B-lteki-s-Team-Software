/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.viewtest;

import hu.unideb.inf.model.Cars;
import hu.unideb.inf.model.Model;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author kodis
 */
@DisplayName("Belteki's Car KFT. applikáció teszt")
public class FXMLCarsSceneController1Test {
    
    private Model model;
    
    public void setModel(Model model) 
    {
        this.model = model;
    }
    
    public FXMLCarsSceneController1Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        model = new Model();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    
    private static Stream<Arguments> testDataIsSpecialChars(){
        return Stream.of(
                Arguments.of("BMW", false),
                Arguments.of("BMW/", true),
                Arguments.of("B!MW", true),
                Arguments.of("BM%W", true),
                Arguments.of("=BMW", true)
        );
    }
    
    /**
     * A bemenet speciális karaktert tartalmaz-e
     */
    @ParameterizedTest
    @MethodSource("testDataIsSpecialChars")
    void testIsSpecialChars(String Special, boolean expResult) {
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result = instance.IsSpecialChars(Special);
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testIsSpecialChars: A bemenet speciális karaktert tartalmaz-e: PASSED");
        }else{
            System.out.println("testIsSpecialChars: A bemenet speciális karaktert tartalmaz-e: FAILED");
        }
        assertEquals(expResult, result);
    }

    private static Stream<Arguments> testDataIsDig(){
        return Stream.of(
                Arguments.of("BMW", false),
                Arguments.of("BMW1", true),
                Arguments.of("BM2W", true),
                Arguments.of("B3MW", true),
                Arguments.of("4BMW", true)
        );
    }
    
    /**
     * A bemenet tartalmaz-e számot
     */
    @ParameterizedTest
    @MethodSource(value = "testDataIsDig")
    void testIsDig(String Dig, boolean expResult) {
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result = instance.IsDig(Dig);
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testIsDig: A bemenet tartalmaz-e számot: PASSED");
        }else{
            System.out.println("testIsDig: A bemenet tartalmaz-e számot: FAILED");
        }
        assertEquals(expResult, result);
    }
    
    private static Stream<Arguments> testDataIsLet(){
        return Stream.of(
                Arguments.of("123", false),
                Arguments.of("B23", true),
                Arguments.of("1M3", true),
                Arguments.of("12W", true)
        );
    }

    /**
     * Tartalmaz-e betüt
     */
    @ParameterizedTest
    @MethodSource("testDataIsLet")
    void testIsLet(String Let, boolean expResult) {
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result = instance.IsLet(Let);
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testIsLet: Tartalmaz-e betűt: PASSED");
        }else{
            System.out.println("testIsLet: Tartalmaz-e betűt: FAILED");
        }
        assertEquals(expResult, result);
    }
    
    private static Stream<Arguments> testDataIsOnlyDig(){
        return Stream.of(
                Arguments.of("123", true),
                Arguments.of("B23", false),
                Arguments.of("1M3", false),
                Arguments.of("12W", false)
        );
    }
    
    /**
     * Csak számot tartalmaz?
     */
    @ParameterizedTest
    @MethodSource("testDataIsOnlyDig")
    void testIsOnlyDig(String Dig, boolean expResult) {
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result = instance.IsOnlyDig(Dig);
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testIsOnlyDig: Csak számot tartalmaz-e: PASSED");
        }else{
            System.out.println("testIsOnlyDig: Csak számot tartalmaz-e: FAILED");
        }
        assertEquals(expResult, result);
    }
    
    /**
     * A beállított pénznem szerinti összeg mentés
     */
    @ParameterizedTest
    @ValueSource(strings = {"GBP", "HUF", "EUR", "USD"})
    void testSetPriceValue(String Currency){
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        String Price = "10000";
        double result = instance.SetPriceValue(Currency, Price);
        double expResult = 1;
        switch (Currency)
        {
            case "GBP":
                expResult = (Double.parseDouble(Price) * 1.2);
                break;
            case "HUF":
                expResult = (Math.round((Double.parseDouble(Price) / 400.0) * 1.2));
                break;
            case "EUR":
                expResult = (Math.round((Double.parseDouble(Price) / 1.2) * 1.2));
                break;
            case "USD":
                expResult = (Math.round((Double.parseDouble(Price) / 1.25) * 1.2));
                break;
            default:
                break;
        }
        if(Double.compare(result, expResult) == 0){
            System.out.println("testSetPriceValue: A beállított pénznem szerinti összeg mentés: PASSED");
        }else{
            System.out.println("testSetPriceValue: A beállított pénznem szerinti összeg mentés: FAILED");
        }
        assertEquals(expResult, result);
    }
    
    private static Stream<Arguments> testDataBadDateFormatum(){
        return Stream.of(
                Arguments.of("2020.04.25", false),
                Arguments.of("2020.4.5", true),
                Arguments.of("2020.f4.25", true),
                Arguments.of("2020-04-25", true)
        );
    }
    
    /**
     * Hibás-e a megadott dátum
     */
    @ParameterizedTest
    @MethodSource("testDataBadDateFormatum")
    void testBadDateFormatum(String Date, boolean expResult){
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result = instance.BadDateFormatum(Date);
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testBadDateFormatum: Helyes-e a megadott dátum: PASSED");
        }else{
            System.out.println("testBadDateFormatum: Helyes-e a megadott dátum: FAILED");
        }
        assertEquals(expResult, result);
    }
    
    
    /**
     * Helyesek-e a megadott beviteli adatok
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/IsHandleUploadButtonPushed.csv", numLinesToSkip = 1)
    void testIsHandleUploadButtonPushed(String Car, boolean expResult){
        FXMLCarsSceneController1 instance = new FXMLCarsSceneController1();
        boolean result;
        String[] token = Car.split(":");
        model.getCar().setBrand(token[0]);
        model.getCar().setType(token[1]);
        model.getCar().setYear(Integer.parseInt(token[2]));
        model.getCar().setMotor(token[3]);
        model.getCar().setColor(token[4]);
        model.getCar().setChassisNumber(token[5]);
        if(!instance.IsLet(token[6])){
            model.getCar().setPrice(Double.parseDouble(token[6]));
            model.getCar().setCurrency(token[7]);
            model.getCar().setLicenseNumber(token[8]);
            result = instance.IsHandleUploadButtonPushed(model);
        }else
            result = false;
        if(Boolean.compare(result, expResult) == 0){
            System.out.println("testIsHandleUploadButtonPushed: Helyesek-e a megadott beviteli adatok: PASSED");
        }else{
            System.out.println("testIsHandleUploadButtonPushed: Helyesek-e a megadott beviteli adatok: FAILED");
        }
        assertEquals(expResult, result);
    }
}
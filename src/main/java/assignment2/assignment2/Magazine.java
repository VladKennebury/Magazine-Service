package assignment2.assignment2;
import java.io.Serializable;
import java.util.*;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - Magazine.Java
 * 
 * Purpose - Stores a list of built in supplements and customers, as well as
 * the weekly cost for the main part of the magazine
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class Magazine implements Serializable{
    ///Stores magazine weekly cost
    private int m_weeklyCost;
    ///Stores list of supplements
    private ArrayList<Supplement> m_supplements;
    ///Stores list of customers
    private ArrayList<Customer> m_customers;
    
    /**
    * Default constructor 
    */
    Magazine(){
        m_weeklyCost = 0;
        m_supplements = new ArrayList();
        m_customers = new ArrayList();
    }
    
    /**
    * Getter for the magazine supplement array
    * @return m_supplements
    */
    public ArrayList<Supplement> GetSupplements(){
        return m_supplements;
    }
    
    /**
    * Getter for magazine weekly cost
    * @return m_weeklyCost
    */
    public int GetWeeklyCost(){
        return m_weeklyCost;
    }
    
    /**
    * Setter for magazine weekly cost
    * @param cost
    */
    public void SetWeeklyCost(int cost){
        m_weeklyCost = cost;
    }
    
    /**
    * Getter for magazine customers
    * @return m_weeklyCost
    */
    public ArrayList<Customer> GetCustomers(){
        return m_customers;
    }
}

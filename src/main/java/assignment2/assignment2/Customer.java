package assignment2.assignment2;
import java.io.Serializable;
import java.util.*;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - Customer.Java
 * 
 * Purpose - Parent class of PayingCustomer and AssociateCustomer
 * storing shared customer details such as name, email and array of
 * supplements.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Customer is never instantiated itself, however allows
 * for polymorphism by storing paying and associate customers within
 * a customer array.
 * 
 * Names cannot be contain numbers such as - Bob123
 * Emails can contain numbers and letters, it is assumed that the
 * input for them will be accurate - bob123@email.com
 */
public class Customer implements Serializable{
    
    /**
    * Stores enumerators used to identify the type of customer
    */
    public enum Type{
        PAYING,
        ASSOCIATE
    }
    
    /// Stores customer name
    private String m_name;
    /// Stores address
    private Address m_address;
    /// Stores customer email
    private String m_email;
    /// Stores customer type
    private Type m_customerType;
    /// Stores customer's list of supplements
    private ArrayList<Supplement> m_supplements;
    
    /**
    * Default constructor 
    */
    Customer(){
        m_name = "";
        m_email = "";
        m_supplements = new ArrayList();
    }
    
    /**
    * Overloaded constructor 
    */
    Customer(String name, String email, Address address, Type type){
        m_name = name;
        m_email = email;
        m_address = address;
        m_customerType = type;
    }
    
    /**
    * Getter for customer name
    * @return m_name
    */
    public String GetName(){
        return m_name;
    }
    
    /**
    * Setter for customer name
    * @param name
    */
    public void SetName(String name){
        m_name = name;
    }
    
    /**
    * Getter for customer address
    * @return m_address
    */
    public Address GetAddress(){
        return m_address;
    }
    
    /**
    * Setter for customer address
    * @param address
    */
    public void SetAddress(Address address){
        m_address = address;
    }
    
    /**
    * Getter for customer email
    * @return m_email
    */
    public String GetEmail(){
        return m_email;
    }
    
    /**
    * Setter for customer email
    * @param email
    */
    public void SetEmail(String email){
        m_email = email;
    }
    
    /**
    * Getter for customer type
    * @return m_customerType
    */
    public Type GetType(){
        return m_customerType;
    }
    
    /**
    * Setter for customer type
    * @param type
    */
    public void SetType(Type type){
        m_customerType = type;
    }
    
    /**
    * Getter for customer supplement array
    * @return m_supplements
    */
    public ArrayList<Supplement> GetSupplements(){
        return m_supplements;
    }
    
    /**
    * Adds supplements to customer supplement array
    * @param supplement
    */
    public void AddSupplement(Supplement supplement){
        m_supplements.add(supplement);
    }
}

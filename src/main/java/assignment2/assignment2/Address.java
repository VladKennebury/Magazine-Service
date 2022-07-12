package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - Address.Java
 * 
 * Purpose - Stores details of an address including street number, street name, 
 * suburb and postcode
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class Address implements Serializable{
    /// Stores street number
    private int m_streetNumber;
    /// Stores street name
    private String m_streetName;
    /// Stores suburb
    private String m_suburb;
    /// Stores postcode
    private int postcode;
    
    /**
    * Default constructor 
    */
    Address(){
        m_streetNumber = 0;
        m_streetName = "";
        m_suburb = "";
        postcode = 0;
    }
    
    /**
    * Overloaded constructor 
    */
    Address(int number, String name, String suburb, int post){
        m_streetNumber = number;
        m_streetName = name;
        m_suburb = suburb;
        postcode = post;
    }
    
    /**
    * Getter for street number
    * @return m_streetNumber
    */
    public int GetStreetNumber(){
        return m_streetNumber;
    }
    
    /**
    * Setter for street number
    * @param number
    */
    public void SetStreetNumber(int number){
        m_streetNumber = number;
    }
    
    /**
    * Getter for street name
    * @return m_streetName
    */
    public String GetStreetName(){
        return m_streetName;
    }
    
    /**
    * Setter for street name
    * @param name
    */
    public void SetStreetName(String name){
        m_streetName = name;
    }
    
    /**
    * Getter for suburb
    * @return m_suburb
    */
    public String GetSuburb(){
        return m_suburb;
    }
    
    /**
    * Setter for suburb
    * @param suburb
    */
    public void SetSuburb(String suburb){
        m_suburb = suburb;
    }
    
    /**
    * Getter for postcode
    * @return m_postcode
    */
    public int GetPostcode(){
        return postcode;
    }
    
    /**
    * Setter for postcode
    * @param post
    */
    public void SetPostcode(int post){
        postcode = post;
    }
    
}

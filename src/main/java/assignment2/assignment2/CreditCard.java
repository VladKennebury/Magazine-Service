package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - CreditCard.Java
 * 
 * Purpose - Subclass of PaymentMethod, used to store credit card information
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Credit Card number cam be entered as 0123 4567, as well as
 * 01234567, this allows polymorphism and thus it is stored as a string.
 * 
 */
public class CreditCard extends PaymentMethod implements Serializable{
    /// Stores credit card number
    private String m_creditCardNumber;
    
    /**
    * Getter for the credit card number - Overrides parent method
    * @return m_creditCardNumber
    */
    public String GetPaymentInformation(){
        return m_creditCardNumber;
    }
    
    /**
    * Setter for the credit card number - Overrides parent method
    * @param information
    */
    public void SetPaymentInformation(String information){
        m_creditCardNumber = information;
    }
}

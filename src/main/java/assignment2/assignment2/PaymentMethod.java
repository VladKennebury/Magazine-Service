package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - PaymentMethod.Java
 * 
 * Purpose - Parent class of CreditCard and DirectDebit, storing the payment
 * type, and methods to be overloaded by child classes.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Payment method isn't directly used, but acts as a psuedo
 * abstract class for the child classes.
 * 
 */
public class PaymentMethod implements Serializable{
    
    /**
    * Stores enumerators used to identify the type of payment method
    */
    public enum Type{
        CREDITCARD,
        DIRECTDEBIT
    }
    
    ///Stores the type of payment method
    private Type m_paymentType;
    ///Stores the type of payment information
    private String m_paymentInformation;
    
    /**
    * Getter for the payment information
    * @return m_paymentInformation
    */
    public String GetPaymentInformation(){
        return m_paymentInformation;
    }
    
    /**
    * Getter for the payment type
    * @return m_paymentType
    */
    public Type GetPaymentType(){
        return m_paymentType;
    }
    
    /**
    * Setter for the payment type
    * @param type
    */
    public void SetPaymentType(Type type){
        m_paymentType = type;
    }
}

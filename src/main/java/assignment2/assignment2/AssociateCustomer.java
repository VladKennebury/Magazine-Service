package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - AssociateCustomer.Java
 * 
 * Purpose - Subclass of Customer, containing extra details such as a
 * paying customer that the associate is tied to.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class AssociateCustomer extends Customer implements Serializable{
    ///Stores the payingCustomer tied to the associate
    private PayingCustomer m_payingCustomer;
    
    /**
    * Overloaded Default constructor 
    */
    AssociateCustomer(String name, String email, Address address, Type type){
        SetName(name);
        SetEmail(email);
        SetAddress(address);
        SetType(type);
    }
    
    /**
    * Getter for the paying customer
    * @return m_payingCustomer
    */
    public PayingCustomer GetPayingCustomer(){
        return m_payingCustomer;
    }
    
    /**
    * Setter for the paying customer
    * @param payingCustomer
    */
    public void SetPayingCustomer(PayingCustomer payingCustomer){
        m_payingCustomer = payingCustomer;
    }
}

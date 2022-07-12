package assignment2.assignment2;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - PayingCustomer.Java
 * 
 * Purpose - Subclass of Customer, containing extra details such as a
 * payment method, and list of associate customers that they pay for.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class PayingCustomer extends Customer implements Serializable{
    ///Stores the payment method
    private PaymentMethod m_paymentMethod;
    ///Stores the array of associate customers
    private ArrayList<AssociateCustomer> m_associates;
    
    /**
    * Overloaded Default constructor 
    */
    PayingCustomer(String name, String email, Address address, Type type){
        SetName(name);
        SetEmail(email);
        SetAddress(address);
        SetType(type);
        m_associates = new ArrayList();
        m_paymentMethod = new PaymentMethod();
    }
    
    /**
    * Getter for the array of associate customers
    * @return m_associates
    */
    public ArrayList<AssociateCustomer> GetAssociates(){
        return m_associates;
    }
    
    /**
    * Setter for the array of associate customers
    * @param associate
    */
    public void AddAssociate(AssociateCustomer associate){
        m_associates.add(associate);
    }
    
    /**
    * Setter for the payment method
    * @param payment
    */
    public void SetPaymentMethod(PaymentMethod payment){
        m_paymentMethod = payment;
    }
    
    /**
    * Getter for the payment method
    * @return m_paymentMethod
    */
    public PaymentMethod GetPaymentMethod(){
        return m_paymentMethod;
    }
}

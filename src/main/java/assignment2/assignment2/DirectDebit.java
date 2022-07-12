package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - DirectDebit.Java
 * 
 * Purpose - Subclass of PaymentMethod, used to store bank account information
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Bank Account number cam be entered as BSB01234567, as well as
 * 01234567, this allows polymorphism and thus it is stored as a string.
 * 
 */
public class DirectDebit extends PaymentMethod implements Serializable{
    /// Stores bank account number
    private String m_bankAccount;
    
    /**
    * Getter for the bank account number - Overrides parent method
    * @return m_bankAccount
    */
    public String GetPaymentInformation(){
        return m_bankAccount;
    }
    
    /**
    * Setter for the bank account number - Overrides parent method
    * @param information
    */
    public void SetPaymentInformation(String information){
        m_bankAccount = information;
    }
}

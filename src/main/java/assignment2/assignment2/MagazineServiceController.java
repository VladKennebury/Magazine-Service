package assignment2.assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - MagazineServiceController.Java
 * 
 * Purpose - Provides methods to manipulate a stored magazine, as well as the 
 * loading and saving of the magazine to a file with the use of serialisation.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Files are saved in the local project directory
 */
public class MagazineServiceController {
    
    /// Constructed magazine
    private Magazine m_magazine;
    /// Stores the magazine's file name
    private String m_fileName;
    /// Stores the save state of the magazine
    private boolean m_isSaved;
    
    /**
    * Default constructor 
    */
    MagazineServiceController(){
        m_magazine = new Magazine();
        m_fileName = "";
        m_isSaved = false;
    }
    
    /**
    * Getter for magazine
    * @return m_magazine
    */
    public Magazine GetMagazine(){
        return m_magazine;
    }
    
    /**
    * Setter for magazine information
    * @param cost
    */
    public void EditMagazineInformation(int cost){
        m_magazine.SetWeeklyCost(cost);
    }
    
    /**
    * Getter for file name
    * @return m_fileName
    */
    public String GetFileName(){
        return m_fileName;
    }
    
    /**
    * Getter for isSaved
    * @return m_isSaved
    */
    public boolean GetIsSaved(){
        return m_isSaved;
    }
    
    /**
    * Creates a new magazine and resets initial values
    */
    public void CreateNewMagazine(){
        m_magazine = new Magazine();
        m_isSaved = false;
        m_fileName = "";
    }
    
    /**
    * Saves the magazine in the local project directory using serialisation
    * @param name
    */
    public void SaveMagazine(String name){
        try
        {
            if(m_isSaved){
                m_fileName = name;
            }
            else{
                m_fileName = name + ".bin";
            }
            
            File file = new File(name);
            if(file.exists()){
                file.delete();
            }
            
            FileOutputStream fileOutputStream = new FileOutputStream(m_fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(m_magazine);
            objectOutputStream.close();
            fileOutputStream.close();
            m_isSaved = true;
        } 
        catch (IOException ioe) 
        {}
    }
    
    /**
    * Deserialiises and loads a magazine object from a file 
    * @param name
    */
    public void LoadMagazine(String name){  
        try
        {
            FileInputStream fileInputStream = new FileInputStream(name);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
 
            m_magazine = (Magazine)objectInputStream.readObject();
 
            objectInputStream.close();
            fileInputStream.close();
            m_isSaved = true;
            m_fileName = name;
        } 
        catch (IOException ioe) 
        {} 
        catch (ClassNotFoundException c) 
        {}
    }
    
    /**
    * Loads a demonstration file containing customers, supplements 
    * and magazine information
    */
    public void Demonstration(){
        LoadMagazine("Magazine2.bin");
    }
    
    /**
    * Adds a paying customer to the service, specifying customer details
    * and payment method
    * @param name
    * @param email
    * @param streetNumber
    * @param streetName
    * @param suburb
    * @param postcode
    * @param billingType
    * @param billingInformation
    */
    public void AddPayingCustomer(String name, String email, int streetNumber, String streetName, 
            String suburb, int postcode, String billingType, String billingInformation){
        
        Address newAddress = new Address(streetNumber, streetName, suburb, postcode);
        
        PayingCustomer newPayingCustomer = new PayingCustomer(name, email, newAddress, Customer.Type.PAYING);
        
        if("Credit Card".equals(billingType)){
            
            CreditCard creditCard = new CreditCard();
            creditCard.SetPaymentInformation(billingInformation);
            creditCard.SetPaymentType(PaymentMethod.Type.CREDITCARD);
            
            newPayingCustomer.SetPaymentMethod(creditCard);
            m_magazine.GetCustomers().add(newPayingCustomer);
        }
        
        if("Direct Debit".equals(billingType)){
            
            DirectDebit directDebit = new DirectDebit();
            directDebit.SetPaymentInformation(billingInformation);
            directDebit.SetPaymentType(PaymentMethod.Type.DIRECTDEBIT);
            
            newPayingCustomer.SetPaymentMethod(directDebit);
            m_magazine.GetCustomers().add(newPayingCustomer);
        }
    }
    
    /**
    * Adds an associate customer to the service, including customer details,
    * and the paying customer to associate with
    * @param name
    * @param email
    * @param streetNumber
    * @param streetName
    * @param suburb
    * @param postcode
     * @param payerName
    */
    public void AddAssociateCustomer(String name, String email, int streetNumber, String streetName, 
            String suburb, int postcode, String payerName){
        
        Address newAddress = new Address(streetNumber, streetName, suburb, postcode);
        
        AssociateCustomer newAssociateCustomer = new AssociateCustomer(name, email, newAddress, Customer.Type.ASSOCIATE);
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(payerName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                ((PayingCustomer)m_magazine.GetCustomers().get(i)).AddAssociate(newAssociateCustomer);
                newAssociateCustomer.SetPayingCustomer(((PayingCustomer)m_magazine.GetCustomers().get(i)));
            }  
        }
        
        m_magazine.GetCustomers().add(newAssociateCustomer);
    }
    
    /**
    * Edits a paying customers details, specifying customer details
    * and payment method
    * @param oldName
    * @param newName
    * @param email
    * @param streetNumber
    * @param streetName
    * @param suburb
    * @param postcode
    * @param billingType
    * @param billingInformation
    */
    public void EditPayingCustomer(String oldName, String newName, String email, int streetNumber, String streetName, 
            String suburb, int postcode, String billingType, String billingInformation){
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(m_magazine.GetCustomers().get(i).GetName().equalsIgnoreCase(oldName)){
                m_magazine.GetCustomers().get(i).SetName(newName);
                m_magazine.GetCustomers().get(i).SetEmail(email);
                m_magazine.GetCustomers().get(i).GetAddress().SetStreetNumber(streetNumber);
                m_magazine.GetCustomers().get(i).GetAddress().SetStreetName(streetName);
                m_magazine.GetCustomers().get(i).GetAddress().SetSuburb(suburb);
                m_magazine.GetCustomers().get(i).GetAddress().SetPostcode(postcode);

                if("Credit Card".equals(billingType)){

                    CreditCard creditCard = new CreditCard();
                    creditCard.SetPaymentInformation(billingInformation);
                    creditCard.SetPaymentType(PaymentMethod.Type.CREDITCARD);

                    ((PayingCustomer)m_magazine.GetCustomers().get(i)).SetPaymentMethod(creditCard);
                }

                if("Direct Debit".equals(billingType)){

                    DirectDebit directDebit = new DirectDebit();
                    directDebit.SetPaymentInformation(billingInformation);
                    directDebit.SetPaymentType(PaymentMethod.Type.DIRECTDEBIT);

                    ((PayingCustomer)m_magazine.GetCustomers().get(i)).SetPaymentMethod(directDebit);
                }
            }
        }
    }
    
    /**
    * Edits an associate customers details, specifying customer details
    * and paying customer
    * @param oldName
    * @param newName
    * @param email
    * @param streetNumber
    * @param streetName
    * @param suburb
    * @param postcode
    * @param newPayer
    */
    public void EditAssociateCustomer(String oldName, String newName, String email, int streetNumber, String streetName, 
            String suburb, int postcode, String newPayer){
        
        String oldPayer = "";
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(m_magazine.GetCustomers().get(i).GetName().equalsIgnoreCase(oldName)){
                m_magazine.GetCustomers().get(i).SetName(newName);
                m_magazine.GetCustomers().get(i).SetEmail(email);
                m_magazine.GetCustomers().get(i).GetAddress().SetStreetNumber(streetNumber);
                m_magazine.GetCustomers().get(i).GetAddress().SetStreetName(streetName);
                m_magazine.GetCustomers().get(i).GetAddress().SetSuburb(suburb);
                m_magazine.GetCustomers().get(i).GetAddress().SetPostcode(postcode);
                
                oldPayer = ((AssociateCustomer)m_magazine.GetCustomers().get(i)).GetPayingCustomer().GetName();
                
                for(int j = 0; j < m_magazine.GetCustomers().size(); j++){
                    if(m_magazine.GetCustomers().get(j).GetName().equalsIgnoreCase(newPayer)){
                        ((PayingCustomer)m_magazine.GetCustomers().get(j)).AddAssociate(((AssociateCustomer)m_magazine.GetCustomers().get(i)));
                        ((AssociateCustomer)m_magazine.GetCustomers().get(i)).SetPayingCustomer(((PayingCustomer)m_magazine.GetCustomers().get(j)));
                    }
                }
            }
        }
        
        //Remove associate from old payer
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(m_magazine.GetCustomers().get(i).GetName().equalsIgnoreCase(oldPayer)){
                for(int j = 0; j < ((PayingCustomer)m_magazine.GetCustomers().get(i)).GetAssociates().size(); j++){
                    if(((PayingCustomer)m_magazine.GetCustomers().get(i)).GetAssociates().get(j).GetName().equalsIgnoreCase(newName)){
                        ((PayingCustomer)m_magazine.GetCustomers().get(i)).GetAssociates().remove(j);
                    }
                }
            }
        }
    }
    
    /**
    * Adds a supplement to a specified customer
    * 
    * @param customerName
    * @param supplementName
    * @param subsciptionType
    */
    public void AddSupplementToCustomer(String customerName, String supplementName, String subsciptionType){
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(customerName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                for(int j = 0; j < m_magazine.GetSupplements().size(); j++){
                    if(supplementName.equalsIgnoreCase(m_magazine.GetSupplements().get(j).GetName())){
                        Supplement newSupplement = new Supplement(m_magazine.GetSupplements().get(j));

                        //Adds the specified supplement based on subscription type
                        if(subsciptionType.equalsIgnoreCase("Weekly")){
                            newSupplement.SetSubscriptionType(Supplement.Type.WEEKLY);
                            m_magazine.GetCustomers().get(i).AddSupplement(newSupplement);
                        }

                        if(subsciptionType.equalsIgnoreCase("Fortnightly")){
                            newSupplement.SetSubscriptionType(Supplement.Type.FORTNIGHTLY);
                            m_magazine.GetCustomers().get(i).AddSupplement(newSupplement);
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Edits a supplement subscription belonging to a specified customer
    * 
    * @param customerName
    * @param supplementName
    * @param subsciptionType
    */
    public void EditCustomerSupplement(String customerName, String supplementName, String subsciptionType){
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(customerName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                for(int j = 0; j < m_magazine.GetCustomers().get(i).GetSupplements().size(); j++){
                    if(supplementName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetSupplements().get(j).GetName())){

                        //Adds the specified supplement based on subscription type
                        if(subsciptionType.equalsIgnoreCase("Weekly")){
                            m_magazine.GetCustomers().get(i).GetSupplements().get(j).SetSubscriptionType(Supplement.Type.WEEKLY);
                        }

                        if(subsciptionType.equalsIgnoreCase("Fortnightly")){
                            m_magazine.GetCustomers().get(i).GetSupplements().get(j).SetSubscriptionType(Supplement.Type.FORTNIGHTLY);
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Edits a supplements name and cost, which belongs to a specified customer
    * 
    * @param oldSupplementName
    * @param newSupplementName
    * @param cost
    */
    public void EditCustomerSupplement(String oldSupplementName, String newSupplementName, int cost){
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            for(int j = 0; j < m_magazine.GetCustomers().get(i).GetSupplements().size(); j++){
                if(oldSupplementName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetSupplements().get(j).GetName())){
                    m_magazine.GetCustomers().get(i).GetSupplements().get(j).SetName(newSupplementName);
                    m_magazine.GetCustomers().get(i).GetSupplements().get(j).SetWeeklyCost(cost);
                }
            }
        }
    }
    
    /**
    * Removes a supplement belonging to a specified customer
    * 
    * @param customerName
    * @param supplementName
    */
    public void RemoveSupplementFromCustomer(String customerName, String supplementName){
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(customerName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                for(int j = 0; j < m_magazine.GetCustomers().get(i).GetSupplements().size(); j++){
                    if(supplementName.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetSupplements().get(j).GetName())){
                        m_magazine.GetCustomers().get(i).GetSupplements().remove(j);
                    }
                }
            }
        }
    }
   
    /**
    * Removes a specified customer from the magazine service
    * @param name
    */
    public void RemoveCustomer(String name){
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            if(name.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                //Removes the associates of the paying customer from the customers array, before removing the paying customer themselves
                if(m_magazine.GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                    for(int j = 0; j < ((PayingCustomer)m_magazine.GetCustomers().get(i)).GetAssociates().size(); j++){
                        for(int k = 0; k < m_magazine.GetCustomers().size(); k++){
                            if(((PayingCustomer)m_magazine.GetCustomers().get(i)).GetAssociates().get(j).GetName().equalsIgnoreCase(m_magazine.GetCustomers().get(k).GetName())){
                                m_magazine.GetCustomers().remove(k);
                            }
                        }
                    }
                }

                //Removes the associate from the list of associates of the paying customer they are tied to, before removing the associate customer
                // from the customers array
                if(m_magazine.GetCustomers().get(i).GetType() == Customer.Type.ASSOCIATE){
                    for(int j = 0; j < m_magazine.GetCustomers().size(); j++){
                        if(((AssociateCustomer)m_magazine.GetCustomers().get(i)).GetPayingCustomer().GetName().equalsIgnoreCase(m_magazine.GetCustomers().get(j).GetName())){
                            for(int k = 0; k < ((PayingCustomer)m_magazine.GetCustomers().get(j)).GetAssociates().size(); k++){
                                if(((PayingCustomer)m_magazine.GetCustomers().get(j)).GetAssociates().get(k).GetName().equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetName())){
                                    ((PayingCustomer)m_magazine.GetCustomers().get(j)).GetAssociates().remove(k);
                                }
                            }
                        }
                    }
                }

                m_magazine.GetCustomers().remove(i);
            }
        }
    }
    
    /**
    * Adds a new supplement to the magazine service
    * 
    * @param name
    * @param cost
    */
    public void AddSupplement(String name, int cost){
        Supplement supplement = new Supplement(name, cost);
        m_magazine.GetSupplements().add(supplement);
    }
    
    /**
    * Edits an existing supplement in the magazine service
    * 
    * @param oldName
    * @param newName
    * @param cost
    */
    public void EditSupplement(String oldName, String newName, int cost){
        for(int i = 0; i < m_magazine.GetSupplements().size(); i++){
            if(oldName.equalsIgnoreCase(m_magazine.GetSupplements().get(i).GetName())){
                m_magazine.GetSupplements().get(i).SetName(newName);
                m_magazine.GetSupplements().get(i).SetWeeklyCost(cost);
            }
        }
    }
    
    /**
    * Removes an existing supplement in the magazine service
    * 
    * @param name
    */
    public void RemoveSupplement(String name){
        
        for(int i = 0; i < m_magazine.GetCustomers().size(); i++){
            for(int j = 0; j < m_magazine.GetCustomers().get(i).GetSupplements().size(); j++){
                if(name.equalsIgnoreCase(m_magazine.GetCustomers().get(i).GetSupplements().get(j).GetName())){
                    m_magazine.GetCustomers().get(i).GetSupplements().remove(j);
                }
            }
        }
        
        for(int k = 0; k < m_magazine.GetSupplements().size(); k++){
            if(name.equalsIgnoreCase(m_magazine.GetSupplements().get(k).GetName())){
                m_magazine.GetSupplements().remove(k);
            }
        }
    }
    
    /**
    * Prints student details
    */
    public void DisplayStudentDetails(){
        System.out.println("=================================================");
        System.out.println("Name and Student Number: Vladislav Kennebury 33644189");
        System.out.println("Mode of Enrolment: External");
        System.out.println("Tutor: Ferdous Sohel");
        System.out.println("Tutorial Time and Day: N/A");
        System.out.println("=================================================");
        System.out.println("");
    }
    
}

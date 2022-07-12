package assignment2.assignment2;

import javafx.scene.control.TreeItem;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - BillingHistoryPayment.Java
 * 
 * Purpose - Used to demonstrate the use of multithreading for billing history
 * and is responsible for displaying payment type and payment information of a
 * paying customer
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class BillingHistoryPayment implements Runnable{
    /// Stores a reference to the billing information
    private TreeItem<String> m_information;
    /// Stores a reference to the magazine controller
    private MagazineServiceController m_controller;
    /// Stores the name of the paying customer
    private String m_name;
    
    /**
    * Overloaded constructor 
    */
    BillingHistoryPayment(MagazineServiceController controller, TreeItem<String> info, String name){
        m_information = info;
        m_controller = controller;
        m_name = name;
    }
    
    /**
    * Called via a start() method for this thread
    */
    @Override
    public void run(){
        TypeOfPayment();
    }
    
    /**
    * Determines the payment type and payment information of a customer and 
    * add it to the information tree item
    */
    public void TypeOfPayment(){
        for(int i = 0; i < m_controller.GetMagazine().GetCustomers().size(); i++){
            if(m_controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(m_name)){
                if(m_controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                    if(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentType() == PaymentMethod.Type.CREDITCARD){
                        AddBranch("Via Credit Card", m_information);
                    }

                    if(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentType() == PaymentMethod.Type.DIRECTDEBIT){
                        AddBranch("Via Direct Debit", m_information);
                    }

                    AddBranch("Billed to: " + ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentInformation(), m_information);
                }
            }
        }
    }
    
    /**
    * Adds a branch to another tree item
    * @param info
    * @param parent
    * @return TreeItem<String>
    */
    public TreeItem<String> AddBranch(String info, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(info);
        parent.getChildren().add(item);
        return item;
    }
}

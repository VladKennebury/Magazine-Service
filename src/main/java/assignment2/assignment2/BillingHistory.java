package assignment2.assignment2;

import javafx.scene.control.TreeItem;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - BillingHistory.Java
 * 
 * Purpose - Used to demonstrate the use of multithreading for billing history
 * and is responsible for displaying the total cost of a paying customer and their
 * associates.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class BillingHistory implements Runnable{
    /// Stores a reference to the billing information
    private TreeItem<String> m_information;
    /// Stores a reference to the magazine controller
    private MagazineServiceController m_controller;
    /// Stores the name of the paying customer
    private String m_name;
    
    /**
    * Overloaded constructor 
    */
    BillingHistory(MagazineServiceController controller, TreeItem<String> info, String name){
        m_information = info;
        m_controller = controller;
        m_name = name;
    }
    
    /**
    * Called via a start() method for this thread
    */
    @Override
    public void run(){
        History();
    }
    
    /**
    * Calculates the billing history of a paying customer and adds it 
    * to the information tree item
    */
    public void History(){
        int weeksInAMonth = 4, FortnightsInAMonth = 2;
        float total = 0;
        
        AddBranch("Previous Monthly Charge - ", m_information);
        AddBranch("Weekly Magazine Cost $" + m_controller.GetMagazine().GetWeeklyCost(), m_information);
        for(int i = 0; i < m_controller.GetMagazine().GetCustomers().size(); i++){
            if(m_name.equalsIgnoreCase(m_controller.GetMagazine().GetCustomers().get(i).GetName())){
                if(m_controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                    for(int j = 0; j < m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); j++){
                        //Calculates and displays based on subscription type
                        if(m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetSubscriptionType() == Supplement.Type.WEEKLY){
                            AddBranch(m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName() + " - $" + 
                                    (m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetWeeklyCost() * weeksInAMonth) + " (Weekly)", m_information);
                            total += m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetWeeklyCost() * weeksInAMonth;
                        }

                        if(m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetSubscriptionType() == Supplement.Type.FORTNIGHTLY){
                            AddBranch(m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName() + " - $" + 
                                    (m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetWeeklyCost() * FortnightsInAMonth) + " (Fortnightly)", m_information);
                            total += m_controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetWeeklyCost() * FortnightsInAMonth;
                        }
                    }

                    //Downcasting of PayingCustomer is used to access their associates array
                    if(!((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().isEmpty()){
                        //Displays charges of associate customers
                        AddBranch("", m_information);
                        AddBranch("List of Associate Charges - ", m_information);
                        for(int k = 0; k < ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().size(); k++){
                            AddBranch("", m_information);
                            AddBranch(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetName() 
                                    + " (" + ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetEmail() + ")", m_information);
                            AddBranch("Weekly Magazine Cost $" + m_controller.GetMagazine().GetWeeklyCost(), m_information);
                            total += m_controller.GetMagazine().GetWeeklyCost();

                            for(int l = 0; l < ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().size(); l++){

                                if(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetSubscriptionType() == Supplement.Type.WEEKLY){
                                    AddBranch(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetName() + " - $" 
                                            + (((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetWeeklyCost() * weeksInAMonth) + " (Weekly)", m_information);
                                    total += ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetWeeklyCost() * weeksInAMonth;
                                }

                                if(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetSubscriptionType() == Supplement.Type.FORTNIGHTLY){
                                    AddBranch(((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetName() + " - $" 
                                            + (((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetWeeklyCost() * FortnightsInAMonth) + " (Fortnightly)", m_information);
                                    total += ((PayingCustomer)m_controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(k).GetSupplements().get(l).GetWeeklyCost() * FortnightsInAMonth;
                                }
                            }
                        }
                    }

                    total += m_controller.GetMagazine().GetWeeklyCost();

                    AddBranch("", m_information);
                    AddBranch("Total - $" + total, m_information);
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

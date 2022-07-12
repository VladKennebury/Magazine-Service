package assignment2.assignment2;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - MagazineValidation.Java
 * 
 * Purpose - Validates magazine information using specified checks
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class MagazineValidation {
    
    /**
    * Checks if a customer name already exists in the magazine service, 
    * using a specified name
    * @param controller
    * @param name
    * @return boolean
    */
    public boolean isDuplicateCustomerName(MagazineServiceController controller, String name){
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(name.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetName())){
                return true;
            }
        }
        return false;
    }
    
    /**
    * Checks if a customer name already exists in the magazine service, 
    * matching an old and new name
    * @param controller
    * @param oldName
    * @param newName
    * @return boolean
    */
    public boolean isDuplicateCustomerName(MagazineServiceController controller, String oldName, String newName){
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(!newName.equalsIgnoreCase(oldName)){
                if(newName.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetName())){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Checks if a customer email already exists in the magazine service, 
    * using a specified email
    * @param controller
    * @param email
    * @return boolean
    */
    public boolean isDuplicateCustomerEmail(MagazineServiceController controller, String email){
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(email.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetEmail())){
                return true;
            }
        }
        return false;
    }
    
    /**
    * Checks if a customer name already exists in the magazine service, 
    * matching a name and email
    * @param controller
    * @param name
    * @param newEmail
    * @return boolean
    */
    public boolean isDuplicateCustomerEmail(MagazineServiceController controller, String name, String newEmail){
        String oldEmail = "";
        
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(name.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetName())){
                oldEmail = controller.GetMagazine().GetCustomers().get(i).GetEmail();
            }
            
            if(!newEmail.equalsIgnoreCase(oldEmail)){
                if(newEmail.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetEmail())){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Checks if a paying customer exists in the magazine service
    * @param controller
    * @return boolean
    */
    public boolean PayingCustomerExists(MagazineServiceController controller){
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                return true;
            }
        }
        return false;
    }
    
    /**
    * Checks if a supplement name already exists in the magazine service, 
    * using a specified name
    * @param controller
    * @param name
    * @return boolean
    */
    public boolean isDuplicateSupplementName(MagazineServiceController controller, String name){
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            if(name.equalsIgnoreCase(controller.GetMagazine().GetSupplements().get(i).GetName())){
                return true;
            }
        }
        return false;
    }
    
    /**
    * Checks if a supplement name already exists in the magazine service, 
    * matching an old and new name
    * @param controller
    * @param oldName
    * @param newName
    * @return boolean
    */
    public boolean isDuplicateSupplementName(MagazineServiceController controller, String oldName, String newName){
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            if(!newName.equalsIgnoreCase(oldName)){
                if(newName.equalsIgnoreCase(controller.GetMagazine().GetSupplements().get(i).GetName())){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Checks if any supplements exist in the magazine service
    * @param controller
    * @return boolean
    */
    public boolean SupplementsExist(MagazineServiceController controller){
        if(controller.GetMagazine().GetSupplements().isEmpty()){
            return false;
        }
        return true;
    }
    
    /**
    * Checks if a customer already has a specified supplement in the magazine service
    * @param controller
    * @param customerName
    * @param supplementName
    * @return boolean
    */
    public boolean isDuplicateCustomerSupplement(MagazineServiceController controller, String customerName, String supplementName){
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(customerName.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetName())){
                for(int j = 0; j < controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); j++){
                    if(supplementName.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

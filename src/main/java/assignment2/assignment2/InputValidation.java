package assignment2.assignment2;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - InputValidation.Java
 * 
 * Purpose - Validates string input
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class InputValidation {
    
    /**
    * Checks if a string is empty
    * @param string
    * @return boolean
    */
    public boolean isEmpty(String string){
        if(string.equals("")){
            return true;
        }
        return false;
    }
    
    /**
    * Checks if a string is a number when parsed to an integer,
    * if the entered string is not empty
    * @param string
    * @return boolean
    */
    public boolean isNumeric(String string){
        if(string.equals("")){
            return false;
        }
        
        try {
            int option = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
    
    /**
    * Checks if a string contains a number value in it's characters,
    * if the entered string is not empty
    * @param string
    * @return boolean
    */
    public boolean hasNoNumbers(String string){
        if(string.equals("")){
            return false;
        }
        
        char chars[] = string.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    
    /**
    * Checks if a string contains a number value that is positive
    * @param string
    * @return boolean
    */
    public boolean isPositive(String string){
        try {
            int number = Integer.parseInt(string);
            if(number >= 0){
                return true;
            }
        } catch (NumberFormatException e) {
        }
        return false;
    }
}

package assignment2.assignment2;

import java.io.Serializable;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - Supplement.Java
 * 
 * Purpose - Stores details of a supplement as well as the subscription
 * type.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class Supplement implements Serializable{
    
    /**
    * Stores enumerators used to identify the type of supplement subscription
    */
    public enum Type{
        WEEKLY,
        FORTNIGHTLY
    }
    
    /// Stores supplement name
    private String m_name;
    /// Stores supplement weekly cost
    private int m_weeklyCost;
    /// Stores supplement subscription type
    private Type m_subscriptionType;
    
    /**
    * Default constructor 
    */
    Supplement(){
        m_name = "";
        m_weeklyCost = 0;
        m_subscriptionType = Type.WEEKLY;
    }
    
    /**
    * Overloaded constructor 
    */
    Supplement(String name, int cost){
        m_name = name;
        m_weeklyCost = cost;
        m_subscriptionType = Type.WEEKLY;
    }
    
    /**
    * Overloaded constructor 
    */
    Supplement(String name, int cost, Type type){
        m_name = name;
        m_weeklyCost = cost;
        m_subscriptionType = type;
    }
    
    /**
    * Copy Constructor - Used to create a deep copy of a supplement
    * @param supplement
    */
    public Supplement(Supplement supplement){
        m_name = supplement.m_name;
        m_weeklyCost = supplement.m_weeklyCost;
        m_subscriptionType = supplement.m_subscriptionType;
    } 
    
    /**
    * Getter for the supplement name
    * @return m_name
    */
    public String GetName(){
        return m_name;
    }
    
    /**
    * Setter for supplement name
    * @param name
    */
    public void SetName(String name){
        m_name = name;
    }
    
    /**
    * Getter for the supplement weekly cost
    * @return m_weeklyCost
    */
    public int GetWeeklyCost(){
        return m_weeklyCost;
    }
    
    /**
    * Setter for supplement weekly cost
    * @param cost
    */
    public void SetWeeklyCost(int cost){
        m_weeklyCost = cost;
    }
    
    /**
    * Getter for the supplement subscription type
    * @return m_subscriptionType
    */
    public Type GetSubscriptionType(){
        return m_subscriptionType;
    }
    
    /**
    * Setter for the supplement subscription type
    * @param type
    */
    public void SetSubscriptionType(Type type){
        m_subscriptionType = type;
    }
}

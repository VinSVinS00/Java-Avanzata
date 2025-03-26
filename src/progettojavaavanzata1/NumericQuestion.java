/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progettojavaavanzata1;

import java.util.Random;


public class NumericQuestion {
    private int num1;
    private int num2;
    private String operator;
    private Random rand = new Random();
    
    public void randomInit(){
        this.num1 = rand.nextInt(99) + 1;
        this.num2 = rand.nextInt(99) + 1;
        if(rand.nextInt() == 0)
            operator = "+";
        else
            operator = "-";
    }
    
    public int getResult(){
        if(operator.equalsIgnoreCase("+"))
            return num1+num2;
        else
            return num1-num2;
    }
    
    public int getNum1(){
        return num1;
    }
    
    public int getNum2(){
        return num2;
    }
    
    public String getOperator(){
        return operator;
    }
}

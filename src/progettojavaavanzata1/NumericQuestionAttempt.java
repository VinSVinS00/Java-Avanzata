/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progettojavaavanzata1;


public class NumericQuestionAttempt {
    private NumericQuestion question;
    private int answer;
    
    public boolean isCorrect(){
        return this.answer == this.question.getResult();
    }
    
    public String getResult(){
        if(this.isCorrect())
            return "Corretto";
        else
            return "Sbagliato";
    }
}

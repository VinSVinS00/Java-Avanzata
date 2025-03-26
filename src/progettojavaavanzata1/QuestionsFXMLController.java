/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettojavaavanzata1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author vitol
 */
public class QuestionsFXMLController implements Initializable {

    @FXML
    private Label operando1;
    @FXML
    private Label operando2;
    @FXML
    private Label operazione;
    @FXML
    private TextField txfRisposta;
    @FXML
    private Button btnNext;
    @FXML
    private Label statusAttuale;
    @FXML
    private Label maxDomande;
    
    NumericQuestion question;
    
    private static int result;
    
    Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        question = new NumericQuestion();
        question.randomInit();
        operando1.setText(question.getNum1() + "");
        operando2.setText(question.getNum2() + "");
        operazione.setText(question.getOperator());
        result = question.getResult();
        
    }    

    @FXML
    private void nextQuestion(ActionEvent event) {
        if(Integer.parseInt(txfRisposta.getText()) == result){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EsitoFXML.fxml"));
                Parent root = loader.load();
                EsitoFXMLController esitoController = loader.getController();
                esitoController.addAnswer(new NumericQuestionAttempt(question,Integer.parseInt(txfRisposta.getText())));
            } catch (IOException ex) {
                Logger.getLogger(QuestionsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            //anche qua va aggiornata la tabella tentativi ma senza generare una nuova domanda
            alert.setTitle("Alert");
            alert.setHeaderText("Risposta errata");
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettojavaavanzata1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;


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
    @FXML
    private Label timerLabel;
    
    NumericQuestion question;
    FXMLDocumentController dati;
    
    private static int result;
    private int domandaCorrente = 1;
    private int numDomande;
    
    Alert alert = new Alert(Alert.AlertType.ERROR);

    int i;
    
    EsitoFXMLController esitoController;
    Parent root;
    
    int tempo;
    private Timeline timeline;
    public void startTimer() {
        tempo = 6;
        timerLabel.setText("Tempo rimasto: " + tempo + "s");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempo--; 
            timerLabel.setText("Tempo rimasto: " + tempo + "s"); 

            if (tempo <= 0) {
                timeline.stop();
                timerLabel.setText("Tempo scaduto!");
                esitoController.addAnswer(new NumericQuestionAttempt(question, -1000));
                if (domandaCorrente < numDomande)
                    nextQuestionTimerScaduto();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    public void setNumeroDomande(int num){
        this.numDomande = num;
        maxDomande.setText(String.valueOf(numDomande));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datiDomande();
        statusAttuale.setText(String.valueOf(domandaCorrente));
        i=0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EsitoFXML.fxml"));
        try {
            Parent root = loader.load();
            esitoController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startTimer();
    }    
    
    @FXML
    private void nextQuestion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EsitoFXML.fxml"));
        Parent root = loader.load();
        EsitoFXMLController esitoController = loader.getController();
        esitoController.addAnswer(new NumericQuestionAttempt(question,Integer.parseInt(txfRisposta.getText())));
        if(Integer.parseInt(txfRisposta.getText()) == result){
            domandaCorrente++;
            if(domandaCorrente <= numDomande){
                datiDomande();
                statusAttuale.setText(String.valueOf(domandaCorrente));
                startTimer();
            } else {
                try{
                    Parent esitoRoot = FXMLLoader.load(getClass().getResource("EsitoFXML.fxml"));
                    Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch(IOException ex){
                        
                }
            }

        }else{
            alert.setTitle("Alert");
            alert.setHeaderText("Risposta errata");
            alert.showAndWait();
            return;
        }
    }
    
    private void nextQuestionTimerScaduto() {
    domandaCorrente++;
    if (domandaCorrente <= numDomande) {
        datiDomande();
        statusAttuale.setText(String.valueOf(domandaCorrente));
        startTimer();
    } else {
        try {
            Parent esitoRoot = FXMLLoader.load(getClass().getResource("EsitoFXML.fxml"));
            Stage stage = (Stage) btnNext.getScene().getWindow();
            Scene scene = new Scene(esitoRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

    
    private void datiDomande(){
        question = new NumericQuestion();
        question.randomInit();
        operando1.setText(question.getNum1() + "");
        operando2.setText(question.getNum2() + "");
        operazione.setText(question.getOperator());
        result = question.getResult();
    }
    
}
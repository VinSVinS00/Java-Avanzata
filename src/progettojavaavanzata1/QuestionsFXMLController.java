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
    
    private NumericQuestion question;
    
    private int domandaCorrente = 1;
    
    private int numDomande;
    
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    
    private EsitoFXMLController esitoController;
    
    private int tempo;
    
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EsitoFXML.fxml"));
        try {
            Parent root = loader.load();
            esitoController = loader.getController();
        } catch (IOException e) {
            
        }
        datiDomande();
        statusAttuale.setText(domandaCorrente + "");
        startTimer();
    }    

    public void setNumeroDomande(int num) {
        this.numDomande = num;
        maxDomande.setText(numDomande + "");
    }
    
    private void startTimer() {
        tempo = 30;
        timerLabel.setText("Tempo rimasto: " + tempo + "s");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempo--;
            timerLabel.setText("Tempo rimasto: " + tempo + "s");

            if (tempo <= 0) {
                timeline.stop();
                timerLabel.setText("Tempo scaduto!");
                esitoController.addAnswer(new NumericQuestionAttempt(question, -1000));
                nextQuestionTimerScaduto();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
        try {
            int rispostaUtente = Integer.parseInt(txfRisposta.getText());
            esitoController.addAnswer(new NumericQuestionAttempt(question, rispostaUtente));

            if (rispostaUtente == question.getResult()) {
                domandaCorrente++;
                if (domandaCorrente <= numDomande) {
                    timeline.stop();
                    datiDomande();
                    statusAttuale.setText(domandaCorrente + "");
                    startTimer();
                } else {
                    cambiaScena(event);
                }
            } else {
                alert.setTitle("Alert");
                alert.setHeaderText("Risposta errata, riprova");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            alert.setTitle("Errore di input");
            alert.setHeaderText("Inserisci un numero valido");
            alert.showAndWait();
        }
    }

    private void nextQuestionTimerScaduto() {
        domandaCorrente++;
        if (domandaCorrente <= numDomande) {
            timeline.stop();
            datiDomande();
            statusAttuale.setText(domandaCorrente + "");
            startTimer();
        } else {
            cambiaScena(null);
        }
    }

    private void cambiaScena(ActionEvent event) {
        try {
            timeline.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EsitoFXML.fxml"));
            Parent esitoRoot = loader.load();
            Stage stage;
            if (event != null) {
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            } else {
                stage = (Stage)btnNext.getScene().getWindow();
            }
            Scene scene = new Scene(esitoRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            
        }
    }

    private void datiDomande() {
        question = new NumericQuestion();
        question.randomInit();
        operando1.setText(question.getNum1() + "");
        operando2.setText(question.getNum2() + "");
        operazione.setText(question.getOperator());
    }
}

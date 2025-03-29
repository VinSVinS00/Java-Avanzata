/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettojavaavanzata1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author vitol
 */
public class EsitoFXMLController implements Initializable {

    @FXML
    private TableView<Tentativo> table;
    @FXML
    private TableColumn<Tentativo, String> colonnaTentativo;
    @FXML
    private TableColumn<Tentativo, String> colonnaEsito;
    
    private static final ObservableList<Tentativo> risposte = FXCollections.observableArrayList();
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colonnaTentativo.setCellValueFactory(new PropertyValueFactory("tentativo"));
        colonnaEsito.setCellValueFactory(new PropertyValueFactory("esito"));
        table.setItems(risposte);
    }
    
    public void addAnswer(NumericQuestionAttempt risposta){
        String tentativo = risposta.getQuestion().getNum1() + " " + risposta.getQuestion().getOperator() + " " + risposta.getQuestion().getNum2() + " = " + 
                           risposta.getAnswer();
        String esito = risposta.getResult();
        risposte.add(new Tentativo(tentativo,esito));
        table.setItems(risposte);
        table.refresh();
    }

    @FXML
    private void esporta(ActionEvent event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tentativi.txt"))) {
            for (Tentativo t : risposte) {
                writer.write(t.getTentativo() + " -> " + t.getEsito() + "\n");
            }
            alert.setTitle("Alert");
            alert.setHeaderText("Tentativi esportati");
            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(EsitoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
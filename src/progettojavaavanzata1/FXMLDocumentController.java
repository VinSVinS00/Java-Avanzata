/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettojavaavanzata1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author vitol
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button btnInvia;
    @FXML
    private TextField txfNome;
    @FXML
    private TextField txfCog;
    @FXML
    private TextField txfDom;
    
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    
    
    @FXML
    private void nextWindow(ActionEvent event) {
        if(txfNome.getText().isEmpty() || txfCog.getText().isEmpty()){
            alert.setTitle("Alert");
            alert.setHeaderText("Errore, campo nome o cognome vuoti");
            alert.showAndWait();
            return;
        }
        else if(txfDom.getText().isEmpty() || Integer.parseInt(txfDom.getText()) <= 0){
            alert.setTitle("Alert");
            alert.setHeaderText("Errore, il numero di domande deve essere positivo");
            alert.showAndWait();
            return;
        }else{
            try{
            Parent root = FXMLLoader.load(getClass().getResource("QuestionsFXML.fxml"));
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }catch(IOException ex){
                
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

package supermarketmanager.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import supermarketmanager.model.supermarket.Floor;
import supermarketmanager.model.supermarket.MarketStructure;

public class PopoutMenu{
    public BorderPane borderPlane;
    public Button saveButton;
    public Button cancelButton;
    MarketStructure<?> marketStructure;
    AnchorPane inserted;


    public void initialize(AnchorPane insertion) {
        borderPlane.setCenter(insertion);
        inserted = insertion;
    }
    public void saveResults(ActionEvent actionEvent) {
        marketStructure = getResult();
        closeWindow(actionEvent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public MarketStructure<?> getResult(){
        return inserted.returnResult();
    }
}

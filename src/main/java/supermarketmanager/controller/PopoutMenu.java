package supermarketmanager.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import supermarketmanager.model.supermarket.MarketStructure;

public class PopoutMenu {
    public BorderPane borderPlane;
    public Button saveButton;
    public Button cancelButton;
    MarketStructure<?> marketStructure;

    public void insertVBOX(Node insert) {
        borderPlane.setCenter(insert);
    }
    public void saveResults(ActionEvent actionEvent) {
    }

    public void closeWindow(ActionEvent actionEvent) {
    }

    public MarketStructure<?> getResult(){
        return marketStructure;
    }
}

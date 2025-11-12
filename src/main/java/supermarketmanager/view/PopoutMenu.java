package supermarketmanager.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.MarketStructure;

public class PopoutMenu{
    public BorderPane borderPlane;
    public Button saveButton;
    public Button cancelButton;
    public boolean cancelled = true;
    MarketStructure<?> marketStructure;
    Insertable inserted;


    public void initialize(Insertable insertion) {
        borderPlane.setCenter(insertion.getRoot());
        inserted = insertion;
    }
    public void saveResults(ActionEvent actionEvent) {
        marketStructure = getResult();
        cancelled = false;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public MarketStructure<?> getResult(){
        return inserted.getResult();
    }
}

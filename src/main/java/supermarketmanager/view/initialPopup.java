package supermarketmanager.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import supermarketmanager.controller.SupermarketManager;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.MarketStructure;

import java.io.File;

public class initialPopup{
    public Button loadButton;
    public Button newButton;
    public Text middleText;
    MarketStructure<?> structure = null;

    public void openExplorer(ActionEvent actionEvent) throws Exception {
        structure = new SupermarketManager("name", new int[]{11,1}, new File("something"));
        FileChooser file = new FileChooser();
        file.setTitle("Open XML File");
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selected = file.showOpenDialog(loadButton.getScene().getWindow());
        if (selected != null) {
            ((SupermarketManager) structure).load(selected);
        }
    }

    public void close(){
        Stage stage = (Stage) loadButton.getScene().getWindow();
        stage.close();
    }

    public void loadFile(ActionEvent actionEvent) throws Exception {
        openExplorer(actionEvent);
        close();
    }

    public void newManager(ActionEvent actionEvent) {
        close();
    }

    public MarketStructure<?> getResult() {
        return structure;
    }
}

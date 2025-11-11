package supermarketmanager.view;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import supermarketmanager.controller.SupermarketManager;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.MarketStructure;

import java.io.File;

public class newManager extends Insertable{
    public AnchorPane rootAnchor;
    public Text errorOutput;
    public TextField nameInput;
    public TextField dimensionX;
    public TextField dimensionY;
    private static final String floorNameError = "Please add a name to your manager!";
    private static final String dimensionError = "Dimension should be a positive number!";

    public void initialize() {
        dimensionX.disableProperty().bind(nameInput.textProperty().isEmpty());
        dimensionY.disableProperty().bind(dimensionX.textProperty().isEmpty());
    }



    public MarketStructure<?> getResult() {
        if(nameInput.getText().isEmpty() || dimensionX.getText().isEmpty() || dimensionY.getText().isEmpty()) {
            return null;
        }
        String name = nameInput.getText().toLowerCase().replaceAll(" ", "_") + ".xml";
        File file = new File(name);
        return new SupermarketManager(nameInput.getText(), new int[] {Integer.parseInt(dimensionX.getText()), Integer.parseInt(dimensionX.getText())}, file);
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
    }

    @Override
    public void edit(MarketStructure<?> itemToEdit) {
        if(itemToEdit instanceof SupermarketManager) {
            nameInput.setText(itemToEdit.getName());
            int[] dimensions = itemToEdit.getDimensions();
            dimensionX.setText(String.valueOf(dimensions[0]));
            dimensionY.setText(String.valueOf(dimensions[1]));
        }
    }

    public void checkString(KeyEvent keyEvent) {
        if(nameInput.getText().isEmpty())
            errorOutput.setText(floorNameError);
        else
            errorOutput.setText("");
    }

    public void checkNumberPositiveX(KeyEvent keyEvent) {
        if(dimensionX.getText().matches("[1-9]\\\\d*"))
            errorOutput.setText(dimensionError);
        else
            errorOutput.setText("");
    }

    public void checkNumberPositiveY(KeyEvent keyEvent) {
        if(dimensionY.getText().matches("[1-9]\\\\d*"))
            errorOutput.setText(dimensionError);
        else
            errorOutput.setText("");
    }
}

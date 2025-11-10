package supermarketmanager.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.Aisle;
import supermarketmanager.model.supermarket.MarketStructure;

public class AisleInsert extends Insertable {
    public AnchorPane rootAnchor;
    public Text errorOutput;
    public TextField nameInput;
    public TextField dimensionX;
    public TextField dimensionY;
    public ComboBox<String> comboBox;

    private static final String floorNameError = "Please add a name to your Aisle!";
    private static final String dimensionError = "Dimension should be a positive number!";

    public void initialize() {
        comboBox.getItems().addAll(Aisle.storageTypes);
        dimensionX.disableProperty().bind(nameInput.textProperty().isEmpty());
        dimensionY.disableProperty().bind(dimensionX.textProperty().isEmpty());
    }

    @Override
    public MarketStructure<?> getResult() {
        String name = nameInput.getText();
        int[] dimensions = {Integer.parseInt(dimensionX.getText()), Integer.parseInt(dimensionY.getText())};
        int storageType = 0;
        switch(comboBox.getValue()){
            case "Refrigerated" -> storageType = 1;
            case "Frozen" -> storageType = 2;
            default -> {
                break;
            }
        }
        return new Aisle(name, dimensions, storageType);
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
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

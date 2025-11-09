package supermarketmanager.view;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.Floor;
import supermarketmanager.model.supermarket.MarketStructure;

public class FloorInsert extends Insertable {
    public AnchorPane rootAnchor;
    public Text errorOutput;
    public TextField nameInput;
    public TextField floorInput;
    public TextField dimensionX;
    public TextField dimensionY;
    private static final String floorNameError = "Please add a name to your floor!";
    private static final String floorNumberError = "Please add a number to your floor!";
    private static final String dimensionError = "Dimension should be a positive number!";

    public void initialize() {
        floorInput.disableProperty().bind(nameInput.textProperty().isEmpty());
        dimensionX.disableProperty().bind(floorInput.textProperty().isEmpty());
        dimensionY.disableProperty().bind(dimensionX.textProperty().isEmpty());
    }

    public void checkString(KeyEvent keyEvent) {
        if(nameInput.getText().isEmpty())
            errorOutput.setText(floorNameError);
        else
            errorOutput.setText("");
    }

    public void checkNumber(KeyEvent keyEvent) {
        if (!floorInput.getText().matches("\\d+"))
            errorOutput.setText(floorNumberError);
        else
            errorOutput.setText("");
    }

    public void checkNumberPositive(String text) {
        if(text.matches("[1-9]\\\\d*"))
            errorOutput.setText(dimensionError);
        else
            errorOutput.setText("");
    }

    public MarketStructure<?> returnResult(){
        if(nameInput.getText().isEmpty() || floorInput.getText().isEmpty() || dimensionX.getText().isEmpty() || dimensionY.getText().isEmpty())
            return null;
        String name = nameInput.getText();
        int[] dimensions = {Integer.parseInt(dimensionX.getText()), Integer.parseInt(dimensionY.getText())};
        int floorNumber = Integer.parseInt(floorInput.getText());
        return new Floor(name, dimensions, floorNumber);
    }

    public void checkNumberPositiveX(KeyEvent keyEvent) {
        checkNumberPositive(dimensionX.getText());
    }

    public void checkNumberPositiveY(KeyEvent keyEvent) {
        checkNumberPositive(dimensionY.getText());
    }

    public Node getRootNode() {
        return rootAnchor;
    }

    @Override
    public MarketStructure<?> getResult() {
        return returnResult();
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
    }
}

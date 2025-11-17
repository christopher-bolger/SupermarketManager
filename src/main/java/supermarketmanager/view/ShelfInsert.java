package supermarketmanager.view;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.MarketStructure;
import supermarketmanager.model.supermarket.Shelf;

public class ShelfInsert extends Insertable {
    public AnchorPane rootAnchor;
    public Text errorOutput;
    public TextField nameInput;
    public TextField shelfNumberInput;
    public TextField dimensionX;
    public TextField dimensionY;

    private static final String shelfNameError = "Please add a name to your Shelf!";
    private static final String shelfNumberError = "Please add a number to your Shelf!";
    private static final String dimensionError = "Dimension should be a positive number!";

    @Override
    public MarketStructure<?> getResult() {
        if(nameInput.getText().isEmpty() || shelfNumberInput.getText().isEmpty() || dimensionX.getText().isEmpty() || dimensionY.getText().isEmpty()) {
            return null;
        }
        String name = nameInput.getText();
        int shelfNumber = Integer.parseInt(shelfNumberInput.getText());
        int[] dimensions = { Integer.parseInt(dimensionX.getText()), Integer.parseInt(dimensionY.getText()) };
        return new Shelf(name, dimensions, shelfNumber);
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
    }

    @Override
    public void edit(MarketStructure<?> itemToEdit) {
        if(itemToEdit instanceof Shelf) {
            nameInput.setText(itemToEdit.getName());
            shelfNumberInput.setText(String.valueOf(((Shelf) itemToEdit).getShelfNumber()));
            int[] dimensions = itemToEdit.getDimensions();
            dimensionX.setText(String.valueOf(dimensions[0]));
            dimensionY.setText(String.valueOf(dimensions[1]));
        }
    }

    public void checkString(KeyEvent keyEvent) {
        if(nameInput.getText().isEmpty())
            errorOutput.setText(shelfNameError);
        else
            errorOutput.setText("");
    }

    public void checkNumber(KeyEvent keyEvent) {
        if (!shelfNumberInput.getText().matches("\\d+"))
            errorOutput.setText(shelfNumberError);
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

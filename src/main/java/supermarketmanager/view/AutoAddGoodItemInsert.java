package supermarketmanager.view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.GoodItem;
import supermarketmanager.model.supermarket.MarketStructure;

public class AutoAddGoodItemInsert extends Insertable {
    public AnchorPane rootAnchor;
    public Text errorOutput;
    public TextField nameInput;
    public ComboBox<String> weightType;
    public ComboBox<String> storageType;
    public TextField descriptionInput;
    public TextField weightInput;
    public TextField priceInput;
    public TextField quantityInput;
    public TextField urlInput;

    private static final String goodItemStringError = "Please add a name to your GoodItem!";
    private static final String goodItemNumberError = "Please add a number to your Shelf!";

    public void initialize(){
        weightType.getItems().addAll(GoodItem.weightTypes);
        storageType.getItems().addAll(GoodItem.storageTypes);
    }

    @Override
    public MarketStructure<?> getResult() {
        String name = nameInput.getText(), description = descriptionInput.getText(), photoUrl = urlInput.getText();
        int storage, weightIndex, quantity = Integer.parseInt(quantityInput.getText());
        double price = Double.parseDouble(priceInput.getText()), weight = Double.parseDouble(weightInput.getText());
//        public static String[] weightTypes = {"Kg", "g", "L", "mL"};
//        public static String[] storageTypes = {"Unrefrigerated", "Refrigerated", "Frozen"};
        switch(weightType.getValue()){
            case "g" -> weightIndex = 1;
            case "L" -> weightIndex = 2;
            case "mL" -> weightIndex = 3;
            default -> weightIndex = 0;
        }
        switch(storageType.getValue()){
            case "Refrigerated" -> storage = 1;
            case "Frozen" -> storage = 2;
            default -> storage = 0;
        }
        //String name, String description, double price, int quantity, double weight, int weightType, int storageType, String photoURL
        return new GoodItem(name, description, price, quantity, weight, weightIndex, storage, photoUrl);
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
    }

    @Override
    public void edit(MarketStructure<?> itemToEdit) {
        return; //this is unused - when you go to edit a GoodItem it won't use this class to edit
    }

    public void checkString(KeyEvent keyEvent) {
        if(nameInput.getText().isEmpty())
            errorOutput.setText(goodItemStringError);
        else
            errorOutput.setText("");
    }

    public void checkNumber(KeyEvent keyEvent) {
        if (!keyEvent.getText().matches("\\d+"))
            errorOutput.setText(goodItemNumberError);
        else
            errorOutput.setText("");
    }

    public void checkNumberPositive(KeyEvent keyEvent) {
        if (!keyEvent.getText().matches("\\d+"))
            errorOutput.setText(goodItemNumberError);
        else
            errorOutput.setText("");
    }
}

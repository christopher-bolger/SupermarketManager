package supermarketmanager.view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.supermarket.Aisle;
import supermarketmanager.model.supermarket.GoodItem;
import supermarketmanager.model.supermarket.MarketStructure;
import supermarketmanager.model.supermarket.Shelf;

public class AddGoodItemInsert extends Insertable {
    public Aisle parentAisle;
    public Text errorOutput;
    public ComboBox<String> weightType;
    public TextField descriptionInput;
    public TextField weightInput;
    public TextField priceInput;
    public TextField quantityInput;
    public TextField urlInput;
    public AnchorPane rootAnchor;
    public TextField nameInput;
    public GoodItem goodItem = null;

    public void initialize(Aisle parent){
        parentAisle = parent;
        weightType.getItems().addAll(GoodItem.weightTypes);
    }

    @Override
    public MarketStructure<?> getResult() {
        if(nameInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || urlInput.getText().isEmpty() || weightInput.getText().isEmpty() || priceInput.getText().isEmpty() || weightType.getValue().isEmpty()) {
            return null;
        }
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
        //String name, String description, double price, int quantity, double weight, int weightType, int storageType, String photoURL
        goodItem = new GoodItem(name, description, price, quantity, weight, weightIndex, parentAisle.getStorageTypeIndex(), photoUrl);
        return goodItem;
    }

    @Override
    public AnchorPane getRoot() {
        return rootAnchor;
    }

    @Override
    public void edit(MarketStructure<?> itemToEdit) {
        if(itemToEdit instanceof GoodItem){
            nameInput.setText(itemToEdit.getName());
            descriptionInput.setText(((GoodItem) itemToEdit).getDescription());
            urlInput.setText(((GoodItem) itemToEdit).getPhotoURL());
            weightType.getSelectionModel().select(((GoodItem) itemToEdit).getWeightType());
            weightInput.setText(Double.toString(((GoodItem) itemToEdit).getWeight()));
            quantityInput.setText(Integer.toString(((GoodItem) itemToEdit).getQuantity()));
            priceInput.setText(Double.toString(((GoodItem) itemToEdit).getPrice()));
        }
    }

    public void checkString(KeyEvent keyEvent) {
    }

    public void checkNumberPositive(KeyEvent keyEvent) {
    }
}

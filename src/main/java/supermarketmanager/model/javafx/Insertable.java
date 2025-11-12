package supermarketmanager.model.javafx;

import javafx.scene.layout.AnchorPane;
import supermarketmanager.model.supermarket.MarketStructure;

public abstract class Insertable extends AnchorPane {
    public abstract MarketStructure<?> getResult();
    public abstract AnchorPane getRoot();
    public abstract void edit(MarketStructure<?> itemToEdit);
}

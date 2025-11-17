package supermarketmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import supermarketmanager.model.javafx.Insertable;
import supermarketmanager.model.linkedlist.LinkedList;
import supermarketmanager.model.supermarket.*;
import supermarketmanager.view.PopoutMenu;
import supermarketmanager.view.initialPopup;

import java.io.File;
import java.io.IOException;

public class MainController {
    public Menu editMenu;
    public MenuItem searchName;
    public MenuItem searchDescription;
    public TextField searchField;
    public TextField descriptionField;
    SupermarketManager manager;
    MarketStructure<?> selectedEntity;
    public VBox mainVBox;
    public MenuBar menuBar;
    public Menu fileButton;
    public MenuItem openFile;
    public MenuItem saveFile;
    public MenuItem saveAsFile;
    public MenuItem closeMenu;
    public MenuItem clearMenu;
    public MenuItem menuDeleteSelected;
    public MenuItem menuEditSelected;
    public Button addFloor;
    public Button addAisle;
    public Button addShelf;
    public Button addGoodItem;
    public Button autoAddGoodItem;
    public TextArea errorOutput;
    public TitledPane superMarket;
    public TextArea entityOutput;
    public TreeView<MarketStructure<?>> treeView;
    TreeItem<MarketStructure<?>> treeRoot;



    public void initialize() throws IOException {
        updateTreeView();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void openExplorer(ActionEvent actionEvent) throws Exception {
        if(manager == null){
            manager = new SupermarketManager("Name", new int[] {100,100}, new File("untitled.xml"));
        }
        FileChooser file = new FileChooser();
        file.setTitle("Open XML File");
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selected = file.showOpenDialog(menuBar.getScene().getWindow());
        if (selected != null) {
            manager.load(selected);
            superMarket.setText(manager.getName());
            updateTreeView();
        }
    }

    public SupermarketManager initialPopup() throws IOException {
        FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/initialPopup.fxml"));
        Parent popupNode = popupLoader.load();
        initialPopup insertController = popupLoader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(popupNode));
        stage.showAndWait();

        MarketStructure<?> result = insertController.getResult();
        return (SupermarketManager) result;
    }

    public MarketStructure<?> insertPopup(String insertPath) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(insertPath));
        Node insertNode = insertLoader.load();
        Insertable insertController = insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insertController); // Pass controller here

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
        return skeletonController.cancelled ? null : insertController.getResult();
    }

    public MarketStructure<?> insertPopupEdit(String insertPath, MarketStructure itemToEdit) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(insertPath));
        Node insertNode = insertLoader.load();
        Insertable insertController = insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insertController);
        insertController.edit(itemToEdit);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
        return skeletonController.cancelled ? null : insertController.getResult();
    }

    public void createNewManager() throws IOException {
        manager = (SupermarketManager) insertPopup("/supermarketmanager/ui/newManager.fxml");
    }

    public void updateTreeView() throws IOException {
        if(manager == null) {
            manager = initialPopup();
            if (manager == null)
                createNewManager();
        }
        treeRoot = new TreeItem<>(manager);
        treeRoot.setExpanded(true);
        treeView.setRoot(treeRoot);
        treeView.setShowRoot(true);
        getTreeChildren();
    }

    //this is ungodly and I apologise
    public void getTreeChildren(){
        if(manager.isEmpty())
            return;
        LinkedList<Integer> listOfFloorNumbers = new LinkedList<>();
        for(Floor floor : manager.getList())
            if(!listOfFloorNumbers.contains(floor.getFloor()))
                listOfFloorNumbers.add(floor.getFloor());
        for(Integer floorNumber : listOfFloorNumbers)
            for(Floor floor : manager.getList()) {
                if(floorNumber == floor.getFloor()) {
                    TreeItem<MarketStructure<?>> nextItem = new TreeItem<>(floor);
                    nextItem.setExpanded(true);
                    treeRoot.getChildren().add(nextItem);
                    if (!floor.isEmpty()) {
                        for (Aisle aisle : floor.getList()) {
                            TreeItem<MarketStructure<?>> nextAisle = new TreeItem<>(aisle);
                            nextAisle.setExpanded(true);
                            nextItem.getChildren().add(nextAisle);
                            if (!aisle.isEmpty()) {
                                for (Shelf shelf : aisle.getList()) {
                                    TreeItem<MarketStructure<?>> nextShelf = new TreeItem<>(shelf);
                                    nextAisle.getChildren().add(nextShelf);
                                    if (!shelf.isEmpty()) {
                                        for (GoodItem goodItem : shelf.getList()) {
                                            TreeItem<MarketStructure<?>> nextGoodItem = new TreeItem<>(goodItem);
                                            nextShelf.getChildren().add(nextGoodItem);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }

    public void saveFile(ActionEvent actionEvent) throws Exception {
        if(manager == null || manager.getFile() == null)
            saveAsFile(actionEvent);
        else
            manager.save();
    }

    public void saveAsFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setInitialFileName(manager.getName() + ".xml");
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        if (file != null) {
            try{
                manager.setFile(file);
                manager.save();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void exitApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void clearData(ActionEvent actionEvent) throws IOException {
        if(manager != null) {
            manager.clear();
            updateTreeView();
        }
    }

    public void deleteSelectedEntity(ActionEvent actionEvent) throws IOException {
        if(selectedEntity == null || selectedEntity == manager)
            return;
        MarketStructure<?> parentItem = manager.findParent(selectedEntity);
        parentItem.remove(selectedEntity);
        updateTreeView();
        updateButtons();
    }

    public void editSelectedEntity(ActionEvent actionEvent) throws IOException {
        switch(selectedEntity){
            case Floor floor -> editFloor(floor);
            case Aisle aisle -> editAisle(aisle);
            case Shelf shelf -> editShelf(shelf);
            case GoodItem goodItem -> editGoodItem(goodItem);
            default -> editManager();
        }
        updateTreeView();
    }

    public void editShelf(Shelf shelf) throws IOException {
        Shelf result = (Shelf) insertPopupEdit("/supermarketmanager/ui/ShelfInsert.fxml", shelf);
        Aisle parent = (Aisle) manager.findParent(selectedEntity);
        LinkedList<GoodItem> oldList = shelf.getList();
        if(result != null) {
            result.addAll(oldList);
            if(parent.replace(shelf, result)) {
                errorOutput.appendText("\n" + "Successfully edited " + shelf.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Failed to edit " + shelf.getName() + ".");
        }
    }

    public void editAisle(Aisle aisle) throws IOException {
        Aisle result = (Aisle) insertPopupEdit("/supermarketmanager/ui/AisleInsert.fxml", aisle);
        Floor parent = (Floor) manager.findParent(selectedEntity);
        LinkedList<Shelf> oldList = aisle.getList();
        if(result != null) {
            result.addAll(oldList);
            if(parent.replace(aisle, result)) {
                errorOutput.appendText("\n" + "Successfully edited " + aisle.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Failed to edit " + aisle.getName() + ".");
        }
    }

    public void editFloor(Floor floor) throws IOException {
        Floor result = (Floor) insertPopupEdit("/supermarketmanager/ui/floorInsert.fxml", floor);
        LinkedList<Aisle> oldList = floor.getList();
        if(result != null) {
            result.addAll(oldList);
            if(manager.replace(floor, result)) {
                errorOutput.appendText("\n" + "Successfully edited " + floor.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Failed to edit " + floor.getName() + ".");
        }
    }

    public void editGoodItem(GoodItem goodItem) throws IOException {
        Shelf shelf = (Shelf) manager.findParent(selectedEntity);
        GoodItem result = (GoodItem) insertPopupEdit("/supermarketmanager/ui/addGoodItem.fxml", goodItem);
        if(result != null) {
            if(shelf.replace((GoodItem) selectedEntity, result)){
                errorOutput.appendText("\n" + "Successfully edited " + goodItem.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Failed to edit " + goodItem.getName() + ".");
        }
    }

    public void editManager() throws IOException {
        SupermarketManager newManager = (SupermarketManager) insertPopupEdit("/supermarketmanager/ui/newManager.fxml", manager);
        if(newManager != null) {
            LinkedList<Floor> oldList = this.manager.getList();
            manager = newManager;
            manager.addAll(oldList);
            errorOutput.appendText("\n" + "Successfully edited " + manager.getName() + ".");
        }
    }

    public void updateButtons(){
        if(manager != null){
            LinkedList<Shelf> list = (LinkedList<Shelf>) manager.getAllShelves();
            autoAddGoodItem.disableProperty().set(list == null || list.isEmpty());
        }
        addFloor.disableProperty().set(true);
        addAisle.disableProperty().set(true);
        addShelf.disableProperty().set(true);
        addGoodItem.disableProperty().set(true);
        switch (selectedEntity){
            case SupermarketManager ignored -> addFloor.disableProperty().set(false);
            case Floor ignored -> addAisle.disableProperty().set(false);
            case Aisle ignored -> addShelf.disableProperty().set(false);
            case Shelf ignored -> addGoodItem.disableProperty().set(false);
            case GoodItem ignored -> {
                addFloor.disableProperty().set(true);
                addAisle.disableProperty().set(true);
                addShelf.disableProperty().set(true);
                addGoodItem.disableProperty().set(true);
            }
            default -> {
                break;
            }
        }
    }

    public void showAddFloor(ActionEvent actionEvent) throws IOException {
        Floor result = (Floor) insertPopup("/supermarketmanager/ui/floorInsert.fxml");
        if(result != null) {
            if(manager.addObject(result, manager)){
                errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + manager.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            updateTreeView();
        }
    }

    public void showAddShelf(ActionEvent actionEvent) throws IOException {
        Shelf result = (Shelf) insertPopup("/supermarketmanager/ui/ShelfInsert.fxml");
        if(result != null) {
            if(manager.addObject(result, selectedEntity)){
                errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + selectedEntity.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            updateTreeView();
        }
    }

    public void showAddAisle(ActionEvent actionEvent) throws IOException {
        Aisle result = (Aisle) insertPopup("/supermarketmanager/ui/AisleInsert.fxml");
        if(result != null) {
            if(manager.addObject(result, selectedEntity)){
                errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + selectedEntity.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            updateTreeView();
        }
    }

    public void showAddGoodItem(ActionEvent actionEvent) throws IOException {
        GoodItem result = (GoodItem) insertPopup("/supermarketmanager/ui/addGoodItem.fxml");
        if(result != null) {
            result.setStorageType(((Aisle) manager.findParent(selectedEntity)).getStorageTypeIndex());
            if(manager.addObject(result, selectedEntity)){
                errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + selectedEntity.getName() + ".");
            }else
                errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            updateTreeView();
        }
    }

    public void showAutoAddGoodItem(ActionEvent actionEvent) throws IOException {
        GoodItem result = (GoodItem) insertPopup("/supermarketmanager/ui/autoAddGoodItemInsert.fxml");
        if(result != null) {
            LinkedList<MarketStructure<?>> list = (LinkedList<MarketStructure<?>>) manager.find(result);
            if(list == null || list.isEmpty()) {
                Shelf parent = manager.findSuitableLocation(result);
                if(manager.addObject(result, parent)){
                    errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + parent.getName() + ".");
                }else
                    errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            }else{
                MarketStructure<?> parentObject = manager.findParent(list.getFirst());
                if(manager.addObject(result, parentObject)){
                    errorOutput.appendText("\n" + "Successfully added " + result.getName() + " to " + parentObject.getName() + ".");
                }else
                    errorOutput.appendText("\n" + "Couldn't add " + result.getName() + "!");
            }
            updateTreeView();
        }
    }

    public void updateEdit(){
        if(selectedEntity != null)
            editMenu.setDisable(false);
    }

    public void showSelectedEntity(MouseEvent mouseEvent) {
        if(treeView.getSelectionModel().getSelectedItem() == null)
            return;
        selectedEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        entityOutput.appendText("\n" + selectedEntity.details());
        entityOutput.setScrollTop(Double.MAX_VALUE);
        updateButtons();
        updateEdit();
        mouseEvent.consume();
    }

    public void showSelectedEntitySearch(MarketStructure<?> item) {
        if(item == null)
            return;
        entityOutput.appendText("\n" + item.details());
        entityOutput.setScrollTop(Double.MAX_VALUE);
    }

    public void search(ActionEvent actionEvent) {
        if(!searchField.getText().isEmpty() || !descriptionField.getText().isEmpty()) {
            String[] searchTerm = {searchField.getText(), descriptionField.getText()};
            LinkedList<MarketStructure<?>> foundItems = (LinkedList<MarketStructure<?>>) manager.search(searchTerm);
            errorOutput.appendText("\n" + "Found " + foundItems.size() + " matches!");
            highlightResults(foundItems);
        }
    }

    public void highlightResults(LinkedList<MarketStructure<?>> results) {
        treeView.getSelectionModel().clearSelection();
        MultipleSelectionModel<TreeItem<MarketStructure<?>>> selectionModel = treeView.getSelectionModel();
        TreeItem<MarketStructure<?>> root = treeView.getRoot();

        for (MarketStructure<?> item : results) {
            TreeItem<MarketStructure<?>> match = findTreeItem(root, item);
            if (match != null) {
                selectionModel.select(match);
            }
        }
        treeView.requestFocus();
    }

    private TreeItem<MarketStructure<?>> findTreeItem(TreeItem<MarketStructure<?>> node, MarketStructure<?> target) {
        if (node.getValue().equals(target)) {
            showSelectedEntitySearch(node.getValue());
            return node;
        }
        for (TreeItem<MarketStructure<?>> child : node.getChildren()) {
            TreeItem<MarketStructure<?>> found = findTreeItem(child, target);
            if (found != null) return found;
        }
        return null;
    }
}

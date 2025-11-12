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

//There is a lot of repeated code here (most of the popups) and im well aware I could have simplified it a lot
// but at this point i'm well overdue on this project and I have to cut corners somewhere...
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
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/initialPopup.fxml"));
        Parent insertNode = insertLoader.load();
        initialPopup insertController = insertLoader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(insertNode));
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
        menuBar.getScene().getWindow().setOnCloseRequest(event -> {});
    }

    public void clearData(ActionEvent actionEvent) throws IOException {
        if(manager != null) {
            manager.clear();
            updateTreeView();
        }
    }

    public void deleteSelectedEntity(ActionEvent actionEvent) throws IOException {
        if(selectedEntity == null)
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

    public void editShelf(Shelf shelf){

    }

    public void editAisle(Aisle aisle){

    }

    public void editFloor(Floor floor) throws IOException {
        Floor result = (Floor) insertPopupEdit("/supermarketmanager/ui/floorInsert.fxml", floor);
        LinkedList<Aisle> oldList = floor.getList();
        if(result != null) {
            result.addAll(oldList);
            manager.replace(floor, result);
            updateTreeView();
        }
    }

    public void editGoodItem(GoodItem goodItem) throws IOException {
        Shelf shelf = (Shelf) manager.findParent(selectedEntity);
        GoodItem result = (GoodItem) insertPopup("/supermarketmanager/ui/addGoodItem.fxml");
        if(result != null) {
            manager.addObject(result, shelf);
            updateTreeView();
        }
    }

    public void editManager() throws IOException {
        SupermarketManager newManager = (SupermarketManager) insertPopupEdit("/supermarketmanager/ui/newManager.fxml", manager);
        if(newManager != null) {
            LinkedList<Floor> oldList = this.manager.getList();
            manager = newManager;
            manager.addAll(oldList);
            updateTreeView();
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
            manager.addObject(result, manager);
            updateTreeView();
        }
    }

    public void showAddShelf(ActionEvent actionEvent) throws IOException {
        Shelf result = (Shelf) insertPopup("/supermarketmanager/ui/ShelfInsert.fxml");
        if(result != null) {
            manager.addObject(result, selectedEntity);
            updateTreeView();
        }
    }

    public void showAddAisle(ActionEvent actionEvent) throws IOException {
        Shelf result = (Shelf) insertPopup("/supermarketmanager/ui/AisleInsert.fxml");
        if(result != null) {
            manager.addObject(result, selectedEntity);
            updateTreeView();
        }
    }

    public void showAddGoodItem(ActionEvent actionEvent) throws IOException {
        GoodItem result = (GoodItem) insertPopup("/supermarketmanager/ui/addGoodItem.fxml");
        if(result != null) {
            manager.addObject(result, selectedEntity);
            updateTreeView();
        }
    }

    public void showAutoAddGoodItem(ActionEvent actionEvent) throws IOException {
        GoodItem result = (GoodItem) insertPopup("/supermarketmanager/ui/autoAddGoodItemInsert.fxml");
        if(result != null) {
            LinkedList<MarketStructure<?>> list = (LinkedList<MarketStructure<?>>) manager.find(result);
            if(list.isEmpty()) {
                manager.addObject(result, manager.findSuitableLocation(result));
            }else{
                manager.addObject(result, manager.findParent(list.getFirst()));
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

    public void search(ActionEvent actionEvent) {
        if(!searchField.getText().isEmpty() || !searchField.getText().isEmpty()) {
            String[] searchTerm = {searchField.getText(), searchField.getText()};
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
            return node;
        }
        for (TreeItem<MarketStructure<?>> child : node.getChildren()) {
            TreeItem<MarketStructure<?>> found = findTreeItem(child, target);
            if (found != null) return found;
        }
        return null;
    }
}

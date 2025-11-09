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
import supermarketmanager.model.linkedlist.LinkedList;
import supermarketmanager.model.supermarket.*;

import java.io.File;
import java.io.IOException;

public class MainController {
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

    public void showMenu(ActionEvent actionEvent) {
    }

    public void initialize() {

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

    public void updateTreeView(){
        treeRoot = new TreeItem<>(manager);
        treeView.setRoot(treeRoot);
        treeView.setShowRoot(false);
        getTreeChildren();
    }

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

    public void clearData(ActionEvent actionEvent) {
        if(manager != null) {
            manager.clear();
            updateTreeView();
        }
    }

    public void deleteSelectedEntity(ActionEvent actionEvent) {
    }

    public void editSelectedEntity(ActionEvent actionEvent) {
    }

    public void showAddFloor(ActionEvent actionEvent) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/floorInsert.fxml"));
        Node insertNode = insertLoader.load();
        FloorInsert insertController = insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insertController); // Pass controller here

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();

        MarketStructure<?> result = skeletonController.getResult();
        manager.add((Floor) result);
    }

    public void showAddAisle(ActionEvent actionEvent) {
    }

    public void showAddShelf(ActionEvent actionEvent) {
    }

    public void showAddGoodItem(ActionEvent actionEvent) {
    }

    public void showAutoAddGoodItem(ActionEvent actionEvent) {
    }

    public void showSelectedEntity(MouseEvent mouseEvent) {
        if(treeView.getSelectionModel().getSelectedItem() == null)
            return;
        selectedEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        entityOutput.appendText("\n" + selectedEntity.details());
        entityOutput.setScrollTop(Double.MAX_VALUE);
        mouseEvent.consume();
    }
}

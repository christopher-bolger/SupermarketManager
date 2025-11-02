package supermarketmanager.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
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
        for(Floor floor : manager.getList()) {
            TreeItem<MarketStructure<?>> nextItem = new TreeItem<>(floor);
            nextItem.setExpanded(true);
            treeRoot.getChildren().add(nextItem);
            if(!floor.isEmpty()) {
                for(Aisle aisle : floor.getList()) {
                    TreeItem<MarketStructure<?>> nextAisle = new TreeItem<>(aisle);
                    nextAisle.setExpanded(true);
                    nextItem.getChildren().add(nextAisle);
                    if(!aisle.isEmpty()) {
                        for(Shelf shelf : aisle.getList()) {
                            TreeItem<MarketStructure<?>> nextShelf = new TreeItem<>(shelf);
                            nextAisle.getChildren().add(nextShelf);
                            if(!shelf.isEmpty()) {
                                for(GoodItem goodItem : shelf.getList()) {
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

    public void showAddFloor(ActionEvent actionEvent) {
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
        if(treeView.getSelectionModel().getSelectedItem().getValue() == null)
            return;
        selectedEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        entityOutput.setText(entityOutput.getText() + "\n" + selectedEntity);
        mouseEvent.consume();
    }
}

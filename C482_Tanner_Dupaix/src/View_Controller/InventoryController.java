package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class InventoryController implements Initializable
{
    @FXML private Button addPartButton;

    private Stage newStage;

    @FXML
    private void addPart(ActionEvent event)
    {
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
            newStage = new Stage();
            newStage.setTitle("Add Part");
            newStage.setScene(new Scene(root, 600, 400));
            newStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        stage.close();
    }

    @FXML
    Button addProductButton;

    @FXML
    private void addProduct(ActionEvent event)
    {
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
            newStage = new Stage();
            newStage.setTitle("Product");
            newStage.setScene(new Scene(root, 830, 520));
            newStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        stage.close();
    }

    @FXML
    Button modifyPart;

    //The button calls the function openModifyPart to add the part
    @FXML
    private void modifyPart() throws IOException
    {
        if(partTable.getSelectionModel().getSelectedItem() instanceof InHouse)
        {
            InHouse partToBeModified = (InHouse) partTable.getSelectionModel().getSelectedItem();
            openModifyInHousePart(partToBeModified);
        } else if (partTable.getSelectionModel().getSelectedItem() instanceof Outsourced)
        {
            Outsourced partToBeModified = (Outsourced) partTable.getSelectionModel().getSelectedItem();
            openModifyOutSourcedPart(partToBeModified);
        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to modify");

            alert.showAndWait();
        }
    }

    private void openModifyInHousePart(InHouse part) throws IOException
    {
        Stage newStage = (Stage) modifyPart.getScene().getWindow();
        newStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));

        ModifyPartController controller = loader.<ModifyPartController>getController();
        controller.initDataInHouse(part);

        stage.setTitle("Modify Part");

        stage.show();
    }

    private void openModifyOutSourcedPart(Outsourced part) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));

        ModifyPartController controller = loader.<ModifyPartController>getController();
        controller.initDataOutsourced(part);

        stage.setTitle("Modify Part");

        stage.show();
    }

    @FXML
    Button modifyProduct;

    @FXML
    private void modifyProduct() throws IOException
    {
        if(productTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to modify");

            alert.showAndWait();
        } else
        {
            Product productToBeModified = productTable.getSelectionModel().getSelectedItem();
            openModifyProduct(productToBeModified);
        }
    }

    private void openModifyProduct(Product product) throws IOException
    {
        Stage newStage = (Stage) modifyProduct.getScene().getWindow();
        newStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));

        ModifyProductController controller = loader.<ModifyProductController>getController();
        controller.initData(product);

        stage.setTitle("Modify Product");

        stage.show();
    }

    @FXML TableView<Part> partTable = new TableView<>();

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partStock;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    Button deletePartButton;

    @FXML
    private void deletePart()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("If deleted this part will no longer be accessible");
        alert.setContentText("Are you sure you want this part to be deleted?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            if(partTable.getSelectionModel().getSelectedItem() instanceof InHouse)
            {
                InHouse partToBeDeleted = (InHouse) partTable.getSelectionModel().getSelectedItem();

                Inventory.deletePart(partToBeDeleted);
            } else if(partTable.getSelectionModel().getSelectedItem() instanceof Outsourced)
            {
                Outsourced partToBeDeleted = (Outsourced) partTable.getSelectionModel().getSelectedItem();

                Inventory.deletePart(partToBeDeleted);
            }
        }

        partTable.getItems().setAll(Inventory.getAllParts());
    }

    @FXML
    TableView<Product> productTable = new TableView<>();

    @FXML
    TableColumn<Product, Integer> productId;

    @FXML
    TableColumn<Product, String> productName;

    @FXML
    TableColumn<Product, Integer> productStock;

    @FXML
    TableColumn<Product, Double> productPrice;

    @FXML
    Button deleteProduct;

    @FXML
    private void deleteProduct()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete product");
        alert.setHeaderText("If this product is deleted it will no longer be accessible");
        alert.setContentText("Are you sure you want to do this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Product productToBeDeleted = productTable.getSelectionModel().getSelectedItem();

            Inventory.deleteProduct(productToBeDeleted);
        }

        productTable.getItems().setAll(Inventory.getAllProducts());
    }

    @FXML
    Button extButton;

    @FXML
    private void exit(){
        Stage stage = (Stage) extButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    Button searchPart;

    @FXML
    TextField searchPartField;

    @FXML
    private void searchPart()
    {
        String search = searchPartField.getText();

        try
        {
            int searchById = Integer.parseInt(search);

            if(Inventory.lookupPart(searchById) instanceof InHouse)
            {
                InHouse newPart = (InHouse) Inventory.lookupPart(searchById);

                ObservableList<Part> partSearch = FXCollections.observableArrayList(newPart);

                partTable.setItems(partSearch);
            } else
            {
                Outsourced newPart = (Outsourced) Inventory.lookupPart(searchById);

                ObservableList<Part> partSearch = FXCollections.observableArrayList(newPart);

                partTable.setItems(partSearch);
            }
        }
        catch (NumberFormatException e) {
            ObservableList<Part> part = Inventory.lookupPart(search);

            partTable.setItems(part);
    }
    }

    @FXML
    Button searchProduct;

    @FXML
    TextField searchProductField;

    @FXML
    private void searchProduct()
    {
        String search = searchProductField.getText();

        try
        {
            int searchById = Integer.parseInt(search);
            Product product = Inventory.lookupProduct(searchById);

            ObservableList<Product> productSearch = FXCollections.observableArrayList(product);

            productTable.setItems(productSearch);
        }
        catch (NumberFormatException e) {
            ObservableList<Product> product = Inventory.lookupProduct(search);

            productTable.setItems(product);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partTable.getItems().addAll(Inventory.getAllParts());

        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        productTable.getItems().addAll(Inventory.getAllProducts());
    }

}


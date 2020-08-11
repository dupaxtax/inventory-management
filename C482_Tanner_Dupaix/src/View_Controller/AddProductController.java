package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AddProductController implements Initializable
{
    @FXML
    TextField name;

    @FXML
    TextField stock;

    @FXML
    TextField price;

    @FXML
    TextField max;

    @FXML
    TextField min;

    @FXML
    Button saveButton;

    @FXML
    TableView<Part> productPartTable;

    @FXML
    TableColumn<Part, Integer> partId;

    @FXML
    TableColumn<Part, String> partName;

    @FXML
    TableColumn<Part, Integer> partStock;

    @FXML
    TableColumn<Part, Double> partPrice;

    @FXML
    private void saveProduct() throws IOException
    {
        checkProduct();
    }

    private void checkProduct() throws IOException
    {
        Stage stage = (Stage) saveButton.getScene().getWindow();

        ObservableList<Part> productParts = FXCollections.observableArrayList(partsInProduct.getItems());
        double totalPrice = 0.00;

        for(int j = 0; j < productParts.size(); ++j)
        {
            if(productParts.get(j) instanceof InHouse)
            {
                InHouse part = (InHouse) productParts.get(j);

                double priceToAdd = part.getPrice();

                totalPrice = totalPrice + priceToAdd;
            } else if (productParts.get(j) instanceof Outsourced)
            {
                Outsourced part = (Outsourced) productParts.get(j);

                double priceToAdd = part.getPrice();

                totalPrice = totalPrice + priceToAdd;
            }
        }

        if (this.name.getText().isEmpty() || this.stock.getText().isEmpty() || this.price.getText().isEmpty() ||
        this.min.getText().isEmpty() || this.max.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must specify a value for all fields.");

            alert.showAndWait();
        } else if (Integer.parseInt(stock.getText()) > Integer.parseInt(max.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The specified inventory is greater than the maximum");

            alert.showAndWait();
        } else if (totalPrice > Double.parseDouble(price.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The total price of parts in this product is more than the price of the product");

            alert.showAndWait();
        } else if (productParts.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There are no parts in this product");

            alert.showAndWait();
        } else if(Integer.parseInt(stock.getText()) < Integer.parseInt(min.getText()))
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setHeaderText("The specified inventory is less than the minimum");

        alert.showAndWait();
        } else
        {
            String name = this.name.getText();
            String stock = this.stock.getText();
            String price = this.price.getText();
            String max = this.max.getText();
            String min = this.min.getText();

            int tempStock = Integer.parseInt(stock);
            double tempPrice = Double.parseDouble(price);
            int tempMax = Integer.parseInt(max);
            int tempMin = Integer.parseInt(min);

            Product newProduct = new Product(Inventory.getNextProductId(), name, tempPrice, tempStock, tempMin, tempMax);

            int size = partsInProduct.getItems().size();

            int i;
            for (i = 0; i < size; ++i)
            {
                newProduct.addAssociatedPart(partsInProduct.getItems().get(i));
            }

            Inventory.addProduct(newProduct);

            FXMLLoader loader =  new FXMLLoader(getClass().getResource("Inventory.fxml"));

            Stage newStage = new Stage(StageStyle.DECORATED);
            newStage.setScene(new Scene((Pane) loader.load()));

            newStage.setTitle("Inventory Management System");

            newStage.show();

            stage.close();
        }
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

            for(int i = 0; i < productPartTable.getItems().size(); ++i)
            {
                if (productPartTable.getItems().get(i) instanceof InHouse)
                {
                    InHouse part = (InHouse) productPartTable.getItems().get(i);

                    if(part.getId() == searchById)
                    {
                        ObservableList<Part> partSearch = FXCollections.observableArrayList(part);

                        productPartTable.setItems(partSearch);
                    }
                } else if (productPartTable.getItems().get(i) instanceof Outsourced)
                {
                    Outsourced part = (Outsourced) productPartTable.getItems().get(i);

                    if(part.getId() == searchById)
                    {
                        ObservableList<Part> partSearch = FXCollections.observableArrayList(part);

                        productPartTable.setItems(partSearch);
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            ObservableList<Part> part = Inventory.lookupPart(search);

            productPartTable.setItems(part);
        }
    }

    @FXML
    TableView<Part> partsInProduct;

    @FXML
    TableColumn<Part, Integer> prodPartId;

    @FXML
    TableColumn<Part, String> prodPartName;

    @FXML
    TableColumn<Part, Integer> prodPartStock;

    @FXML
    TableColumn<Part, Double> prodPartPrice;

    private void inHousePartToProduct(InHouse addPart)
    {
        prodPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        prodPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        prodPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        prodPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partsInProduct.getItems().add(addPart);
    }

    private void outSourcePartToProduct(Outsourced addPart)
    {
        prodPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        prodPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        prodPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        prodPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partsInProduct.getItems().add(addPart);
    }

    @FXML
    Button addPartToProduct;

    @FXML
    private void addPartToProduct()
    {
        if(productPartTable.getSelectionModel().getSelectedItem() instanceof InHouse)
        {
            InHouse partToAdd = (InHouse) productPartTable.getSelectionModel().getSelectedItem();

            inHousePartToProduct(partToAdd);
        } else if (productPartTable.getSelectionModel().getSelectedItem() instanceof Outsourced)
        {
            Outsourced partToAdd = (Outsourced) productPartTable.getSelectionModel().getSelectedItem();

            outSourcePartToProduct(partToAdd);
        }
    }

    @FXML
    Button deletePart;

    @FXML
    private void removePart()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove part from product");
        alert.setHeaderText("The selected part will be removed from this product if you proceed");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) ;
        {
            if(partsInProduct.getSelectionModel().getSelectedItem() instanceof InHouse)
            {
                InHouse partToDelete = (InHouse) partsInProduct.getSelectionModel().getSelectedItem();

                partsInProduct.getItems().remove(partToDelete);
            } else if(partsInProduct.getSelectionModel().getSelectedItem() instanceof Outsourced)
            {
                Outsourced partToDelete = (Outsourced) partsInProduct.getSelectionModel().getSelectedItem();

                partsInProduct.getItems().remove(partToDelete);
            }
        }
    }

    @FXML
    Button cnclButton;

    @FXML
    private void cancelButton() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("All information entered will not be saved if you proceed");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            Stage stage = (Stage) cnclButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inventory Management System");
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productPartTable.getItems().addAll(Inventory.getAllParts());
    }
}

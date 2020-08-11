package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ModifyPartController
{
    @FXML
    Button cnclButton;

    @FXML
    private void cancelButton()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel modify part");
        alert.setHeaderText("Any information changed you have made will not be saved if you proceed");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Parent root;
            Stage stage = (Stage) cnclButton.getScene().getWindow();
            stage.close();

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    RadioButton inHouse;

    @FXML
    private void displayMachine()
    {
        if (inHouse.isSelected())
        {
            cmpName.setText("Machine ID");
            cmpNameTxt.setPromptText("Mach ID");
            cmpNameTxt.clear();
        } else if (outSourced.isSelected())
        {
            cmpName.setText("Company Name");
            cmpNameTxt.setPromptText("Comp Name");
            cmpNameTxt.clear();
        }
    }

    @FXML
    RadioButton outSourced;

    @FXML
    TextField partID;

    @FXML
    TextField partName;

    @FXML
    TextField partStock;

    @FXML
    TextField partPrice;

    @FXML
    TextField partMax;

    @FXML
    TextField partMin;

    @FXML
    TextField cmpNameTxt;

    @FXML
    Text cmpName;

    public void initDataInHouse(InHouse part)
    {
        partID.setText(Integer.toString(part.getId()));
        partName.setText(part.getName());
        partStock.setText(Integer.toString(part.getStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        partMax.setText(Integer.toString(part.getMax()));
        partMin.setText((Integer.toString(part.getMin())));
        cmpNameTxt.setText(Integer.toString(((InHouse) part).getMachineId()));
        inHouse.setSelected(true);
    }

    public void initDataOutsourced(Outsourced part)
    {
        partID.setText(Integer.toString(part.getId()));
        partName.setText(part.getName());
        partStock.setText(Integer.toString(part.getStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        partMax.setText(Integer.toString(part.getMax()));
        partMin.setText((Integer.toString(part.getMin())));
        cmpNameTxt.setText(((Outsourced) part).getCompanyName());
        outSourced.setSelected(true);
        cmpName.setText("Company Name");
    }

    @FXML
    Button save;

    @FXML
    private void saveButton()
    {
        if(partName.getText().trim().isEmpty() || partStock.getText().trim().isEmpty() || partPrice.getText().trim().isEmpty()
                || partMax.getText().trim().isEmpty() || partMin.getText().trim().isEmpty() || cmpNameTxt.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must specify a value for all fields.");

            alert.showAndWait();
        } else if (Integer.parseInt(partStock.getText()) > Integer.parseInt(partMax.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The specified inventory is greater than the maximum");

            alert.showAndWait();
        } else if(Integer.parseInt(partStock.getText()) < Integer.parseInt(partMin.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The specified inventory is less than minimum");

            alert.showAndWait();
        } else
        {
            if(Inventory.lookupPart(Integer.parseInt(partID.getText())) instanceof InHouse)
            {
                InHouse part = (InHouse) Inventory.lookupPart(Integer.parseInt(partID.getText()));
                if(inHouse.isSelected())
                {
                    InHouse modifiedPart = new InHouse(
                            Integer.parseInt(partID.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(cmpNameTxt.getText())
                    );

                    ObservableList<Part> newList = Inventory.getAllParts();

                    int index = newList.indexOf(part);

                    Inventory.updatePart(index, modifiedPart);
                } else if (outSourced.isSelected())
                {
                    Outsourced modifiedPart = new Outsourced(
                            Integer.parseInt(partID.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            cmpNameTxt.getText()
                    );

                    ObservableList<Part> newList = Inventory.getAllParts();

                    int index = newList.indexOf(part);

                    Inventory.updatePart(index, modifiedPart);
                }
                Parent root;

                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();

                FXMLLoader loader =  new FXMLLoader(getClass().getResource("Inventory.fxml"));

                try
                {
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            } else if (Inventory.lookupPart(Integer.parseInt(partID.getText())) instanceof Outsourced)
            {
                Outsourced part = (Outsourced) Inventory.lookupPart(Integer.parseInt(partID.getText()));
                if(outSourced.isSelected())
                {
                    Outsourced modifiedPart = new Outsourced(
                            Integer.parseInt(partID.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            cmpNameTxt.getText()
                    );

                    ObservableList<Part> newList = Inventory.getAllParts();

                    int index = newList.indexOf(part);

                    Inventory.updatePart(index, modifiedPart);
                } else if (inHouse.isSelected())
                {
                    InHouse modifiedPart = new InHouse(
                            Integer.parseInt(partID.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(cmpNameTxt.getText())
                    );

                    ObservableList<Part> newList = Inventory.getAllParts();

                    int index = newList.indexOf(part);

                    Inventory.updatePart(index, modifiedPart);
                }
                Parent root;

                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();

                FXMLLoader loader =  new FXMLLoader(getClass().getResource("Inventory.fxml"));

                try
                {
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}

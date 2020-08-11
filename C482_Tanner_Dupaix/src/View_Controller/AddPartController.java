package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;
import java.util.Optional;


public class AddPartController
{

    private AddPartController addPartController;
    private boolean selectedRadioButton;

    @FXML
    Button cnclButton;

    @FXML
    private void cancelButton()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("All information will not be saved if you proceed");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
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
    Text cmpName;

    @FXML
    TextField cmpNameTxt;

    @FXML
    ToggleGroup partToggleGroup;

    @FXML
    private void displayMachine()
    {
        Stage stage = (Stage) inHouse.getScene().getWindow();

        inHouse.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->
        { });

            cmpName.setText("Machine ID");
            cmpNameTxt.setPromptText("Mach ID");
            selectedRadioButton = true;
    }

    @FXML RadioButton outSourced;

    @FXML
    private void displayCompany()
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

    @FXML Button saveButton;

    @FXML TextField partName;
    @FXML TextField partStock;
    @FXML TextField partPrice;
    @FXML TextField partMin;
    @FXML TextField partMax;

    @FXML
    private void saveClicked() throws IOException
    {
        Stage stage = (Stage) saveButton.getScene().getWindow();

        if (partName.getText().isEmpty() || partStock.getText().isEmpty() || partPrice.getText().isEmpty()
                || partMax.getText().isEmpty() || partMin.getText().isEmpty() || cmpNameTxt.getText().isEmpty())
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
        }  else
        {
            String name = partName.getText();
            String stock = partStock.getText();
            String price = partPrice.getText();
            String min = partMin.getText();
            String max = partMax.getText();
            String last = cmpNameTxt.getText();

            int tempStock = Integer.parseInt(stock);
            double tempPrice = Double.parseDouble(price);
            int tempMin = Integer.parseInt(min);
            int tempMax = Integer.parseInt(max);

            if(inHouse.isSelected())
            {
                int machineId = Integer.parseInt(last);
                InHouse newPart = new InHouse(Inventory.getNextParttId(), name, tempPrice, tempStock, tempMin, tempMax, machineId);
                Inventory.addPart(newPart);
            }
            else
            {
                String companyName = last;
                Outsourced newPart = new Outsourced(Inventory.getNextParttId(), name, tempPrice, tempStock, tempMin, tempMax, companyName);
                Inventory.addPart(newPart);
            }

            FXMLLoader loader =  new FXMLLoader(getClass().getResource("Inventory.fxml"));

            Stage newStage = new Stage(StageStyle.DECORATED);
            newStage.setScene(new Scene((Pane) loader.load()));

            newStage.setTitle("Inventory Management System");

            newStage.show();

            stage.close();
        }
    }
}

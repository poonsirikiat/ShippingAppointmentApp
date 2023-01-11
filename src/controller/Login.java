package controller;

import DAO.DBAppointment;
import DAO.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.DBConnection;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


public class Login implements Initializable {
    public Label loginLabel;
    public Label userIDLabel;
    public TextField userIDLogin;
    public TextField passwordLogin;
    public Label passwordLabel;
    public Button loginButton;
    public Label loginLocation;
    public Button exitButton;
    public Label locationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();

        loginLocation.setText(ZoneId.systemDefault().toString());

        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", locale);
        loginLabel.setText(rb.getString("title"));
        userIDLabel.setText(rb.getString("user"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        locationLabel.setText(rb.getString("location"));
    }

    /**
     * This method get user login input and check for validation to get into the system.
     * If there are appointment within 15 minutes, the user will be notify
     * Any login attempt will be recorded
     * @param actionEvent login action
     * @throws IOException FXMLLoader
     */
    public void loginAction(ActionEvent actionEvent) throws IOException {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
            ObservableList<Appointment> appointmentObservableList = DBAppointment.getAppointmentList();

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            String userID = userIDLogin.getText();
            String userPassword = passwordLogin.getText();
            boolean validation = DBUser.loginValidation(userID, userPassword);
            LocalDateTime appIn15Min = LocalDateTime.now().plusMinutes(15);
            boolean confirmAppIn15Min = false;

            if (validation) {
                outputFile.println("Successful login attempt by user: " + userID + " at " + Timestamp.valueOf(LocalDateTime.now()));

//                for (Appointment app : appointmentObservableList) {
//                    LocalDateTime appStart = app.getAppStartDateTime();
//                    if ((appStart.isEqual(appIn15Min) || appStart.isBefore(appIn15Min)) && appStart.isAfter(LocalDateTime.now())) {
//                        int appID = app.getAppID();
//                        confirmAppIn15Min = true;
//
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Upcoming Appointment");
//                        alert.setHeaderText("There is an upcoming appointment within 15 min: Appointment ID = " + appID + " at " + appStart);
//                        alert.showAndWait();
//                    }
//                }
//                if (!confirmAppIn15Min) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Upcoming Appointment");
//                    alert.setHeaderText("There is no upcoming appointment within 15 min");
//                    alert.showAndWait();
//                }
                Parent parent = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                outputFile.println("Fail login attempt by user: " + userID + " at " + Timestamp.valueOf(LocalDateTime.now()));
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setHeaderText(rb.getString("text"));
                alert.showAndWait();
            }
            outputFile.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method will close the system and disconnect from database
     * @param actionEvent exit action
     */
    public void exitAction(ActionEvent actionEvent) {
        DBConnection.closeConnection();
        System.exit(0);
    }
}

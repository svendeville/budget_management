/*
 * @2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 *
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv;

import com.snv.stage.Application;
import com.snv.stage.ApplicationEvent;
import com.snv.view.MainView;
import com.snv.view.Splash;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sylvain
 */
@SpringBootApplication
public class BudgetManagementApplication extends AbstractJavaFxApplicationSupport {

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n.main.main");

    private static final Application application = new Application();

    public static void main(final String[] args) throws Exception {
        launch(BudgetManagementApplication.class, MainView.class, new Splash(), args);
    }

    public static Application getApplication() {
        return application;
    }

    @Bean("mainApplication")
    public BudgetManagementApplication mainApplication() {
        return this;
    }

    public void confirmExitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("confirmation"));
        alert.setHeaderText(bundle.getString("application.exit.confirmation"));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.showAndWait().ifPresent(this::exitApplication);
    }

    public void changeLanguage(String languageId) {
        this.bundle = ResourceBundle.getBundle("i18n.main.main", Locale.forLanguageTag(languageId));
    }

    private void exitApplication(ButtonType buttonType) {
        try {
            if (buttonType.equals(ButtonType.YES)) {
                stop();
                Platform.exit();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Oops! An unrecoverable error occurred.\nPlease contact your software vendor." +
                            "\n\nThe application will stop now.\n\nError: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait().ifPresent((response) -> {
                Platform.exit();
            });
        }
    }

    @PostConstruct
    public void setEventListener() {
        application.addEventHandler(ApplicationEvent.APPLICATION_EXIT, event -> {
            BudgetManagementApplication.this.confirmExitApplication();
        });
    }

    public void showIdentification() throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setResources(ResourceBundle.getBundle("i18n/auth/authentication"));
        fXMLLoader.setLocation(getClass().getResource("/com/snv/view/auth/authentication.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(fXMLLoader.getResources().getString("title"));
        stage.show();

        stage.setOnCloseRequest((WindowEvent event1) -> {
            this.confirmExitApplication();
        });
    }
}

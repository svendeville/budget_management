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
import com.snv.stage.node.ApplicationNode;
import com.snv.view.MainView;
import com.snv.view.Splash;
import com.snv.view.auth.AuthenticationView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sylvain
 */
@SpringBootApplication
@Slf4j
public class BudgetManagementApplication extends AbstractJavaFxApplicationSupport {

    private static String threadName = "FX application thread";

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n.main.main");

    private static final Application application = new Application();

    private boolean identificationMustShow = true;

    @Autowired
    private ApplicationNode node;

    public static void main(final String[] args) {
        launch(BudgetManagementApplication.class, MainView.class, new Splash(), args);
    }

    public static Application getApplication() {
        return application;
    }

    @Bean("mainApplication")
    public BudgetManagementApplication mainApplication() {
        log.info("Configuration de main application");
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

    @EventListener(ContextRefreshedEvent.class)
    public void setEventListener() {
        this.node.addEventHandler(ApplicationEvent.APPLICATION_EXIT, event -> {
            if (!event.isConsumed()) {
                this.confirmExitApplication();
            }
            event.consume();
        });
    }

    public void showIdentification() {
        BudgetManagementApplication.showView(AuthenticationView.class, Modality.APPLICATION_MODAL);
    }
}

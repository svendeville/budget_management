/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Management.
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
 * along with Budget Management. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.controllers;

import com.snv.entity.Credential;
import com.snv.entity.User;
import com.snv.services.UserService;
import com.snv.stage.ApplicationEvent;
import com.snv.stage.node.ApplicationNode;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Rest Crud Implentation that manages Users request. Its main function is to catch request, convert data
 * into functional one and delegate to service the tasks to perform operations upon UserController.
 * Finally, send the response back to the consumer
 * If exceptions occurs, treatment of the corresponding responses are made
 */
@FXMLController
@Slf4j
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationNode node;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private User user = new User();

    @FXML
    private Long userId;

    @FXML
    private Collection<User> users = new ArrayList<>();

    public void post(final Event event) {
        /*
        if (user.getProfile() == null) {
            //user.setProfile(Profile.ADMIN);
        }
        */
        this.user = this.userService.create(user);
    }

    public void get(final Event event) {
        this.user = this.userService.get(userId);
    }

    public void getAll(final Event event) {
        this.users = this.userService.getAll();
    }

    public void put(final Event event) {
        this.user = this.userService.put(user);
    }

    public void delete(final Event event) {
        this.userService.delete(user);
    }

    public void login(final ActionEvent event) {
        Credential credential = new Credential();
        credential.setLogin(this.login.getText());
        credential.setPassword(this.password.getText());
        Alert alert = new Alert(Alert.AlertType.NONE, credential.toString(), ButtonType.OK);
        alert.show();
        /*try {
            this.user = this.authenticationService.authenticate(credential);
        } catch (HmacException e) {
            log.error("Authentication failure", e);
        }*/
        //throw new InvalidCredentialException("Authentication failure");
    }

    public void logout(String token) {
        //this.authenticationService.logout();
        this.user = new User();
    }

    public void cancel(ActionEvent actionEvent) {
        this.node.fireEvent(ApplicationEvent.exitApplicationEvent(actionEvent.getTarget()));
    }
}

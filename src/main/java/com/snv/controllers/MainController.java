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


import com.snv.BudgetManagementApplication;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@FXMLController
@Slf4j
public class MainController {

    @Autowired
    private BudgetManagementApplication mainApplication;

    public void onExitApplication(ActionEvent actionEvent) {
        log.debug("User choice exit menu item, system while exit !");
        this.mainApplication.confirmExitApplication();
    }

    public void onLanguageChange(ActionEvent actionEvent) {
        this.mainApplication.changeLanguage(((MenuItem) actionEvent.getTarget()).getId());
    }

//    public void onApplicationStarted()
}

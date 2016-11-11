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
package com.snv.berkeleydb;

public enum DataBaseNamesEnum {
    BUDGETS("BUDGETS"), CATEGORIES("CATEGORIES"), COMPTES("COMPTES"), ECHEANCIERS("ECHEANCIERS"), MODEDEPAIEMENT("MODEDEPAIEMENT"), MOUVEMENTS(
            "MOUVEMENTS"), MOUVEMENTSECHEANCIER("MOUVEMENTSECHEANCIER"), PARAMETERS("PARAMETERS"), TYPEDECOMPTES(
                    "TYPEDECOMPTES"), TYPEDEMOUVEMENT("TYPEDEMOUVEMENT"), TYPEECHEANCIER("TYPEECHEANCIER"), USERS("USERS");

    private final String value;

    DataBaseNamesEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

/*
 * @2016 Sylvain Vendeville.
 * This file is part of MesComptes.
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
package com.snv.budget.exceptions;

/**
 * @author Sylvain Vendeville
 *
 */
public class ParameterKeyNotInitializedException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3935075547089105740L;

    public ParameterKeyNotInitializedException() {
        super();
    }

    public ParameterKeyNotInitializedException(final String message) {
        super(message);
    }

    public ParameterKeyNotInitializedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParameterKeyNotInitializedException(final Throwable cause) {
        super(cause);
    }
}

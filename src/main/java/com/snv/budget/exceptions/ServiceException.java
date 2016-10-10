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
public class ServiceException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -182467549117744262L;

    /**
     *
     */
    public ServiceException() {
    }

    /**
     * @param arg0
     */
    public ServiceException(final String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ServiceException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public ServiceException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    /**
     * @param arg0
     */
    public ServiceException(final Throwable arg0) {
        super(arg0);
    }
}

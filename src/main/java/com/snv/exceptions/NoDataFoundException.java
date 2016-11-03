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
package com.snv.exceptions;

import com.sleepycat.je.DatabaseException;
public class NoDataFoundException extends DatabaseException {

    /**
     *
     */
    private static final long serialVersionUID = -4640880209058226046L;

    public NoDataFoundException(final String message) {
        super(message);
    }

    public NoDataFoundException(final String message, final Throwable t) {
        super(message, t);
    }

    public NoDataFoundException(final Throwable t) {
        super(t);
    }
}

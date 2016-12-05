/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 * Budget Managment is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Budget Managment is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.user;

import java.util.List;
import java.util.UUID;

/**
 * interface to manage users
 */
interface UserService {
    
    /**
     * Create a new user with the given information
     * The user must be valid
     * @param user the {@link User} user to create
     * @return the user created with the new technical identifier
     */
    User create(final User user);
    
    /**
     * Get a user by identifier
     * @param userId, the identifier to get the user
     * @return the user found by the identifier, null otherwise
     */
    User get(final Long userId);
    
    /**
     * Get all users
     * @return the list of user containing all dataBase users
     */
    List<User> getAll();
    
    /**
     * Udapte user PUT endpoint
     * @param user the user to update
     * @return the same user.
     */
    User put(final User user);
    
    /**
     * Delete user
     * @param user the user to delete
     * @return boolean true if user is successfuly deleted, false otherwise
     */
    Boolean delete(final User user);
    
    /**
     * Find user By login
     * @param Credential to find user
     * @return The user found by credentiel, InvalidCredentialException otherwise
     */
    User byLogin(final Credential credential);
    
    /**
     * Generate new token for current session
     * @param user the user to session
     * @return new string token
     */
    UUID generateToken();
}

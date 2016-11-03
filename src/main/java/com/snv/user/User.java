/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 * MesComptes is free software: you can redistribute it and/or modify
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

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Pojo to manage the users
 */
@Data
public class User implements Serializable {
    
    /**
     * Serial Version for Serialization
     */
    private static final long serialVersionUID = -1959859065593124107L;
    
    private Long id;
    
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String firstName;
    
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String lastName;
    
    @NotNull
    @NotBlank
    @Size(min = 3)
    @Email
    private String email;
    
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String login;
    
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String password;
}

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
package com.snv.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    //private Profile profile;
    
    private List<GrantedAuthority> authorities = new ArrayList<>();

    private String publicSecret;

    private String privateSecret;

    private String csrfId;

    private String jwt;
}

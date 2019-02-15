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

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Currency;
import java.util.List;

/**
 * Pojo to manage The users bank accounts
 */
@Data
public class Account implements Serializable {

    /**
     * Serial Version for Serialization
     */
    private static final long serialVersionUID = -1959859066547124107L;

    private Long id;

    @NotNull
    @Min(1000)
    @Max(9999)
    private Long bankNumber;

    @NotNull
    @Min(1000)
    @Max(9999)
    private Long bankDeskNumber;

    @NotNull
    @Min(10000000L)
    @Max(99999999999L)
    private Long accountNumber;

    @NotNull
    @Min(10)
    @Max(99)
    private Long keyNumber;

    @NotNull
    private Currency currency;

    @NotNull
    @NotBlank
    @Size(min = 4)
    private String bankName;

    @NotNull
    @NotBlank
    @Size(min = 4)
    private String bankAddress;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String bankWebSite;

    @NotNull
    @NotEmpty
    private List<User> users;

}

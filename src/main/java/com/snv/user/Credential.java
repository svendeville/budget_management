/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snv.user;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author sylvain
 */
@Data
public class Credential implements Serializable {
    
    /**
     * Serial Version for Serialization
     */
    private static final long serialVersionUID = -2564859065593124107L;
    
    private String login;
    private String password;
}

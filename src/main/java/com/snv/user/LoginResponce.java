/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snv.user;

import java.util.UUID;
import lombok.Data;

/**
 *
 * @author sylvain
 */
@Data
public class LoginResponce {
    
    private UUID token;
    private User user;
}

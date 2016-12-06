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
package com.snv.guard;

import com.snv.guard.hmac.HmacRequester;
import com.snv.user.User;
import com.snv.user.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sylvain
 */
@Service
public class DefaultHmacRequester implements HmacRequester {
    
    @Autowired
    private UserService userService;

    @Override
    public Boolean canVerify(HttpServletRequest request) {
        return request.getRequestURI().contains("/api") 
                && !request.getRequestURI().contains("/api/users/login")
                && !request.getRequestURI().contains("/api/users/create");
    }

    @Override
    public String getPublicSecret(String iss) {
        User user = userService.get(Long.valueOf(iss));
        if(user != null){
            return user.getPublicSecret();
        }
        return null;
    }
}

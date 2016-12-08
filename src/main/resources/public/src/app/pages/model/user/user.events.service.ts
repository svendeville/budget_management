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
import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {User} from './user';

@Injectable()
export class UserEventsService extends Subject<any> {
    constructor() {
        super();
    }
    loginSuccess(user:User) {
        if(user) {
            user.authenticated = true;
            super.next(user);
        }
    }
    logout(user:User) {
        if(user) {
            user.authenticated = false;
            super.next(user);
        }
    }
}

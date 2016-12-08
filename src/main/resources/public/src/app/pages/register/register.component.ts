import {Component, ViewEncapsulation} from '@angular/core';
import {FormGroup, AbstractControl, FormBuilder, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {EmailValidator, EqualPasswordsValidator} from '../../theme/validators';
import {RegisterService} from './register.service';
import { User } from "./../model/user/user";

@Component({
  selector: 'register',
  encapsulation: ViewEncapsulation.None,
  styles: [require('./register.scss')],
  template: require('./register.html'),
})
export class Register {

  public form:FormGroup;
  public lastName:AbstractControl;
  public firstName:AbstractControl;
  public email:AbstractControl;
  public login:AbstractControl;
  public password:AbstractControl;
  public repeatPassword:AbstractControl;
  public passwords:FormGroup;

  public submitted:boolean = false;

  constructor(fb: FormBuilder, private registerService: RegisterService, private router:Router) {

    this.form = fb.group({
      'lastName': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'firstName': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'email': ['', Validators.compose([Validators.required, EmailValidator.validate])],
      'login': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'passwords': fb.group({
        'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
        'repeatPassword': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
      }, {validator: EqualPasswordsValidator.validate('password', 'repeatPassword')})
    });

    this.lastName = this.form.controls['lastName'];
    this.firstName = this.form.controls['firstName'];
    this.email = this.form.controls['email'];
    this.login = this.form.controls['login'];
    this.passwords = <FormGroup> this.form.controls['passwords'];
    this.password = this.passwords.controls['password'];
    this.repeatPassword = this.passwords.controls['repeatPassword'];
  }

  public onSubmit(values:Object):void {
    this.submitted = true;
    if (this.form.valid) {
       let user:User = new User();
       this.populateUser(user);
       this.registerService.post(user)
          .subscribe(registerUser => {
            user.id = registerUser.id;
            this.router.navigate(['']);
        });
    }
  }
  
  private populateUser(user:User) : void {
     user.lastName = this.lastName.value;
     user.firstName = this.firstName.value;
     user.email = this.email.value;
     user.login = this.login.value;
     user.password = this.password.value;
  }
}

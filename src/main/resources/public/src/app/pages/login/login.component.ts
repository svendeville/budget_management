import {Component, ViewEncapsulation} from '@angular/core';
import { Router} from '@angular/router';
import {FormGroup, AbstractControl, FormBuilder, Validators} from '@angular/forms';
import { Credential, User } from './../model/user';
import {LoginService} from './';

@Component({
  selector: 'login',
  encapsulation: ViewEncapsulation.None,
  styles: [require('./login.scss')],
  template: require('./login.html'),
})
export class Login {

  public form:FormGroup;
  public login:AbstractControl;
  public password:AbstractControl;
  public submitted:boolean = false;
  public user:User;

  constructor(private router: Router,fb: FormBuilder, private loginService: LoginService) {
    this.form = fb.group({
      'login': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
    });

    this.login = this.form.controls['login'];
    this.password = this.form.controls['password'];
  }

  public onSubmit():void {
    this.submitted = true;
    if (this.form.valid) {
        let credential:Credential = new Credential();
        credential.login = this.login.value;
        credential.password = this.password.value;
        this.loginService
            .post(credential)
            .subscribe(user => {
                this.user = user;
                console.log("Successfully logged", user.firstName + " " + user.lastName);
                this.router.navigate(['/pages/dashbord']);
        });
    }
  }
}

export class User {

  id: string;
  lastName: string;
  firstName: string;
  email: string;
  login: string;
  password: string;
  profile: string;
  authorities: Array<string>;
  authenticated = true;

  constructor(user?: {
    id: number, lastName: string, firstName: string, email: string,
    login: string, profile: string, authorities: Array<string>
  }) {
    if (user) {
      Object.assign(this, user);
      this.authenticated = false;
    }
  }
}

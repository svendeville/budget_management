import {RouterModule, Routes} from "@angular/router";
import {Pages} from "./pages.component";
import {ModuleWithProviders} from "@angular/core";
// noinspection TypeScriptValidateTypes

// export function loadChildren(path) { return System.import(path); };

export const routes: Routes = [
  {
    path: 'pages',
    component: Pages,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      {path: 'login', loadChildren: './login/login.module#LoginModule'},
      {path: 'register', loadChildren: './register/register.module#RegisterModule'},
      {path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule'}
    ]
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);

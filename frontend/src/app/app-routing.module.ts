import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './components/home/home.component'
import {ProjectComponent} from './components/project/project.component'
import {UserdashboardComponent} from './components/userdashboard/userdashboard.component'
import { LoginComponent } from './components/login/login.component';
import {AuthGuard} from './auth.guard'
import { ComponentComponent } from './components/component/component.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'userdashboard', component: UserdashboardComponent, canActivate: [AuthGuard],data: { requiredRoles: ['ROLE_MEMBER', 'ROLE_MANAGER'] } },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'project/:projNumber', component: ProjectComponent, canActivate: [AuthGuard] },
  { path: 'component/:compNumber', component: ComponentComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

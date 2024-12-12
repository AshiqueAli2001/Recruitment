// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminLandingComponent } from './admin-landing/admin-landing.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { QuestionsComponent } from './questions/questions.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { AddCandidateComponent } from './add-candidate/add-candidate.component';
import { CandidateComponent } from './candidate/candidate.component';
import { AddCriteriaComponent } from './add-criteria/add-criteria.component';
import { ViewQuestionComponent } from './view-question/view-question.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { CandidateLandingComponent } from './candidate-landing/candidate-landing.component';
import { TestComponent } from './test/test.component';
import { TestCodeComponent } from './test-code/test-code.component';
import { ViewCodequestionComponent } from './view-codequestion/view-codequestion.component';
import { ViewResultComponent } from './viewresult/view.result.component';
import { AuthGuard } from './shared/auth.guard';
import { TestGuard } from './shared/test.guard';
import { ThankyouComponent } from './thankyou/thankyou.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'adminlanding', component: AdminLandingComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'sidenav', component: SidenavComponent,canActivate:[AuthGuard] , data: { expectedRole: 'ADMIN' }},
  { path: 'question', component: QuestionsComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'adduser', component: AddAdminComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' } },
  { path: 'addcandidate', component: AddCandidateComponent ,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' }},

  { path: 'candidate', component: CandidateComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' } },

  { path: 'criteria', component: AddCriteriaComponent,canActivate:[AuthGuard] , data: { expectedRole: 'ADMIN' }},
  { path: 'allques', component: ViewQuestionComponent,canActivate:[AuthGuard] , data: { expectedRole: 'ADMIN' }},
  { path: 'profile', component: AdminProfileComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' } },

  { path: 'candidatelanding', component: CandidateLandingComponent,canActivate:[AuthGuard,TestGuard] , data: { expectedRole: 'USER' }},
  { path: 'test', component: TestComponent ,canActivate:[AuthGuard,TestGuard], data: { expectedRole: 'USER' }},
  { path: 'test-code', component: TestCodeComponent ,canActivate:[AuthGuard,TestGuard], data: { expectedRole: 'USER' }},

  { path: 'codeques', component: ViewCodequestionComponent,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' }},
  { path: 'testresult', component: ViewResultComponent ,canActivate:[AuthGuard], data: { expectedRole: 'ADMIN' }},
  { path: 'thankYou', component: ThankyouComponent ,canActivate:[AuthGuard], data: { expectedRole: 'USER' }},

  // Add other routes as needed
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect to login by default
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

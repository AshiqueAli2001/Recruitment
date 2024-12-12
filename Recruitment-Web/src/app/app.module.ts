// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AdminLandingComponent } from './admin-landing/admin-landing.component';
import { AddCriteriaComponent } from './add-criteria/add-criteria.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { AdminbodyComponent } from './adminbody/adminbody.component';
import { ViewQuestionComponent } from './view-question/view-question.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { CandidateLandingComponent } from './candidate-landing/candidate-landing.component';
import { TestComponent } from './test/test.component';
import { HttpClientModule } from '@angular/common/http'; 
import { AddAdminComponent } from './add-admin/add-admin.component';
import { TestCodeComponent } from './test-code/test-code.component';
import { FormsModule } from '@angular/forms';
import { QuestionsComponent } from './questions/questions.component';
import { ToastrModule } from 'ngx-toastr';
import { ViewCodequestionComponent } from './view-codequestion/view-codequestion.component';
import { ViewResultComponent } from './viewresult/view.result.component';
import { authInterceptorProviders } from './interceptor/http.interceptor';
import { AuthService } from './auth.service';
import { ThankyouComponent } from './thankyou/thankyou.component';
import { CalendarModule } from 'primeng/calendar';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminLandingComponent,
    AddCriteriaComponent,
    SidenavComponent,
    AddAdminComponent,
    AdminbodyComponent,
    ViewQuestionComponent,
    AdminProfileComponent,
    CandidateLandingComponent,
    TestComponent,
    TestCodeComponent,
    QuestionsComponent,
    ViewCodequestionComponent,
    ViewResultComponent,
    ThankyouComponent
     ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CalendarModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
       positionClass:'toast-top-center',
    preventDuplicates:true,
    tapToDismiss:true}),
  ],

  providers: [AuthService,authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

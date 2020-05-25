import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { StudentsComponent } from './students/students.component';
import { ProblemsComponent } from './problems/problems.component';
import { GradesComponent } from './grades/grades.component';
import { StudentListComponent } from './students/student-list/student-list.component';
import { StudentAddComponent } from './students/student-add/student-add.component';
import { StudentDetailComponent } from './students/student-detail/student-detail.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import { HomeComponent } from './home/home.component';
import {StudentService} from "./students/shared/student.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { ProblemListComponent } from './problems/problem-list/problem-list.component';
import { ProblemDetailComponent } from './problems/problem-detail/problem-detail.component';
import { ProblemAddComponent } from './problems/problem-add/problem-add.component';
import {ProblemService} from "./problems/shared/problem.service";
import { GradeListComponent } from './grades/grade-list/grade-list.component';
import { GradeDetailComponent } from './grades/grade-detail/grade-detail.component';
import { GradeAddComponent } from './grades/grade-add/grade-add.component';
import {GradeService} from "./grades/shared/grade.service";

@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    ProblemsComponent,
    GradesComponent,
    StudentListComponent,
    StudentAddComponent,
    StudentDetailComponent,
    HomeComponent,
    ProblemListComponent,
    ProblemDetailComponent,
    ProblemAddComponent,
    GradeListComponent,
    GradeDetailComponent,
    GradeAddComponent
  ],
    imports: [
        BrowserModule,
        RouterModule,
        HttpClientModule,
        AppRoutingModule,
        FormsModule
    ],
  providers: [StudentService, ProblemService, GradeService],
  bootstrap: [AppComponent]
})
export class AppModule { }

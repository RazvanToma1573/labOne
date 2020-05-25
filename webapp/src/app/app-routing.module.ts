import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {StudentsComponent} from "./students/students.component";
import {StudentDetailComponent} from "./students/student-detail/student-detail.component";
import {StudentAddComponent} from "./students/student-add/student-add.component";
import {HomeComponent} from "./home/home.component";
import {ProblemsComponent} from "./problems/problems.component";
import {ProblemDetailComponent} from "./problems/problem-detail/problem-detail.component";
import {GradesComponent} from "./grades/grades.component";
import {GradeDetailComponent} from "./grades/grade-detail/grade-detail.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'students', component: StudentsComponent, children: [
      {path: 'details/:id', component: StudentDetailComponent}
    ]},
  {path: 'problems', component: ProblemsComponent, children: [
      {path: 'details/:id', component: ProblemDetailComponent}
    ]},
  {path: 'grades', component: GradesComponent, children: [
      {path: 'details/:id', component: GradeDetailComponent}
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

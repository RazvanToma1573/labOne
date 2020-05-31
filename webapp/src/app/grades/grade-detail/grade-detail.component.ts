import {Component, Input, OnInit} from '@angular/core';
import {Grade} from "../shared/grade.module";
import {GradeService} from "../shared/grade.service";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {ProblemService} from "../../problems/shared/problem.service";
import {StudentService} from "../../students/shared/student.service";
import {Student} from "../../students/shared/student.model";
import {Problem} from "../../problems/shared/problem.model";

@Component({
  selector: 'app-grade-detail',
  templateUrl: './grade-detail.component.html',
  styleUrls: ['./grade-detail.component.css']
})
export class GradeDetailComponent implements OnInit {
  @Input() grade: Grade = new Grade(new Student(), new Problem(), 1);
  student: Student = new Student();
  problem: Problem = new Problem();
  constructor(private gradeService: GradeService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.gradeService.getGrade(+params['id'])))
      .subscribe(grade => {
        this.grade = grade;
        console.log(grade);
      });
  }

  onUpdate() {
    this.gradeService.update(this.grade);
  }

}

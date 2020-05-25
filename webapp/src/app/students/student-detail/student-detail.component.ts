import {Component, Input, OnInit} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Location} from "@angular/common";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  @Input() student: Student = new Student();

  constructor(private studentService: StudentService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.studentService.getStudent(+params['id'])))
      .subscribe(student => this.student = student);
  }

  onUpdate() {
    this.studentService.update(this.student);
  }

  goBack() {
    this.location.back();
  }

}

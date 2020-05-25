import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {GradeService} from "../shared/grade.service";
import {Problem} from "../../problems/shared/problem.model";
import {Grade} from "../shared/grade.module";

@Component({
  selector: 'app-grade-add',
  templateUrl: './grade-add.component.html',
  styleUrls: ['./grade-add.component.css']
})
export class GradeAddComponent implements OnInit {
  @ViewChild('studentId') studentIdRef: ElementRef;
  @ViewChild('problemId') problemIdRef: ElementRef;
  private studentIdAdd : number = 0;
  private problemIdAdd : number = 0;
  @Input() currentPage: number;

  constructor(private gradeService: GradeService) { }

  ngOnInit(): void {
  }

  onAdd() {
    const grade = new Grade(this.studentIdRef.nativeElement.value, this.problemIdRef.nativeElement.value, 0);
    this.gradeService.save(this.currentPage, grade);
    this.studentIdRef.nativeElement.value = 0;
    this.problemIdRef.nativeElement.value = 0;
    this.studentIdAdd = 0;
    this.problemIdAdd = 0;
  }

  checkCanAdd() {
    return this.studentIdAdd === 0 || this.problemIdAdd === 0;
  }

  changeStudentId(event) {
    this.studentIdAdd = event.target.value;
  }

  changeProblemId(event) {
    this.problemIdAdd = event.target.value;
  }

}

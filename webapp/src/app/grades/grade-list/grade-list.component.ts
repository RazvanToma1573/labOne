import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Grade} from "../shared/grade.module";
import {GradeService} from "../shared/grade.service";
import {Router} from "@angular/router";
import {Student} from "../../students/shared/student.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-grade-list',
  templateUrl: './grade-list.component.html',
  styleUrls: ['./grade-list.component.css']
})
export class GradeListComponent implements OnInit {
  error: string;
  grades: Array<Grade>;
  selectedGrade: Grade;
  currentPage = 0;
  nrOfPages = 0;
  @ViewChild('sortStudentId') sortStudentIdRef: ElementRef;
  @ViewChild('sortProblemId') sortProblemIdRef: ElementRef;
  @ViewChild('sortActualGrade') sortActualGradeRef: ElementRef;

  constructor(private gradeService: GradeService,
              private router: Router,
              private location: Location) { }

  ngOnInit(): void {
    this.getGrades(0);
    this.currentPage = 0;
    this.gradeService.getAllGrades().subscribe(
      grades => this.nrOfPages = grades.length
    );
    this.gradeService.listUpdated
      .subscribe((grades: Grade[]) => {
        this.grades = grades;
      });
    this.gradeService.pagesUpdated
      .subscribe(nr => this.nrOfPages = nr);
  }

  getGrades(page: number) {
    this.gradeService.getGrades(page)
      .subscribe(grades => this.grades = grades,
        error => this.error = error);
  }

  onSelect(grade: Grade) {
    this.selectedGrade = grade;
    this.router.navigate(['/grades/details', this.selectedGrade.id]);
  }

  onSort() {
    var studentId = this.sortStudentIdRef.nativeElement.value;
    var problemId = this.sortProblemIdRef.nativeElement.value;
    var actualGrade = this.sortActualGradeRef.nativeElement.value;
    this.gradeService.getGradesSorted(this.currentPage, studentId, problemId, actualGrade);
  }

  onLeft() {
    this.currentPage -= 1;
    this.getGrades(this.currentPage);
  }

  onRight() {
    this.currentPage += 1;
    this.getGrades(this.currentPage);
  }

  remove(id: number) {
    this.gradeService.delete(this.currentPage, id);
  }

  goBack() {
    this.location.back();
  }

}

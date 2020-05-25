import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {min} from "rxjs/operators";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  error: string;
  students: Array<Student>;
  selectedStudent : Student;
  currentPage = 0;
  nrOfPages = 0;
  sorted = false;
  filtered = false;
  @ViewChild('sortId') sortIdRef: ElementRef;
  @ViewChild('sortFirstName') sortFirstNameRef: ElementRef;
  @ViewChild('sortLastName') sortLastNameRef: ElementRef;
  @ViewChild('filterType') filterTypeRef: ElementRef;
  @ViewChild('filterArgument') filterArgumentRef: ElementRef;

  constructor(private studentService: StudentService,
              private router: Router,
              private location: Location) { }

  ngOnInit(): void {
    this.getStudents(0);
    this.currentPage = 0;
    this.studentService.getAllStudents().subscribe(
      students => this.nrOfPages = students.length
    );
    this.studentService.listUpdated
      .subscribe((students: Student[]) => {
        this.students = students;
      });
    this.studentService.pagesUpdated
      .subscribe(nr => this.nrOfPages = nr);

  }

  getStudents(page: number) {
    this.studentService.getStudents(page)
      .subscribe(
        students => {
          this.students = students;
          console.log(students);
        },
        error => this.error = <any>error
      );
  }

  onSelect(student: Student) {
    this.selectedStudent = student;
    this.router.navigate(['/students/details', this.selectedStudent.id]);
  }

  onRemove(student: Student) {
    if(confirm("Do you really want to remove the student?")) {
      this.studentService.delete(this.currentPage, student.id);
      if((this.currentPage)*5 === this.nrOfPages) {
        this.currentPage -= 1;
        this.getStudents(this.currentPage);
      }
      if(this.selectedStudent.id === student.id) {
        this.router.navigate(['/students']);
      }
    }
  }

  onSort() {
    var id = this.sortIdRef.nativeElement.value;
    var firstName = this.sortFirstNameRef.nativeElement.value;
    var lastName = this.sortLastNameRef.nativeElement.value;
    this.studentService.getStudentsSorted(0, id, firstName, lastName);
    this.currentPage = 0;
    this.studentService.currentPage = 0;
  }

  onLeft() {
    this.currentPage -= 1;
    if(this.sorted) {
      this.getSorted(this.currentPage);
    }
    else if(this.filtered) {
      this.getFiltered(this.currentPage);
    }
    else {
      this.getStudents(this.currentPage);
    }
  }

  onRight() {
    this.currentPage += 1;
    if(this.sorted) {
      this.getSorted(this.currentPage);
    }
    else if(this.filtered) {
      this.getFiltered(this.currentPage);
    }
    else {
      this.getStudents(this.currentPage);
    }

  }

  onFilter() {
    var type = this.filterTypeRef.nativeElement.value;
    var argument = this.filterArgumentRef.nativeElement.value;
    this.studentService.filter(this.currentPage, this.filterTypeRef.nativeElement.value,
      this.filterArgumentRef.nativeElement.value);
  }

  goBack() {
    this.location.back();
  }

  getSorted(page: number) {
    this.filtered = false;
    this.sorted = true;
    var column = "";
    var order = "";
    if(this.sortIdRef.nativeElement.value !== 'none') {
      column = 'id';
      order = this.sortIdRef.nativeElement.value;
    }
    else if(this.sortFirstNameRef.nativeElement.value !== 'none') {
      column = 'firstName';
      order = this.sortFirstNameRef.nativeElement.value;
    }
    else {
      column = 'lastName';
      order = this.sortLastNameRef.nativeElement.value;
    }
    this.studentService.getAllStudents()
      .subscribe(students => {
        let coef = 1;
        if(order === "asc") {
          coef  = -1;
        }
        students.sort((st1, st2) => {
          if(st1[column] < st2[column]) {
            return 1*coef;
          }
          else {
            return -1*coef;
          }
        });
        console.log(students, page);
        console.log(students.slice(page*5, 5));
        this.students = students.slice(page*5, Math.min(page*5 + 5, students.length));
      });
  }

  getFiltered(page: number) {
    this.filtered = true;
    this.sorted = false;
    var argument = this.filterArgumentRef.nativeElement.value;
    var column = this.filterTypeRef.nativeElement.value;
    this.studentService.getAllStudents().subscribe(
      students => {
        console.log(students);
        students = students.filter(student => student[column] === argument);
        console.log(students);
        this.students = students.slice(page*5, Math.min(page*5 + 5, students.length));
      }
    )
  }

}

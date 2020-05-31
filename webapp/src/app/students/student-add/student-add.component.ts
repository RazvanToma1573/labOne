import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {StudentService} from "../shared/student.service";
import {Student} from "../shared/student.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-student-add',
  templateUrl: './student-add.component.html',
  styleUrls: ['./student-add.component.css']
})
export class StudentAddComponent implements OnInit {
  @ViewChild('firstName') firstNameRef: ElementRef;
  @ViewChild('lastName') lastNameRef: ElementRef;
  @Input() currentPage: number;
  private firstNameAdd : string = "";
  private lastNameAdd : string = "";

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
  }

  onAdd() {
    if(!this.firstNameAdd.match("[a-zA-Z]+")) {
      alert("First Name should contain only letters!");
    }
    else if(!this.lastNameAdd.match("[a-zA-Z]+")) {
      alert("Last Name should contain only letters")
    }
    else {
      const student = new Student(this.firstNameRef.nativeElement.value, this.lastNameRef.nativeElement.value);
      this.studentService.save(student);
      this.firstNameRef.nativeElement.value = "";
      this.lastNameRef.nativeElement.value = "";
      this.firstNameAdd = "";
      this.lastNameAdd = "";
    }
  }

  checkCanAdd() {
    return this.firstNameAdd.length === 0 || this.lastNameAdd.length === 0;
  }

  changeFirstName(event) {
    this.firstNameAdd = event.target.value;
  }

  changeLastName(event) {
    this.lastNameAdd = event.target.value;
  }

}

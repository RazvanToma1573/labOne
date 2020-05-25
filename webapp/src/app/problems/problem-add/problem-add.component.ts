import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {ProblemService} from "../shared/problem.service";
import {Student} from "../../students/shared/student.model";
import {Problem} from "../shared/problem.model";

@Component({
  selector: 'app-problem-add',
  templateUrl: './problem-add.component.html',
  styleUrls: ['./problem-add.component.css']
})
export class ProblemAddComponent implements OnInit {
  @ViewChild('description') descriptionRef: ElementRef;
  @ViewChild('difficulty') difficultyRef: ElementRef;
  @Input() currentPage: number;

  private descriptionAdd : string = "";
  private difficultyAdd : string = "";
  constructor(private problemService: ProblemService) { }

  ngOnInit(): void {
  }

  onAdd() {
    const problem = new Problem(this.descriptionRef.nativeElement.value, this.difficultyRef.nativeElement.value);
    this.problemService.save(this.currentPage, problem);
    this.descriptionRef.nativeElement.value = "";
    this.difficultyRef.nativeElement.value = "";
    this.descriptionAdd = "";
    this.difficultyAdd = "";
  }

  checkCanAdd() {
    return this.descriptionAdd.length === 0 || this.difficultyAdd.length === 0;
  }

  changeDescription(event) {
    this.descriptionAdd = event.target.value;
  }

  changeDifficulty(event) {
    this.difficultyAdd = event.target.value;
  }

}

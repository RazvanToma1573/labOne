import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Problem} from "../shared/problem.model";
import {ProblemService} from "../shared/problem.service";
import {Router} from "@angular/router";
import {Student} from "../../students/shared/student.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-problem-list',
  templateUrl: './problem-list.component.html',
  styleUrls: ['./problem-list.component.css']
})
export class ProblemListComponent implements OnInit {
  error: string;
  problems: Array<Problem>;
  selectedProblem: Problem;
  @ViewChild('sortId') sortIdRef: ElementRef;
  @ViewChild('sortDescription') sortDescriptionRef: ElementRef;
  @ViewChild('sortDifficulty') sortDifficultyRef: ElementRef;
  @ViewChild('filterType') filterTypeRef: ElementRef;
  @ViewChild('filterArgument') filterArgumentRef: ElementRef;
  currentPage = 0;
  nrOfPages = 0;

  constructor(private problemService: ProblemService,
              private router: Router,
              private location: Location) { }

  ngOnInit(): void {
    this.getProblems();
    this.currentPage = 0;
    this.problemService.listUpdated
      .subscribe((problems: Problem[]) => {
        this.problems = problems;
      });
  }

  getProblems() {
    this.problemService.getProblems()
      .subscribe(problems => {
        this.problems = problems;
          console.log(this.problems);
        },
        error => this.error = error);
  }

  onSelect(problem: Problem) {
    this.selectedProblem = problem;
    this.router.navigate(['/problems/details', this.selectedProblem.id]);
  }

  onRemove(problem: Problem) {
    if(confirm("Do you really want to remove the problem?")) {
      this.problemService.delete(problem.id);
      if(this.selectedProblem.id === problem.id) {
      this.router.navigate(['/problems']);
      }
    }
  }

  onSort() {
    var id = this.sortIdRef.nativeElement.value;
    var description = this.sortDescriptionRef.nativeElement.value;
    var difficulty = this.sortDifficultyRef.nativeElement.value;
    //this.problemService.getProblemsSorted(id, description, difficulty);
  }

  getFiltered() {
    this.problemService.filter(this.filterTypeRef.nativeElement.value, this.filterArgumentRef.nativeElement.value);
  }


  goBack() {
    this.location.back();
  }
}

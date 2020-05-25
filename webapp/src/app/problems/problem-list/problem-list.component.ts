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
  currentPage = 0;
  nrOfPages = 0;

  constructor(private problemService: ProblemService,
              private router: Router,
              private location: Location) { }

  ngOnInit(): void {
    this.getProblems(0);
    this.currentPage = 0;
    this.problemService.getAllProblems().subscribe(
      problems => this.nrOfPages = problems.length
    );
    this.problemService.listUpdated
      .subscribe((problems: Problem[]) => {
        this.problems = problems;
      });
    this.problemService.pagesUpdated
      .subscribe(nr => this.nrOfPages = nr);
  }

  getProblems(page: number) {
    this.problemService.getProblems(page)
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
      this.problemService.delete(this.currentPage, problem.id);
      if(this.selectedProblem.id === problem.id) {
      this.router.navigate(['/problems']);
      }
    }
  }

  onSort() {
    var id = this.sortIdRef.nativeElement.value;
    var description = this.sortDescriptionRef.nativeElement.value;
    var difficulty = this.sortDifficultyRef.nativeElement.value;
    this.problemService.getProblemsSorted(this.currentPage, id, description, difficulty);
  }

  onLeft() {
    this.currentPage -= 1;
    this.getProblems(this.currentPage);
  }

  onRight() {
    this.currentPage += 1;
    this.getProblems(this.currentPage);
  }

  goBack() {
    this.location.back();
  }
}

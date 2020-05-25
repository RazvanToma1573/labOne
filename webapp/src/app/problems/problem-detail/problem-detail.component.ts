import {Component, Input, OnInit} from '@angular/core';
import {Problem} from "../shared/problem.model";
import {ProblemService} from "../shared/problem.service";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-problem-detail',
  templateUrl: './problem-detail.component.html',
  styleUrls: ['./problem-detail.component.css']
})
export class ProblemDetailComponent implements OnInit {
  @Input() problem: Problem = new Problem();

  constructor( private problemService: ProblemService,
               private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.problemService.getProblem(+params['id'])))
      .subscribe(problem => this.problem = problem);
  }

  onUpdate() {
    this.problemService.update(this.problem);
  }

}

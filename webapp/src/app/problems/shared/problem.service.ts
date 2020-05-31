import {EventEmitter, Injectable} from "@angular/core";
import {Problem} from "./problem.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Student} from "../../students/shared/student.model";

@Injectable()
export class ProblemService {
  private url = 'http://localhost:8080/api/problems';
  listUpdated = new EventEmitter<Problem[]>();
  pagesUpdated = new EventEmitter<number>();
  currentPage: number = 0;

  constructor(private httpClient: HttpClient) {
  }

  getProblems(): Observable<Problem[]> {
    return this.httpClient.get<Array<Problem>>(this.url);
  }

  getAllProblems(): Observable<Problem[]> {
    return this.httpClient.get<Array<Problem>>(this.url);
  }

  getProblem(id: number): Observable<Problem> {
    return this.getAllProblems()
      .pipe(map(
        problems => problems.find(problem => problem.id === id)
      ))
  }

  save(problem: Problem) {
    this.httpClient.post<Problem>(this.url, problem)
      .subscribe(_ => {
        this.getProblems().subscribe(
          problems => {
            this.listUpdated.emit(problems);
          }
        )
      })
  }

  update(problem: Problem): void {
    const link = `${this.url}/${problem.id}`;
    this.httpClient.put<Problem>(link, problem)
      .subscribe(_ => {
        this.getProblems()
          .subscribe(problems => this.listUpdated.emit(problems));
      });
  }

  delete(id: number): void {
    this.httpClient.delete(`http://localhost:8080/api/grades/${id}`);
    const link = `${this.url}/${id}`;
    this.httpClient.delete(link)
      .subscribe(_ => {
        this.getProblems()
          .subscribe(problems => {
            this.listUpdated.emit(problems);
          });
      });
  }

  filter(type: string, argument: string) {
    if(type !== 'none') {
      if(type === 'description') {
        this.httpClient.get<Array<Problem>>(this.url + "/filter/" + argument)
          .subscribe(problems => this.listUpdated.emit(problems))
      }
      else {
        this.getProblems()
          .subscribe(problems => this.listUpdated.emit(problems.filter(problem => problem.difficulty === argument)))
      }
    }
    else {
      this.getProblems()
        .subscribe(problems => this.listUpdated.emit(problems));
    }
  }
}

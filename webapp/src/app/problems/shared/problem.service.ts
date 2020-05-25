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

  getProblems(page: number): Observable<Problem[]> {
    this.currentPage = page;
    return this.httpClient.get<Array<Problem>>(this.url + "/" + page);
  }

  getAllProblems(): Observable<Problem[]> {
    return this.httpClient.get<Array<Problem>>(this.url);
  }

  getProblemsSorted(page: number, id: string, description: string, difficulty: string): void {
    var param = "";
    if(id !== "none") {
      param += "id-" + (id.toLowerCase() === "desc");
    }
    if(description !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "description-" + (description.toLowerCase() === "desc");
    }
    if(difficulty !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "difficulty-" + (difficulty.toLowerCase() === "desc");
    }
    this.httpClient.get<Array<Problem>>(this.url + "/sorted/" + page + "/" + param)
      .subscribe(problems => {
        this.listUpdated.emit(problems)
      });
  }

  getProblem(id: number): Observable<Problem> {
    return this.getAllProblems()
      .pipe(map(
        problems => problems.find(problem => problem.id === id)
      ))
  }

  save(page: number, problem: Problem) {
    this.httpClient.post<Problem>(this.url, problem)
      .subscribe(_ => {
        this.getProblems(page).subscribe(
          problems => {
            this.listUpdated.emit(problems);
            this.getAllProblems().subscribe(
              problems => this.pagesUpdated.emit(problems.length)
            )
          }
        )
      })
  }

  update(problem: Problem): void {
    const link = `${this.url}/${problem.id}`;
    this.httpClient.put<Problem>(link, problem)
      .subscribe(_ => {
        this.getProblems(this.currentPage)
          .subscribe(problems => this.listUpdated.emit(problems));
      });
  }

  delete(page: number, id: number): void {
    this.httpClient.delete(`http://localhost:8080/api/grades/${id}`);
    const link = `${this.url}/${id}`;
    this.httpClient.delete(link)
      .subscribe(_ => {
        this.getProblems(page)
          .subscribe(problems => {
            this.listUpdated.emit(problems);
            this.getAllProblems().subscribe(
              problems => this.pagesUpdated.emit(problems.length)
            )
          });
      });
  }
}

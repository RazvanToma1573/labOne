import {EventEmitter, Injectable} from "@angular/core";
import {Grade} from "./grade.module";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Student} from "../../students/shared/student.model";
import {StudentService} from "../../students/shared/student.service";
import {ProblemService} from "../../problems/shared/problem.service";
import {Problem} from "../../problems/shared/problem.model";

@Injectable()
export class GradeService {
  private url = 'http://localhost:8080/api/grades';
  listUpdated = new EventEmitter<Grade[]>();
  pagesUpdated = new EventEmitter<number>();
  currentPage: number = 0;

  constructor(private httpClient: HttpClient,
              private studentService: StudentService,
              private problemService: ProblemService) {
  }

  getGrades(): Observable<Grade[]> {
    return this.httpClient.get<Array<Grade>>(this.url);
  }

  getAllGrades(): Observable<Grade[]> {
    return this.httpClient.get<Array<Grade>>(this.url);
  }

  getGradesSorted(page: number, studentId: string, problemId: string, actualGrade: string) : void {
    var param = "";
    if(studentId !== "none") {
      param += "studentId-" + (studentId.toLowerCase() === "desc");
    }
    if(problemId !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "problemId-" + (problemId.toLowerCase() === "desc");
    }
    if(actualGrade !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "actualGrade-" + (actualGrade.toLowerCase() === "desc");
    }
    this.httpClient.get<Array<Grade>>(this.url + "/sorted/" + page + "/" + param)
      .subscribe(grades => {
        this.listUpdated.emit(grades)
      });
  }

  getGrade(id: number): Observable<Grade> {
    return this.getAllGrades()
      .pipe(map(grades => grades.find(grade => grade.id === id)));
  }

  save(studentId: number, problemId: number) {
    this.httpClient.post<Grade>(this.url + "/" + studentId + "/" + problemId, null)
      .subscribe(_ => {
        this.getGrades()
          .subscribe(grades => {
            this.listUpdated.emit(grades);
          });
      },
        error => alert(error));
  }

  update(grade: Grade): void {
    const link = `${this.url}/${grade.student.id}/${grade.problem.id}/${grade.actualGrade}`;
    this.httpClient.put<Grade>(link, null)
      .subscribe(_ => {
        this.getGrades()
          .subscribe(grades => this.listUpdated.emit(grades));
      },
        error => {
        alert(error.error);
        console.log(error);
        });
  }

  getStudent(id:number): Observable<Student> {
    return this.studentService.getStudent(id);
  }

  getProblem(id: number): Observable<Problem> {
    return this.problemService.getProblem(id);
  }

  delete(id: number) {
    this.httpClient.delete(this.url + "/remove/" + id)
      .subscribe(_ => {
        this.getGrades()
          .subscribe(grades => {
            this.listUpdated.emit(grades);
          })
      })
  }


}

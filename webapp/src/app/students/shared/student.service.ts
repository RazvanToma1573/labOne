import {HttpClient} from "@angular/common/http";
import {Student} from "./student.model";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {EventEmitter, Injectable, Output} from "@angular/core";

@Injectable()
export class StudentService {
  private url = 'http://localhost:8080/api/students';
  listUpdated = new EventEmitter<Student[]>();
  pagesUpdated = new EventEmitter<number>();
  currentPage: number = 0;

  constructor(private httpClient: HttpClient) {
  }

  getStudents(page: number): Observable<Student[]> {
    this.currentPage = page;
    return this.httpClient.get<Array<Student>>(this.url + "/" + page);
  }

  getAllStudents(): Observable<Student[]> {
    return this.httpClient.get<Array<Student>>(this.url);
  }

  getStudentsSorted(page: number, id: string, firstName: string, lastName: string): void {
    var param = "";
    if (id !== "none") {
      param += "id-" + (id.toLowerCase() === "desc");
    }
    if (firstName !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "firstName-" + (firstName.toLowerCase() === "desc");
    }
    if (lastName !== "none") {
      if (param.length > 0) {
        param += "&";
      }
      param += "lastName-" + (lastName.toLowerCase() === "desc");
    }
    this.httpClient.get<Array<Student>>(this.url + "/sorted/" + page + "/" + param)
      .subscribe(students => {
        this.listUpdated.emit(students);
        this.getAllStudents().subscribe(
          students => this.pagesUpdated.emit(students.length)
        )
      });
  }

  getStudent(id: number): Observable<Student> {
    return this.getAllStudents()
      .pipe(map(
        students => students.find(student => student.id === id)
      ));
  }

  save(page: number, student: Student): void {
    this.httpClient.post(this.url, student)
      .subscribe(_ => {
          this.getStudents(page)
            .subscribe(students => {
              this.listUpdated.emit(students);
              this.getAllStudents().subscribe(
                students => this.pagesUpdated.emit(students.length)
              )
            })
        },
        error => {
          console.log("error");
          alert("Error:" + " Invalid input");
        });
  }

  update(student: Student): void {
    const link = `${this.url}/${student.id}`;
    this.httpClient.put<Student>(link, student)
      .subscribe(_ => {
        this.getStudents(this.currentPage)
          .subscribe(students => this.listUpdated.emit(students));
      });
  }

  delete(page: number, id: number): void {
    const link = `${this.url}/${id}`;
    this.httpClient.delete(link)
      .subscribe(_ => {
        this.getStudents(page)
          .subscribe(students => {
            this.listUpdated.emit(students);
            this.getAllStudents().subscribe(
              students => this.pagesUpdated.emit(students.length))
          });

      });
  }

  filter(page: number, type: string, argument: string) {
    if (type !== "none") {
      this.httpClient.get<Array<Student>>(this.url + "/filter/" + page + "/" + type + "/" + argument)
        .subscribe(
          students => {
            this.listUpdated.emit(students);
            this.getAllStudents().subscribe(
              students =>
                this.pagesUpdated.emit(students.length)
            );
          });
    }
  }
}

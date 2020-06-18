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

  getStudents(): Observable<Student[]> {
    return this.httpClient.get<Array<Student>>(this.url);
  }

  getAllStudents(): Observable<Student[]> {
    return this.httpClient.get<Array<Student>>(this.url);
  }

  getStudent(id: number): Observable<Student> {
    return this.getAllStudents()
      .pipe(map(
        students => students.find(student => student.id === id)
      ));
  }

  save(student: Student): void {
    this.httpClient.post(this.url, student)
      .subscribe(_ => {
          this.getStudents()
            .subscribe(students => {
              this.listUpdated.emit(students);
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
        this.getStudents()
          .subscribe(students => this.listUpdated.emit(students));
      });
  }

  getGraph(): Observable<any> {
    return this.httpClient.get(this.url + "/graph")
  }

  delete(id: number): void {
    const link = `${this.url}/${id}`;
    this.httpClient.delete(link)
      .subscribe(_ => {
        this.getStudents()
          .subscribe(students => {
            this.listUpdated.emit(students);
          });

      });
  }

  filter(type: string, argument: string) {
    if (type !== "none") {
      if(type === "firstName") {
        this.httpClient.get<Array<Student>>(this.url+"/filter/" + argument)
          .subscribe(students => {
            this.listUpdated.emit(students);
          })
      }
      else {
        this.getStudents()
          .subscribe(students => {
            this.listUpdated.emit(students.filter(student => student.lastName === argument));
          })
      }
    }
    else {
      this.getStudents()
        .subscribe(students => this.listUpdated.emit(students));
    }
  }
}

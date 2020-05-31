import {Student} from "../../students/shared/student.model";
import {Problem} from "../../problems/shared/problem.model";


export class Grade {
  id: number;
  student: Student;
  problem: Problem;
  actualGrade: number;

  constructor(student: Student, problem: Problem, grade: number) {
    this.student = student;
    this.problem = problem;
    this.actualGrade = grade;
  }
}

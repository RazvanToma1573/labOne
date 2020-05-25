

export class Grade {
  id: number;
  studentId: number;
  problemId: number;
  actualGrade: number;

  constructor(studentId: number, problemId: number, grade: number) {
    this.studentId = studentId;
    this.problemId = problemId;
    this.actualGrade = grade;
  }
}

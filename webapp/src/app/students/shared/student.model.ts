import {Grade} from "../../grades/shared/grade.module";

export class Student {
  id: number;
  firstName: string;
  lastName: string;
  grades: Grade[];

  constructor(firstName: string = "", lastName: string = "") {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}

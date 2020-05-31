import {Grade} from "../../grades/shared/grade.module";

export class Problem {
  id: number;
  grades: Grade[];
  constructor(public description: string = '',
              public difficulty: string = '') {
  }
}

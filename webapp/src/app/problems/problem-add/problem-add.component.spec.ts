import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemAddComponent } from './problem-add.component';

describe('ProblemAddComponent', () => {
  let component: ProblemAddComponent;
  let fixture: ComponentFixture<ProblemAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

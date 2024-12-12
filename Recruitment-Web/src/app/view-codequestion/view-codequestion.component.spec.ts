import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCodequestionComponent } from './view-codequestion.component';

describe('ViewCodequestionComponent', () => {
  let component: ViewCodequestionComponent;
  let fixture: ComponentFixture<ViewCodequestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCodequestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCodequestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

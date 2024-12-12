import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateLandingComponent } from './candidate-landing.component';

describe('CandidateLandingComponent', () => {
  let component: CandidateLandingComponent;
  let fixture: ComponentFixture<CandidateLandingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateLandingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

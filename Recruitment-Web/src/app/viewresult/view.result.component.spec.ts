import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { View.ResultComponent } from './view.result.component';

describe('View.ResultComponent', () => {
  let component: View.ResultComponent;
  let fixture: ComponentFixture<View.ResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ View.ResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(View.ResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassangerOptionsComponent } from './passanger-options.component';

describe('PassangerOptionsComponent', () => {
  let component: PassangerOptionsComponent;
  let fixture: ComponentFixture<PassangerOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PassangerOptionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PassangerOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

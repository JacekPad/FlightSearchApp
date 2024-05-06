import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightRouteDetailsComponent } from './flight-route-details.component';

describe('FlightRouteDetailsComponent', () => {
  let component: FlightRouteDetailsComponent;
  let fixture: ComponentFixture<FlightRouteDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FlightRouteDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FlightRouteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicTableSearchComponent } from './dynamic-table-search.component';

describe('DynamicTableSearchComponent', () => {
  let component: DynamicTableSearchComponent;
  let fixture: ComponentFixture<DynamicTableSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicTableSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicTableSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPrefListComponent } from './user-pref-list.component';

describe('UserPrefListComponent', () => {
  let component: UserPrefListComponent;
  let fixture: ComponentFixture<UserPrefListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPrefListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPrefListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

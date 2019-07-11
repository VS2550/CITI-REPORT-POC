import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { DynamicTableComponent } from './dynamic-table/dynamic-table.component';

import {MultiSelectModule} from 'primeng/multiselect';
import {ButtonModule} from 'primeng/button';
import {DropdownModule} from 'primeng/dropdown';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';
import {DialogModule} from 'primeng/dialog';

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { DataServiceService } from 'src/app/dynamic-table/data-service.service';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { DynamicTableSearchComponent } from './dynamic-table-search/dynamic-table-search.component';
import { ReportConfigComponent } from './report-config/report-config.component';
import { UserPrefListComponent } from './user-pref-list/user-pref-list.component';

@NgModule({
  declarations: [
    AppComponent,
    DynamicTableComponent,
    DynamicTableSearchComponent,
    ReportConfigComponent,
    UserPrefListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    ButtonModule,
    TableModule,
    InputTextModule,
	DialogModule,
    MultiSelectModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [DataServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }

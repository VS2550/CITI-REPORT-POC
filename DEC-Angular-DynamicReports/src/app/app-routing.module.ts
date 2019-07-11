import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DynamicTableComponent } from 'src/app/dynamic-table/dynamic-table.component';
import { DynamicTableSearchComponent } from 'src/app/dynamic-table-search/dynamic-table-search.component';
import { ReportConfigComponent } from 'src/app/report-config/report-config.component';
import { UserPrefListComponent } from './user-pref-list/user-pref-list.component';

const routes: Routes = [
    {redirectTo:"func1", path: "", pathMatch: "full" },
    {path: 'func1', component: DynamicTableComponent},
    {path: 'func2', component: DynamicTableSearchComponent},
    {path: 'config', component: ReportConfigComponent},
	 {path: 'userPrefList', component: UserPrefListComponent},
    { redirectTo:"func1", path: "**" }
   
   
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule { }

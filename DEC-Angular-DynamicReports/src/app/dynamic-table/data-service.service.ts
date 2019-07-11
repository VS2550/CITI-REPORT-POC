import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable ,of } from 'rxjs';
import { catchError, tap} from 'rxjs/operators';
import { TableMap } from 'src/app/dynamic-table/dynamic-table.component';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class DataServiceService {
  //url = 'http://18.234.202.117:11123/citi/';
  url = 'http://localhost:11121/citi/';
  downloadUrl = 'http://localhost:11121/download/';
  constructor(private http: HttpClient) { }

  getTableDropdown(): Observable<any> {
    return this.http.get<any>(this.url+'getAllTableName',httpOptions)
      .pipe(
      tap(result => this.log('tables Dropdown fetched')),
      catchError(this.handleError('getTableDropdown', []))
      );
  }

  getColumnDropdown(selectedtable): Observable<any> {
    const Turl = `${this.url+'getAllColumnNames'}/${selectedtable}`;
    console.log(Turl)
    return this.http.get<any>(encodeURI(Turl), httpOptions)
      .pipe(
      tap(result => this.log('columns Dropdown fetched')),
      catchError(this.handleError('getColumnDropdown', []))
      );
  }

  getTableRecords(tableMap:TableMap): Observable<any> {
    return this.http.post<any>(this.url+'getTableRecords',tableMap, httpOptions)
    .pipe(
      tap(result =>  this.log('table Records fetched')),
      catchError(this.handleError('getTableRecords'))
    );
  }
  saveUserPref(tableMap:TableMap): Observable<any> {
    return this.http.post<any>(this.url+'saveUserPreference',tableMap, httpOptions)
    .pipe(
      tap(result =>  this.log('USer Pref saved ')),
      catchError(this.handleError('saveUserPref'))
    );
  }
  
  getAllSavedReports(): Observable<any> {
    
    return this.http.get<any>(this.url+'getAllSavedReports', httpOptions)
      .pipe(
      tap(result => this.log(' getAllSavedReports fetched')),
      catchError(this.handleError('getAllSavedReports', []))
      );
  }
  
  getSavedReportsData(reportObjectName:string): Observable<any>{
    const Turl = `${this.url+'getSavedReportsData'}/${reportObjectName}`;
    return this.http.get<any>(Turl, httpOptions)
      .pipe(
      tap(result => this.log(' getSavedReportsData fetched')),
      catchError(this.handleError('getSavedReportsData', []))
      );
  }
  
  

  getReportConfig(): Observable<any> {
    return this.http.get<any>(this.url+'getReportConfigData',httpOptions)
      .pipe(
      tap(result => this.log('getReportConfig fetched')),
      catchError(this.handleError('getReportConfig', []))
      );
  }
  
  downloadSavedReportsData(reportObjectName:string): Observable<any>{
	 // alert("abc");
    const Turl = `${this.downloadUrl+'downloadSavedReportsData1'}/${reportObjectName}`;
	//alert(Turl);
    
	
	return this.http.get<any>(Turl, httpOptions)
      .pipe(
      tap(result => this.log(' getSavedReportsData fetched')),
      catchError(this.handleError('getSavedReportsData', []))
      );
  }
  
  

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.log(`${operation} failed: ${error.message}`);
      return of(error);
    };
  }
  private log(message: string) {
   console.log(message)
  }
}

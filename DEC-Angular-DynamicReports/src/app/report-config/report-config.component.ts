import { Component, OnInit } from '@angular/core';
import { DataServiceService } from 'src/app/dynamic-table/data-service.service';

@Component({
  selector: 'app-report-config',
  templateUrl: './report-config.component.html',
  styleUrls: ['./report-config.component.css']
})
export class ReportConfigComponent implements OnInit {
  reportConfig: any[];
  cols: any[];
  constructor(private dataService:DataServiceService) { }

  ngOnInit() {
    this.cols = [
      { field: 'columnName', header: 'Column Name' },
      { field: 'fieldName', header: 'Field Name' },
      { field: 'reportName', header: 'Report Name' },
      { field: 'searchCriteria', header: 'Search Criteria' },
      { field: 'aggregateCriteria', header: 'Aggregate Criteria' }
    ];
    this.dataService.getReportConfig().subscribe(result =>{
      this.reportConfig=result;
    });
  }

}

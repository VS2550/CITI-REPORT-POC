import { Component, OnInit } from '@angular/core';
import { DataServiceService } from 'src/app/dynamic-table/data-service.service';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit {
  tables: any[];
  selectedTable: any;
  columns: any[];
  selectedColumn: string[];
  cols: any[];
  tableData:any[];

  constructor(private dataService: DataServiceService) {
    this.tables = [];
    this.columns = [];

  }

  ngOnInit() {
    this.dataService.getTableDropdown().subscribe(result => {
      let response = result;
      this.tables = [];
      for (let key in response) {
        this.tables.push({ "label": key, "value": response[key] })
      }
    });
  }

  callColumnApi(event) {
    this.dataService.getColumnDropdown(this.selectedTable.label).subscribe(result => {
      let response = result;
      this.columns = [];
      for (let key in response.TABLE_COLUMN_NAME) {
        this.columns.push({ "label": key, "value": response.TABLE_COLUMN_NAME[key] })
      }
      this.columns = JSON.parse(JSON.stringify(this.columns))
    });
  }

  getTableRecordsApi(event) {
    let tMap = new TableMap();
    tMap.reportName = this.selectedTable.label;
    tMap.columnName = '';
    this.selectedColumn.forEach(element => {
      tMap.columnName = tMap.columnName.concat(JSON.parse(JSON.stringify(element)).label + ',');
    });
    tMap.columnName = tMap.columnName.slice(0, -1);
    tMap.searchCriteria="0=0";
    console.log('TMAP ' + JSON.stringify(tMap));

    this.dataService.getTableRecords(tMap).subscribe(result => {
      let response = result;
      this.cols = [];
      for (let key in response.UI_Table_Heading) {
        this.cols.push({ "field": key, "header": response.UI_Table_Heading[key] })
      }
      this.cols = JSON.parse(JSON.stringify(this.cols));

      this.tableData=JSON.parse(JSON.stringify(response.UI_Table_Data));
    });
  }

}


export class TableMap {
  columnName: string;
  reportName: any;
  searchCriteria: any;
  constructor() {

  }
}
import { Component, OnInit } from '@angular/core';
import { DataServiceService } from 'src/app/dynamic-table/data-service.service';
import { InputText } from 'primeng/inputtext';
import { ExcelService } from 'src/app/excel.service';

@Component({
  selector: 'app-dynamic-table-search',
  templateUrl: './dynamic-table-search.component.html',
  styleUrls: ['./dynamic-table-search.component.css']
})
export class DynamicTableSearchComponent implements OnInit {
  tables: any[];
  selectedTable: any;
  columns: any[];
  selectedColumn: string[];
  cols: any[];
  tableData:any[];
  searchFields: any[];
  apiResponse: any[];

  

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
      this.columns = JSON.parse(JSON.stringify(this.columns));
      
      this.searchFields = [];
      for (let key in response.SEARCH_COLUMN_NAME) {
        this.searchFields.push({ "label": key, "value": response.SEARCH_COLUMN_NAME[key] })
      }
      this.searchFields = JSON.parse(JSON.stringify(this.searchFields));

    });
  }

  getTableRecordsApi(event) {
    console.log((<HTMLInputElement>document.getElementById(this.searchFields[0].label)).value);
    let tMap = new TableMap();
    tMap.reportName = this.selectedTable.label;
    tMap.columnName = '';
    this.selectedColumn.forEach(element => {
      tMap.columnName = tMap.columnName.concat(JSON.parse(JSON.stringify(element)).label + ',');
    });
    tMap.columnName = tMap.columnName.slice(0, -1);
    tMap.searchCriteria="";
   

//map search Criteria

for(let index in this.searchFields){
  let fieldValue=(<HTMLInputElement>document.getElementById(this.searchFields[index].label)).value
  if(fieldValue !== ""){
    tMap.searchCriteria+=this.searchFields[index].label+"='"+fieldValue+"' AND ";
  }
}
tMap.searchCriteria = tMap.searchCriteria.slice(0, -5);
console.log('TMAP ' + JSON.stringify(tMap));

    this.dataService.getTableRecords(tMap).subscribe(result => {
      let response = result;
	 // this.apiResponse= JSON.stringify(result);
      this.cols = [];
      for (let key in response.UI_Table_Heading) {
        this.cols.push({ "field": key, "header": response.UI_Table_Heading[key] })
      }
      this.cols = JSON.parse(JSON.stringify(this.cols));

      this.tableData=JSON.parse(JSON.stringify(response.UI_Table_Data));
    });
  }
userPreferenceReportName:string;
display:boolean=false;
success:boolean=false;
  saveUserPref(event){
      let tMap = new TableMap();
    tMap.reportName = this.selectedTable.label;
    tMap.columnName = '';
    this.selectedColumn.forEach(element => {
      tMap.columnName = tMap.columnName.concat(JSON.parse(JSON.stringify(element)).label + ',');
    });
    tMap.columnName = tMap.columnName.slice(0, -1);
    tMap.userPreferenceReportName=this.userPreferenceReportName;
   

    this.dataService.saveUserPref(tMap).subscribe(result => {
		this.userPreferenceReportName="";
		this.success=true;
     //alert('Saved Successfully');
	});
  }
  
  exportToExcel() {
	 // alert(JSON.stringify(this.tableData));
	 alert("Comming soon");
    //this.excelService.exportAsExcelFile(JSON.stringify(this.apiResponse), 'sample');
  }
  comingSoon(){
	alert('Feature Not available!! Coming Soon..');
  }
  printComponent(cmpName) {
     let printContents = document.getElementById(cmpName).innerHTML;
     let originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
}

  
}


export class TableMap {
  columnName: string;
  reportName: any;
  searchCriteria: string;
  userPreferenceReportName:string;
  constructor() {

  }
}


import { Component, OnInit } from '@angular/core';
import { DataServiceService } from 'src/app/dynamic-table/data-service.service';
//import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-user-pref-list',
  templateUrl: './user-pref-list.component.html',
  styleUrls: ['./user-pref-list.component.css']
})
export class UserPrefListComponent implements OnInit {

  constructor(private dataService: DataServiceService) { }
  reportList:any[]=[];
  cols: any[];
  tableData:any[];
  

  ngOnInit() {
	  this.dataService.getAllSavedReports().subscribe(result => {
		let response=result;
		this.reportList=[];
		for(let i of response){
			
			this.reportList.push({ "label": i.reportObjectName , "value": i.reportObjectName });
		}
		//this.reportList=result;
		
		
    });
  }
  showReport(event){
	let reportObjectName=event.value.value;
    this.dataService.getSavedReportsData(reportObjectName).subscribe(result => {
      let response = result;
      this.cols = [];
      for (let key in response.UI_Table_Heading) {
        this.cols.push({ "field": key, "header": response.UI_Table_Heading[key] })
      }
      this.cols = JSON.parse(JSON.stringify(this.cols));

      this.tableData=JSON.parse(JSON.stringify(response.UI_Table_Data));
    });
  }
  
  downloadReport(event){
	  //alert("test");
	let reportObjectName=event.value.value;
    this.dataService.downloadSavedReportsData(reportObjectName)
	
	 .subscribe(result => {
      let response = result;
     
	 const blob = new Blob([response], { type: 'application/octet-stream' });
  const url= window.URL.createObjectURL(blob);
  window.open(url);
	 
    }); 
  }

}

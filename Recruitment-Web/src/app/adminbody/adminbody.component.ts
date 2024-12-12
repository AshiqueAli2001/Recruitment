import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-adminbody',
  templateUrl: './adminbody.component.html',
  styleUrls: ['./adminbody.component.css','./adminbody.component.scss']
})
export class AdminbodyComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input() collapsed = false;
  @Input() screenWidth = 0;

  getBodyClass(): string{
    let styleClass = '';
    if(this.collapsed && this.screenWidth > 768){
      styleClass = 'body-trimmed';
    }
    else if(this.collapsed && this.screenWidth <= 768 && this.screenWidth > 0)
    {
      styleClass = 'body-md-screen'
    }
    return styleClass;
  }

}

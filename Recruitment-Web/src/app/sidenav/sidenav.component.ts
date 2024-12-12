import { Component, Output, EventEmitter, OnInit, HostListener} from '@angular/core';
import { navbarData } from './nav-data';
import { Router } from '@angular/router';

interface SideNavToggle{
  ScreenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
   collapsed = false;
   screenWidth = 0;
  navData = navbarData;

  @HostListener('window:resize', ['$event'])
  onResize(event: any){
    this.screenWidth = window.innerWidth;
    if(this.screenWidth<=768)
    {
      this.collapsed=false;
      this.onToggleSideNav.emit({collapsed: this.collapsed, ScreenWidth: this.screenWidth});
    }
  }

  toggleCollapse(): void{
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({collapsed: this.collapsed, ScreenWidth: this.screenWidth});
  }

  closeSidenav(): void{
    this.collapsed = false
    this.onToggleSideNav.emit({collapsed: this.collapsed, ScreenWidth: this.screenWidth});
  }

  logout(): void{
    this.router.navigate(['/login']);
  }

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.screenWidth=window.innerWidth;
    console.log(this.screenWidth)
  }

}

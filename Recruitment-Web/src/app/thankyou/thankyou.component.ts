import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-thankyou',
  templateUrl: './thankyou.component.html',
  styleUrls: ['./thankyou.component.css']
})
export class ThankyouComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {

     // Set a timeout of 10 seconds (10000 milliseconds)
     setTimeout(() => {
      // Navigate to the login page
      this.router.navigate(['/login']);
    }, 10000);
  }

}

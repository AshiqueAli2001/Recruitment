import { TestService } from './../services/test.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestGuard implements CanActivate {
  constructor(private testService: TestService, private router: Router,private toastr: ToastrService) {}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      const pageToVisit = state.url;
      if (!this.testService.hasVisitedPage(pageToVisit)) {
        this.testService.markPageAsVisited(pageToVisit);
        return true;
      } else {
        // Redirect to some other page or show a message indicating that the page has already been visited.
        this.toastr.error("You cannot leave this page..")
        // return this.router.createUrlTree(['/']);
        return false;
      }
  }
  
}

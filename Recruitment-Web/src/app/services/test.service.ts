import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor() { }

  private visitedPages: string[] = [];

  markPageAsVisited(page: string): void {
    this.visitedPages.push(page);
  }

  hasVisitedPage(page: string): boolean {
    return this.visitedPages.includes(page);
  }

  clearVisitedPages(): void {
    this.visitedPages = [];
  }
}

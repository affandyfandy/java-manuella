import { Component, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { Router, NavigationEnd } from '@angular/router';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatTabsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  selectedIndex = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.updateTabIndex();
    });
  }

  onTabChange(event: MatTabChangeEvent): void {
    const index = event.index;
    if (index === 0) {
      this.router.navigate(['/']);
    } else if (index === 1) {
      this.router.navigate(['/product']);
    }
  }

  private updateTabIndex(): void {
    const currentRoute = this.router.url;
    if (currentRoute.includes('/product')) {
      this.selectedIndex = 1;
    } else {
      this.selectedIndex = 0;
    }
  }
}
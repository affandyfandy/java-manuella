import { Component } from '@angular/core';
import {MatTabsModule} from '@angular/material/tabs';
import { Router } from '@angular/router';
import { MatTabChangeEvent } from '@angular/material/tabs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatTabsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(private router: Router) {}

  onTabChange(event: MatTabChangeEvent): void {
    const index = event.index;
    if (index === 0) {
      this.router.navigate(['/']);
    } else if (index === 1) {
      this.router.navigate(['/product']);
    }
  }
}
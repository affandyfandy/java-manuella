import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router'
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, SearchBarComponent, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular-demo';
}
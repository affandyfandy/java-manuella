import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-login-success',
  standalone: true,
  imports: [MatCardModule],
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent {}

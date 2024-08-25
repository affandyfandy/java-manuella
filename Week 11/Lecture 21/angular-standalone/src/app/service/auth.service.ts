import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { User } from '../model/user.model';

const baseUrl = 'http://localhost:3000/users';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<boolean> {
    return this.http.get<User[]>(baseUrl, {
      params: {
        username: username,
        password: password
      }
    }).pipe(
      map(users => users.length > 0),
      catchError(() => of(false))
    );
  }
}
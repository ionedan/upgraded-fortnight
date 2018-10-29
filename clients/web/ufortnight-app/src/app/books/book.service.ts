import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable  } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';

import { IBook } from '../models';

interface IBooksApiResponse {
  _embedded: {
      bookList: IBook[];
  };
}

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private booksApi = 'http://localhost:8080/api/books';

  constructor(
    private http: HttpClient
  ) {}

  getBooks(): Observable<IBook[]> {
    return this.http.get<IBooksApiResponse>(this.booksApi)
      .pipe(
        map(response => response._embedded.bookList),
        tap(books => console.log('All:', books)),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    const err = `Error: ${error.status} la ${error.url}`;
    return Observable.throw(err);
  }
}

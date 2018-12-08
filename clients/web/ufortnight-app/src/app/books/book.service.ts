import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, of  } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';

import { IBook, ILanguage, IBookCategory, IBookType } from '../models';
import { ThrowStmt } from '@angular/compiler';

interface IBooksApiResponse {
  _embedded: {
      bookList: IBook[];
  };
}

// const languages = of(
//   {
//     id: 1,
//     name: 'romanian'
//   },
//   {
//     id: 2,
//     name: 'english'
//   }
// );

@Injectable({
  providedIn: 'root'
})
export class BookService {

  // private booksApi = 'http://localhost:8080/api/books';
  private booksApi = 'http://localhost:3000/';

  constructor(
    private http: HttpClient
  ) {}

  getBooks(): Observable<IBook[]> {
    return this.http.get<IBooksApiResponse>(this.booksApi + 'books')
      .pipe(
        map(response => response._embedded.bookList),
        tap(books => console.log('All:', books)),
        catchError(this.handleError)
      );
  }

  getBookCategories(): Observable<IBookCategory[]> {
    return this.http.get<IBookCategory[]>(this.booksApi + 'bookCategories')
      .pipe(
        catchError(this.handleError)
      );
  }

  getBookTypes(): Observable<IBookType[]> {
    return this.http.get<IBookType[]>(this.booksApi + 'bookTypes')
      .pipe(
        catchError(this.handleError)
      );
  }

  getLanguages(): Observable<ILanguage[]> {
    return this.http.get<ILanguage[]>(this.booksApi + 'languages')
      .pipe(
        tap(languages => console.log(languages)),
        catchError(this.handleError)
      );
  }

  createBook(newBook: IBook): Observable<IBook[]> {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');
    return this.http.post<IBooksApiResponse>(this.booksApi, newBook, { headers })
      .pipe(
        map(response => response._embedded.bookList),
        tap(books => console.log(books)),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    const err = `Error: ${error.status} la ${error.url}`;
    return Observable.throw(err);
  }
}

import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { IBook } from 'src/app/models';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {

  errorMessage: string;
  books: IBook[];

  constructor(private bookService: BookService) { }

  ngOnInit() {
    console.log('On init code');

    this.bookService.getBooks().subscribe(
      books => this.books = books,
      error => this.errorMessage = <any>error,
      () => console.log('books loaded')
    );
  }

  getBooks(): IBook[] {
    return this.books;
  }

}

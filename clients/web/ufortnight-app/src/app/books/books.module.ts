import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookListComponent } from '../books/book-list/book-list.component';
import { BookNewComponent } from '../books/book-new/book-new.component';
import { BookEditComponent } from '../books/book-edit/book-edit.component';
import { BookService } from './book.service';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [BookListComponent, BookNewComponent, BookEditComponent],
  providers: [ BookService ]
})
export class BooksModule { }

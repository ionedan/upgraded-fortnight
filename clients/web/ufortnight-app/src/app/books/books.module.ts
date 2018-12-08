import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BookListComponent } from '../books/book-list/book-list.component';
import { BookNewComponent } from '../books/book-new/book-new.component';
import { BookEditComponent } from '../books/book-edit/book-edit.component';
import { BookService } from './book.service';
import { BookRouting } from './book-routing.module';
import { BookLayoutComponent } from './book-layout/book-layout.component';

@NgModule({
  imports: [
    CommonModule, BookRouting, FormsModule, ReactiveFormsModule
  ],
  declarations: [BookLayoutComponent, BookListComponent, BookNewComponent, BookEditComponent],
  providers: [ BookService ]
})
export class BooksModule { }

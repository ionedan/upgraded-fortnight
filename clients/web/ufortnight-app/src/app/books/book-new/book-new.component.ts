import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ILanguage, IBookCategory, IBookType } from '../../models';
import { BookService } from '../book.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.scss']
})
export class BookNewComponent implements OnInit {

  categories: Observable<IBookCategory>;
  types: Observable<IBookType>;
  languages: Observable<ILanguage>;
  bookForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private bookService: BookService) {
      this.bookForm = this.formBuilder.group(
        {
          title: '',
          subtitle: '',
          description: '',
          author: '',
          language: '',
          type: '',
          category: ''
        }
      );
    }

  ngOnInit() {
    this.categories = this.bookService.getBookCategories();
    this.types = this.bookService.getBookTypes();
    this.languages = this.bookService.getLanguages();
  }

  save() {
    console.log(this.bookForm.value);
  }
}

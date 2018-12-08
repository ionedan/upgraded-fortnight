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

  categories: IBookCategory[];
  types: IBookType[];
  languages: ILanguage[];
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
    this.bookService.getBookCategories()
      .subscribe(
        categories => this.categories = categories,
        error => console.log(error)
      );
    this.bookService.getBookTypes()
      .subscribe(
        types => this.types = types,
        error => console.log(error)
      );
    this.bookService.getLanguages()
      .subscribe(
        languages => this.languages = languages,
        error => console.log(error)
      );
  }

  save() {
    console.log(this.bookForm.value);
  }
}

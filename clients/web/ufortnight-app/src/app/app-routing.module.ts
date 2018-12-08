import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './books/book-list/book-list.component';
import { BookNewComponent } from './books/book-new/book-new.component';

const appRoutes: Routes = [
];


@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes),
    ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}

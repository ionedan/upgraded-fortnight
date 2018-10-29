import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './books/book-list/book-list.component';

const appRoutes: Routes = [
    { path: 'books', component: BookListComponent },
];


@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes),
    ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}

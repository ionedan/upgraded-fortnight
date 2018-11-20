import { Routes, RouterModule } from '@angular/router';
import { BookListComponent } from './book-list/book-list.component';
import { BookNewComponent } from './book-new/book-new.component';
import { NgModule } from '@angular/core';
import { BookLayoutComponent } from './book-layout/book-layout.component';


const bookRoutes: Routes = [
    {
        path: 'books',
        component: BookLayoutComponent,
        children: [
            {
                path: 'list',
                component: BookListComponent
            },
            {
                path: 'new',
                component: BookNewComponent
            }
    ]
 },
];

@NgModule({
    imports: [
        RouterModule.forChild(bookRoutes),
    ],
    exports: [ RouterModule ]
})
export class BookRouting {}

package org.ionedan.ufortnight.libraryservice.domain.models;

import org.springframework.util.Assert;

public enum BookTypes {
    EbookKindle("Ebook/Kindle"),
    EbookPdf("Ebook/Pdf"),
    PrintHardcover("Print/Hardcover"),
    PrintPaperback("Print/Paperback");

    private final String name;

    BookTypes(String name) {
        Assert.notNull(name, "Book type cannot be null.");
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

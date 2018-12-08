export interface IAuthor {
    id: number;
    firstName: string;
    lastName: string;
}

// _links: {self: {href: string}}

export interface IBook {
    id: number;
    authors: IAuthor[] ;
    categories: IBookCategory[];
    description: string;
    title: string;
    type: IBookType;
    subtitle: string;
    languages: ILanguage[];
    publisher: IPublisher;
    yearOfAppearance: number;
}

export interface IBookCategory {
    id: number;
    name: string;
}

export interface IBookType {
    id: number;
    name: string;
}

export interface ILanguage {
    id: number;
    name: string;
}

export interface IPublisher {
    id: number;
    name: string;
}

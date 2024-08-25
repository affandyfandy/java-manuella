# Product Management System

## Overview
This project is a web-based application built using Angular that provides functionalities to manage products in a Point of Sale (POS) system. It allows users to create, read, update, and delete (CRUD) product information, as well as search and sort products dynamically.

## Technologies Used
- **Angular**: Frontend framework for building web applications.
- **Angular Material**: UI component library for Angular.
- **RxJS**: Library for reactive programming with asynchronous streams.
- **TypeScript**: Superset of JavaScript that adds static typing.
- **JSON Server**: Used as a mock backend to simulate API calls.

## Parent listens for child event demo
There is an example of the "Parent listens for child event" pattern. This pattern occurs when a parent component listens for events emitted by its child component. In this case, the  [`ProductListComponent`](angular-demo/src/app/pages/product) acts as the parent component, and it listens for events emitted by the [`SearchBarComponent`](angular-demo/src/app/main/components/search-bar).

The `ProductListComponent` is the parent component that contains both the `SearchBarComponent` and the `ProductTableComponent`. It manages the overall product list and applies filtering and sorting based on user input. The `SearchBarComponent` is a child component that provides a search bar and sorting options to the user. It emits events whenever the user performs a search or changes the sorting order.

In `SearchBarComponent`, events are emitted using Angular’s `EventEmitter`
```ts
@Output() filterChange = new EventEmitter<string>();
@Output() sortChange = new EventEmitter<{ field: string, order: string }>();
@Output() search = new EventEmitter<{ query: string; sort: { field: string; order: string } }>();
```

In the `ProductListComponent`, the parent component listens to these events using Angular’s event binding syntax `((eventName)="handlerMethod($event)")`
```html
<div class="product-list">
    <app-search-bar 
        (filterChange)="applyFilter($event)" 
        (sortChange)="sortData($event)" 
        (search)="onSearch($event)">
    </app-search-bar>
    <app-product-table [products]="filteredProducts"></app-product-table>
</div>  
```
- `(filterChange)="applyFilter($event)"`: Listens for the `filterChange` event from the `SearchBarComponent` and calls the `applyFilter` method in `ProductListComponent`.
- `(sortChange)="sortData($event)"`: Listens for the `sortChange` event and calls the `sortData` method in `ProductListComponent`.
- `(search)="onSearch($event)"`: Listens for the search event and calls the `onSearch` method in `ProductListComponent`.

### How it works
- When a user types in the search bar or selects a sort option in the 1SearchBarComponent`, it emits an event.
- The `SearchBarComponent` uses `EventEmitter` to emit the respective events (`filterChange`, `sortChange`, `search`).
- The `ProductListComponent` listens for these events and triggers appropriate methods (`applyFilter`, `sortData`, `onSearch`) to update the displayed product list based on the user's input.

## [Product Service](angular-demo/src/app/services/product.service.ts)
- `getProducts()`: Fetch all products from the server.
- `getProductById(id: number)`: Fetch product by ID.
- `addProduct(product: Product)`: Add a new product.
- `updateProduct(product: Product)`: Update an existing product.
- `deleteProduct(id: number)`: Delete a product by ID.
- `filterProducts(query: string)`: Filter products based on search query.
- `updateProductStatus(id: number, status: ProductStatus)`: Update product status.

## Project Structures
```cmd
│   index.html
│   main.ts
│   styles.css
│
└───app
    │   app.config.ts
    │   app.routes.ts
    │
    ├───config
    │       app.constants.ts
    │
    ├───core
    │   └───pipes
    │           custom-date.pipe.ts
    │
    ├───main
    │   └───components
    │       ├───footer
    │       │       footer.component.css
    │       │       footer.component.html
    │       │       footer.component.spec.ts
    │       │       footer.component.ts
    │       │
    │       ├───header
    │       │       header.component.css
    │       │       header.component.html
    │       │       header.component.spec.ts
    │       │       header.component.ts
    │       │
    │       ├───main
    │       │       app.component.css
    │       │       app.component.html
    │       │       app.component.spec.ts
    │       │       app.component.ts
    │       │
    │       ├───product-edit
    │       │       product-edit.component.css
    │       │       product-edit.component.html
    │       │       product-edit.component.spec.ts
    │       │       product-edit.component.ts
    │       │
    │       ├───product-table
    │       │       product-table.component.css
    │       │       product-table.component.html
    │       │       product-table.component.spec.ts
    │       │       product-table.component.ts
    │       │
    │       └───search-bar
    │               search-bar.component.css
    │               search-bar.component.html
    │               search-bar.component.spec.ts
    │               search-bar.component.ts
    │
    ├───models
    │       product-status.ts
    │       product.model.ts
    │
    ├───pages
    │   ├───home
    │   │       home.component.css
    │   │       home.component.html
    │   │       home.component.spec.ts
    │   │       home.component.ts
    │   │       home.routes.ts
    │   │
    │   └───product
    │           product-list.component.css
    │           product-list.component.html
    │           product-list.component.spec.ts
    │           product-list.component.ts
    │           product.routes.ts
    │
    └───services
            product.service.ts
```

## How to Run
1. Start json-server
```cmd
npx json-server db.json
```

2. Run angular app
```cmd
ng serve
```

## Screenshots
![alt text](image.png)

![alt text](image-7.png)

![alt text](image-2.png)

![alt text](image-5.png)

![alt text](image-1.png)

![alt text](image-6.png)

![alt text](image-4.png)
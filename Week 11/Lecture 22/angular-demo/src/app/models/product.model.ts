import { ProductStatus } from "./product-status";

export class Product {
    id: number;
    createdTime: Date;
    name: string;
    price: number;
    status: ProductStatus;
    updatedTime: Date;

    constructor(
        id: number,
        createdTime: Date,
        name: string,
        price: number,
        status: ProductStatus,
        updatedTime: Date
      ) {
        this.id = id;
        this.createdTime = createdTime;
        this.name = name;
        this.price = price;
        this.status = status;
        this.updatedTime = updatedTime;
      }
}

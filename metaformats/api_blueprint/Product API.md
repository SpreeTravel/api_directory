FORMAT: 1A

# Product API
This is a prototype product API.

## Authentication
Currently the Product API does not provide authenticated access.

## Error States
The common [HTTP Response Status Codes](https://github.com/for-GET/know-your-http-well/blob/master/status-codes.md) are used.

# Group Product
Resources for working with a single product.

## Product [/products/{id}]
Provides access to a single product.

+ Parameters
    + id (string) ... ID of the product

+ Model (application/json)

    JSON representation of products.

    + Body

            {
            "id": "1",
            "name": "Product One",
            "description": "This is the full description of the product.",
            "url": "http://example.com",
            "image": "http://example.com/image.jpg",
            "thumbnailUrl": "http://example.com/image-thumb.jpg",
            "keywords": "western, cowboy",
            "brand": "Brand Name",
            "color": "Black",
            "itemCondition": "New",
            "manufacturer": "Manufacturer Name",
            "model": "Black",
            "sku": "SKU #",
            "weight": "12 pounds",
            "width": "12 inches",
            "height": "12 inches",
            "depth": "12 inches"
            }

### Retrieve A Single Product [GET]
+ Response 200
    
    [Product][]

### Edit A Product [PATCH]
Updates A Product

+ Request (application/json)

        {
        "id": "1",
        "name": "Product One",
        "description": "This is the full description of the product.",
        "url": "http://example.com",
        "image": "http://example.com/image.jpg",
        "thumbnailUrl": "http://example.com/image-thumb.jpg",
        "keywords": "western, cowboy",
        "brand": "Brand Name",
        "color": "Black",
        "itemCondition": "New",
        "manufacturer": "Manufacturer Name",
        "model": "Black",
        "sku": "SKU #",
        "weight": "12 pounds",
        "width": "12 inches",
        "height": "12 inches",
        "depth": "12 inches"
        }

+ Response 200
    
    [Product][]

### Delete A Product [DELETE]
+ Response 204

## Products Collection [/products{?q}]
Provides access to all products.

+ Model (application/json)

    JSON representation of products.

    + Body

            {
            "id": "1",
            "name": "Product One",
            "description": "This is the full description of the product.",
            "url": "http://example.com",
            "image": "http://example.com/image.jpg",
            "thumbnailUrl": "http://example.com/image-thumb.jpg",
            "keywords": "western, cowboy",
            "brand": "Brand Name",
            "color": "Black",
            "itemCondition": "New",
            "manufacturer": "Manufacturer Name",
            "model": "Black",
            "sku": "SKU #",
            "weight": "12 pounds",
            "width": "12 inches",
            "height": "12 inches",
            "depth": "12 inches"
            }

### List All Products [GET]
+ Parameters
    + q (optional, string) ... Keyword query to search for products.

+ Response 200

    [Products Collection][]

### Create a Products [POST]
Allows the creation of a new product

+ Request (application/json)

        {
        "id": "1",
        "name": "Product One",
        "description": "This is the full description of the product.",
        "url": "http://example.com",
        "image": "http://example.com/image.jpg",
        "thumbnailUrl": "http://example.com/image-thumb.jpg",
        "keywords": "western, cowboy",
        "brand": "Brand Name",
        "color": "Black",
        "itemCondition": "New",
        "manufacturer": "Manufacturer Name",
        "model": "Black",
        "sku": "SKU #",
        "weight": "12 pounds",
        "width": "12 inches",
        "height": "12 inches",
        "depth": "12 inches"
        }

+ Response 201

    [Product][]
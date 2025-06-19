# Manage Books Service

## System Require

- Java Version 17
- MySQL Version 8.0.42
- Docker

## Database Schema

```sql
CREATE TABLE sys.books (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    author varchar(255),
    INDEX (author),
    published_date date);
```

## API Specification

#### Endpoint: POST {Host}/books
#### Request Body:

```json
{
  "title": "string (Mandatory)",
  "author": "string (Mandatory)",
  "publishedDate": "date (Optional)" //format : yyyy-MM-dd & require : ThaiBuddhistDate
}
```

#### Endpoint: GET {Host}/books?author={authorName}
#### Request Param:

``` 
authorName String Optional
```

## Integration Test Detail
#### API CreateBook
``` text
- เริ่มทำการทดสอบโดยการ Call Function createBook ที่อยู่ใน BooksController เพื่อเรียกไปยัง Function createBooksService ที่อยู่ใน BookService จากนั้น Function createBooksService จะทำการ Call ไปที่ BookRepository ที่เชื่อต่อกับ Database เพื่อบันทึกข้อมูลลง Database
```
#### API GetBookByAuthor
```
เริ่มทำการทดสอบโดยการ Call Function getBookByAuthor ที่อยู่ใน BooksController เพื่อเรียกไปยัง Function getByAuthorService ที่อยู่ใน BookService จากนั้น Function getByAuthorService จะทำการ Call ไปที่ BookRepository ที่เชื่อต่อกับ Database เพื่อดึงข้อมูล
```

## Example API

### Endpoint: POST {Host}/books
```curl
curl --location 'localhost:8080/books' \
    --header 'Content-Type: application/json' \
    --data '{
    "title":"TestTitle",
    "author":"TestAuthor",
    "publishedDate":"2568-02-20"
    }'
```
#### Ex. Request Body
```json
{
  "title":"TestTitle",
  "author":"TestAuthor",
  "publishedDate":"2568-02-20"
}
```
#### Ex. Response Body
```json
{
  "id": 15,
  "title": "TestTitle",
  "author": "TestAuthor",
  "publishedDate": "2025-02-20"
}
```

### Endpoint: GET {Host}/books?author={authorName}
```curl
curl --location 'localhost:8080/books?author=TestAuthor'
```
#### Ex. Response Body
```json
[
  {
    "id": 15,
    "title": "TestTitle1",
    "author": "TestAuthor",
    "publishedDate": "2000-02-20"
  },
  {
    "id": 16,
    "title": "TestTitle2",
    "author": "TestAuthor",
    "publishedDate": "2025-06-16"
  },
  {
    "id": 17,
    "title": "TestTitle3",
    "author": "TestAuthor",
    "publishedDate": "2025-06-16"
  }
]
```
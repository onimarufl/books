# books
Manage Books Service

Required
- Java Version 17
- MySQL Version 8.0.42

Database Schema
- CREATE TABLE books (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  author varchar(255),
  INDEX (author),
  published_date date);

API Specification

URL : POST {Host}/books
  - title String Mandatory (ชื่อหนังสือ)
  - author String Mandatory (ชื่อผู้เขียน)
  - published_date Optional (วันที่เผยแพร่)
  - calendarType String Condition
    Mandatory = ถ้า published_date ไม่เป็นค่าว่าง / Optional = ถ้า published_date เป็นค่าว่าง และสามารถใช้ได้เพียง GREGORIAN และ BUDDHIST_YEAR

URL : GET {Host}/books?author={authorName}
- authorName String Optional (ชื่อผู้เขียน)

Integration Test Detail
- API CreateBook เริ่มทำการทดสอบโดยการ Call Function createBook ที่อยู่ใน BooksController เพื่อเรียกไปยัง Function createBooksService ที่อยู่ใน BookService จากนั้น Function createBooksService จะทำการ Call ไปที่ BookRepository ที่เชื่อต่อกับ Database เพื่อบันทึกข้อมูลลง Database


- API GetBookByAuthor เริ่มทำการทดสอบโดยการ Call Function createBooksService ที่อยู่ใน BookService เพื่อเตรียมข้อมูลสำหรับทำการทดสอบ จากนั้นทำการ Call Function getBookByAuthor ที่อยู่ใน BooksController เพื่อเรียกไปยัง Function getByAuthorService ที่อยู่ใน BookService จากนั้น Function getByAuthorService จะทำการ Call ไปที่ BookRepository ที่เชื่อต่อกับ Database เพื่อดึงข้อมูลที่เตรียมไว้


Example API

POST API

    POST Ex. curl --location 'localhost:8080/books' \
    --header 'Content-Type: application/json' \
    --data '{
    "title":"TestTitle",
    "author":"TestAuthor",
    "publishedDate":"2025-02-20",
    "calendarType":"GREGORIAN"
    }'

    - POST Ex. Json Request Body
      {
      "title":"TestTitle",
      "author":"TestAuthor",
      "publishedDate":"2025-02-20",
      "calendarType":"GREGORIAN"
      }

    - POST Ex. Json Response Body
    {
    "id": 15,
    "title": "TestTitle",
    "author": "TestAuthor",
    "publishedDate": "2025-02-20"
    }
        

GET API

    - GET Ex. curl --location 'localhost:8080/books?author=TestAuthor'

    - GET Ex. Response 
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
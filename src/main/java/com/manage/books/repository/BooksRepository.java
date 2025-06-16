package com.manage.books.repository;

import com.manage.books.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Integer> {
    List<Books> findByAuthor(String title);
}

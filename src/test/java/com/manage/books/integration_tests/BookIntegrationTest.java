package com.manage.books.integration_tests;

import com.manage.books.controller.BooksController;
import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.repository.BooksRepository;
import com.manage.books.service.BooksService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@Transactional
@Testcontainers
public class BookIntegrationTest {

    @Autowired
    private BooksController booksController;

    @Autowired
    private BooksService booksService;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.42")
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("testPass");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
    }

    @Test
    public void createBookIntegrationTest() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(LocalDate.now());

        ResponseEntity<?> actual = booksController.createBook(booksRequest);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    public void GetBookIntegrationTest() {
        String author = "TestAuthorGet";
        List<Books> actual = booksController.getBookByAuthor(author);
        assertNotNull(actual);

    }
}

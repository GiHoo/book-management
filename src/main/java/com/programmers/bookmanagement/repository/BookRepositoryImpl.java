package com.programmers.bookmanagement.repository;

import com.programmers.bookmanagement.exception.DataAccessException;
import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.bookmanagement.common.Utils.toLocalDateTime;
import static com.programmers.bookmanagement.common.Utils.toUUID;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String findAll = """
            select *
            from books
            """;

    private static final String INSERT = """
            INSERT
            INTO books(book_id, book_name, category, price, description, created_at, updated_at)
            VALUES (UUID_TO_BIN(:bookId), :bookName, :category, :price, :description, :createdAt, :updatedAt)       
            """;

    private static final String UPDATE = """
            UPDATE 
            books
            SET book_name = :bookName, category = :category, price = :price, description = :description, updated_at = :updatedAt WHERE book_id = UUID_TO_BIN(:bookId)            
            """;

    private static final String FINDBYID = """
            SELECT * 
            FROM books 
            WHERE book_id = UUID_TO_BIN(:bookId)
            """;

    private static final String FINDBYNAME = """
            SELECT * 
            FROM books 
            WHERE book_name = :bookName
            """;

    private static final String FINDBYCATEGORY = """
            SELECT * 
            FROM books 
            WHERE category = :category
            """;

    private static final String DELETEBYID = """
            DELETE 
            FROM books 
            WHERE book_id = UUID_TO_BIN(:bookId)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(findAll, bookRowMapper);
    }

    @Override
    public Book insert(Book book) {
        int update = jdbcTemplate.update(INSERT, toParam(book));
        if(update != 1) throw new DataAccessException("책이 추가되지 않았습니다.");
        return book;
    }

    @Override
    public Book update(Book book) {
        int update = jdbcTemplate.update(UPDATE, toParam(book));
        if (update != 1) throw new DataAccessException("책이 수정되지 않았습니다.");
        return book;
    }

    @Override
    public Optional<Book> findById(UUID bookId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(FINDBYID,
                            Collections.singletonMap("bookId", bookId.toString().getBytes()),
                            bookRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Book> findByName(String bookName) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(FINDBYNAME,
                            Collections.singletonMap("bookName", bookName),
                            bookRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findByCategory(Category category) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(FINDBYCATEGORY,
                            Collections.singletonMap("category", category.toString()),
                            bookRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Book deleteById(UUID bookId) {
        Optional<Book> findBook = findById(bookId);
        jdbcTemplate.update(DELETEBYID, Collections.singletonMap("bookId", bookId.toString().getBytes()));
        return findBook.get();
    }

    private static final RowMapper<Book> bookRowMapper = ((resultSet, i) -> {
        UUID bookId = toUUID(resultSet.getBytes("book_id"));
        String bookName = resultSet.getString("book_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Book(bookId, bookName, category, price, description, createdAt, updatedAt);
    });

    private Map<String, Object> toParam(Book book) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bookId", book.getBookId().toString().getBytes());
        paramMap.put("bookName", book.getBookName());
        paramMap.put("category", book.getCategory().toString());
        paramMap.put("price", book.getPrice());
        paramMap.put("description", book.getDescription());
        paramMap.put("createdAt", book.getCreatedAt());
        paramMap.put("updatedAt", book.getUpdatedAt());
        return paramMap;
    }
}

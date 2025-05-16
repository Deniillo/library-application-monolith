package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.dao.model.Book;
import itmo.deniill.service.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Management", description = "Полное управление книжными записями")
public class BookController {

    @Autowired private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить все книги",
            description = "Возвращает полный список всех книг в системе с пагинацией и фильтрацией"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получен список книг",
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Неподдерживаемый тип данных",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Content type 'text/xml' is not supported\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Internal server error occurred while processing request\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Book>> getAllBooks(
    ) {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить книгу по ID",
            description = "Возвращает детальную информацию о книге по её уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга успешно найдена",
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга не найдена",
                    content = @Content(examples = @ExampleObject(value = "{\"message\": \"Book not found with id: 999\"}"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный формат ID",
                    content = @Content(examples = @ExampleObject(value = "{\"message\": \"Invalid ID format\"}"))
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Неподдерживаемый тип данных",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Content type 'text/xml' is not supported\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Internal server error occurred while processing request\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Book> getBookById(
            @Parameter(
                    description = "Уникальный идентификатор книги",
                    required = true,
                    example = "123"
            )
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Создать новую книгу",
            description = "Создает новую запись книги с переданными данными в формате JSON",
            tags = "Book Management"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Книга успешно создана",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Невалидные входные данные",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Missing fields",
                                            value = "{\"message\": \"Name and PhotoUrl are required\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid format",
                                            value = "{\"message\": \"Validation error: Invalid URL format\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Неподдерживаемый тип данных",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Content type 'text/xml' is not supported\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Internal server error occurred while processing request\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Book> createBook(
            @Parameter(
                    description = "Данные книги в формате JSON",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)))
            @RequestBody Book book
    ) {
        if (book.getName() == null || book.getPhotoUrl() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Name and PhotoUrl are required!"
            );
        }

        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Обновить книгу",
            description = "Полностью обновляет информацию о существующей книге"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Книга успешно обновлена",
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга для обновления не найдена",
                    content = @Content(examples = @ExampleObject(value = "{\"message\": \"Book not found with id: 999\"}"))
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Неподдерживаемый тип данных",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Content type 'text/xml' is not supported\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Internal server error occurred while processing request\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID книги для обновления", example = "123")
            @PathVariable Integer id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные для книги",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Book.class),
                            examples = @ExampleObject(
                                    value = "{\"name\": \"Обновленное название\", \"description\": \"Новое описание\", \"genre\": \"FANTASY\"}"
                            )
                    )
            )
            @RequestBody Book bookDetails
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить книгу",
            description = "Удаляет книгу из системы по её идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Книга успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Книга для удаления не найдена",
                    content = @Content(examples = @ExampleObject(value = "{\"message\": \"Book not found with id: 999\"}"))
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Неподдерживаемый тип данных",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Content type 'text/xml' is not supported\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"message\": \"Internal server error occurred while processing request\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги для удаления", example = "123")
            @PathVariable Integer id
    ) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

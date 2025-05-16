package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.dao.model.News;
import itmo.deniill.exception.NewsNotFoundException;
import itmo.deniill.service.services.interfaces.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@Tag(name = "News Controller", description = "Управление новостными записями")
@AllArgsConstructor
public class NewsController {
    @Autowired
    private final NewsService newsService;

    @QueryMapping
    public News getNewsByIdGQL(@Argument Integer id) {
        return newsService.findById(id);
    }

    @QueryMapping
    public List<News> getAllNewsGQL() {
        return newsService.findAll();
    }

    @GetMapping
    @Operation(
            summary = "Получить все новости",
            description = "Возвращает список всех новостных записей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка новостей",
                            content = @Content(schema = @Schema(implementation = News.class)))
                            })
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить новость по ID",
            description = "Возвращает новость по указанному идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новость найдена",
                            content = @Content(schema = @Schema(implementation = News.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Новость не найдена",
                            content = @Content)
            })
    public ResponseEntity<News> getNewsById(
            @Parameter(description = "ID новости", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(newsService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новую новость",
            description = "Создает новую новостную запись",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Новость успешно создана",
                            content = @Content(schema = @Schema(implementation = News.class)))
            })
    public ResponseEntity<News> createNews(
            @RequestBody
            @Schema(description = "Данные для новой новости", required = true)
            News news) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.save(news));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить новость",
            description = "Обновляет существующую новость",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новость успешно обновлена",
                            content = @Content(schema = @Schema(implementation = News.class)))
            })
    public ResponseEntity<News> updateNews(
            @Parameter(description = "ID новости для обновления", example = "1")
            @PathVariable int id,

            @RequestBody
            @Schema(description = "Обновленные данные новости", required = true)
            News newsDetails) {
        return ResponseEntity.ok(newsService.update(id, newsDetails));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удалить новость",
            description = "Удаляет новость по указанному ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Новость успешно удалена"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Новость не найдена")
            })
    public ResponseEntity<Void> deleteNews(
            @Parameter(description = "ID новости для удаления", example = "1")
            @PathVariable int id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NewsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNewsNotFoundException(NewsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

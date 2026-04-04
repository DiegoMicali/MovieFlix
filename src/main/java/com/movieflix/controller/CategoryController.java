package com.movieflix.controller;

import com.movieflix.entity.Category;
import com.movieflix.mapper.CategoryMapper;
import com.movieflix.request.CategoryRequest;
import com.movieflix.response.CategoryResponse;
import com.movieflix.response.MovieResponse;
import com.movieflix.response.StreamingResponse;
import com.movieflix.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Resource responsable for management of movie categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get Categories", description = "Method to get a list of all categories",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Successfully found all categories",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get Category by Id", description = "Method to get a category by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found the category",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getByCategoryId(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save Category", description = "Method to save a new streaming plataform",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Category saved successfully",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category newCategory = CategoryMapper.toCategory(categoryRequest);
        Category savedCategory = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(summary = "Delete Streaming by Id", description = "Method to delete a streaming plataform by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Successfully deleted the streaming plataform", content = @Content())
    @ApiResponse(responseCode = "404", description = "Category not finded")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCategoryById(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

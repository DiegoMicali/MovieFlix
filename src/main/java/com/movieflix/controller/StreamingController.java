package com.movieflix.controller;

import com.movieflix.entity.Streaming;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.request.StreamingRequest;
import com.movieflix.response.LoginResponse;
import com.movieflix.response.MovieResponse;
import com.movieflix.response.StreamingResponse;
import com.movieflix.service.StreamingService;
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
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
@Tag(name = "Streaming", description = "Resource responsable for management of streamings plataforms")
public class StreamingController {


    private final StreamingService streamingService;

    @Operation(summary = "Get Streamings", description = "Method to get a list of all streamings plataforms",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found all streaming plataforms",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StreamingResponse.class))))
    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAllStreamings() {
        List<StreamingResponse> streamings = streamingService.findAll()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();
        return ResponseEntity.ok(streamings);
    }

    @Operation(summary = "Get Streaming by Id", description = "Method to get a streaming plataform by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found the streaming plataform",
            content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getByStreamingId(@PathVariable Long id) {
        return streamingService.findById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save Streaming", description = "Method to save a new streaming plataform",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Streaming plataform saved successfully",
            content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@Valid @RequestBody StreamingRequest streamingRequest) {
        Streaming newStreaming = StreamingMapper.toStreaming(streamingRequest);
        Streaming savedStreaming = streamingService.save(newStreaming);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }


    @Operation(summary = "Delete Streaming by Id", description = "Method to delete a streaming plataform by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Successfully deleted the streaming plataform", content = @Content())
    @ApiResponse(responseCode = "404", description = "Streaming plataform not finded", content = @Content())
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByStreamingById(@PathVariable Long id) {
        streamingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

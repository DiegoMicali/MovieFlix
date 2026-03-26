package com.movieflix.mapper;

import com.movieflix.entity.Streaming;
import com.movieflix.request.StreamingRequest;
import com.movieflix.response.StreamingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public static Streaming toStreaming(StreamingRequest streamingResquest) {
        return Streaming
                .builder()
                .name(streamingResquest.name())
                .build();

    }

    public static StreamingResponse toStreamingResponse(Streaming streaming) {
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}

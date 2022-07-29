package com.example.cdwebbe.payload;

import com.example.cdwebbe.model.History;
import com.example.cdwebbe.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItemResponse {
    private Movie movie ;
    private History history ;
}

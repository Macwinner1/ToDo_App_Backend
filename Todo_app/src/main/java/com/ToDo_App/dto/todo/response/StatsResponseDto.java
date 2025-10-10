package com.ToDo_App.dto.todo.response;

import com.ToDo_App.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatsResponseDto extends BaseResponseDto {
    public StatsResponseDto(HttpStatus status, String message, StatsData data) {
        super(status, message, data);
    }

    @Data
    @AllArgsConstructor
    public static class StatsData {
        private long total;
        private long completed;
        private long pending;
        private long highPriority;
    }
}
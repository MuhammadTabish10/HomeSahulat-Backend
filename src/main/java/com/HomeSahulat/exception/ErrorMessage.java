package com.HomeSahulat.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage<T>
{
    T error;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    LocalDateTime time;
}


package com.example.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTodoRequest {
  @NotBlank private String title;
}

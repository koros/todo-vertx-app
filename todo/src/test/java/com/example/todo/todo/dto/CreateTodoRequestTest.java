package com.example.todo.todo.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CreateTodoRequest Tests")
class CreateTodoRequestTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should validate successfully with valid title")
  void shouldValidateSuccessfullyWithValidTitle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Valid Todo");

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Should fail validation when title is null")
  void shouldFailValidationWhenTitleIsNull() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle(null);

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).hasSize(1);
    assertThat(violations)
        .extracting(ConstraintViolation::getMessage)
        .contains("must not be blank");
  }

  @Test
  @DisplayName("Should fail validation when title is blank")
  void shouldFailValidationWhenTitleIsBlank() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("   ");

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).hasSize(1);
    assertThat(violations)
        .extracting(ConstraintViolation::getMessage)
        .contains("must not be blank");
  }

  @Test
  @DisplayName("Should fail validation when title is empty")
  void shouldFailValidationWhenTitleIsEmpty() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("");

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should accept title with whitespace in middle")
  void shouldAcceptTitleWithWhitespaceInMiddle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Todo with spaces");

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Should get and set title")
  void shouldGetAndSetTitle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();

    // When
    request.setTitle("My Todo");

    // Then
    assertThat(request.getTitle()).isEqualTo("My Todo");
  }

  @Test
  @DisplayName("Should accept long title")
  void shouldAcceptLongTitle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    String longTitle = "A".repeat(1000);
    request.setTitle(longTitle);

    // When
    Set<ConstraintViolation<CreateTodoRequest>> violations = validator.validate(request);

    // Then
    assertThat(violations).isEmpty();
  }
}

package com.shop.buy.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
      EntityNotFoundException ex, WebRequest request) {
    return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
      DuplicateResourceException ex, WebRequest request) {
    return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex, WebRequest request) {
    String errorMessage = "Erro de banco de dados: ";

    Throwable rootCause = ex.getRootCause();
    if (rootCause != null) {
      String rootCauseMessage = rootCause.getMessage();

      if (rootCauseMessage != null) {
        if (rootCauseMessage.contains("unique constraint")
            || rootCauseMessage.contains("Duplicate entry")) {

          String fieldName = extractFieldNameFromError(rootCauseMessage);
          String entityName = extractEntityNameFromError(rootCauseMessage);

          if (fieldName != null && !fieldName.isEmpty()) {
            errorMessage =
                "Já existe um "
                    + (entityName != null ? entityName : "registro")
                    + " com o mesmo "
                    + fieldName;
          } else {
            errorMessage = "Já existe um registro com os mesmos valores para campos únicos";
          }

          return createErrorResponse(errorMessage, HttpStatus.CONFLICT, request);
        }
      }

      errorMessage += rootCauseMessage;
    } else {
      errorMessage += ex.getMessage();
    }

    return createErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, request);
  }

  private String extractFieldNameFromError(String errorMessage) {

    String[] commonFields = {"cnpj", "cpf", "email", "name", "username", "phone", "description"};
    String[] formattedFields = {
      "CNPJ", "CPF", "e-mail", "nome", "nome de Usuário", "telefone", "descrição"
    };

    errorMessage = errorMessage.toLowerCase();

    for (String field : commonFields) {
      if (errorMessage.contains(field)) {
        return formattedFields[Arrays.asList(commonFields).indexOf(field)];
      }
    }

    if (errorMessage.contains("key (") && errorMessage.contains(")=")) {
      int start = errorMessage.indexOf("key (") + 5;
      int end = errorMessage.indexOf(")=", start);
      if (start > 0 && end > start) {
        return errorMessage.substring(start, end);
      }
    }

    if (errorMessage.contains("for key '")) {
      int keyIndex = errorMessage.indexOf("for key '") + 9;
      int endIndex = errorMessage.indexOf("'", keyIndex);
      if (keyIndex > 0 && endIndex > keyIndex) {
        String key = errorMessage.substring(keyIndex, endIndex);

        if (key.contains(".")) {
          return key.substring(key.indexOf(".") + 1);
        }
        return key;
      }
    }

    return null;
  }

  private String extractEntityNameFromError(String errorMessage) {

    String[] entities = {
      "supplier", "brand", "customer", "product", "employee", "category", "sale"
    };

    String[] translatedEntities = {
      "fornecedor", "marca", "cliente", "produto", "funcionário", "categoria", "venda"
    };

    errorMessage = errorMessage.toLowerCase();

    for (int i = 0; i < entities.length; i++) {
      if (errorMessage.contains(entities[i])) {
        return translatedEntities[i];
      }
    }

    if (errorMessage.contains("table ")) {
      int tableIndex = errorMessage.indexOf("table ") + 6;
      int endIndex = errorMessage.indexOf(" ", tableIndex);
      if (tableIndex > 0 && endIndex > tableIndex) {
        return errorMessage.substring(tableIndex, endIndex);
      }
    }

    return null;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ValidationErrorResponse errorResponse =
        new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Falha na validação",
            LocalDateTime.now(),
            request.getDescription(false),
            errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();

    ex.getConstraintViolations()
        .forEach(
            violation -> {
              String fieldName = violation.getPropertyPath().toString();
              String errorMessage = violation.getMessage();
              errors.put(fieldName, errorMessage);
            });

    ValidationErrorResponse errorResponse =
        new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Falha na validação",
            LocalDateTime.now(),
            request.getDescription(false),
            errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    return createErrorResponse(
        "Ocorreu um erro inesperado: " + ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(
      String message, HttpStatus status, WebRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            status.value(), message, LocalDateTime.now(), request.getDescription(false));
    return new ResponseEntity<>(errorResponse, status);
  }
}

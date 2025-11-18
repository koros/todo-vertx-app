package com.example.todo.config;

import com.example.todo.todo.mapper.TodoMapper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.mapstruct.factory.Mappers;

/**
 * Provides MapStruct object mappers for DTO-to-entity conversions.
 *
 * <p>This module configures compile-time generated mappers that eliminate boilerplate mapping code
 * while providing type-safe conversions between:
 *
 * <ul>
 *   <li>DTOs (Data Transfer Objects) - API request/response payloads
 *   <li>Entities - JPA domain model objects
 * </ul>
 */
@Module
public class ObjectMapperModule {

  /**
   * Provides the MapStruct-generated todo mapper for DTO-entity conversions.
   *
   * @return compiled todo mapper instance
   */
  @Provides
  @Singleton
  public TodoMapper todoMapper() {
    return Mappers.getMapper(TodoMapper.class);
  }
}

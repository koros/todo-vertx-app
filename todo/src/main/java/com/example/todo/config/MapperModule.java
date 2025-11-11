package com.example.todo.config;

import com.example.todo.todo.mapper.TodoMapper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.mapstruct.factory.Mappers;

@Module
public class MapperModule {

  @Provides
  @Singleton
  public TodoMapper provideTodoMapper() {
    return Mappers.getMapper(TodoMapper.class);
  }
}

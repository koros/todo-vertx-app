package com.example.todo.config;

import com.example.todo.common.ProblemFailureHandler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WebModule_ProblemFailureHandlerFactory implements Factory<ProblemFailureHandler> {
  private final WebModule module;

  public WebModule_ProblemFailureHandlerFactory(WebModule module) {
    this.module = module;
  }

  @Override
  public ProblemFailureHandler get() {
    return problemFailureHandler(module);
  }

  public static WebModule_ProblemFailureHandlerFactory create(WebModule module) {
    return new WebModule_ProblemFailureHandlerFactory(module);
  }

  public static ProblemFailureHandler problemFailureHandler(WebModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.problemFailureHandler());
  }
}

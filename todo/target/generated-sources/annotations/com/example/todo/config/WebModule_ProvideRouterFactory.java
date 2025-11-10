package com.example.todo.config;

import com.example.todo.common.ProblemFailureHandler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class WebModule_ProvideRouterFactory implements Factory<Router> {
  private final WebModule module;

  private final Provider<Vertx> vertxProvider;

  private final Provider<ProblemFailureHandler> failureProvider;

  public WebModule_ProvideRouterFactory(WebModule module, Provider<Vertx> vertxProvider,
      Provider<ProblemFailureHandler> failureProvider) {
    this.module = module;
    this.vertxProvider = vertxProvider;
    this.failureProvider = failureProvider;
  }

  @Override
  public Router get() {
    return provideRouter(module, vertxProvider.get(), failureProvider.get());
  }

  public static WebModule_ProvideRouterFactory create(WebModule module,
      Provider<Vertx> vertxProvider, Provider<ProblemFailureHandler> failureProvider) {
    return new WebModule_ProvideRouterFactory(module, vertxProvider, failureProvider);
  }

  public static Router provideRouter(WebModule instance, Vertx vertx,
      ProblemFailureHandler failure) {
    return Preconditions.checkNotNullFromProvides(instance.provideRouter(vertx, failure));
  }
}

package com.example.todo.config;

import com.example.todo.app.MainVerticle;
import com.example.todo.todo.api.TodoRoutes;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class AppModule_ProvideMainVerticleFactory implements Factory<MainVerticle> {
  private final AppModule module;

  private final Provider<Router> routerProvider;

  private final Provider<TodoRoutes> routesProvider;

  public AppModule_ProvideMainVerticleFactory(AppModule module, Provider<Router> routerProvider,
      Provider<TodoRoutes> routesProvider) {
    this.module = module;
    this.routerProvider = routerProvider;
    this.routesProvider = routesProvider;
  }

  @Override
  public MainVerticle get() {
    return provideMainVerticle(module, routerProvider.get(), routesProvider.get());
  }

  public static AppModule_ProvideMainVerticleFactory create(AppModule module,
      Provider<Router> routerProvider, Provider<TodoRoutes> routesProvider) {
    return new AppModule_ProvideMainVerticleFactory(module, routerProvider, routesProvider);
  }

  public static MainVerticle provideMainVerticle(AppModule instance, Router router,
      TodoRoutes routes) {
    return Preconditions.checkNotNullFromProvides(instance.provideMainVerticle(router, routes));
  }
}

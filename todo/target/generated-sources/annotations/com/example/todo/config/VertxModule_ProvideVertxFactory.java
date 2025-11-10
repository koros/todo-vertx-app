package com.example.todo.config;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.vertx.core.Vertx;
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
public final class VertxModule_ProvideVertxFactory implements Factory<Vertx> {
  private final VertxModule module;

  public VertxModule_ProvideVertxFactory(VertxModule module) {
    this.module = module;
  }

  @Override
  public Vertx get() {
    return provideVertx(module);
  }

  public static VertxModule_ProvideVertxFactory create(VertxModule module) {
    return new VertxModule_ProvideVertxFactory(module);
  }

  public static Vertx provideVertx(VertxModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideVertx());
  }
}

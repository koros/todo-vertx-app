package com.example.todo.config;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import org.hibernate.reactive.stage.Stage;

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
public final class HibernateModule_ProvideSessionFactoryFactory implements Factory<Stage.SessionFactory> {
  private final HibernateModule module;

  public HibernateModule_ProvideSessionFactoryFactory(HibernateModule module) {
    this.module = module;
  }

  @Override
  public Stage.SessionFactory get() {
    return provideSessionFactory(module);
  }

  public static HibernateModule_ProvideSessionFactoryFactory create(HibernateModule module) {
    return new HibernateModule_ProvideSessionFactoryFactory(module);
  }

  public static Stage.SessionFactory provideSessionFactory(HibernateModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideSessionFactory());
  }
}

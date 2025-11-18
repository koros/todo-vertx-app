package com.example.todo.config;

import com.example.todo.app.Bootstrap;
import com.example.todo.app.MainVerticle;
import dagger.Component;
import io.vertx.core.Vertx;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

/**
 * Root Dagger component that assembles the complete dependency graph.
 *
 * <p>This component orchestrates the entire application by composing modules in a logical order:
 *
 * <ol>
 *   <li><strong>Infrastructure:</strong> Core runtime (Vert.x) and persistence (Hibernate Reactive)
 *   <li><strong>HTTP Server:</strong> Web routing, error handling, and API documentation
 *   <li><strong>Business Domain:</strong> Todo CRUD operations and business logic
 *   <li><strong>Cross-Cutting:</strong> Object mapping utilities
 *   <li><strong>Lifecycle:</strong> Application startup and HTTP server orchestration
 * </ol>
 *
 * <p>The component is singleton-scoped, creating a single instance of each provided dependency for
 * the lifetime of the application.
 */
@Singleton
@Component(
    modules = {
      // Infrastructure Layer - Core runtime and data access
      VertxInfrastructureModule.class,
      DatabasePersistenceModule.class,

      // HTTP Server Layer - Request handling and API documentation
      HttpServerModule.class,
      ApiDocumentationModule.class,

      // Business Domain Layer - Todo operations
      TodoDomainModule.class,

      // Cross-Cutting Concerns - Utilities and helpers
      ObjectMapperModule.class,

      // Application Layer - Lifecycle and orchestration
      ApplicationLifecycleModule.class
    })
public interface AppComponent {

  /** Provides access to the Vert.x runtime instance. */
  Vertx vertx();

  /** Provides access to the Hibernate Reactive SessionFactory. */
  Stage.SessionFactory sessionFactory();

  /** Injects dependencies into the Bootstrap class. */
  void inject(Bootstrap bootstrap);

  /** Provides the main application verticle. */
  MainVerticle mainVerticle();
}

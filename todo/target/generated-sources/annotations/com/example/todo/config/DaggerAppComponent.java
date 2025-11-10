package com.example.todo.config;

import com.example.todo.app.Bootstrap;
import com.example.todo.app.MainVerticle;
import com.example.todo.common.ProblemFailureHandler;
import com.example.todo.todo.api.TodoRoutes;
import com.example.todo.todo.repo.TodoRepositoryHr;
import com.example.todo.todo.service.TodoService;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import javax.annotation.processing.Generated;
import org.hibernate.reactive.stage.Stage;

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
public final class DaggerAppComponent {
  private DaggerAppComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AppComponent create() {
    return new Builder().build();
  }

  public static final class Builder {
    private VertxModule vertxModule;

    private HibernateModule hibernateModule;

    private AppModule appModule;

    private WebModule webModule;

    private Builder() {
    }

    public Builder vertxModule(VertxModule vertxModule) {
      this.vertxModule = Preconditions.checkNotNull(vertxModule);
      return this;
    }

    public Builder hibernateModule(HibernateModule hibernateModule) {
      this.hibernateModule = Preconditions.checkNotNull(hibernateModule);
      return this;
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder webModule(WebModule webModule) {
      this.webModule = Preconditions.checkNotNull(webModule);
      return this;
    }

    public AppComponent build() {
      if (vertxModule == null) {
        this.vertxModule = new VertxModule();
      }
      if (hibernateModule == null) {
        this.hibernateModule = new HibernateModule();
      }
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      if (webModule == null) {
        this.webModule = new WebModule();
      }
      return new AppComponentImpl(vertxModule, hibernateModule, appModule, webModule);
    }
  }

  private static final class AppComponentImpl implements AppComponent {
    private final AppComponentImpl appComponentImpl = this;

    private Provider<Vertx> provideVertxProvider;

    private Provider<Stage.SessionFactory> provideSessionFactoryProvider;

    private Provider<TodoRepositoryHr> provideRepoHrProvider;

    private Provider<TodoService> provideServiceProvider;

    private Provider<TodoRoutes> provideRoutesProvider;

    private Provider<ProblemFailureHandler> problemFailureHandlerProvider;

    private Provider<Router> provideRouterProvider;

    private Provider<MainVerticle> provideMainVerticleProvider;

    private AppComponentImpl(VertxModule vertxModuleParam, HibernateModule hibernateModuleParam,
        AppModule appModuleParam, WebModule webModuleParam) {

      initialize(vertxModuleParam, hibernateModuleParam, appModuleParam, webModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final VertxModule vertxModuleParam,
        final HibernateModule hibernateModuleParam, final AppModule appModuleParam,
        final WebModule webModuleParam) {
      this.provideVertxProvider = DoubleCheck.provider(VertxModule_ProvideVertxFactory.create(vertxModuleParam));
      this.provideSessionFactoryProvider = DoubleCheck.provider(HibernateModule_ProvideSessionFactoryFactory.create(hibernateModuleParam));
      this.provideRepoHrProvider = DoubleCheck.provider(TodoModule_ProvideRepoHrFactory.create(provideSessionFactoryProvider));
      this.provideServiceProvider = DoubleCheck.provider(TodoModule_ProvideServiceFactory.create(((Provider) provideRepoHrProvider)));
      this.provideRoutesProvider = DoubleCheck.provider(TodoModule_ProvideRoutesFactory.create(provideServiceProvider));
      this.problemFailureHandlerProvider = DoubleCheck.provider(WebModule_ProblemFailureHandlerFactory.create(webModuleParam));
      this.provideRouterProvider = DoubleCheck.provider(WebModule_ProvideRouterFactory.create(webModuleParam, provideVertxProvider, problemFailureHandlerProvider));
      this.provideMainVerticleProvider = DoubleCheck.provider(AppModule_ProvideMainVerticleFactory.create(appModuleParam, provideRouterProvider, provideRoutesProvider));
    }

    @Override
    public Vertx vertx() {
      return provideVertxProvider.get();
    }

    @Override
    public Stage.SessionFactory sessionFactory() {
      return provideSessionFactoryProvider.get();
    }

    @Override
    public TodoRoutes todoRoutes() {
      return provideRoutesProvider.get();
    }

    @Override
    public void inject(Bootstrap bootstrap) {
    }

    @Override
    public MainVerticle mainVerticle() {
      return provideMainVerticleProvider.get();
    }
  }
}

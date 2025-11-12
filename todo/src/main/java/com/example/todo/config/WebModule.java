package com.example.todo.config;

import com.example.todo.common.ProblemFailureHandler;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import javax.inject.Named;
import javax.inject.Singleton;
import org.zalando.problem.jackson.ProblemModule;

@Module
public class WebModule {
  @Provides
  @Singleton
  @Named("root")
  Router rootRouter(Vertx vertx, ProblemFailureHandler failure) {
    var mapper = DatabindCodec.mapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new ProblemModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    var router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route().failureHandler(failure);
    return router;
  }

  @Provides
  @Singleton
  ProblemFailureHandler problemFailureHandler() {
    return new ProblemFailureHandler();
  }
}

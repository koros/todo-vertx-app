package com.example.todo.app;

import com.example.todo.config.AppComponent;
import com.example.todo.config.AppVertxInstance;
import com.example.todo.config.DaggerAppComponent;

public class Bootstrap {
  public static void main(String[] args) {
    AppComponent c = DaggerAppComponent.create();
    // Give HR the exact Vert.x instance the app uses:
    AppVertxInstance.VERTX = c.vertx();

    c.vertx().deployVerticle(c.mainVerticle());
  }
}

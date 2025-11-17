window.onload = function () {
  window.ui = SwaggerUIBundle({
    url: "/api-docs/openapi.yaml",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    layout: "BaseLayout",
  });
};

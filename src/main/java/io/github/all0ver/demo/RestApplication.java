package io.github.all0ver.demo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Wlacza JAX-RS. Wszystkie zasoby REST dostepne sa pod prefiksem /api
 * (pelny adres: /demo/api/..., gdzie "demo" to kontekst z nazwy pliku WAR).
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
}

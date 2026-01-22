package com.tecin.mina.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Sirve index.html para las rutas de la SPA (React Router) cuando se accede
 * directamente o se recarga. Las peticiones /api/* siguen yendo a los REST controllers.
 */
@Controller
public class SpaController {

    @GetMapping(value = { "/", "/minas", "/verificar", "/resumen" })
    public String index() {
        return "forward:/index.html";
    }
}

package es.swapsounds.dws_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Bienvenidos a SwapSounds");
        return "greeting_template";
    }

    @GetMapping("/hola")
    public String hola(Model model) {
        return "greeting_template";
    }

}
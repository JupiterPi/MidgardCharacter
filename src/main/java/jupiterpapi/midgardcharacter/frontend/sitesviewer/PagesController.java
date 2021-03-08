package jupiterpapi.midgardcharacter.frontend.sitesviewer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class PagesController {
    final Loader loader = new Loader();

    @GetMapping(value = {"", "/home"})
    public String getHomePage() {
        return loader.loadPage("home");
    }

    @GetMapping("/my")
    public String getWelcomePage() {
        return loader.loadPage("my");
    }

    @GetMapping("/characters")
    public String getCharactersList() {
        return loader.loadPage("characters");
    }

    // res

    @GetMapping("/res/{fileName}")
    public String getTextResource(@PathVariable String fileName) {
        return loader.getTextResource(fileName);
    }

    @GetMapping("/pic/{fileName}")
    public ResponseEntity<byte[]> getPictureResource(@PathVariable String fileName) {
        return loader.getPictureResource(fileName);
    }
}


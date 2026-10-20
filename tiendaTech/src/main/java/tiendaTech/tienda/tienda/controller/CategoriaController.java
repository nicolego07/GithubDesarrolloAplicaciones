package tiendaTech.tienda.tienda.controller;

import tiendaTech.tienda.tienda.service.CategoriaService;
import tiendaTech.tienda.tienda.domain.Categoria;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        var categorias = categoriaService.getCategorias(false);

        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());

        return "categoria/listado";
    }
    
    @PostMapping("/guardar")
public String guardar(
        Categoria categoria,
        @RequestParam("imagenFile") MultipartFile imagenFile) {

    categoriaService.save(categoria, imagenFile);

    return "redirect:/categoria/listado";
}


}

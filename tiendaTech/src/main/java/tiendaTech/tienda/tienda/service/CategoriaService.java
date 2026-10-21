package tiendaTech.tienda.tienda.service;

import tiendaTech.tienda.tienda.domain.Categoria;
import tiendaTech.tienda.tienda.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FirebaseStorageService firebaseStorageService;

public CategoriaService(
        CategoriaRepository categoriaRepository,
        @Autowired(required = false)
        FirebaseStorageService firebaseStorageService) {

    this.categoriaRepository = categoriaRepository;
    this.firebaseStorageService = firebaseStorageService;
}

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) {
            return categoriaRepository.findByActivoTrue();
        }

        return categoriaRepository.findAll();
    }
    
    @Transactional
public void save(Categoria categoria, MultipartFile imagenFile) {

    // Si estamos editando una categoría existente
    if (categoria.getIdCategoria() != null) {

        Categoria categoriaExistente = categoriaRepository
                .findById(categoria.getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría no existe"
                ));

        // Conservamos la imagen que ya tenía guardada
        categoria.setRutaImagen(categoriaExistente.getRutaImagen());
    }

    // Guardamos la categoría en MySQL
    categoria = categoriaRepository.save(categoria);

    // Si el usuario seleccionó una imagen nueva
    if (firebaseStorageService != null
        && imagenFile != null
        && !imagenFile.isEmpty())  {
        try {
            String rutaImagen = firebaseStorageService.uploadImage(
                    imagenFile,
                    "categoria",
                    categoria.getIdCategoria()
            );

            categoria.setRutaImagen(rutaImagen);
            categoriaRepository.save(categoria);

        } catch (IOException e) {
            System.err.println("Error al subir imagen: " + e.getMessage());
        }
    }
}}

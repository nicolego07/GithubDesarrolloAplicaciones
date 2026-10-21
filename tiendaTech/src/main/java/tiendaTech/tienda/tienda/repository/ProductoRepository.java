
package tiendaTech.tienda.tienda.repository;


import tiendaTech.tienda.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();
}


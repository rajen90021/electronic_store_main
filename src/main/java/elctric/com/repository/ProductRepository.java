package elctric.com.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.Categories;
import elctric.com.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	
	Page<Product>	findBytitleContaining(String keyword,Pageable pagable);
        
	
	
	Page<Product> findByLiveTrue( Pageable pagable);
        
//	Page<Product> findByIsStock(Pageable pagable);
	
	     Page<Product>   findByCategories(Categories categories,Pageable pageable);

}

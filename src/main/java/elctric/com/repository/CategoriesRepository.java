package elctric.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import elctric.com.entity.Categories;


public interface CategoriesRepository extends JpaRepository<Categories, String> {

	
	
	 List<Categories> findByTitleContaining(String keyword);
//	 List<Categories> findByTitleContaining(String keyword);
	   
}

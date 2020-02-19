package com.layers.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.layers.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
	 List<Product> findByVersion(Integer Version);
	 List<Product> findByProductId(String ProductId);

	 List<Product> findByDescription(String Description); 
	 List<Product> findByPrice(BigDecimal Price);
	 List<Product> findByImageUrl(String imageUrl);
	 	
	
	 @Query("SELECT COUNT(*) FROM Product p")
	 long getProductCount();
	 
	 @Query("SELECT p FROM Product p WHERE p.description = ?1 and p.productId= ?2")
	 List<Product> getProductByDescription(String description,String product_id);
	 
	 @Query("SELECT p FROM Product p Order By price desc")
	 List<Product> getOrderByDescription();
	 
	 @Query("SELECT p From Product p WHERE p.version >= ?1")   
	 List<Product> getGreaterVerion(Integer version);
	
	 List<Product> findTopByOrderByImageUrlDesc();
	 
	 List<Product> findFirstByOrderByImageUrlDesc();	 
}

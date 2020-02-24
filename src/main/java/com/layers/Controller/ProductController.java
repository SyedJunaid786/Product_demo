package com.layers.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.layers.Entity.Product;
import com.layers.Service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class ProductController
{
	@Autowired
    private ProductService productService;
    
    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }
    
    @ApiOperation(value = "View a list of available products",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    
    @RequestMapping(value = "/image/{category}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Product>> ImageList(@PathVariable("category") String category,Model model)
    {    
        Iterable<Product> productList = productService.listAllProductsByImage(category);
        
        if(productService.isZero(productList) == 0)
        {
        	return new ResponseEntity("Image Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);   
	
    }
    
    @RequestMapping(value = "/version/{category}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Product>> VersionList(@PathVariable("category") Integer Version,Model model)
    {   
        Iterable<Product> productList = productService.listAllProductsByVersion(Version);
        
        if(productService.isZero(productList) == 0)
        {
        	return new ResponseEntity("Version Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);   
	
    }
    
    @RequestMapping(value = "/productId/{category}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Product>> ProductIdList(@PathVariable("category") String category,Model model)
    {
        Iterable<Product> productList = productService.listAllProductsByProductId(category);
        
        if(productService.isZero(productList) == 0)
        {
        	return new ResponseEntity("ProductId Not Found", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(productList, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/price/{category}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Product>> PriceList(@PathVariable("category") BigDecimal category,Model model)
    {
        Iterable<Product> productList = productService.listAllProductsByPrice(category);
        
        if(productService.isZero(productList) == 0)
        {
        	return new ResponseEntity("Price Not Found", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(productList, HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/description/{category}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Product>> DescriptionList(@PathVariable("category") String category,Model model)
    {
        Iterable<Product> productList = productService.listAllProductsByDescription(category);  
        
        if(productService.isZero(productList) == 0)
        {
        	return new ResponseEntity("Description Not Found", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(productList, HttpStatus.OK);
        
    }
    
    
    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Product> list(Model model)
    {
    	Long start_time=System.currentTimeMillis();
        Iterable<Product> productList = productService.listAllProducts();
        Long end_time=System.currentTimeMillis();
        Long total_time=end_time-start_time;
        System.out.println(total_time);
        
        return productList;
    }
    
    
    @ApiOperation(value = "Search a product with an ID",response = Product.class)
    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> showProduct(@PathVariable Integer id)
    {
    	Optional<Product> product = productService.getSingleProductById(id);
    	                           
    	if (product.isPresent()) 
		{
			 return new ResponseEntity<>(product.get(),HttpStatus.OK);
		} 
		else 
		{
			return new ResponseEntity("Product Not Found", HttpStatus.NOT_FOUND);
		}
    }
    
    @ApiOperation(value = "Add a product")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody Product product)
    {
    	Long start_time=System.currentTimeMillis();
        productService.saveProduct(product);
        Long end_time=System.currentTimeMillis();
        Long total_time=end_time-start_time;
        System.out.println(total_time);
        return new ResponseEntity("Product saved successfully", HttpStatus.OK);
    }
    
    @ApiOperation(value = "Update a product")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product)
    {  
        Product storedProduct = productService.getProductById(id);
        storedProduct.setDescription(product.getDescription());
        storedProduct.setImageUrl(product.getImageUrl());
        storedProduct.setPrice(product.getPrice());
        productService.saveProduct(storedProduct);
        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
    }
    
    @ApiOperation(value = "Delete a product")
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Integer id)
    {
        productService.deleteProduct(id);
        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
    }
    
    @ApiOperation(value = "Count The Number Of product")
    @RequestMapping(value="/count",method= RequestMethod.GET, produces = "application/json")
    public long getProductCount()
	{
		return productService.getProductCount();
	} 
    
    @RequestMapping(value="/{description}/{product_id}",method= RequestMethod.GET, produces = "application/json")
    public List<Product> getProductDescPid(@PathVariable String description,@PathVariable String product_id)
	{
		return productService.getProductDescription(description, product_id);
	}
    
    @RequestMapping(value="/order",method= RequestMethod.GET, produces = "application/json")
    public List<Product> getOrderByDescription()
	{
		return productService.getOrderByDescription();
	}
    
    @RequestMapping(value="/greater/{version}",method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Product>> getGreaterVersion(@PathVariable Integer version)
	{
    	List<Product> product= productService.getGreaterVersion(version);
		 
    	 if(productService.isZero(product) == 0)
         {
         	return new ResponseEntity("Versiom Not Found", HttpStatus.NOT_FOUND);
         }
         
         return new ResponseEntity<>(product, HttpStatus.OK);
		
	}
    
    @RequestMapping(value="/top",method= RequestMethod.GET, produces = "application/json")
    public List<Product> findTopByOrderByImageUrlAsc()
	{    
		return productService.findTopByOrderByImageUrlAsc();
	}
}

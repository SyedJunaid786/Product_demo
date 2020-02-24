package com.layers.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Table
@Entity
@Getter
@Setter
@NamedQueries(value = { 
		               @NamedQuery(name = "Product.byPrice", query = "Select p from Product p"),
		               @NamedQuery(name = "Product.byVersion", query = "Select p from Product p"),
		               @NamedQuery(name = "Product.byProductId", query = "Select p from Product p"),
		               @NamedQuery(name = "Product.byDescription", query = "Select p from Product p")
		               })
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    @Column(name = "id" , nullable=false)
    private Integer id;
    
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the product")
    private Integer version;
    
    @ApiModelProperty(notes = "The application-specific product ID")
    private String productId;
    
    @ApiModelProperty(notes = "The product description")
    private String description;
    
    @ApiModelProperty(notes = "The image URL of the product")
    private String imageUrl;
    
    
    @ApiModelProperty(notes = "The price of the product", required = true)
    private List<Integer> price;
//    private BigDecimal price;
  
}


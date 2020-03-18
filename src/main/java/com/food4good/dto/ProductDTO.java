package com.food4good.dto;

import com.food4good.database.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;

import com.food4good.config.GlobalProperties;
import com.food4good.database.entities.Products;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO {

    private String dishName;
    private String dishDescription;
    private String fixPrice;
    private String maxPrice;
    private String minPrice;
    private Integer amount;
    private String originalPrice;
    private String discount;
    private String maxNumOfDishes;

    public static ProductDTO convertFromEntity(Products dish) {
        String num = String.valueOf( new GlobalProperties().getMaxNumOfDishes());
    	ProductDTO productDTO=new ProductDTO();
    	productDTO.setId(dish.getId());
        productDTO.setAmount(dish.getAmount());
        productDTO.setDishDescription(dish.getDescription());
        productDTO.setDishName(dish.getName());
        productDTO.setFixPrice(dish.getFixPrice());
        productDTO.setMaxPrice(dish.getMaxPrice());
        productDTO.setMinPrice(dish.getMinPrice());
        productDTO.setOriginalPrice(dish.getOrigPrice());
        productDTO.setDiscount(calculateDiscount(dish));
        productDTO.setMaxNumOfDishes(num);
        return productDTO;
    }



    public static Products convertToEntity(ProductDTO productDTO, Supplier supplier){
        Products products=new Products();
        products.setAmount(productDTO.getAmount());
        products.setDescription(productDTO.getDishDescription());
        products.setFixPrice(productDTO.getFixPrice());
        products.setMaxPrice(productDTO.getMaxPrice());
        products.setMinPrice(productDTO.getMinPrice());
        products.setName(productDTO.getDishName());
        products.setOrigPrice(productDTO.getOriginalPrice());
        products.setSupplier(supplier);
        return products;
    }
    private static String calculateDiscount(Products dish) {
        double origPrice=Double.valueOf(dish.getOrigPrice());
        double finalPrice;
        if(Strings.isNullOrEmpty(dish.getFixPrice()))
        {
            finalPrice=Double.valueOf(dish.getMaxPrice());
        }else
        {
            finalPrice=Double.valueOf(dish.getFixPrice());
        }
        return String.valueOf((100*finalPrice/origPrice));
    }
}

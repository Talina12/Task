package com.food4good.dto;

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

    public static ProductDTO convertFromEntity(Products dish) {
        ProductDTO productDTO=new ProductDTO();
        productDTO.setAmount(dish.getAmount());
        productDTO.setDishDescription(dish.getDescription());
        productDTO.setDishName(dish.getName());
        productDTO.setFixPrice(dish.getFixPrice());
        productDTO.setMaxPrice(dish.getMaxPrice());
        productDTO.setMinPrice(dish.getMinPrice());
        productDTO.setOriginalPrice(dish.getOrigPrice());
        productDTO.setDiscount(calculateDiscount(dish));
        return productDTO;
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

package com.food4good.dto;

import com.food4good.database.entities.Products;
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

    public static ProductDTO convertFromEntity(Products dish) {
        ProductDTO productDTO=new ProductDTO();
        productDTO.setAmount(dish.getAmount());
        productDTO.setDishDescription(dish.getDescription());
        productDTO.setDishName(dish.getName());
        productDTO.setFixPrice(dish.getFixPrice());
        productDTO.setMaxPrice(dish.getMaxPrice());
        productDTO.setMinPrice(dish.getMinPrice());
        return productDTO;
    }
}

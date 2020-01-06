package com.food4good.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.food4good.database.entities.Dish;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO extends BaseDTO {

    private String dishName;
    private String dishDescription;
    private String price;
    private Integer amount;

    public static DishDTO convertFromEntity(Dish dish) {
        DishDTO dishDTO=new DishDTO();
        dishDTO.setAmount(dish.getAmount());
        dishDTO.setDishDescription(dish.getDescription());
        dishDTO.setDishName(dish.getName());
        dishDTO.setPrice(dish.getPrice());
        return dishDTO;
    }
}

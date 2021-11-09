package com.comert.mhl.database.foodcategory.resource;

import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.ResponseMessage;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/foodcategory")
public class FootCategoryResource {

    @EJB
    private FoodCategoryService foodCategoryService;

    public FootCategoryResource() {
    }

    public FootCategoryResource(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }

    @GET
    @Path("find")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findFoodCategoryById(@QueryParam("id") Integer foodCategoryId) {
        FoodCategory foodCategory;

        try {
            foodCategory = foodCategoryService.findFoodCategoryById(foodCategoryId);
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(404)
                    .entity(new ResponseMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foodCategory)
                .build();
    }

    @POST
    @Path("save")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response saveFoodCategory(FoodCategory foodCategory) {
        try {
            foodCategoryService.saveFoodCategory(foodCategory);
        } catch (EntityNotSavedException exception) {
            return Response
                    .status(400)
                    .entity(new ResponseMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .build();
    }

    @PUT
    @Path("update")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updateFoodCategory(FoodCategory foodCategory) {
        try {
            foodCategoryService.updateFoodCategory(foodCategory);
        } catch (EntityNotUpdatedException exception) {
            return Response
                    .status(400)
                    .entity(new ResponseMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response deleteFoodCategory(@QueryParam("id") Integer foodCategoryId) {
        try {
            foodCategoryService.deleteFoodCategory(foodCategoryId);
        } catch (EntityNotDeletedException exception) {
            return Response
                    .status(400)
                    .entity(new ResponseMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .build();
    }

    @GET
    @Path("listfoodcategories")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response listFoodCategories(@QueryParam("firstresult") int firstResult, @QueryParam("maxresult") int maxResult) {
        Set<FoodCategory> foodCategorySet;

        if (firstResult == 0 && maxResult == 0)
            foodCategorySet = foodCategoryService.listFoodCategories();
        else
            foodCategorySet = foodCategoryService.listFoodCategories(firstResult, maxResult);

        if (foodCategorySet.isEmpty()) {
            return Response
                    .ok()
                    .entity(404)
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(foodCategorySet)
                    .build();
        }

    }

    @GET
    @Path("listfoodcategoryandchildentities")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response findFoodCategoryByIdWithChildEntities(@QueryParam("id") Integer foodCategoryId) {

        Set<Food> foods;

        try {
            foods = foodCategoryService.findFoodCategoryById(foodCategoryId).getFoods();
            if (foods.isEmpty())
                throw new EntityNotFoundException("Foods could not be found", foodCategoryId.toString());
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(404)
                    .entity(new ResponseMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foods)
                .build();
    }

}

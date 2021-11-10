package com.comert.mhl.database.foodcategory.resource;

import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.ExceptionMessage;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
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
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response findFoodCategoryById(@QueryParam("foodcategoryid") Integer foodCategoryId) {
        FoodCategory foodCategory;

        try {
            foodCategory = foodCategoryService.findFoodCategoryById(foodCategoryId);
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
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
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foodCategory.getFoodCategoryId())
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
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
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
    public Response deleteFoodCategory(@QueryParam("foodcategoryid") Integer foodCategoryId) {
        try {
            foodCategoryService.deleteFoodCategory(foodCategoryId);
        } catch (EntityNotDeletedException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
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

        try {
            if (firstResult == 0 && maxResult == 0)
                foodCategorySet = foodCategoryService.listFoodCategories();
            else
                foodCategorySet = foodCategoryService.listFoodCategories(firstResult, maxResult);

        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foodCategorySet)
                .build();
    }

    @GET
    @Path("listfoodcategoriesbyidandname")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response listFoodCategoriesByIdAndName() {
        Set<IdAndName> foodCategoryIdAndNames;

        try {
            foodCategoryIdAndNames = foodCategoryService.listFoodCategoriesByIdAndName();
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foodCategoryIdAndNames)
                .build();
    }

    @GET
    @Path("listfoodcategorychildentities")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response listFoodCategoryChildEntities(@QueryParam("foodcategoryid") Integer foodCategoryId) {
        Set<Food> foodSet;

        try {
            foodSet = foodCategoryService.findFoodCategoryById(foodCategoryId).getFoods();
            if (foodSet.isEmpty())
                throw new EntityNotFoundException("Food Category does not have child entities", foodCategoryId.toString());
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionMessage(exception.getMessage(), exception.getProperty()))
                    .build();
        }

        return Response
                .ok()
                .entity(foodSet)
                .build();
    }

}

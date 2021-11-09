package com.comert.mhl.database.foodcategory.service.impl;

import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.common.validator.ValidationUtils;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.repository.FoodCategoryRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static com.comert.mhl.database.common.util.FoodCategoryData.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@EnabledOnJre(JRE.JAVA_11)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class FoodCategoryServiceImplUTest {

    private FoodCategoryServiceImpl service;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidationUtils validationUtils = new ValidationUtils(validator);
    private FoodCategoryRepository repository;

    @BeforeEach
    protected void setUpScenario() {
        repository = Mockito.mock(FoodCategoryRepository.class);
        service = new FoodCategoryServiceImpl(repository, validationUtils);
    }

    @AfterEach
    protected void tearDownScenario() {

    }

    @Tag(value = "Exceptional")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class ValidatorTestCase {

        @Test
        public void testSaveEntityWithInvalidId() {
            final var foodCategory = foodCategoryWithIdAndName(1, "Milk Category");

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> service.saveFoodCategory(foodCategory)
                    )
                    .withMessage("Field must be null")
                    .matches(e -> e.getProperty().equals("foodCategoryId"));
        }

        @Test
        public void testSaveEntityWithInvalidName() {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setFoodCategoryName(null);

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> service.saveFoodCategory(foodCategory))
                    .withMessage("Field can not be null")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );

            foodCategory.setFoodCategoryName("1");

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> service.saveFoodCategory(foodCategory))
                    .withMessage("Field size must be between 2 and 50 characters")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );

            foodCategory.setFoodCategoryName("123456789012345678901234567890123456789012345678901");

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> service.saveFoodCategory(foodCategory))
                    .withMessage("Field size must be between 2 and 50 characters")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );
        }

        @Test
        public void testSaveEntityWithUniqueName() {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setFoodCategoryName("Unique Category");

            doThrow(EntityNotSavedException.class).when(repository).saveFoodCategory(foodCategory);

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> service.saveFoodCategory(foodCategory)
                    );

            verify(repository, times(1)).saveFoodCategory(foodCategory);
        }

        @Test
        public void testFindEntityWithInvalidId() {
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.findFoodCategoryById(null)
                    )
                    .withMessage("Field can not be null")
                    .matches(e -> e.getProperty().equals("foodCategoryId"));
        }

        @Test
        public void testFindNullEntity() {
            when(repository.findFoodCategoryById(99)).thenReturn(null);

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.findFoodCategoryById(99)
                    )
                    .withMessage("Food Category could not be found")
                    .matches(e -> e.getProperty().equals("99"));

            verify(repository, times(1)).findFoodCategoryById(99);
        }

        @Test
        public void testUpdateEntityWithInvalidId() {
            final var foodCategory = foodCategoryWithIdAndName(null, "Milk Category");

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory)
                    )
                    .withMessage("Field can not be null")
                    .matches(e -> e.getProperty().equals("foodCategoryId"));
        }

        @Test
        public void testUpdateEntityWithInvalidName() {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setFoodCategoryId(1);
            foodCategory.setFoodCategoryName(null);

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory))
                    .withMessage("Field can not be null")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );

            foodCategory.setFoodCategoryName("1");

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory))
                    .withMessage("Field size must be between 2 and 50 characters")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );

            foodCategory.setFoodCategoryName("123456789012345678901234567890123456789012345678901");

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory))
                    .withMessage("Field size must be between 2 and 50 characters")
                    .matches(
                            e -> e.getProperty().equals("foodCategoryName")
                    );
        }

        @Test
        public void testUpdateEntityWithUniqueName() {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setFoodCategoryId(1);
            foodCategory.setFoodCategoryName("Unique Category");

            doThrow(EntityNotUpdatedException.class).when(repository).updateFoodCategory(foodCategory);

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory)
                    );

            verify(repository, times(1)).updateFoodCategory(foodCategory);
        }

        @Test
        public void testUpdateDeletedEntity() {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setFoodCategoryId(1);
            foodCategory.setFoodCategoryName("Removed Category");

            doThrow(EntityNotUpdatedException.class).when(repository).updateFoodCategory(foodCategory);

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> service.updateFoodCategory(foodCategory)
                    );

            verify(repository, times(1)).updateFoodCategory(foodCategory);
        }

        @Test
        public void testDeleteEntityWithInvalidId() {
            assertThatExceptionOfType(EntityNotDeletedException.class)
                    .isThrownBy(
                            () -> service.deleteFoodCategory(null)
                    )
                    .withMessage("Field can not be null")
                    .matches(e -> e.getProperty().equals("foodCategoryId"));
        }

        @Test
        public void testDeleteNotFoundEntity() {
            doThrow(EntityNotDeletedException.class).when(repository).deleteFoodCategory(1);

            assertThatExceptionOfType(EntityNotDeletedException.class)
                    .isThrownBy(
                            () -> service.deleteFoodCategory(1)
                    );

            verify(repository, times(1)).deleteFoodCategory(1);
        }

        @Test
        public void testEmptyListAndThrowException() {
            when(repository.listFoodCategories()).thenReturn(new HashSet<FoodCategory>());
            when(repository.listFoodCategories(2, 5)).thenReturn(new HashSet<FoodCategory>());

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.listFoodCategories()
                    )
                    .withMessage("Food Categories could not be found")
                    .matches(e -> e.getProperty().equals("Food Categories"));

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.listFoodCategories(2, 5)
                    )
                    .withMessage("Food Categories could not be found")
                    .matches(e -> e.getProperty().equals("firstResult : " + "2" + " , maxResult : " + "5"));

            verify(repository, times(1)).listFoodCategories();
            verify(repository, times(1)).listFoodCategories(2, 5);
        }

        @Test
        public void testListEntityWithNegativeArgument() {
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.listFoodCategories(-1, 1)
                    )
                    .withMessage("Fields can not be negative")
                    .matches(e -> e.getProperty().equals("firstResult or maxResult"))
            ;

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.listFoodCategories(1, -1)
                    )
                    .withMessage("Fields can not be negative")
                    .matches(e -> e.getProperty().equals("firstResult or maxResult"));

        }

        @Test
        public void testEmptyListIdAndNamesAndThrowException() {
            when(repository.listFoodCategoriesByIdAndName()).thenReturn(new HashSet<IdAndName>());

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> service.listFoodCategoriesByIdAndName()
                    )
                    .withMessage("Food Category Id And Names could not be found")
                    .matches(e -> e.getProperty().equals("Food Categories"));

            verify(repository, times(1)).listFoodCategoriesByIdAndName();
        }

    }

    @Tag(value = "CRUD")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class CRUDTestCase {

        @Test
        public void testSaveAndFindEntity() {
            final var foodCategory = foodCategory1();
            final var persistedCategory = persistedFoodCategory();

            doNothing().when(repository).saveFoodCategory(foodCategory);
            when(repository.findFoodCategoryById(1)).thenReturn(persistedCategory);

            service.saveFoodCategory(foodCategory);
            final var foundCategory = service.findFoodCategoryById(1);

            verify(repository, times(1)).saveFoodCategory(foodCategory);
            verify(repository, times(1)).findFoodCategoryById(1);

            assertEquals(foundCategory, persistedCategory);
        }

        @Test
        public void testUpdateEntity() {
            final var foodCategory = foodCategory1();
            foodCategory.setFoodCategoryId(1);
            final var mergedCategory = mergedFoodCategory();
            when(repository.updateFoodCategory(foodCategory)).thenReturn(mergedCategory);

            final var foundCategory = service.updateFoodCategory(foodCategory);

            verify(repository, times(1)).updateFoodCategory(foodCategory);

            assertEquals(foundCategory, mergedCategory);
        }

        @Test
        public void testDeleteEntity() {
            doNothing().when(repository).deleteFoodCategory(1);

            service.deleteFoodCategory(1);

            verify(repository, times(1)).deleteFoodCategory(1);
        }

        @Test
        public void testListEntities() {
            final var fullCategorySet = fullFoodCategorySet();
            final var filteredCategorySet = filteredFoodCategorySet();

            when(repository.listFoodCategories()).thenReturn(fullCategorySet);
            when(repository.listFoodCategories(0, 1)).thenReturn(filteredCategorySet);

            final var foundFullCategorySet = service.listFoodCategories();
            final var foundFilteredCategorySet = service.listFoodCategories(0, 1);

            verify(repository, times(1)).listFoodCategories();
            verify(repository, times(1)).listFoodCategories(0, 1);

            assertEquals(foundFullCategorySet, fullCategorySet);
            assertEquals(foundFilteredCategorySet, filteredCategorySet);
        }

        @Test
        public void testIdAndNames() {
            final var categoryIdAndNameSet = foodCategoryIdAndNames();
            when(repository.listFoodCategoriesByIdAndName()).thenReturn(categoryIdAndNameSet);

            Set<IdAndName> foundCategoryIdAndNameSet = service.listFoodCategoriesByIdAndName();

            verify(repository, times(1)).listFoodCategoriesByIdAndName();

            assertEquals(foundCategoryIdAndNameSet, categoryIdAndNameSet);
        }

    }

}
package com.comert.mhl.database.foodcategory.repository;

import com.comert.mhl.database.common.*;
import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import jakarta.ejb.SessionContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.comert.mhl.database.common.util.FoodCategoryData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@EnabledOnJre(JRE.JAVA_11)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class FoodCategoryRepositoryUTest extends GenericUnitTest {

    private EntityManager entityManager;

    private FoodCategoryRepository repository;
    private TransactionalCommandExecutor commandExecutor;

    @Mock
    private SessionContext sessionContext;

    @BeforeEach
    @Override
    public void setUpScenario() {
        entityManager = createEntityManager();
        sessionContext = Mockito.mock(SessionContext.class);
        repository = new FoodCategoryRepository(entityManager, sessionContext);
        commandExecutor = new TransactionalCommandExecutor(entityManager);
    }

    @AfterEach
    @Override
    public void tearDownScenario() {
        commandExecutor.clearDataBase(Food.class, FoodCategory.class);
        if (entityManager != null && entityManager.isOpen())
            entityManager.close();
    }

    @Tag(value = "CRUD")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class CRUDTestCase {

        @ParameterizedTest
        @ValueSource(strings = {"Milk Category", "Meat Category"})
        public void testSuccessPersistAndFindAndGetReferenceEntity(final FoodCategory foodCategory) {
            //managed entity
            commandExecutor.executeTransactionalCommand(
                    () -> repository.saveFoodCategory(foodCategory)
            );

            entityManager.detach(foodCategory);
            assertFalse(entityManager.contains(foodCategory));

            //again managed entity
            final var foundCategory = repository.findFoodCategoryById(foodCategory.getFoodCategoryId());

            assertThat(foundCategory)
                    .isNotNull()
                    .matches(
                            fC -> fC
                                    .getFoodCategoryId() > 0
                                    && fC.getFoodCategoryName().equals(foodCategory.getFoodCategoryName())
                    );

            entityManager.detach(foundCategory);
            assertFalse(entityManager.contains(foundCategory));

            //again managed entity
            final var deReferencedCategory = entityManager.getReference(FoodCategory.class, foundCategory.getFoodCategoryId());

            assertTrue(entityManager.contains(deReferencedCategory));
        }

        @ParameterizedTest
        @ValueSource(strings = {"Milk Category", "Meat Category"})
        public void testSuccessMergeEntity(final FoodCategory foodCategory) {
            //managed entity
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));

            final var persistedCategoryId = foodCategory.getFoodCategoryId();
            final var persistedCategoryVersion = foodCategory.getVersion();
            final var persistedCategoryName = foodCategory.getFoodCategoryName();

            entityManager.detach(foodCategory);
            assertFalse(entityManager.contains(foodCategory));

            final var toMergeFoodCategoryName = "Updated Category";
            foodCategory.setFoodCategoryName(toMergeFoodCategoryName);

            //again managed entity
            final var mergedCategory = commandExecutor.executeTransactionalCommand(() -> repository.updateFoodCategory(foodCategory));

            assertThat(mergedCategory)
                    .isNotNull()
                    .matches(
                            mC -> Objects.equals(mC.getFoodCategoryId(), persistedCategoryId)
                                    && mC.getVersion() > persistedCategoryVersion
                                    && !mC.getFoodCategoryName().equals(persistedCategoryName)
                                    && mC.getFoodCategoryName().equals(toMergeFoodCategoryName)
                    );
        }

        @ParameterizedTest
        @ValueSource(strings = {"Milk Category", "Meat Category"})
        public void testSuccessRefreshEntity(FoodCategory foodCategory) {
            commandExecutor.executeTransactionalCommand(
                    () -> repository.saveFoodCategory(foodCategory)
            );

            final var persistedCategoryName = foodCategory.getFoodCategoryName();
            final var mergedCategoryName = "Updated Category";

            final var anotherSession = createEntityManager();
            final var anotherTransactionCommandExecutor = new TransactionalCommandExecutor(anotherSession);

            // updating name in another session and transaction
            anotherTransactionCommandExecutor.executeTransactionalCommand(
                    () -> {
                        final var query = anotherSession.createQuery(
                                "update FoodCategory fg set fg.foodCategoryName=:name where fg.foodCategoryId =:id"
                        );
                        query.setParameter("name", mergedCategoryName);
                        query.setParameter("id", foodCategory.getFoodCategoryId());
                        query.executeUpdate();
                    }
            );

            // before retrieving
            assertEquals(persistedCategoryName, foodCategory.getFoodCategoryName());

            entityManager.refresh(foodCategory);
            // after retrieving
            assertEquals(mergedCategoryName, foodCategory.getFoodCategoryName());

            if (anotherSession.isOpen())
                anotherSession.close();
        }

        @ParameterizedTest
        @ValueSource(strings = {"Milk Category", "Meat Category"})
        public void testSuccessRemoveEntity(final FoodCategory foodCategory) {
            //managed entity
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));

            commandExecutor.executeTransactionalCommand(() -> repository.deleteFoodCategory(foodCategory.getFoodCategoryId()));
            final var removedCategoryId = foodCategory.getFoodCategoryId();

            //removed entity
            final var removedCategory = repository.findFoodCategoryById(removedCategoryId);

            assertNull(removedCategory);
            assertFalse(entityManager.contains(foodCategory));
        }

        @Test
        public void testSuccessListEntities() {
            final var toPersistCategories = fullFoodCategorySetToPersist();

            toPersistCategories.forEach(
                    foodCategory -> commandExecutor.executeTransactionalCommand(
                            () -> repository.saveFoodCategory(foodCategory)
                    )

            );

            var foundCategories = repository.listFoodCategories();

            assertThat(foundCategories)
                    .hasSize(2)
                    .containsAll(toPersistCategories);

            foundCategories = repository.listFoodCategories(0, 1);

            assertThat(foundCategories)
                    .hasSize(1)
                    .containsAnyElementsOf(toPersistCategories);
        }

        @Test
        public void testListEntitiesByIdAndName() {
            final var toPersistCategories = fullFoodCategorySetToPersist();

            toPersistCategories.forEach(
                    foodCategory -> commandExecutor.executeTransactionalCommand(
                            () -> repository.saveFoodCategory(foodCategory)
                    )

            );

            final var category1IdAndName = new IdAndName(((FoodCategory) toPersistCategories.toArray()[0]).getFoodCategoryId(), ((FoodCategory) toPersistCategories.toArray()[0]).getFoodCategoryName());
            final var category2IdAndName = new IdAndName(((FoodCategory) toPersistCategories.toArray()[1]).getFoodCategoryId(), ((FoodCategory) toPersistCategories.toArray()[1]).getFoodCategoryName());

            final var categoryIdAndNames = repository.listFoodCategoriesByIdAndName();

            assertThat(categoryIdAndNames)
                    .hasSize(2)
                    .containsAll(
                            Set.of(category1IdAndName, category2IdAndName)
                    );
        }
    }

    @Tag(value = "Consistency")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class ConsistencyTestCase {

        @Test
        public void testHashCodeAndEqual() {
            // 2 transient not EQUAL
            final var foodCategory1 = new FoodCategory("1");
            final var foodCategory2 = new FoodCategory("2");
            assertFalse(foodCategory1.equals(foodCategory2));

            //
            commandExecutor.executeTransactionalCommand(() -> {
                        repository.saveFoodCategory(foodCategory1);
                        repository.saveFoodCategory(foodCategory2);
                    }
            );
            final var id1 = foodCategory1.getFoodCategoryId();
            final var id2 = foodCategory2.getFoodCategoryId();

            // 2 Managed represent different record  NOT EQUAL
            var fg1 = repository.findFoodCategoryById(id1);
            var fg2 = repository.findFoodCategoryById(id2);
            assertFalse(fg1.equals(fg2));

            // 2 Managed represent same record  EQUAL
            fg1 = repository.findFoodCategoryById(id1);
            fg2 = repository.findFoodCategoryById(id1);
            assertTrue(fg1.equals(fg2));

            // 1 Deatached and 1 Managed represent same record EQUAL
            entityManager.detach(fg1);
            fg2 = repository.findFoodCategoryById(id1);
            assertTrue(fg1.equals(fg2));

            // 1 Re-attached and 1 Managed represent same record EQUAL
            FoodCategory finalFg = fg1;
            fg1 = commandExecutor.executeTransactionalCommand(
                    () -> repository.updateFoodCategory(finalFg)
            );
            assertTrue(fg1.equals(fg2));

        }

    }


    @Tag(value = "Fetch")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class FetchTestCase implements FetchTest {

        @Test
        @Override
        public void testFetchLazyOrEagerLoading() {
            final var foodCategory = foodCategoryWithChildEntities();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));
            final var persistedCategoryId = foodCategory.getFoodCategoryId();

            entityManager.detach(foodCategory);
            entityManager.clear();
            secondLevelCache.evictAll();

            //fetch category and childs with left join
            final var persistedCategory = repository.findFoodCategoryById(persistedCategoryId);

            entityManager.close(); //session closed

            assertThat(persistedCategory.getFoods())
                    .hasSize(2)
                    .containsAll(foodCategory.getFoods());

            entityManager = createEntityManager();
            sessionContext = Mockito.mock(SessionContext.class);
            repository = new FoodCategoryRepository(entityManager, sessionContext);
            commandExecutor = new TransactionalCommandExecutor(entityManager);
        }
    }

    @Tag(value = "Cascade")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class CascadeTestCase implements CascadeTest {

        //CascadeType.ALL
        private FoodCategory foodCategory;

        @BeforeEach
        public void setUpScenario() {
            foodCategory = foodCategoryWithChildEntities();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));
        }

        @Test
        @Override
        public void testPersistEntityWithChildEntities() {
            final var persistedFood1 = ((Food) foodCategory.getFoods().toArray()[0]);
            final var persistedFood2 = ((Food) foodCategory.getFoods().toArray()[1]);


            assertThat(foodCategory.getFoods())
                    .hasSize(2);

            assertThat(persistedFood1)
                    .matches(
                            pF -> pF.getFoodId() > 0
                                    && pF.getFoodCategory().getFoodCategoryName().equals(foodCategory.getFoodCategoryName())
                    );
            assertThat(persistedFood2)
                    .matches(
                            pF -> pF.getFoodId() > 0
                                    && pF.getFoodCategory().getFoodCategoryName().equals(foodCategory.getFoodCategoryName())
                    );
        }

        @Test
        @Override
        public void testMergeEntityWithChildEntities() {
            final var persistedFoodName = ((Food) foodCategory.getFoods().toArray()[0]).getFoodName();
            final var mergedFoodName = "Updated Food";

            foodCategory.getFoods().forEach(
                    food -> {
                        if (food.getFoodName().equals(persistedFoodName)) {
                            food.setFoodName(mergedFoodName);
                        }
                    }
            );

            commandExecutor.executeTransactionalCommand(() -> repository.updateFoodCategory(foodCategory));

            assertThat(foodCategory.getFoods())
                    .hasSize(2)
                    .noneMatch(mF -> mF.getFoodName().equals(persistedFoodName))
                    .anyMatch(mF -> mF.getFoodName().equals(mergedFoodName));
        }

        @Test
        @Override
        public void testRefreshEntityWithChildEntities() {
            final var persistedCategoryId = foodCategory.getFoodCategoryId();
            final var persistedCategoryName = foodCategory.getFoodCategoryName();
            var food1 = ((Food) foodCategory.getFoods().toArray()[0]);
            var food2 = ((Food) foodCategory.getFoods().toArray()[1]);

            final var persistedFood1Id = food1.getFoodId();
            final var persistedFood1Name = food1.getFoodName();

            final var persistedFood2Id = food2.getFoodId();
            final var persistedFood2Name = food2.getFoodName();

            final var mergedCategoryName = "Updated Category";
            final var mergedFood1Name = "Updated Food 1";
            final var mergedFood2Name = "Updated Food 2";

            final var anotherSession = createEntityManager();
            final var anotherTransactionCommandExecuter = new TransactionalCommandExecutor(anotherSession);

            // updating name in another session and transaction
            anotherTransactionCommandExecuter.executeTransactionalCommand(
                    () -> {
                        final var foodCategory = anotherSession.find(FoodCategory.class, persistedCategoryId);
                        foodCategory.setFoodCategoryName(mergedCategoryName);

                        final var f1 = anotherSession.find(Food.class, persistedFood1Id);
                        f1.setFoodName(mergedFood1Name);
                        final var f2 = anotherSession.find(Food.class, persistedFood2Id);
                        f2.setFoodName(mergedFood2Name);

                        anotherSession.merge(foodCategory);
                        anotherSession.merge(f1);
                        anotherSession.merge(f2);
                    }
            );

            // before retrieving
            assertEquals(persistedCategoryName, foodCategory.getFoodCategoryName());
            assertEquals(persistedFood1Name, food1.getFoodName());
            assertEquals(persistedFood2Name, food2.getFoodName());

            entityManager.refresh(foodCategory);

            // after retrieving
            assertEquals(mergedCategoryName, foodCategory.getFoodCategoryName());
            assertThat(foodCategory.getFoods())
                    .hasSize(2)
                    .anyMatch(
                            food -> food.getFoodName().equals(mergedFood1Name)
                    )
                    .anyMatch(
                            food -> food.getFoodName().equals(mergedFood2Name)
                    );

            if (anotherSession.isOpen())
                anotherSession.close();
        }

        @Test
        @Override
        public void testOrphanRemovalEntityFromChildEntities() {
            final var toOrphanRemoveFood = ((Food) foodCategory.getFoods().toArray()[0]);

            foodCategory.removeFood(toOrphanRemoveFood);

            entityManager.detach(foodCategory);
            entityManager.clear();

            final var mergedCategory = commandExecutor.executeTransactionalCommand(() -> repository.updateFoodCategory(foodCategory));

            assertThat(mergedCategory.getFoods())
                    .hasSize(1)
                    .doesNotContain(toOrphanRemoveFood);

        }

        @Test
        @Override
        public void testDetachEntityWithChildEntities() {
            final var food1 = ((Food) foodCategory.getFoods().toArray()[0]);
            final var food2 = ((Food) foodCategory.getFoods().toArray()[1]);

            assertTrue(entityManager.contains(foodCategory));
            assertTrue(entityManager.contains(food1));
            assertTrue(entityManager.contains(food2));

            entityManager.detach(foodCategory);

            assertFalse(entityManager.contains(foodCategory));
            assertFalse(entityManager.contains(food1));
            assertFalse(entityManager.contains(food2));
        }

        @Test
        @Override
        public void testRemoveEntityWithChildEntities() {
            assertThat(foodCategory.getFoods())
                    .hasSize(2);

            final var toRemoveFood1Id = ((Food) foodCategory.getFoods().toArray()[0]).getFoodId();
            final var toRemoveFood2Id = ((Food) foodCategory.getFoods().toArray()[1]).getFoodId();
            commandExecutor.executeTransactionalCommand(() -> repository.deleteFoodCategory(foodCategory.getFoodCategoryId()));

            final var removedCategory = repository.findFoodCategoryById(foodCategory.getFoodCategoryId());
            final var removedFood1 = entityManager.find(Food.class, toRemoveFood1Id);
            final var removedFood2 = entityManager.find(Food.class, toRemoveFood2Id);

            assertNull(removedCategory);
            assertNull(removedFood1);
            assertNull(removedFood2);
        }

    }

    @Tag(value = "Cache")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class CacheTest {

        @Test
        public void testEntitiesInCache() {
            final var foodCategory = foodCategoryWithChildEntities();

            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));
            final var persistedCategoryId = foodCategory.getFoodCategoryId();

            entityManager.detach(foodCategory);
            entityManager.clear();

            final var persistedCategory = repository.findFoodCategoryById(persistedCategoryId);

            final var childEntities = persistedCategory.getFoods();

            // in session
            assertTrue(entityManager.contains(persistedCategory));
            assertTrue(entityManager.contains(childEntities.toArray()[0]));
            assertTrue(entityManager.contains(childEntities.toArray()[1]));

            // in second-level cache
            assertTrue(secondLevelCache.contains(FoodCategory.class, foodCategory.getFoodCategoryId()));
            assertTrue(secondLevelCache.contains(Food.class, ((Food) childEntities.toArray()[0]).getFoodId()));
            assertTrue(secondLevelCache.contains(Food.class, ((Food) childEntities.toArray()[1]).getFoodId()));
        }

    }

    @Tag(value = "Exceptional")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class CRUDExceptionalTestCase implements CRUDExceptional {

        @Disabled
        @Test
        @Override
        public void testPersistEntityExistException() {
        }

        /*
         * in service
         * id check
         */
        @Test
        @Override
        public void testPersistIllegalArgumentException() {
            final var foodCategory = foodCategory1();
            foodCategory.setFoodCategoryId(-1);

            assertThatExceptionOfType(PersistenceException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.saveFoodCategory(foodCategory)
                            ))
                    .withCauseInstanceOf(PersistentObjectException.class)
                    .withMessageContaining("detached entity passed to persist");

            foodCategory.setFoodCategoryId(0);

            assertThatExceptionOfType(PersistenceException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.saveFoodCategory(foodCategory)
                            ))
                    .withCauseInstanceOf(PersistentObjectException.class)
                    .withMessageContaining("detached entity passed to persist");

            foodCategory.setFoodCategoryId(1);

            assertThatExceptionOfType(PersistenceException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.saveFoodCategory(foodCategory)
                            ))
                    .withCauseInstanceOf(PersistentObjectException.class)
                    .withMessageContaining("detached entity passed to persist");
        }

        /*
        * in repository
        * try...catch
        }
         */
        @Test
        @Override
        public void testPersistConstraintViolationException() {
            final var foodCategory = foodCategory1();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));
            entityManager.detach(foodCategory);

            final var entityWithSameName = new FoodCategory();
            entityWithSameName.setFoodCategoryName(foodCategory.getFoodCategoryName());

            assertThatExceptionOfType(EntityNotSavedException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.saveFoodCategory(entityWithSameName)
                            ))
                    .withMessage("Field must be unique")
                    .matches(e -> e.getProperty().equals("foodCategoryName"));

            verify(sessionContext, times(1)).setRollbackOnly();
        }

        /*
         * in service
         * null check
         */
        @Test
        @Override
        public void testFindIllegalArgumentException() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.findFoodCategoryById(null)
                            )
                    );
        }

        /*
         * in service
         * Optional...orElseThrow
         */
        @Test
        @Override
        public void testFindDoNotReturnNullEntity() {
            Optional<FoodCategory> foodCategory = Optional.ofNullable(repository.findFoodCategoryById(1));

            assertFalse(foodCategory.isPresent());

            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(
                            () -> foodCategory.orElseThrow(
                                    () -> new EntityNotFoundException("test message", "null"))
                    )
                    .withMessage("test message")
                    .matches(e -> e.getProperty().equals("null"));
        }


        @Test
        @Override
        public void testMergeConstraintViolationException() {
            final var foodCategory1 = foodCategory1();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory1));
            entityManager.detach(foodCategory1);

            final var foodCategory2 = foodCategory2();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory2));
            entityManager.detach(foodCategory2);

            foodCategory1.setFoodCategoryName(foodCategory2.getFoodCategoryName());

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.updateFoodCategory(foodCategory1)
                            ))
                    .withMessage("Field must be unique")
                    .matches(e -> e.getProperty().equals("foodCategoryName"));

            verify(sessionContext, times(1)).setRollbackOnly();
        }

        @Test
        @Override
        public void testMergeIllegalArgumentException() {
            final var foodCategory = foodCategory1();
            commandExecutor.executeTransactionalCommand(() -> repository.saveFoodCategory(foodCategory));
            entityManager.detach(foodCategory);

            final var anotherSession = createEntityManager();
            final var anotherRepository = new FoodCategoryRepository(anotherSession, Mockito.mock(SessionContext.class));
            var anotherCommandExecuter = new TransactionalCommandExecutor(anotherSession);
            anotherCommandExecuter.executeTransactionalCommand(
                    () -> anotherRepository.deleteFoodCategory(foodCategory.getFoodCategoryId())
            );

            anotherSession.close();

            assertThatExceptionOfType(EntityNotUpdatedException.class)
                    .isThrownBy(() -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.updateFoodCategory(foodCategory)
                            )
                    )
                    .withMessage("Food Category might have already been deleted")
                    .matches(e -> e.getProperty().equals(foodCategory.getFoodCategoryName()));

            verify(sessionContext, times(1)).setRollbackOnly();
        }

        @Test
        @Override
        public void testRemoveIllegalArgumentException() {
            final var foodCategory = new FoodCategory("Removed Entity");
            foodCategory.setFoodCategoryId(null);
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.deleteFoodCategory(foodCategory.getFoodCategoryId())
                            ));
        }

        @Test
        @Override
        public void testRemoveEntityNotFoundException() {
            final var foodCategory = new FoodCategory("Removed Entity");
            foodCategory.setFoodCategoryId(15);
            assertThatExceptionOfType(EntityNotDeletedException.class)
                    .isThrownBy(
                            () -> commandExecutor.executeTransactionalCommand(
                                    () -> repository.deleteFoodCategory(foodCategory.getFoodCategoryId())
                            )
                    )
                    .withMessage("Food Category might have already been deleted")
                    .matches(e -> e.getProperty().equals(foodCategory.getFoodCategoryId().toString()));

            verify(sessionContext, times(1)).setRollbackOnly();
        }

    }

}
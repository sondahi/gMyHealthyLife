package com.comert.mhl.database;

import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.comert.mhl.database.common.util.FoodCategoryData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnJre(JRE.JAVA_11)
@ExtendWith(ArquillianExtension.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class DataBaseITest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "database-int-test.war")
                .addPackages(true, "com.comert.mhl.database")
                .addAsWebInfResource("META-INF/beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/test-mysql-ds.xml")
                .addAsLibraries(
                        Maven.resolver()
                                .loadPomFromFile("pom.xml")
                                .resolve("org.assertj:assertj-core"
                                        , "org.apache.commons:commons-lang3")
                                .withTransitivity()
                                .asFile()
                );
    }

    private Client client;
    private WebTarget target;

    @BeforeEach
    public void setUpScenario() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/database-int-test/testresource");
    }

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    public class FoodCategoryITest {

        @BeforeEach
        public void setUpScenario() {
            client = ClientBuilder.newClient();
            target = target.path("foodcategory");
        }

        @Order(value = 1)
        @RunAsClient
        @Test
        public void testSaveEntities() {
            final var foodCategory1 = foodCategory1();
            final var foodCategory2 = foodCategory2();
            final var foodCategoryWithChildEntities = foodCategoryWithChildEntities();

            Response response1 = target.path("/save")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(foodCategory1, MediaType.APPLICATION_JSON));

            assertThat(response1)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    )
                    .matches(r -> r.readEntity(Integer.class) == 1);

            Response response2 = target.path("/save")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(foodCategory2, MediaType.APPLICATION_JSON));

            assertThat(response2)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    )
                    .matches(r -> r.readEntity(Integer.class) == 2);

            Response response3 = target.path("/save")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(foodCategoryWithChildEntities, MediaType.APPLICATION_JSON));

            assertThat(response3)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    )
                    .matches(r -> r.readEntity(Integer.class) == 3);

        }

        @Order(value = 2)
        @RunAsClient
        @Test
        public void testFindEntity() {
            Response response = target.path("/find")
                    .queryParam("foodcategoryid", 1)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            final var foodCategory = response.readEntity(FoodCategory.class);

            assertThat(response)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    );

            assertThat(foodCategory)
                    .matches(fC -> fC.getFoodCategoryName().equals(foodCategory1().getFoodCategoryName()));
        }

        @Order(value = 3)
        @RunAsClient
        @Test
        public void testUpdateEntity() {
            final var foodCategory = new FoodCategory("Updated Food Category");
            foodCategory.setFoodCategoryId(1);

            Response response1 = target.path("/update")
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(foodCategory, MediaType.APPLICATION_JSON));

            assertThat(response1)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    );

            Response response2 = target.path("/find")
                    .queryParam("foodcategoryid", 1)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            final var updatedFoodCategory = response2.readEntity(FoodCategory.class);

            assertThat(response2)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    );

            assertThat(updatedFoodCategory)
                    .matches(fC -> fC.getFoodCategoryName().equals("Updated Food Category"));

        }

        // Burda kaldık

        @Disabled
        @Order(value = 4)
        @RunAsClient
        @Test
        public void testListEntities() {
            Response response1 = target.path("/listfoodcategories")
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            Set<FoodCategory> foundFoodCategorySet1 = response1.readEntity(HashSet.class);

            assertThat(response1)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    );

            foundFoodCategorySet1.iterator().forEachRemaining(System.out::println);

           assertTrue(foundFoodCategorySet1.contains(foodCategoryWithIdAndName(1,"Updated Food Category")));

            Response response2 = target.path("/listfoodcategories")
                    .queryParam("firstresult", 0)
                    .queryParam("maxresult", 2)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            assertThat(response2)
                    .matches(
                            r -> r.getStatus() == Response.Status.OK.getStatusCode()
                    )
                    .matches(
                            r -> r.readEntity(Set.class)
                                    .size() == 2
                    );

        }

        @Disabled
        @Order(value = 5)
        @RunAsClient
        @Test
        public void testChildEntities() {
            Response response = target.path("/listfoodcategorychildentities")
                    .queryParam("foodcategoryid", 3)
                    .request(MediaType.APPLICATION_JSON)
                    .get();


            assertThat(response)
                    .matches(
                            r -> r.readEntity(Set.class).size() == 2
                    );
        }

        @Order(value = 6)
        @RunAsClient
        @Test
        public void testDeleteEntity() {

            Response response1 = target.path("/delete")
                    .queryParam("foodcategoryid", 1)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            assertThat(response1)
                    .matches(
                            r -> r.getStatus() == 200
                    );

            Response response2 = target.path("/delete")
                    .queryParam("foodcategoryid", 2)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            assertThat(response2)
                    .matches(
                            r -> r.getStatus() == 200
                    );

            Response response3 = target.path("/delete")
                    .queryParam("foodcategoryid", 3)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            assertThat(response3)
                    .matches(
                            r -> r.getStatus() == 200
                    );

        }

    }

}

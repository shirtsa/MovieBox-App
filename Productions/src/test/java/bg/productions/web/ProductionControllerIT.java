package bg.productions.web;

import bg.productions.ProductionsApplication;
import bg.productions.model.entities.Production;
import bg.productions.model.enums.Genre;
import bg.productions.model.enums.ProductionType;
import bg.productions.repository.ProductionRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductionsApplication.class)
@AutoConfigureMockMvc
public class ProductionControllerIT {

    //Define SpringBootTest which builds the whole application context including the controller (ProductionController)
    //Config the mechanism of SpringTest which is MockMvc

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private MockMvc mockMvc; // can send real HTTP requests through his own API

    @Test
    public void testGetById() throws Exception {
        //Arrange
        var actualEntity = createTestProduction();

        // ACT
        mockMvc.perform(get("/productions/{id}", actualEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.rentPrice", is(actualEntity.getRentPrice())))
                .andExpect(jsonPath("$.length", is(actualEntity.getLength())))
                .andExpect(jsonPath("$.rating", is(actualEntity.getRating())))
                .andExpect(jsonPath("$.year", is(actualEntity.getYear())))
                .andExpect(jsonPath("$.imageUrl", is(actualEntity.getImageUrl())))
                .andExpect(jsonPath("$.videoUrl", is(actualEntity.getVideoUrl())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())))
                .andExpect(jsonPath("$.genre").value(equalTo("HORROR")))
                .andExpect(jsonPath("$.productionType").value(equalTo("MOVIE")));
    }

    @Test
    public void testProductionNotFound() throws Exception {
        mockMvc.perform(get("/productions/{id}", "9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateProduction() throws Exception {

        String uniqueName = "Alien: Romulus " + System.currentTimeMillis();

        MvcResult result = mockMvc.perform(post("/productions")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "%s",
                                    "imageUrl": "https://wiziwiz.com/wp-content/uploads/2024/06/wiziwiz-alien-romulus-poster-.jpg",
                                    "videoUrl": "x0XDEhP4MQs",
                                    "year": 2024,
                                    "length": 132,
                                    "rating": 5,
                                    "rentPrice": 10,
                                    "description": "While scavenging the deep ends of a derelict space station, a group of young space colonizers come face to face with the most terrifying life form in the universe.",
                                    "productionType": "MOVIE",
                                    "genre": "HORROR"
                                }
                                """.formatted(uniqueName))
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();

        int id = JsonPath.read(body, "$.id");

        Optional<Production> createdProductionOpt = productionRepository.findById((long) id);

        Assertions.assertTrue(createdProductionOpt.isPresent());

        Production production = createdProductionOpt.get();

        Assertions.assertEquals(uniqueName, production.getName());
        Assertions.assertEquals("https://wiziwiz.com/wp-content/uploads/2024/06/wiziwiz-alien-romulus-poster-.jpg", production.getImageUrl());
        Assertions.assertEquals("x0XDEhP4MQs", production.getVideoUrl());
        Assertions.assertEquals(2024, production.getYear());
        Assertions.assertEquals(132, production.getLength());
        Assertions.assertEquals(5, production.getRating());
        Assertions.assertEquals(10, production.getRentPrice());
        Assertions.assertEquals("While scavenging the deep ends of a derelict space station, a group of young space colonizers come face to face with the most terrifying life form in the universe.", createdProductionOpt.get().getDescription());
        Assertions.assertEquals(Genre.HORROR, production.getGenre());
        Assertions.assertEquals(ProductionType.MOVIE, production.getProductionType());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteProduction() throws Exception {
        var actualEntity = createTestProduction();

        mockMvc.perform(delete("/productions/{id}", actualEntity.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        Assertions.assertTrue(productionRepository.findById(actualEntity.getId()).isEmpty());
    }

    private Production createTestProduction() {
       return productionRepository.save(
                new Production()
                        .setName("Alien")
                        .setRentPrice(10)
                        .setLength(132)
                        .setRating(5)
                        .setYear(2024)
                        .setImageUrl("https://wiziwiz.com/wp-content/uploads/2024/06/wiziwiz-alien-romulus-poster-.jpg")
                        .setVideoUrl("x0XDEhP4MQs")
                        .setDescription("While scavenging the deep ends of a derelict space station.")
                        .setGenre(Genre.HORROR)
                        .setProductionType(ProductionType.MOVIE));
    }
}

package ir.sk.stock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import ir.sk.stock.DatabaseContainerConfiguration
import ir.sk.stock.model.Stock
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StockControllerTest : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val baseUrl = "/api/v1/stock"
    private val nonExistingStockId = 23424221L

    private val stock1 = Stock(1L, "BNB", BigDecimal("1200.4"))
    private val stock2 = Stock(2L, "CTC", BigDecimal("45.14"))
    private val stock3 = Stock(3L, "KTR", BigDecimal("123.09"))

    @Test
    @Order(1)
    fun testListStocks() {
        mockMvc.perform(
            get(baseUrl)
                .param("page", "0")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(stock1.id))
            .andExpect(jsonPath("$[0].name").value(stock1.name))
            .andExpect(jsonPath("$[0].currentPrice").value(stock1.currentPrice))
            .andExpect(jsonPath("$[1].id").value(stock2.id))
            .andExpect(jsonPath("$[1].name").value(stock2.name))
            .andExpect(jsonPath("$[1].currentPrice").value(stock2.currentPrice))
            .andExpect(jsonPath("$[2].id").value(stock3.id))
            .andExpect(jsonPath("$[2].name").value(stock3.name))
            .andExpect(jsonPath("$[2].currentPrice").value(stock3.currentPrice))
    }

    @Test
    @Order(2)
    fun testGetStock() {
        mockMvc.perform(
            get("$baseUrl/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stock1.id))
            .andExpect(jsonPath("$.name").value(stock1.name))
            .andExpect(jsonPath("$.currentPrice").value(stock1.currentPrice))
    }

    @Test
    @Order(4)
    fun testCreateStock() {
        val newStock = Stock(null, "New Stock", BigDecimal("453.98"))
        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStock))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(4L))
            .andExpect(jsonPath("$.name").value(newStock.name))
            .andExpect(jsonPath("$.currentPrice").value(newStock.currentPrice))
    }

    @Test
    @Order(3)
    fun testUpdateStockPrice() {
        val newPrice = BigDecimal("34235.3")
        mockMvc.perform(
            patch("$baseUrl/2")
                .param("newPrice", newPrice.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(jsonPath("$.id").value(stock2.id))
            .andExpect(jsonPath("$.name").value(stock2.name))
            .andExpect(jsonPath("$.currentPrice").value(newPrice))
    }

    @Test
    @Order(5)
    fun testDeleteStock() {
        mockMvc.perform(
            delete("$baseUrl/1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }

    @Test
    @Order(6)
    fun testGetStockNotFound() {
        mockMvc.perform(
            get("$baseUrl/$nonExistingStockId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(404))
            .andExpect(jsonPath("$.statusMessage").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Stock not found with id: $nonExistingStockId"))
    }

    @Test
    @Order(7)
    fun testUpdateStockPriceNotFound() {
        mockMvc.perform(
            patch("$baseUrl/$nonExistingStockId")
                .accept(MediaType.APPLICATION_JSON)
                .param("newPrice", "454.5")
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(404))
            .andExpect(jsonPath("$.statusMessage").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Stock not found with id: $nonExistingStockId"))
    }

    @Test
    @Order(8)
    fun testGetStockBadRequest() {
        mockMvc.perform(
            get("$baseUrl/wrongParamType")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.statusMessage").value("Bad Request"))
            .andExpect(jsonPath("$.message").value("id should be of type long"))
    }

    @Test
    @Order(9)
    fun testUpdateStockPriceNewPriceBadRequest() {
        mockMvc.perform(
            patch("$baseUrl/1")
                .accept(MediaType.APPLICATION_JSON)
                .param("newPrice", "wrongParamType")
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.statusMessage").value("Bad Request"))
            .andExpect(jsonPath("$.message").value("newPrice should be of type java.math.BigDecimal"))
    }

    @Test
    @Order(10)
    fun testUpdateStockPriceNewPriceMissingParam() {
        mockMvc.perform(
            patch("$baseUrl/1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.statusMessage").value("Bad Request"))
            .andExpect(jsonPath("$.message").value("Required request param 'newPrice' is missing"))
    }

    @Test
    @Order(11)
    fun testCreateStockMethodArgumentInvalid() {
        val newStock = Stock(null, "New Stock", BigDecimal("-2"))
        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStock))
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.statusMessage").value("Bad Request"))
            .andExpect(jsonPath("$.message").value("Stock price could not be less than 0"))
    }

    @Test
    @Order(12)
    fun testUpdateStockNegativePriceParam() {
        mockMvc.perform(
            patch("$baseUrl/1")
                .accept(MediaType.APPLICATION_JSON)
                .param("newPrice", "-3")
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.statusMessage").value("Bad Request"))
            .andExpect(jsonPath("$.message").value("Stock price could not be less than 0"))
    }
}

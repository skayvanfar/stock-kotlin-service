package ir.sk.stock.controller

import ir.sk.stock.exception.StockNotFoundException
import ir.sk.stock.model.Stock
import ir.sk.stock.repository.StockRepository
import ir.sk.stock.service.StockService
import ir.sk.stock.service.StockServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class StockServiceTest {

    @Mock
    private lateinit var stockRepository: StockRepository

    private lateinit var stockService: StockService

    private lateinit var sampleStockList: List<Stock>
    private lateinit var sampleStock: Stock

    @BeforeEach
    fun setUp() {
        sampleStockList = listOf(
            Stock(1L, "TRR", BigDecimal("3435.343")),
            Stock(2L, "SVF", BigDecimal("23.55"))
        )
        sampleStock = Stock(1L, "Stock A", BigDecimal("3435.343"))
        stockService = StockServiceImpl(stockRepository)
    }

    @Test
    fun `listStocks should return a list of stocks`() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(sampleStockList)
        Mockito.`when`(stockRepository.findAll(pageable)).thenReturn(page)

        val result = stockService.listStocks(0, 10)

        Assertions.assertEquals(sampleStockList, result)
    }

    @Test
    fun `getStock should return a stock when it exists`() {
        val stockId = 1L

        Mockito.`when`(stockRepository.findById(stockId)).thenReturn(Optional.of(sampleStock))
        val result = stockService.getStock(stockId)

        Assertions.assertEquals(sampleStock, result)
    }

    @Test
    fun `getStock should throw StockNotFoundException when stock doesn't exist`() {
        val stockId = 98989L

        Mockito.`when`(stockRepository.findById(stockId)).thenReturn(Optional.empty())

        Assertions.assertThrows(StockNotFoundException::class.java) {
            stockService.getStock(stockId)
        }
    }

    @Test
    fun `createStock should return the created stock`() {
        Mockito.`when`(stockRepository.save(sampleStock)).thenReturn(sampleStock)

        val result = stockService.createStock(sampleStock)

        Assertions.assertEquals(sampleStock, result)
    }

    @Test
    fun `updateStockPrice should return the updated stock with the new price`() {
        val stockId = 1L
        val newPrice = BigDecimal("234.05")
        val updatedStock = sampleStock.copy(currentPrice = newPrice)

        Mockito.`when`(stockRepository.findById(stockId)).thenReturn(Optional.of(sampleStock))
        Mockito.`when`(stockRepository.save(updatedStock)).thenReturn(updatedStock)

        val result = stockService.updateStockPrice(stockId, newPrice)

        Assertions.assertEquals(newPrice, result.currentPrice)
    }

    @Test
    fun `deleteStock should call deleteById`() {
        val stockId = 1L

        stockService.deleteStock(stockId)
        Mockito.verify(stockRepository).deleteById(stockId)
    }
}

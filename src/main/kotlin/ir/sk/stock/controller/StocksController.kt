package ir.sk.stock.controller

import ir.sk.stock.model.Stock
import ir.sk.stock.service.StockService
import jakarta.validation.Valid
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/stocks")
@Validated
class StocksController(private val stockService: StockService) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listStocks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "\${stock-service.page-size.default}") size: Int
    ): List<Stock> = stockService.listStocks(page, size)

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getStock(@PathVariable id: Long): Stock = stockService.getStock(id)

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun createStock(@RequestBody @Valid stock: Stock): Stock = stockService.createStock(stock)

    @PatchMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateStockPrice(
        @PathVariable id: Long,
        @RequestParam @PositiveOrZero(message = "Stock price could not be less than 0") newPrice: BigDecimal
    ): Stock = stockService.updateStockPrice(id, newPrice)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStock(@PathVariable id: Long) = stockService.deleteStock(id)
}

package ir.sk.stock.service

import ir.sk.stock.model.Stock
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * Created by sad.kayvanfar on 2/2/2022
 */
@Service
interface StockService {
    fun listStocks(page: Int, size: Int): List<Stock>
    fun getStock(id: Long): Stock
    fun createStock(stock: Stock): Stock
    fun updateStockPrice(id: Long, newPrice: BigDecimal): Stock
    fun deleteStock(id: Long)
}

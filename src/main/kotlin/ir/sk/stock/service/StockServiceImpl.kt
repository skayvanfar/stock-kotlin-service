package ir.sk.stock.service

import ir.sk.stock.exception.StockNotFoundException
import ir.sk.stock.model.Stock
import ir.sk.stock.repository.StockRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class StockServiceImpl(private val stockRepository: StockRepository) : StockService {
    override fun listStocks(page: Int, size: Int): List<Stock> =
        stockRepository.findAll(PageRequest.of(page, size)).content

    override fun getStock(id: Long): Stock =
        stockRepository.findById(id).orElseThrow { StockNotFoundException(id) }

    override fun createStock(stock: Stock): Stock =
        stockRepository.save(stock)

    override fun updateStockPrice(id: Long, newPrice: BigDecimal): Stock {
        val stock = getStock(id)
        return stock.copy(currentPrice = newPrice).also { stockRepository.save(it) }
    }

    override fun deleteStock(id: Long) =
        stockRepository.deleteById(id)
}

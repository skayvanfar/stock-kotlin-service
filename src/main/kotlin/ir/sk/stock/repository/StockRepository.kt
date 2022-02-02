package ir.sk.stock.repository

import ir.sk.stock.model.Stock
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Created by sad.kayvanfar on 2/2/2022
 */
@Repository
interface StockRepository : PagingAndSortingRepository<Stock?, Long?>
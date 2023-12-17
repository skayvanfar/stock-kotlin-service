package ir.sk.stock.repository

import ir.sk.stock.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by sad.kayvanfar on 2/2/2022
 */
@Repository
interface StockRepository : JpaRepository<Stock, Long>

package ir.sk.stock.model

import java.math.BigDecimal
import java.time.Instant

data class Stock (
     val id: Long,
     val name: String,
     val currentPrice: BigDecimal,
     val lastUpdate: Instant
     )
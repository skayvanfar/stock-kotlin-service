package ir.sk.stock.model

import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "TBL_STOCK")
data class Stock(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        val currentPrice: BigDecimal,
        val lastUpdate: Instant
)
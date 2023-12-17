package ir.sk.stock.model

import java.math.BigDecimal
import java.time.Instant
import jakarta.persistence.*
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp

@Entity
@Table(name = "TBL_STOCK")
data class Stock(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val name: String,

        @field:PositiveOrZero(message = "Stock price could not be less than 0")
        @Column(nullable = false)
        val currentPrice: BigDecimal,

        @field:UpdateTimestamp(source = SourceType.DB)
        val lastUpdate: Timestamp? = null,

        @field:CreationTimestamp(source = SourceType.DB)
        val createDate: Timestamp? = null
)

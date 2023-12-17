package ir.sk.stock.exception

class ApiErrorMessage(    val statusCode: Int,
                          val statusMessage: String? = null,
                          val message: String? = null)

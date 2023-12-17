package ir.sk.stock.exception

class StockNotFoundException(id: Long) : RuntimeException("Stock not found with id: $id")

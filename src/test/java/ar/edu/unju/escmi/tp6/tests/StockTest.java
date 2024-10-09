package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;

class StockTest {

	@BeforeEach
	void setUp() throws Exception {
		CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
	}

	@Test
	void test() {
		CollectionStock.reducirStock(CollectionStock.stocks.get(0), 10);
		assertEquals(CollectionStock.stocks.get(0).getCantidad(), 2);
	}
}

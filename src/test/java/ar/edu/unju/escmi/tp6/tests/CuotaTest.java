package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;


	class CuotaTest {
		Credito expected;
		@BeforeEach
		void setUp() throws Exception {
			Producto producto = new Producto(1,"test","test",100,"test");
			Detalle detalle = new Detalle(5,0,producto);
			Cliente cliente = new Cliente(1,"test","test","test");
			List<Detalle> detalles = new ArrayList<Detalle>();
			detalles.add(detalle);
			Factura factura = new Factura(LocalDate.now(),1,cliente,detalles);
			TarjetaCredito tarjeta = new TarjetaCredito(1,LocalDate.now().plusYears(1),cliente,1000);
			List<Cuota> cuotas = new ArrayList<Cuota>();
			expected = new Credito(tarjeta,factura,cuotas);
		}
		
		@AfterEach
		void tearDown() throws Exception{
			expected=null;
		}

		@Test
		void testCuotasNull() {
			assertNotNull(expected.getCuotas());
		}
		
		@Test
		void testTreintaCuotas() {
			assertEquals(expected.getCuotas().size(),30);
		}
		
		@Test
		void testNoMoreThanTreintaCuotas() {
			assertTrue(expected.getCuotas().size()<31);
		}
}

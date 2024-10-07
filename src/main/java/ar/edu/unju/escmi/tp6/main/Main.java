package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	static long contNroFactura= 1;
	
	public static void main(String[] args) {
		
        CollectionCliente.precargarClientes();
		CollectionTarjetaCredito.precargarTarjetas();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        int opcion = 0;
        do {
        	System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta");
            System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
            System.out.println("3- Mostrar lista de los electrodomésticos");
            System.out.println("4- Consultar stock");
            System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
            System.out.println("6- Salir");

            System.out.println("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            switch (opcion) {
            case 1: System.out.println("--- VENTA DE PRODUCTO ---");
            	comprarProductos();

            	break;
            case 2:
            	
            	break;
            	
            default: System.out.println("Esta no es una opcion."); break;
            }
        }while(opcion != 6);
        scanner.close();

	}
	
	public static Detalle comprarProducto(double limite) {
    	
    	System.out.println("Ingrese el codigo del electrodomestico que quiere comprar: ");
    	long codP = scanner.nextLong();
    	Producto producto = CollectionProducto.buscarProducto(codP);
    	while (producto == null) {
    		System.out.println("-	!No existe un producto con este codigo¡		-");
    		System.out.println("Ingrese otro codigo: ");
    		codP = scanner.nextLong();
    		producto = CollectionProducto.buscarProducto(codP);
    	}
    	
    	
    	Stock stock = CollectionStock.buscarStock(producto);
    	if (stock.getCantidad()==0) {
    		System.out.println("-	!No hay stock de este producto¡		-");
    		return null;
    	}
    	
    	
    	producto.toString();
    	System.out.println("\nStock del producto: "+stock.getCantidad());
    	System.out.println("¿Cuantas unidades desea comprar? : ");
    	
    	int cantidad=scanner.nextInt();
    	while (cantidad<0 || (stock.getCantidad() - cantidad <0) ) {
    		System.out.println("¡ERROR!\n Recuerde ingresars una cantidad que sea mayor igual a 0, menor igual al stock del producto: ");
    		cantidad=scanner.nextInt();
    	}
    	
    	Detalle detalle = new Detalle(cantidad, 0, producto);
    	
    	return detalle;
	}
	
	public static void comprarProductos() {
    	//Ingreso de datos del cliente
    	System.out.println("Ingrese su dni: ");
    	long dni = scanner.nextLong();
    	Cliente cliente = CollectionCliente.buscarCliente(dni);
    	while (cliente == null) {
    		System.out.println("-	!No existe un cliente con este dni¡		-");
    		System.out.println("Ingrese otro dni:");
    		dni = scanner.nextLong();
    		cliente = CollectionCliente.buscarCliente(dni);
    	}
    	
    	
    	System.out.println("Ingrese su numero de tarjeta de credito: ");
    	long numero= scanner.nextLong();
    	TarjetaCredito tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(numero);
    	while (tarjeta == null || tarjeta.getCliente() == null || tarjeta.getCliente().getDni() != dni) {
    	    if (tarjeta == null) {
    	        System.out.println("- ¡Esta tarjeta de crédito no existe! -");
    	    } else if (tarjeta.getCliente() == null) {
    	        System.out.println("- ¡Esta tarjeta de crédito no está asociada a ningún cliente! -");
    	    } else if (tarjeta.getCliente().getDni() != dni) {
    	        System.out.println("- ¡Esta tarjeta de crédito no está a su nombre! -");
    	    }
    	    System.out.println("Ingrese un número de tarjeta que sea de " + cliente.getNombre() + ": ");
    	    numero = scanner.nextLong();
    	    tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(numero);
    	}
    	//
    	/*
    	 * 45111222
    	 * 232323
    	 * 1111
    	 */
    	//Compra de productos
        List<Detalle> detalles = new ArrayList<Detalle>();
    	double limite = tarjeta.getLimiteCompra();
    	Detalle detalle1= comprarProducto(limite);
    	if (detalle1.getImporte() > limite) {
    		System.out.println("---    !El precio de la compra es superior al limite de la tarjeta¡	   ---");
    		System.out.println("Su compra excede por"+(detalle1.getImporte() - limite)+"el limite de la tarjeta, no se cargara el ultimo producto seleccionado");
    	} else if (detalle1!=null) {
    		limite -= detalle1.getImporte();
    		detalles.add(detalle1);
    	}
    	
    	do {
    		int seguirCompra = 0; 

    		while (true) {
    		    try {
    		        System.out.println("¿Desea seguir con la compra?");
    		        System.out.println("1. SI");
    		        System.out.println("2. NO");

    		        seguirCompra = scanner.nextInt();

    		        if (seguirCompra == 1 || seguirCompra == 2) {
    		        	break;
    		        } else {
    		            System.out.println("Ingrese 1 para SI o 2 para NO.");
    		        }

    		    } catch (InputMismatchException e) {
    		        System.out.println("Ingrese un número (1 para SI, 2 para NO).");
    		        scanner.next();
    		    }
    		}
    		
    		if (seguirCompra==2) break;
    		
        	Detalle detalle2= comprarProducto(limite);
        	if (detalle2.getImporte() > limite) {
        		System.out.println("!El precio de la compra es superior al limite de la tarjeta¡");
        	} else if (detalle2!=null) {
        		limite -= detalle2.getImporte();
        		detalles.add(detalle2);
        	}
        	
    	} while (true);
    	
    	Factura factura = new Factura(LocalDate.now(), contNroFactura++, cliente, detalles);
    	CollectionFactura.agregarFactura(factura);
    	
    	Credito credito= new Credito(tarjeta, factura, new ArrayList<Cuota>());
    	CollectionCredito.agregarCredito(credito);
    	System.out.println("Se realizo la compra correctamente");
    	System.out.println(factura.toString());
	}
	
}
;
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
	static LocalDate finDePromocion = LocalDate.of(2024, 12, 22);
	
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
	            if (LocalDate.now().isAfter(finDePromocion)) {
	            	System.out.println("!LA PROMOCION 'AHORA 30' ACABO EL 22/12/24, EL SISTEMA DE COMPRA YA NO ESTA DISPONIBLE, GRACIAS POR SU VISITA");
	            	
	            } else
	            	ventaProducto();

            	break;
            case 2: System.out.println("----- Compras del Cliente ----");
            	comprasDeCliente();
            	break;
            
            case 3: 
            	if (LocalDate.now().isAfter(finDePromocion))
            		System.out.println("!LA PROMOCION 'AHORA 30' ACABO EL 22/12/24, EL SISTEMA DE COMPRA YA NO ESTA DISPONIBLE, GRACIAS POR SU VISITA");
	            else
	            	CollectionProducto.mostrarProductosDisponiblesEnAhora30();
            	
            	break;
            case 4:
            	if (LocalDate.now().isAfter(finDePromocion))
            		System.out.println("!LA PROMOCION 'AHORA 30' ACABO EL 22/12/24, EL SISTEMA DE COMPRA YA NO ESTA DISPONIBLE, GRACIAS POR SU VISITA");
	            else
	            	CollectionProducto.mostrarStockProductosEnAhora30();
            	break;
            case 5:
            	revisarCreditoCliente();
            	break;
            default: System.out.println("Esta no es una opcion."); break;
            }
        }while(opcion != 6);
        scanner.close();

	}
	
	public static Detalle comprarProducto(double limite) {
    	
        long codP = -1;
        Producto producto = null;

        while (producto == null) {
            System.out.println("Ingrese el código del electrodoméstico que quiere comprar: ");
            try {
                codP = scanner.nextLong();
                producto = CollectionProducto.buscarProducto(codP);
                if (producto == null) System.out.println("- 	¡No existe un producto con este código!	  -\nIngrese otro código: ");
                
            } catch (InputMismatchException e) {
                System.out.println("- 	¡El código debe ser un número!	 -");
                scanner.next();
            }
        }
    	
    	if (limite>(double)1500000)limite=1500000;
    	if(producto.getTipoProducto().equals("Celular") && limite>(double)800000) limite=800000;
    	
    	Stock stock = CollectionStock.buscarStock(producto);
    	if (stock.getCantidad()==0) {
    		System.out.println("-	!No hay stock de este producto¡		-");
    		return null;
    	}
    	
    	
    	System.out.println(producto.toString());
    	System.out.println("\nStock del producto: "+stock.getCantidad());
    	System.out.println("¿Cuantas unidades desea comprar? : ");
    	
    	int cantidad=-1;
    	do {
            try {
                cantidad=scanner.nextInt();
                if (cantidad<0 || (stock.getCantidad() - cantidad <0))
        			System.out.println("¡ERROR!\n Recuerde ingresar una cantidad que sea mayor igual a 0, menor igual al stock del producto: ");
        		else if ((cantidad * producto.getPrecioUnitario()) > limite) {
        			System.out.println("¡ERROR!\n El precio total de esta compra es superior al limite de su tarjeta, su limite es '"+limite+"', elija una cantidad menor de productos: ");
        			System.out.println("El precio unitario del producto es: "+producto.getPrecioUnitario());
        		}
            } catch (InputMismatchException e) {
                System.out.println("- 	¡La cantidad debe ser un número!	 -");
                scanner.next();
            }
    	} while (cantidad<0 || (stock.getCantidad() - cantidad <0) || (cantidad * producto.getPrecioUnitario()) > limite);
    	
    	Detalle detalle = new Detalle(cantidad, 0, producto);
    	
    	return detalle;
	}
	
	public static void ventaProducto() {
		
		//Entrada de datos del Cliente
        long dni = -1;  
        Cliente cliente = null;

        while (cliente == null) {
            try {
                System.out.println("Ingrese su dni: ");
                dni = scanner.nextLong(); 

                cliente = CollectionCliente.buscarCliente(dni);
                if (cliente == null) {
                    System.out.println("- ¡No existe un cliente con este dni! -");
                }
            } catch (InputMismatchException e) {
                System.out.println("¡ERROR! Ingrese un número válido para el DNI.");
                scanner.next(); 
            }
        }

        long numero = -1;  
        TarjetaCredito tarjeta = null;

        while (tarjeta == null || tarjeta.getCliente() == null || tarjeta.getCliente().getDni() != dni) {
            try {
                System.out.println("Ingrese su número de tarjeta de crédito: ");
                numero = scanner.nextLong();

                tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(numero);
                if (tarjeta == null) {
                    System.out.println("- ¡Esta tarjeta de crédito no existe! -");
                } else if (tarjeta.getCliente() == null) {
                    System.out.println("- ¡Esta tarjeta de crédito no está asociada a ningún cliente! -");
                } else if (tarjeta.getCliente().getDni() != dni) {
                    System.out.println("- ¡Esta tarjeta de crédito no está a su nombre! -");
                }
            } catch (InputMismatchException e) {
                System.out.println("¡ERROR! Ingrese un número válido para el número de tarjeta.");
                scanner.next(); 
            }
        }

    	//Compra de producto
        List<Detalle> detalles = new ArrayList<Detalle>();
    	double limite = tarjeta.getLimiteCompra();
    	Detalle detalle1= comprarProducto(limite);
    	detalles.add(detalle1);
    	/*
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
    	*/
    	Factura factura = new Factura(LocalDate.now(), contNroFactura++, cliente, detalles);
    	CollectionFactura.agregarFactura(factura);
    	
    	Credito credito= new Credito(tarjeta, factura, new ArrayList<Cuota>());
    	CollectionCredito.agregarCredito(credito);
    	
    	System.out.println("Se realizo la compra correctamente");
    	System.out.println(factura.toString());
	}
	
	
	public static long dniCliente() {
		long dni = -1;  

        while (true) {
            try {
                System.out.println("Ingrese su dni: ");
                dni = scanner.nextLong(); 


                if (CollectionCliente.buscarCliente(dni) == null) {
                    System.out.println("- ¡No existe un cliente con este dni! -");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("¡ERROR! Ingrese un número válido para el DNI.");
                scanner.next(); 
            }
        }
        return dni;
	}
	
	public static void comprasDeCliente() {
        
		long dni = dniCliente();
		
    	System.out.println("Las compras de este cliente son las siguientes: ");
    	boolean compras=false;
		for (Factura factura : CollectionFactura.facturas) {
			if (factura.getCliente().getDni() == dni) {
				compras=true;
				for (Detalle detalle : factura.getDetalles()) {
					System.out.println(detalle.toString());
				}
			}
		}
		
		if (!compras) System.out.println("------------------------\n 		ESTE CLIENTE NO TIENE COMPRAS\n------------------------");
	}
	
	public static void revisarCreditoCliente() {
		  long dni = dniCliente();
		  CollectionCredito.consultarCredito(dni);
	}
}

package ar.edu.unju.escmi.tp6.dominio;

public class Stock {
	 private int cantidad;
	 private Producto producto;

	 public Stock() {
     }

     public Stock(int cantidad, Producto producto) {
         this.cantidad = cantidad;
         this.producto = producto;
     }
 
     public int getCantidad() {
         return cantidad;
     }

     public void setCantidad(int cantidad) {
         this.cantidad = cantidad;
     }

     public Producto getProducto() {
         return producto;
     }

     public void setProducto(Producto producto) {
         this.producto = producto;
     }

	@Override
	public String toString() {
		return "\nCódigo: " + producto.getCodigo()
				+ "\nTipo de Prodcuto: " + producto.getTipoProducto()
				+ "\nDescripcion: " + producto.getDescripcion()
				+ "\nStock" + (cantidad > 0 ? cantidad : "SIN STOCK")
				+ "\n------------------------";
	}

	
     
     
}

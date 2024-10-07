package ar.edu.unju.escmi.tp6.dominio;

public class Producto {

	private long codigo;
	private String tipoProducto;
    private String descripcion;
    private double precioUnitario;
    private String origenFabricacion;

    public Producto() {

    }

    public Producto(long codigo,String tipoProducto, String descripcion, double precioUnitario, String origenFabricacion) {
        this.codigo = codigo;
        this.tipoProducto = tipoProducto;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.origenFabricacion = origenFabricacion;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    
    public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getOrigenFabricacion() {
        return origenFabricacion;
    }

    public void setOrigenFabricacion(String origenFabricacion) {
        this.origenFabricacion = origenFabricacion;
    }

    @Override
    public String toString() {
        return "\nCodigo: " + codigo 
        		+ "\nTipo de Producto " + tipoProducto
        		+ "\nDescripcion: " + descripcion 
        		+ "\nPrecio Unitario: " + precioUnitario
                + "\nOrigen fabricacion: " + origenFabricacion;
    }
}

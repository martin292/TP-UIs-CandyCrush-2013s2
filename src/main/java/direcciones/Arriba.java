package direcciones;

import java.awt.Point;

public class Arriba extends Direccion {

	public boolean puedeMover(Point point, Point dimension) {
		return point.y < dimension.y;
	}

	public Point puntoEnDireccion(Point point) {
		return new Point(point.x, point.y + 1);
	}

	@Override
	public Direccion opuesta() {
		return Direccion.ABAJO;
	}
	
	@Override
	public boolean esArriba() {
		return true;
	}
}

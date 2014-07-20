package direcciones;

import java.awt.Point;

public class Derecha extends Direccion {

	public boolean puedeMover(Point point, Point dimension) {
		return point.x < dimension.x;
	}

	public Point puntoEnDireccion(Point point) {
		return new Point(point.x + 1, point.y);
	}

	@Override
	public Direccion opuesta() {
		return Direccion.IZQUIERDA;
	}

}

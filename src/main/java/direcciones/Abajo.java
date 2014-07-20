package direcciones;

import java.awt.Point;

public class Abajo extends Direccion {

	public boolean puedeMover(Point point, Point dimension) {
		return point.y > 0;
	}

	public Point puntoEnDireccion(Point point) {
		return new Point(point.x, point.y - 1);
	}

	@Override
	public Direccion opuesta() {
		return Direccion.ARRIBA;
	}

}

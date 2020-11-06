package refactoring;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static refactoring.Rover.Heading.*;
import static refactoring.Rover.Order.*;

public class Rover {

	private static Mapa mapa;
	private  Heading heading;
	private Position position;

	private ViewPoint viewPoint;

	public Rover(String facing, int x, int y) {
		this(Heading.of(facing), x, y);
	}

	public Rover(Heading heading, int x, int y) {
		this(heading, new Position(x,y));
	}

	public Rover(Heading heading, Position position) {
		this.heading = heading;
		this.position = position;
	}

	/*public Rover(ViewPoint viewPoint){
		this.viewPoint = viewPoint;
	}*/

	// getViewPoint

	public void setMapa(Mapa mapa){
		this.mapa = mapa;
	}

	public static Mapa getMapa(){ return mapa; }

	public Heading heading(){
		return heading;
	}

	public Position position(){
		return position;
	}

	public void go(String instructions){
		go(ordersIn(instructions));
	}

	private Order[] ordersIn(String instructions) {
		Order[] orders = new Order[instructions.length()];
		for (int i = 0; i < instructions.length(); i++) {
			orders[i] = orderOf(instructions.charAt(i));
		}
		return orders;
	}

	private Order orderOf(char c) {
		return c == 'L' ? Left : c == 'F' ? Forward : c == 'B' ? Backward : c == 'R' ? Right : null;
	}

	public void go(Order... orders){
		 //for(Order order : orders) execute(order); es lo mismo que la de abajo, pero la de abajo es programación funcional
		stream(orders).filter(order -> order != null).forEach(this::execute);
	}

	private void execute(Order orders) {
		actions.get(orders).execute();
	}

	private void backward() {
		position = position.backward(heading);
	}

	private void forward() {
		position = position.forward(heading);
	}

	private Heading turnRight() {
		return heading = heading.turnRight();
	}

	private Heading turnLeft() {
		return heading = heading.turnLeft();
	}

	private static Map<Order, Action> actions = new HashMap<>();
	{
		actions.put(Left, this::turnLeft); // es lo mismo que () -> heading = heading.turnLeft();
		actions.put(Right, this::turnRight);
		actions.put(Forward, this::forward);
		actions.put(Backward, this::backward);
	}
/* con ViewPoint
	private static Map<Order, Action> actions = new HashMap<>();
	{
		actions.put(Left, () -> viewPoint = viewPoint.turnLeft()); // es lo mismo que () -> heading = heading.turnLeft();
		actions.put(Right, () -> viewPoint = viewPoint.turnRight());
		actions.put(Forward, () -> viewPoint = viewPoint.forward());
		actions.put(Backward, () -> viewPoint = viewPoint.backward());
	}
	*/
	@FunctionalInterface
	public interface Action{
		void execute();
	}

	public static class Position {
		private int x;
		private int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		private boolean sensor(Position pos){
			if (getMapa().getForwardPosition(pos) == -1){
				return false;
			}
			return true;
		}

		// Refactoring with Query
		public Position forward(Heading heading){
			Position pos = new Position(x + dx(heading), y + dy(heading));
			if(sensor(pos) == true){
				return pos;
			}
			System.out.println("Hay un obstáculo en la posición: " + pos + ", por tanto me quedo en la posición: " + new Position(x,y));
			return new Position(x,y);
		}

		public Position backward(Heading heading) {
			return new Position(x -= dx(heading), y -= dy(heading));
		}

		private int dx(Heading heading){
			return heading == East ? 1 : heading == West ? -1 : 0;
		}

		private int dy(Heading heading){
			return heading == North ? 1 : heading == South ? -1 : 0;
		}

		@Override
		public boolean equals(Object object) {
			return isSameClass(object) && equals((Position) object);
		}

		private boolean equals(Position position) {
			return position == this || (x == position.x && y == position.y);
		}

		private boolean isSameClass(Object object) {
			return object != null && object.getClass() == Position.class;
		}

		@Override
		public String toString() {
			return "Position{" + "x=" + x + ", y=" + y + '}';
		}
	}

	public enum Order {
		Forward, Backward, Left, Right
	}

	public enum Heading {
		North, East, South, West;

		public static Heading of(String label) {
			return of(label.charAt(0));
		}

		public static Heading of(char label) {
			return label =='N' ? North : label == 'S' ? South : label == 'W' ? West : label == 'E' ? East : null;
		}

		public Heading turnRight() {
			return values()[add(+1)];
		}

		public Heading turnLeft() {
			return values()[add(-1)];
		}

		private int add(int offset) {
			return (this.ordinal() + offset + values().length) % values().length;
		}
	}
}


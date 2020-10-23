package refactoring;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static refactoring.Rover.Heading.*;
import static refactoring.Rover.Order.*;

public class Rover {

	private  Heading heading;
	private Position position;

	public Rover(String facing, int x, int y) {
		this(Heading.of(facing), x, y);
	}

	public Rover(Heading heading, int x, int y) {
		this.heading = heading;
		position = new Position(x,y);
	}

	public Rover(Heading heading, Position position) {
		this.heading = heading;
		this.position = position;
	}

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

		// Refactoring with Query
		public Position forward(Heading heading){
			return new Position(x += dx(heading), y += dy(heading));
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


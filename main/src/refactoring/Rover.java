package refactoring;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static refactoring.Rover.Order.*;

public class Rover {

	private ViewPoint viewPoint;

	public Rover(ViewPoint viewPoint){
		this.viewPoint = viewPoint;
	}

	public ViewPoint getViewPoint() {
		return viewPoint;
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
		set(go(stream(orders)));
	}
	private void set(ViewPoint viewPoint){
		if(viewPoint == null) return;
		this.viewPoint = viewPoint;
	}
	private ViewPoint go(Stream<Order> orders){
		return orders.filter(Objects::nonNull).reduce(viewPoint, this::execute, (v1,v2)->null);
	}

	private ViewPoint execute(ViewPoint v, Order o){
		return v != null ? actions.get(o).execute(v) : null;
	}

// con ViewPoint
	private Map<Order, Action> actions = new HashMap<>();
	{
		actions.put(Left, ViewPoint::turnLeft); // es lo mismo que () -> heading = heading.turnLeft();
		actions.put(Right, ViewPoint::turnRight);
		actions.put(Forward, ViewPoint::forward);
		actions.put(Backward, ViewPoint::backward);
	}

	@FunctionalInterface
	public interface Action{
		ViewPoint execute(ViewPoint viewPoint);
	}

	public enum Order {
		Forward, Backward, Left, Right
	}
}


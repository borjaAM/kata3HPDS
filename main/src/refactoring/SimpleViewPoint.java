package refactoring;

import static refactoring.SimpleViewPoint.Heading.*;

public class SimpleViewPoint implements ViewPoint{
    private Heading heading;
    private Position position;

    public SimpleViewPoint(String facing, int x, int y){
        this(Heading.of(facing), x,y);
    }
    public SimpleViewPoint(Heading heading, int x, int y){
        this(heading, new Position(x,y));
    }

    public SimpleViewPoint(Heading heading, Position position) {
        this.heading = heading;
        this.position = position;
    }

    public Heading getHeading() {
        return heading;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public ViewPoint turnLeft() {
        heading = heading.turnLeft();
        return this;
    }

    @Override
    public ViewPoint turnRight() {
        heading = heading.turnRight();
        return this;
    }

    @Override
    public ViewPoint forward() {
        position = position.forward(heading);
        return this;
    }

    @Override
    public ViewPoint backward() {
        position = position.backward(heading);
        return this;
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

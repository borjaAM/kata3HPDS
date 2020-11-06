package refactoring;
import refactoring.Rover.Position;

import java.util.Arrays;

public class Mapa {

    private int[][] map;

    public Mapa(int dim) {
        map = new int[dim][dim];
    }

    public int[][] getMap() {
        return map;
    }

    public void createObstacle(Position position){
        map[position.getX()][position.getY()] = -1;
    }

    public int getForwardPosition(Position position){
        return map[position.getX()][position.getY()];
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                res += map[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }
}

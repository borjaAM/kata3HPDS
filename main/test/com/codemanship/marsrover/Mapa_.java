package com.codemanship.marsrover;
import org.junit.Before;
import org.junit.Test;
import refactoring.Mapa;
import refactoring.Rover;
import refactoring.Rover.Position;
import static refactoring.Rover.Heading.*;
import static refactoring.Rover.Order.*;

import static org.assertj.core.api.Assertions.assertThat;

public class Mapa_ {
    private Mapa mapa;
    private Rover rover;

    @Before
    public void initialize_new_map(){
        mapa = new Mapa(5);
        rover = new Rover(North, 0, 0);
        rover.setMapa(mapa);
    }
    @Test
    public void createObstacles(){
        mapa.createObstacle(new Position(0,1));
        mapa.createObstacle(new Position(2, 0));
        System.out.println(mapa.toString());
        assertThat(mapa.getForwardPosition(new Position(0,1))).isEqualTo(-1);
        assertThat(mapa.getForwardPosition(new Position(2,0))).isEqualTo(-1);
        assertThat(rover.position().forward(rover.heading())).isEqualTo(new Position(0,0));
        rover = new Rover(East, 0, 0);
        assertThat(rover.position().forward(rover.heading()).forward(rover.heading())).isEqualTo(new Position(1,0));
    }
}

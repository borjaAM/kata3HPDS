package com.codemanship.marsrover;

import org.junit.Test;
import refactoring.Rover;
import refactoring.SimpleViewPoint;
import refactoring.SimpleViewPoint.Position;

import static org.assertj.core.api.Assertions.assertThat;
import static refactoring.SimpleViewPoint.Heading.*;
import static refactoring.Rover.Order.*;

public class Rover__ {

    @Test
    public void could_be_initialized_with_legacy_constructor() {
        assertThat((new SimpleViewPoint("N", 5, 5).getHeading())).isEqualTo(North);
        assertThat(new SimpleViewPoint("South", 5, 5).getHeading()).isEqualTo(South);
        assertThat(new SimpleViewPoint("North", 5, 5).getPosition()).isEqualTo(new Position(5,5));
    }

    @Test
    public void could_be_initialized_using_new_constructors() {
        assertThat(((SimpleViewPoint)new Rover(new SimpleViewPoint(North, new Position(4,4))).getViewPoint()).getPosition()).isEqualTo(new Position(4,4));
        assertThat(((SimpleViewPoint)new Rover(new SimpleViewPoint(North, new Position(4,4))).getViewPoint()).getHeading()).isEqualTo(North);
        assertThat(((SimpleViewPoint)new Rover(new SimpleViewPoint(North, 4, 4)).getViewPoint()).getPosition()).isEqualTo(new Position(4,4));
        assertThat(((SimpleViewPoint)new Rover(new SimpleViewPoint(North, 4, 4)).getViewPoint()).getHeading()).isEqualTo(North);
    }

    @Test
    public void could_turn_left() {
        Rover rover = new Rover(new SimpleViewPoint(North, new Position(3, 3)));
        rover.go(Left);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,3));
    }

    @Test
    public void could_turn_right() {
        Rover rover = new Rover(new SimpleViewPoint(East, new Position(5, 1)));
        rover.go(Right);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(South);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(5,1));
    }

    @Test
    public void could_go_forward() {
        Rover rover = new Rover(new SimpleViewPoint(South, new Position(1, 1)));
        rover.go(Forward);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(South);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(1,0));
    }

    @Test
    public void could_go_backward() {
        Rover rover = new Rover(new SimpleViewPoint(West, new Position(-4, 4)));
        rover.go(Backward);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(-3,4));
    }

    @Test
    public void could_execute_many_orders() {
        Rover rover = new Rover(new SimpleViewPoint(West, new Position(3, 1)));
        rover.go(Backward, Left, Forward, Right, Forward);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }

    @Test
    public void could_execute_legacy_instructions() {
        Rover rover = new Rover(new SimpleViewPoint(West, new Position(3, 1)));
        rover.go("BLFRF");
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }


    @Test
    public void could_ignore_legacy_instructions() {
        Rover rover = new Rover(new SimpleViewPoint(West, new Position(3, 1)));
        rover.go("BL*FRF");
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint)rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }

}

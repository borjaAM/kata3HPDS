package com.codemanship.marsrover;

import org.junit.Test;
import refactoring.SimpleViewPoint.Position;

import static org.junit.Assert.assertEquals;
import static refactoring.SimpleViewPoint.Heading.*;

public class Position_ {

	@Test
	public void should_calculate_forward_position() {
		assertEquals(new Position(-1,0), new Position(0,0).forward(North).forward(West).forward(South));
		assertEquals(new Position(-3,5), new Position(-3,5).forward(North).forward(West).forward(South).forward(East));
		assertEquals(new Position(2,-2), new Position(0,-1).forward(North).forward(East).forward(East).forward(South).forward(South));
		assertEquals(new Position(-10,10), new Position(-10,9).forward(North));
	}

	@Test
	public void should_calculate_backward_position() {
		assertEquals(new Position(-1,-2), new Position(0,0).backward(North).backward(East).backward(North));
		assertEquals(new Position(-2,6), new Position(-3,5).backward(North).backward(South).backward(South).backward(East).backward(West).backward(West));
		assertEquals(new Position(-1,-2), new Position(1,-1).backward(North).backward(East).backward(East));
		assertEquals(new Position(5,5), new Position(4,6).backward(North).backward(West));
	}
}

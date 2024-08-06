package com.example.scrabble.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessageTest {
  
  @Test
  void testMessage() {
    Object data = "Hello";
    Message message = new Message(data, "Hello");

    assertEquals(data, message.getData());
    assertEquals("Hello", message.getType());
  }

  @Test
  void testEquals() {
    Object data = "Hello";
    Message message = new Message(data, "Hello");
    Message message2 = new Message(data, "Hello");
    assertEquals(message, message2);
  }

  @Test
  void testToString() {
    Object data = "Hello";
    Message message = new Message(data, "Hello");

    assertEquals("Message{data=Hello, type='Hello'}", message.toString());
  }
}

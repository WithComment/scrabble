package com.example.scrabble.controller;

/**
 * Custom class to send STOMP messages to the frontend
 */
class Message {
  Object data;
  String type;

  public Message(Object data, String type) {
    this.data = data;
    this.type = type;
  }

  public Object getData() {
    return data;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Message other = (Message) obj;
    return (data.equals(other.data)) && (type.equals(other.type));
  }

  @Override
  public String toString() {
    return "Message{" + "data=" + data + ", type='" + type + '\'' + '}';
  }
}

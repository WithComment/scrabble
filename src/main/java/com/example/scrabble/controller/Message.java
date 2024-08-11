package com.example.scrabble.controller;

/**
 * Custom class to send STOMP messages to the frontend.
 */
class Message {
  Object data;
  String type;

  /**
   * Constructs a Message with the specified data and type.
   *
   * @param data the data to be sent in the message
   * @param type the type of the message
   */
  public Message(Object data, String type) {
    this.data = data;
    this.type = type;
  }

  /**
   * Gets the data of the message.
   *
   * @return the data of the message
   */
  public Object getData() {
    return data;
  }

  /**
   * Gets the type of the message.
   *
   * @return the type of the message
   */
  public String getType() {
    return type;
  }

  /**
   * Checks if this message is equal to another object.
   *
   * @param obj the object to compare with
   * @return true if the objects are equal, false otherwise
   */
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

  /**
   * Returns a string representation of the message.
   *
   * @return a string representation of the message
   */
  @Override
  public String toString() {
    return "Message{" + "data=" + data + ", type='" + type + '\'' + '}';
  }
}
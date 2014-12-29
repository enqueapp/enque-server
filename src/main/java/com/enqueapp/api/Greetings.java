/*package com.enqueapp.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.util.ArrayList;

import javax.inject.Named;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 /
@Api(
    name = "helloworld",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class Greetings {

  public static ArrayList<MessageObject> greetings = new ArrayList<MessageObject>();

  static {
    greetings.add(new MessageObject("hello world!"));
    greetings.add(new MessageObject("goodbye world!"));
  }

  public MessageObject getGreeting(@Named("id") Integer id) throws NotFoundException {
    try {
      return greetings.get(id);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFoundException("Greeting not found with an index: " + id);
    }
  }

  public ArrayList<MessageObject> listGreeting() {
    return greetings;
  }

  @ApiMethod(name = "greetings.multiply", httpMethod = "post")
  public MessageObject insertGreeting(@Named("times") Integer times, MessageObject greeting) {
    MessageObject response = new MessageObject();
    StringBuilder responseBuilder = new StringBuilder();
    for (int i = 0; i < times; i++) {
      responseBuilder.append(greeting.getMessage());
    }
    greetings.add(new MessageObject(responseBuilder.toString()));
    response.setMessage(responseBuilder.toString());
    return response;
  }

  @ApiMethod(name = "greetings.authed", path = "MessageObject/authed")
  public MessageObject authedGreeting(User user) {
    MessageObject response = new MessageObject("hello " + user.getEmail());
    return response;
  }
}
*/
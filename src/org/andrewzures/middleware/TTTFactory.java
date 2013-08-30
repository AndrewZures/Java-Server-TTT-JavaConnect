package org.andrewzures.middleware;

public interface TTTFactory {
    Board getBoard(String type);
    Player getPlayer(String type, String mark);
}

// org.andrewzures.middleware.TTTFactory factory = (org.andrewzures.middleware.TTTFactory)org.jruby.Ruby.currentRuby.executeScriptlet("require 'jfactory'; JFactory,new");

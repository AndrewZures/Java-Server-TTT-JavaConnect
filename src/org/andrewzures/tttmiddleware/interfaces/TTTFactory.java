package org.andrewzures.tttmiddleware.interfaces;

public interface TTTFactory {
    Board getBoard(String type);
    Player getPlayer(String type, String mark);
}

// org.andrewzures.tttmiddleware.interfaces.TTTFactory factory = (org.andrewzures.tttmiddleware.interfaces.TTTFactory)org.jruby.Ruby.currentRuby.executeScriptlet("require 'jfactory'; JFactory,new");

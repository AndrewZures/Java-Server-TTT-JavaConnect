public interface TTTFactory {
    Board getBoard(String type);
    Player getPlayer(String type, String mark);
}

// TTTFactory factory = (TTTFactory)org.jruby.Ruby.currentRuby.executeScriptlet("require 'jfactory'; JFactory,new");

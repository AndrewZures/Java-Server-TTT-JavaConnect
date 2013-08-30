package org.andrewzures.tttmiddleware;
import org.andrewzures.tttmiddleware.gameresponders.*;
import org.andrewzures.java_server.*;
import org.andrewzures.tttmiddleware.helpers.PostParser;
import org.andrewzures.tttmiddleware.interfaces.TTTFactory;
import org.andrewzures.tttmiddleware.stringbuilders.GameStringBuilder;
import org.jruby.Ruby;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.HashMap;

public class Driver {

    public static void main(String[] args) throws IOException, ScriptException {
        int port = 8189;
        TTTFactory factory = (TTTFactory) Ruby.newInstance().evalScriptlet("require 'jfactory'; JFactory.new");
        String startingPath = ".";
        ServerSocketInterface myServerSocket = new MyServerSocket();
        Logger logger = new Logger();
        Server server = new Server(
                port,
                startingPath,
                myServerSocket,
                logger
        );
        PostParser parser = new PostParser();
        HashMap<Integer, Game> gameMap = new HashMap<Integer, Game>();
        GameStringBuilder gameStringBuilder = new GameStringBuilder(gameMap);
        MoveResponder moveResponder = new MoveResponder(gameMap, parser, gameStringBuilder);
        IntroResponder introResponder = new IntroResponder();
        NewGameResponder newGameResponder = new NewGameResponder(gameMap, parser, factory, gameStringBuilder);

        server.addRoute("POST", "/new_game", newGameResponder);
        server.addRoute("GET", "/new_game", introResponder);
        server.addRoute("POST", "/move", moveResponder);
        server.go();
    }
}

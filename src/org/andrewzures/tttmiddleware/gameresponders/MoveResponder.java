package org.andrewzures.tttmiddleware.gameresponders;

import org.andrewzures.java_server.Request;
import org.andrewzures.java_server.ResponderInterface;
import org.andrewzures.java_server.Response;
import org.andrewzures.tttmiddleware.Game;
import org.andrewzures.tttmiddleware.stringbuilders.GameStringBuilder;
import org.andrewzures.tttmiddleware.helpers.PostParser;

import java.util.HashMap;

public class MoveResponder implements ResponderInterface {
    PostParser parser;
    HashMap<Integer, Game> gameMap;
    GameStringBuilder gameStringBuilder;

    public MoveResponder(HashMap<Integer, Game> gameMap, PostParser parser, GameStringBuilder gameStringBuilder) {
        this.gameMap = gameMap;
        this.parser = parser;
        this.gameStringBuilder = gameStringBuilder;
    }

    public Response respond(Request request) {
        String variables = parser.getFormBody(request);
        System.out.println("move responder variables = " + variables);
        HashMap<String, String> postMap = parser.parsePostHash(variables);
        String[] variableList = {"move", "player", "board_id"};
        if (!this.hasVariables(postMap, variableList)) {
            System.out.println("no variables");
            return null;
        }
        Game game = gameMap.get(Integer.parseInt(postMap.get("board_id")));
        if (game == null) return null;
        game.runGameLoop(postMap.get("player"), Integer.parseInt(postMap.get("move")));

        Response response = new Response();
        response.inputStream = gameStringBuilder.updateGame(game);
        if (response.inputStream == null) return null;
        response = this.populateHeader(response);
        return response;
    }

    public Response populateHeader(Response response) {
        response.method = "GET";
        response.path = "/move";
        response.statusCode = "200";
        response.statusText = "OK";
        response.httpType = "HTTP/1.1";
        response.contentType = "text/html";
        return response;
    }

    public boolean hasVariables(HashMap<String, String> postMap, String[] variableList) {
        for (int i = 0; i < variableList.length; i++) {
            if (!postMap.containsKey(variableList[i])) {
                return false;
            }
        }
        return true;
    }
}

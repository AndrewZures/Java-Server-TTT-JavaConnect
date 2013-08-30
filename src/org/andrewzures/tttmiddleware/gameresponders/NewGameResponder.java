package org.andrewzures.tttmiddleware.gameresponders;

import org.andrewzures.java_server.Request;
import org.andrewzures.java_server.ResponderInterface;
import org.andrewzures.java_server.Response;
import org.andrewzures.tttmiddleware.Game;
import org.andrewzures.tttmiddleware.stringbuilders.GameStringBuilder;
import org.andrewzures.tttmiddleware.helpers.PostParser;
import org.andrewzures.tttmiddleware.interfaces.TTTFactory;

import java.util.HashMap;

public class NewGameResponder implements ResponderInterface {
    PostParser parser;
    TTTFactory factory;
    GameStringBuilder gameStringBuilder;
    HashMap<Integer, Game> gameMap;
    String player1, player2, gameType;
    int count = 0;

    public NewGameResponder(HashMap<Integer,
            Game> gameMap,
                            PostParser parser,
                            TTTFactory factory,
                            GameStringBuilder gameStringBuilder) {
        this.gameMap = gameMap;
        this.parser = parser;
        this.factory = factory;
        this.gameStringBuilder = gameStringBuilder;
    }

    public Response respond(Request request) {
        Game game = this.createNewGame(request);
        if (game == null) return null;
        game.runGameLoop("X", -1);

        Response response = new Response();

        response.inputStream = gameStringBuilder.updateGame(game);
        if (response.inputStream == null) return null;
        response = this.populateHeader(response);
        return response;
    }

    public Response populateHeader(Response response) {
        response.method = "POST";
        response.path = "/move";
        response.statusCode = "200";
        response.statusText = "OK";
        response.httpType = "HTTP/1.1";
        response.contentType = "text/html";
        return response;
    }

    private Game createNewGame(Request request) {
        String variables = parser.getFormBody(request);
        System.out.println("game variables = " + variables);
        HashMap<String, String> postMap = parser.parsePostHash(variables);
        if (postMap.containsKey("player1")
                && postMap.containsKey("player2")
                && postMap.containsKey("game_type")) {
            player1 = postMap.get("player1");
            player2 = postMap.get("player2");
            gameType = postMap.get("game_type");
        }
        int id = getID();

        Game game = new Game(id,
                factory.getBoard(gameType),
                factory.getPlayer(player1, "X"),
                factory.getPlayer(player2, "O")
        );

        this.printGame(game);
        if (!game.isCorrectlySetUp()) {
            return null;
        } else {
            gameMap.put(id, game);
            return game;
        }
    }

    public void printGame(Game game) {
        System.out.println("id = " + game.getID());
        System.out.println("gametype >>" + gameType + "<<");
        System.out.println("board = " + game.getBoard());
        System.out.println("game creating failed");
    }


    private int getID() {
        int newId = count;
        count++;
        return newId;
    }
}

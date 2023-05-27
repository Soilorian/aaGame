package main.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import main.model.DataBase;
import main.model.Player;

import java.io.IOException;

public class PlayerAdapter extends TypeAdapter<Player> {
    @Override
    public void write(JsonWriter out, Player value) throws IOException {
        out.beginObject();
        out.name("name");
        out.value(value.getUsername());
        out.name("password");
        out.value(value.getPassword());
        out.name("profile");
        out.value(value.getProfileIconAddress());
        out.name("maxScore");
        out.value(value.getMaxScore());
        out.name("time");
        out.value(value.getMaxScoreTime());
        out.endObject();
    }

    @Override
    public Player read(JsonReader in) throws IOException {
        String name = null, password = null, address = null;
        int maxScore = 0, maxScoreTime = 0;
        in.beginObject();
        String fieldName = null;
        while (in.hasNext()){
            JsonToken token = in.peek();
            if (token.equals(JsonToken.NAME))
                fieldName = in.nextName();
            assert fieldName != null;
            if (fieldName.equals("name")){
                token = in.peek();
                name = in.nextString();
            }
            if (fieldName.equals("password")){
                token = in.peek();
                password = in.nextString();
            }
            if (fieldName.equals("profile")){
                token = in.peek();
                address = in.nextString();
            }
            if (fieldName.equals("maxScore")){
                token = in.peek();
                maxScore = in.nextInt();
            }
            if (fieldName.equals("maxScoreTime")){
                token = in.peek();
                maxScoreTime = in.nextInt();
            }
        }
        in.endObject();
        Player player = new Player(name, password, DataBase.getTextureFromAddress(address));
        player.setMaxScore(maxScore);
        player.setMaxScoreTime(maxScoreTime);
        return player;
    }
}

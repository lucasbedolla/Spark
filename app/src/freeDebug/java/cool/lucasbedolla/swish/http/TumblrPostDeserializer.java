package cool.lucasbedolla.ruby.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tumblr.jumblr.types.UnknownTypePost;

import java.lang.reflect.Type;

/**
 * Created by Lucas Leabres on 9/12/2016.
 * this class wraps http JSON response into usable object
 */
public class TumblrPostDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jobject = json.getAsJsonObject();
        String typeName = jobject.get("type").getAsString();
        String className = typeName.substring(0, 1).toUpperCase() + typeName.substring(1) + "Post";
        try {
            Class<?> clz = Class.forName("com.tumblr.jumblr.types." + className);
            return jdc.deserialize(json, clz);
        } catch (ClassNotFoundException e) {
            System.out.println("deserialized post for unknown type: " + typeName);
            return jdc.deserialize(json, UnknownTypePost.class);
        }

    }
}

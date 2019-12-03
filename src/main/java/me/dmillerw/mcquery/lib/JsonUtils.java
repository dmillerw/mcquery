package me.dmillerw.mcquery.lib;

import com.google.gson.*;
import net.minecraft.nbt.*;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    private static Gson _gson;

    public static Gson gson() {
        if (_gson == null) {
            GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(INBT.class, new GenericNbtSerializer());

            _gson = builder.create();
        }
        return _gson;
    }

    public static String toJson(Object obj) {
        try {
            return gson().toJson(obj);
        } catch (Exception ex) {
            ex.printStackTrace();;
            return "{}";
        }
    }

    public static <T> JsonArray listToArray(List<T> list) {
        JsonArray array = new JsonArray();
        list.forEach((i) -> {
            array.add(gson().toJson(i));
        });
        return array;
    }

    private static class GenericNbtSerializer implements JsonSerializer<INBT> {

        @Override
        public JsonElement serialize(INBT src, Type typeOfSrc, JsonSerializationContext context) {
            Object value = null;
            if (src instanceof CollectionNBT) {
                value = JsonNull.INSTANCE;
            } else if (src instanceof NumberNBT) {
                value = ((NumberNBT) src).getAsNumber();
            } else if (src instanceof StringNBT) {
                value = src.getString();
            } else if (src instanceof CompoundNBT) {
                value = new JsonObject();
                for (String key : ((CompoundNBT) src).keySet()) {
                    ((JsonObject) value).add(key, context.serialize(((CompoundNBT) src).get(key), INBT.class));
                }
            }
            return value instanceof JsonElement ? (JsonElement) value : context.serialize(value);
        }
    }
}

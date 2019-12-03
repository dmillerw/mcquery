package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;

import java.util.Map;
import java.util.UUID;

public class McGameProfile {

    public final UUID id;
    public final String name;
    public final Map<String, String> properties = Maps.newHashMap();

    public McGameProfile(GameProfile gameProfile) {
        this.id = gameProfile.getId();
        this.name = gameProfile.getName();
        gameProfile.getProperties().forEach((k, v) -> {
            properties.put(k, v.getName() + "=" + v.getValue());
        });
    }
}

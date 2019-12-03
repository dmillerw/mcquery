package me.dmillerw.mcquery;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public ForgeConfigSpec.ConfigValue<String> port;
        public ForgeConfigSpec.ConfigValue<String> apiKey;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            port = builder.define("port", "8080");
            apiKey = builder.define("apiKey", "YOUNEEDTOSETME");
            builder.pop();
        }
    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}
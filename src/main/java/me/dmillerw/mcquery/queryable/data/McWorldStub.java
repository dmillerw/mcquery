package me.dmillerw.mcquery.queryable.data;

import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;

public class McWorldStub {

    public final String type;
    public final String name;
    public final long seed;
    public final boolean raining;
    public final boolean thundering;
    public final Difficulty difficulty;
//    public final CompoundNBT gameRules;

    public McWorldStub(ServerWorld world) {
        final WorldInfo info = world.getWorldInfo();

        this.type = world.dimension.getType().getRegistryName().toString();
        this.name = info.getWorldName();
        this.seed = info.getSeed();
        this.raining = info.isRaining();
        this.thundering = info.isThundering();
        this.difficulty = info.getDifficulty();
    }
}

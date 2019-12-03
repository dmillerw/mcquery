package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Lists;
import me.dmillerw.mcquery.queryable.Entities;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class McWorld extends McWorldStub {

    public McWorld(ServerWorld world) {
        super(world);
    }
}

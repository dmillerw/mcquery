package me.dmillerw.mcquery.queryable;

import com.google.common.collect.Lists;
import me.dmillerw.mcquery.queryable.data.McWorld;
import me.dmillerw.mcquery.queryable.data.McWorldStub;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.List;

public class Worlds {

    public static List<McWorldStub> getAllWorlds() {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        List<McWorldStub> list = Lists.newArrayList();
        server.getWorlds().forEach((world) -> list.add(new McWorldStub(world)));

        return list;
    }

    public static ServerWorld getMcWorld(String world) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        return server.getWorld(DimensionType.byName(new ResourceLocation(world)));
    }

    public static McWorld getWorld(String world) {
        return new McWorld(getMcWorld(world));
    }
}

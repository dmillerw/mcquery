package me.dmillerw.mcquery.queryable;

import me.dmillerw.mcquery.queryable.data.McPlayer;
import me.dmillerw.mcquery.queryable.data.McPlayerInventories;
import me.dmillerw.mcquery.queryable.data.McPlayerStub;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Players {

    public static List<McPlayerStub> getAllPlayers() {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        return server.getPlayerList().getPlayers()
                .stream()
                .map(McPlayer::new)
                .collect(Collectors.toList());
    }

    public static McPlayer getPlayer(UUID uuid) {
        return null;
    }

    public static McPlayerInventories getPlayerInventory(UUID uuid) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        return new McPlayerInventories(server.getPlayerList().getPlayerByUUID(uuid));
    }
}

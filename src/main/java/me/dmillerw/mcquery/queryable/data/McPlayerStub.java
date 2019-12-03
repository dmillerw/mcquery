package me.dmillerw.mcquery.queryable.data;

import net.minecraft.entity.player.PlayerEntity;

public class McPlayerStub extends McLivingEntity {

    public final McGameProfile gameProfile;

    public McPlayerStub(PlayerEntity player) {
        super(player);

        this.gameProfile = new McGameProfile(player.getGameProfile());
    }
}

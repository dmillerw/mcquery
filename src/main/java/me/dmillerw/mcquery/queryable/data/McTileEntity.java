package me.dmillerw.mcquery.queryable.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class McTileEntity {

    public final String type;
    public final CompoundNBT tag;

    public McTileEntity(TileEntity tile) {
        this.type = tile.getType().getRegistryName().toString();
        this.tag = tile.write(new CompoundNBT());
    }
}

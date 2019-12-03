package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class McBlockState {

    public final String name;
    public final BlockPos position;
    public final Map<String, String> properties = Maps.newHashMap();

    public final McTileEntity tileEntity;

    public McBlockState(World world, BlockPos pos, BlockState state) {
        this.name = state.getBlock().getRegistryName().toString();

        this.position = pos;

        state.getValues().forEach((k, v) -> {
            this.properties.put(k.getName(), v.toString());
        });

        if (world.getTileEntity(pos) != null)
            this.tileEntity = new McTileEntity(Objects.requireNonNull(world.getTileEntity(pos)));
        else
            this.tileEntity = null;
    }
}

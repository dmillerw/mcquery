package me.dmillerw.mcquery.lib;

import net.minecraft.util.math.BlockPos;

public class PosUtils {

    public static BlockPos fromString(String xyz) {
        String[] split = xyz.split(",");
        return new BlockPos(Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]));
    }
}

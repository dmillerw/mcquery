package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class McPlayerInventories {

    public final List<McItemStack> playerInventory = Lists.newArrayList();
    public final Map<String, Object> hotbar;
    public final List<McItemStack> enderChestInventory = Lists.newArrayList();

    public McPlayerInventories(PlayerEntity player) {
        this.playerInventory.addAll(player.inventory.mainInventory
                .stream()
                .skip(PlayerInventory.getHotbarSize())
                .map(McItemStack::create)
                .collect(Collectors.toList()));

        this.hotbar = QuickyBuilder.builder()
                .set("selectedIndex", player.inventory.currentItem)
                .set("inventory", player.inventory.mainInventory
                        .subList(0, PlayerInventory.getHotbarSize())
                        .stream()
                        .map(McItemStack::create)
                        .collect(Collectors.toList()))
                .build();

        final EnderChestInventory enderInv = player.getInventoryEnderChest();
        for (int i=0; i<enderInv.getSizeInventory(); i++) {
            enderChestInventory.add(McItemStack.create(enderInv.getStackInSlot(i)));
        }
    }
}

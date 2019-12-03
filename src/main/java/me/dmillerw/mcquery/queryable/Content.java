package me.dmillerw.mcquery.queryable;

import me.dmillerw.mcquery.queryable.data.McItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class Content {

    private static List<McItemStack> allContent;

    public static List<McItemStack> getContent() {
        if (allContent == null) {
            NonNullList<ItemStack> items = NonNullList.create();
            for (int i = 0; i< ItemGroup.getGroupCountSafe(); i++) {
                ItemGroup group = ItemGroup.GROUPS[i];
                for(Item item : ForgeRegistries.ITEMS) {
                    item.fillItemGroup(group, items);
                }
            }

            allContent = items.stream().map(McItemStack::create).collect(Collectors.toList());
        }

        return allContent;
    }
}

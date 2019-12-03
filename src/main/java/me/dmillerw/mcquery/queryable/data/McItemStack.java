package me.dmillerw.mcquery.queryable.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.translation.LanguageMap;

import java.lang.reflect.Field;

public class McItemStack extends McData {

    public static McItemStack create(Block block) {
        return create(new ItemStack(block));
    }

    public static McItemStack create(Item item) {
        return create(new ItemStack(item));
    }

    public static McItemStack create(ItemStack itemStack) {
        return new McItemStack(itemStack);
    }

    public String item;
    public String displayName;

    public String itemGroup;

    public Boolean isEmpty;
    public Integer count;
    public Integer damage;
    public Integer maxDamage;

    public CompoundNBT tag;
    public CompoundNBT capabilityTag;

    private McItemStack(ItemStack itemStack) {
        boolean empty = itemStack == null || itemStack.isEmpty();
        this.item = empty ? null : itemStack.getItem().getRegistryName().toString();
        this.displayName = empty ? null : LanguageMap.getInstance().translateKey(itemStack.getTranslationKey());
        this.itemGroup = empty ? null : itemStack.getItem().getGroup() == null ? null : itemStack.getItem().getGroup().getTranslationKey();
        this.isEmpty = empty ? true : null;
        this.count = empty ? null : itemStack.getCount();
        this.damage = empty ? null : itemStack.getDamage();
        this.maxDamage = empty ? null : itemStack.getMaxDamage();
        this.tag = empty ? null : itemStack.getTag();

        if (!empty) {
            try {
                final Field capTagField = ItemStack.class.getDeclaredField("capNBT");
                capTagField.setAccessible(true);
                this.capabilityTag = (CompoundNBT) capTagField.get(itemStack);
            } catch (Exception ignore) {
                this.capabilityTag = new CompoundNBT();
            }
        } else {
            capabilityTag = null;
        }

        cleanup();
    }

    @Override
    public void cleanup() {
        if (isEmpty != null && !isEmpty) {
            if (maxDamage == 0 && damage == 0) {
                maxDamage = null;
                damage = null;
            }

            if (count == 1)
                count = null;
        }
    }
}

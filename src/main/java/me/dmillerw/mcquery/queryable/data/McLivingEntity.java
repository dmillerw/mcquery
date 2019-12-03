package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.List;
import java.util.Map;

public class McLivingEntity extends McEntity {

    // attributes, potion effects, armour and hand inventories

    public final float health;
    public final float maxHealth;

    public final Map<String, Double> attributes = Maps.newHashMap();
    public final List<McActiveEffect> potionEffects = Lists.newArrayList();
    public final Map<EquipmentSlotType, McItemStack> equipment = Maps.newHashMap();

    public McLivingEntity(LivingEntity entity) {
        super(entity);

        this.health = entity.getHealth();
        this.maxHealth = entity.getMaxHealth();

        entity.getAttributes().getAllAttributes().forEach((attr) -> {
            attributes.put(attr.getAttribute().getName(), attr.getValue());
        });

        entity.getActivePotionEffects().forEach((eff) -> {
            this.potionEffects.add(new McActiveEffect(eff));
        });

        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            this.equipment.put(slot, McItemStack.create(entity.getItemStackFromSlot(slot)));
        }
    }
}

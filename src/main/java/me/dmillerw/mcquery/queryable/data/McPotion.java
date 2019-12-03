package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Maps;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.Map;

public class McPotion {

    public final String name;
    public final EffectType type;
    public final Map<String, AttributeModifier> attributes = Maps.newHashMap();
    public final int liquidColor;

    public McPotion(Effect effect) {
        this.name = effect.getName();
        this.type = effect.getEffectType();
        effect.getAttributeModifierMap().forEach((e, v) -> {
            this.attributes.put(e.getName(), v);
        });
        this.liquidColor = effect.getLiquidColor();
    }
}

package me.dmillerw.mcquery.queryable.data;

import net.minecraft.potion.EffectInstance;

public class McActiveEffect {

    public final McPotion potion;
    public final Integer duration;
    public final Integer amplifier;

    public McActiveEffect(EffectInstance instance) {
        this.potion = new McPotion(instance.getPotion());
        this.duration = instance.getDuration();
        this.amplifier = instance.getAmplifier();
    }
}

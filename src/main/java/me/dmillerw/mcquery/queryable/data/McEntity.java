package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Maps;
import me.dmillerw.mcquery.queryable.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class McEntity {

    public final UUID uuid;

    public final String name;
    public final String classification;

    public final Double x;
    public final Double y;
    public final Double z;

    public final Map<String, String> entityData = Maps.newHashMap();

    public McEntity(Entity entity) {
        this.uuid = entity.getUniqueID();

        final EntityType type = entity.getType();
        this.name = EntityType.getKey(type).toString();
        this.classification = type.getClassification().name();

        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;

        final EntityDataManager data = entity.getDataManager();
        Objects.requireNonNull(data.getAll()).forEach((entry) -> {
            entityData.put(Entities.getDataIdName(entity.getClass(), entry.getKey().getId()), entry.getValue().toString());
        });
    }
}

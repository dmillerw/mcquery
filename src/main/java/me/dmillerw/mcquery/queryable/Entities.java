package me.dmillerw.mcquery.queryable;

import com.google.common.collect.Maps;
import me.dmillerw.mcquery.queryable.data.McEntity;
import me.dmillerw.mcquery.queryable.data.McLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.world.server.ServerWorld;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Entities {

    private static final Map<Class<? extends Entity>, Map<Integer, String>> singularIdToNameMap = Maps.newHashMap();

    public static String getDataIdName(Class<? extends Entity> clazz, int id) {
        return getIdToNameMap(clazz).get(id);
    }

    private static Map<Integer, String> getIdToNameMap(Class<? extends Entity> clazz) {
        Map<Integer, String> map = singularIdToNameMap.get(clazz);
        if (map != null) return map;
        else map = Maps.newHashMap();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == DataParameter.class) {
                try {
                    field.setAccessible(true);
                    DataParameter param = (DataParameter) field.get(clazz);
                    map.put(param.getId(), field.getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        singularIdToNameMap.put(clazz, map);

        Class superclass = clazz.getSuperclass();
        while (superclass != Object.class) {
            map.putAll(getIdToNameMap(superclass));
            superclass = superclass.getSuperclass();
        }

        return map;
    }

    public static List<McEntity> getAllEntitiesInWorld(ServerWorld world) {
        return world.getEntities().map((e) -> {
            if (e instanceof LivingEntity) {
                return new McLivingEntity((LivingEntity) e);
            } else {
                return new McEntity(e);
            }
        }).collect(Collectors.toList());
    }
}

package net.id.incubus_core.condition.api;

import net.id.incubus_core.condition.IncubusCondition;
import net.id.incubus_core.condition.base.ConditionManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * An API intended to aid use of {@code Condition}s. <br>
 * <br>
 * ~ Jack
 * @author AzazelTheDemonLord
 */
@SuppressWarnings("unused")
public class ConditionAPI {
    /**
     * @param type The {@code EntityType} to test
     * @return A list of all conditions the given entity is not immune to.
     */
    public static List<Condition> getValidConditions(EntityType<?> type) {
        return IncubusCondition.CONDITION_REGISTRY
                .stream()
                .filter(condition -> !type.isIn(condition.exempt))
                .collect(Collectors.toList());
    }

    /**
     * @param id The unique {@code Identifier} of the {@code Condition}.
     * @return The {@code Condition} corresponding to the given {@code Identifier}
     */
    public static Condition getOrThrow(Identifier id) {
        return IncubusCondition.CONDITION_REGISTRY.getOrEmpty(id).orElseThrow((() -> new NoSuchElementException("No ConditionManager found registered for entry: " + id.toString())));
    }

    /**
     * @param condition The {@code Condition} to test
     * @param entity The entity to test
     * @return Whether the given {@code Condition} is currently outwardly
     * visible on the given entity.
     */
    public static boolean isVisible(Condition condition, LivingEntity entity) {
        if(!condition.isExempt(entity)) {
            return IncubusCondition.CONDITION_MANAGER_KEY.get(entity).getScaledSeverity(condition) >= condition.visThreshold;
        }
        return false;
    }

    /**
     * @param entity The entity you want to get the {@code ConditionManager} of.
     * @return The {@code ConditionManager} of the given entity.
     */
    public static ConditionManager getConditionManager(LivingEntity entity) {
        return IncubusCondition.CONDITION_MANAGER_KEY.get(entity);
    }

    /**
     * Syncs a given entity's {@code ConditionManager}.
     * @param entity The entity whose {@code ConditionManager} you wish to sync.
     */
    public static void trySync(LivingEntity entity) {
        IncubusCondition.CONDITION_MANAGER_KEY.sync(entity);
    }

    /**
     * @param condition The {@code Condition} you want the translation string of
     * @return The translation string of the given {@code Condition}
     */
    public static String getTranslationString(Condition condition) {
        return "condition." + condition.getId().getNamespace() + ".condition." + condition.getId().getPath();
    }
}

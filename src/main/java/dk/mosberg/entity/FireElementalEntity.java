// Implement the FollowOwnerGoal and TrackOwnerAttackerGoal classes
// ...existing code...
package dk.mosberg.entity;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Fire Elemental summoned entity. Fights for the summoner with fire-based attacks.
 */
public class FireElementalEntity extends PathAwareEntity {
    @Nullable
    private UUID ownerUuid;
    private int lifetime = 600; // 30 seconds (20 ticks per second)

    public FireElementalEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        // Fire immunity set via attributes
    }

    public static DefaultAttributeContainer.Builder createFireElementalAttributes() {
        return PathAwareEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 40.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3).add(EntityAttributes.ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(3, new FollowOwnerGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this));
    }

    public void setOwner(@Nullable PlayerEntity owner) {
        if (owner != null) {
            this.ownerUuid = owner.getUuid();
        }
    }

    @Nullable
    public PlayerEntity getOwner() {
        World world = this.getEntityWorld();
        if (this.ownerUuid != null && world instanceof net.minecraft.server.world.ServerWorld) {
            return ((net.minecraft.server.world.ServerWorld) world).getPlayerByUuid(this.ownerUuid);
        }
        return null;
    }

    @Override
    public void tick() {
        super.tick();

        World world = this.getEntityWorld();

        // Countdown lifetime (server-side only)
        if (world instanceof net.minecraft.server.world.ServerWorld) {
            lifetime--;
            if (lifetime <= 0) {
                this.discard();
            }
        }

        // Note: Particle effects are handled by client-side renderer (FireElementalRenderer)
    }

    // Fire elementals are immune to fire damage
    // Override damage handling if needed

    /**
     * Custom AI goal to follow the summoner.
     */
    private static class FollowOwnerGoal extends Goal {
        private final FireElementalEntity elemental;
        private static final double MIN_DISTANCE = 4.0;
        private static final double MAX_DISTANCE = 16.0;

        public FollowOwnerGoal(FireElementalEntity elemental) {
            this.elemental = elemental;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = elemental.getOwner();
            if (owner == null || owner.isSpectator()) {
                return false;
            }
            double distance = elemental.squaredDistanceTo(owner);
            return distance > MIN_DISTANCE * MIN_DISTANCE;
        }

        @Override
        public void start() {
            PlayerEntity owner = elemental.getOwner();
            if (owner != null) {
                elemental.getNavigation().startMovingTo(owner, 1.0);
            }
        }

        @Override
        public boolean shouldContinue() {
            PlayerEntity owner = elemental.getOwner();
            if (owner == null) {
                return false;
            }
            double distance = elemental.squaredDistanceTo(owner);
            return distance > MIN_DISTANCE * MIN_DISTANCE && distance < MAX_DISTANCE * MAX_DISTANCE;
        }

        @Override
        public void stop() {
            elemental.getNavigation().stop();
        }
    }

    /**
     * Custom AI goal to attack enemies that attack the owner.
     */
    private static class TrackOwnerAttackerGoal extends Goal {
        private final FireElementalEntity elemental;

        public TrackOwnerAttackerGoal(FireElementalEntity elemental) {
            this.elemental = elemental;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = elemental.getOwner();
            if (owner == null) {
                return false;
            }
            return owner.getAttacker() != null && elemental.getTarget() == null;
        }

        @Override
        public void start() {
            PlayerEntity owner = elemental.getOwner();
            if (owner != null && owner.getAttacker() != null) {
                elemental.setTarget(owner.getAttacker());
            }
        }
    }

    /**
     * Custom AI goal to attack the owner's target.
     */
    private static class AttackWithOwnerGoal extends Goal {
        private final FireElementalEntity elemental;

        public AttackWithOwnerGoal(FireElementalEntity elemental) {
            this.elemental = elemental;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = elemental.getOwner();
            if (owner == null) {
                return false;
            }
            return owner.getAttacking() != null && elemental.getTarget() == null;
        }

        @Override
        public void start() {
            PlayerEntity owner = elemental.getOwner();
            if (owner != null && owner.getAttacking() != null) {
                elemental.setTarget(owner.getAttacking());
            }
        }
    }

    public int getLifetime() {
        return lifetime;
    }
}

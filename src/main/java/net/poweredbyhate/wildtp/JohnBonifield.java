package net.poweredbyhate.wildtp;

import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Achievement;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created on 8/16/2017.
 *
 * @author RoboMWM
 */
public class JohnBonifield implements Player
{
    private String name;
    private String displayName;
    private UUID uuid;
    private InetSocketAddress address;
    private Player p;

    public JohnBonifield(Player player)
    {
        name = player.getName();
        displayName = player.getDisplayName();
        uuid = player.getUniqueId();
        address = player.getAddress();
        p = player;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public void setDisplayName(String s)
    {

    }

    @Override
    public String getPlayerListName()
    {
        return null;
    }

    @Override
    public void setPlayerListName(String s)
    {

    }

    @Override
    public String getPlayerListHeader()
    {
        return null;
    }

    @Override
    public String getPlayerListFooter()
    {
        return null;
    }

    @Override
    public void setPlayerListHeader(String s)
    {

    }

    @Override
    public void setPlayerListFooter(String s)
    {

    }

    @Override
    public void setPlayerListHeaderFooter(String s, String s1)
    {

    }

    @Override
    public void setCompassTarget(Location location)
    {

    }

    @Override
    public Location getCompassTarget()
    {
        return null;
    }

    @Override
    public InetSocketAddress getAddress()
    {
        return null;
    }

    @Override
    public int getProtocolVersion()
    {
        return 0;
    }

    @Nullable
    @Override
    public InetSocketAddress getVirtualHost()
    {
        return null;
    }

    @Override
    public boolean isConversing()
    {
        return false;
    }

    @Override
    public void acceptConversationInput(String s)
    {

    }

    @Override
    public boolean beginConversation(Conversation conversation)
    {
        return false;
    }

    @Override
    public void abandonConversation(Conversation conversation)
    {

    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent conversationAbandonedEvent)
    {

    }

    @Override
    public void sendRawMessage(String s)
    {

    }

    @Override
    public void kickPlayer(String s)
    {

    }

    @Override
    public void chat(String s)
    {

    }

    @Override
    public boolean performCommand(String s)
    {
        return false;
    }

    @Override
    public boolean isSneaking()
    {
        return false;
    }

    @Override
    public void setSneaking(boolean b)
    {

    }

    @Override
    public boolean isSprinting()
    {
        return false;
    }

    @Override
    public void setSprinting(boolean b)
    {

    }

    @Override
    public void saveData()
    {

    }

    @Override
    public void loadData()
    {

    }

    @Override
    public void setSleepingIgnored(boolean b)
    {

    }

    @Override
    public boolean isSleepingIgnored()
    {
        return false;
    }

    /**
     * @param location
     * @param b
     * @param b1
     * @deprecated
     */
    @Override
    public void playNote(Location location, byte b, byte b1)
    {

    }

    @Override
    public void playNote(Location location, Instrument instrument, Note note)
    {

    }

    @Override
    public void playSound(Location location, Sound sound, float v, float v1)
    {

    }

    @Override
    public void playSound(Location location, String s, float v, float v1)
    {

    }

    @Override
    public void playSound(Location location, Sound sound, SoundCategory soundCategory, float v, float v1)
    {

    }

    @Override
    public void playSound(Location location, String s, SoundCategory soundCategory, float v, float v1)
    {

    }

    @Override
    public void stopSound(Sound sound)
    {

    }

    @Override
    public void stopSound(String s)
    {

    }

    @Override
    public void stopSound(Sound sound, SoundCategory soundCategory)
    {

    }

    @Override
    public void stopSound(String s, SoundCategory soundCategory)
    {

    }

    /**
     * @param location
     * @param effect
     * @param i
     * @deprecated
     */
    @Override
    public void playEffect(Location location, Effect effect, int i)
    {

    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T t)
    {

    }

    /**
     * @param location
     * @param material
     * @param b
     * @deprecated
     */
    @Override
    public void sendBlockChange(Location location, Material material, byte b)
    {

    }

    @Override
    public void sendBlockChange(Location location, BlockData blockData)
    {

    }

    /**
     * @param location
     * @param i
     * @param i1
     * @param i2
     * @param bytes
     * @deprecated
     */
    @Override
    public boolean sendChunkChange(Location location, int i, int i1, int i2, byte[] bytes)
    {
        return false;
    }

    @Override
    public void sendSignChange(Location location, String[] strings) throws IllegalArgumentException
    {

    }

    @Override
    public void sendMap(MapView mapView)
    {

    }

    @Override
    public void sendActionBar(String s)
    {

    }

    @Override
    public void sendActionBar(char c, String s)
    {

    }

    @Override
    public void setPlayerListHeaderFooter(BaseComponent[] baseComponents, BaseComponent[] baseComponents1)
    {

    }

    @Override
    public void setPlayerListHeaderFooter(BaseComponent baseComponent, BaseComponent baseComponent1)
    {

    }

    @Override
    public void setTitleTimes(int i, int i1, int i2)
    {

    }

    @Override
    public void setSubtitle(BaseComponent[] baseComponents)
    {

    }

    @Override
    public void setSubtitle(BaseComponent baseComponent)
    {

    }

    @Override
    public void showTitle(BaseComponent[] baseComponents)
    {

    }

    @Override
    public void showTitle(BaseComponent baseComponent)
    {

    }

    @Override
    public void showTitle(BaseComponent[] baseComponents, BaseComponent[] baseComponents1, int i, int i1, int i2)
    {

    }

    @Override
    public void showTitle(BaseComponent baseComponent, BaseComponent baseComponent1, int i, int i1, int i2)
    {

    }

    @Override
    public void sendTitle(Title title)
    {

    }

    @Override
    public void updateTitle(Title title)
    {

    }

    @Override
    public void hideTitle()
    {

    }

    @Override
    public void updateInventory()
    {

    }

    /**
     * @param achievement
     * @deprecated
     */
    @Override
    public void awardAchievement(Achievement achievement)
    {

    }

    /**
     * @param achievement
     * @deprecated
     */
    @Override
    public void removeAchievement(Achievement achievement)
    {

    }

    /**
     * @param achievement
     * @deprecated
     */
    @Override
    public boolean hasAchievement(Achievement achievement)
    {
        return false;
    }

    @Override
    public void incrementStatistic(Statistic statistic) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic) throws IllegalArgumentException
    {

    }

    @Override
    public void incrementStatistic(Statistic statistic, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void setStatistic(Statistic statistic, int i) throws IllegalArgumentException
    {

    }

    @Override
    public int getStatistic(Statistic statistic) throws IllegalArgumentException
    {
        return 0;
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
    {

    }

    @Override
    public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException
    {
        return 0;
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void setStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
    {

    }

    @Override
    public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
    {
        return 0;
    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType, int i) throws IllegalArgumentException
    {

    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType, int i)
    {

    }

    @Override
    public void setStatistic(Statistic statistic, EntityType entityType, int i)
    {

    }

    @Override
    public void setPlayerTime(long l, boolean b)
    {

    }

    @Override
    public long getPlayerTime()
    {
        return 0;
    }

    @Override
    public long getPlayerTimeOffset()
    {
        return 0;
    }

    @Override
    public boolean isPlayerTimeRelative()
    {
        return false;
    }

    @Override
    public void resetPlayerTime()
    {

    }

    @Override
    public void setPlayerWeather(WeatherType weatherType)
    {

    }

    @Override
    public WeatherType getPlayerWeather()
    {
        return null;
    }

    @Override
    public void resetPlayerWeather()
    {

    }

    @Override
    public void giveExp(int i)
    {

    }

    @Override
    public void giveExp(int i, boolean b)
    {

    }

    @Override
    public int applyMending(int i)
    {
        return 0;
    }

    @Override
    public void giveExpLevels(int i)
    {

    }

    @Override
    public float getExp()
    {
        return 0;
    }

    @Override
    public void setExp(float v)
    {

    }

    @Override
    public int getLevel()
    {
        return 0;
    }

    @Override
    public void setLevel(int i)
    {

    }

    @Override
    public int getTotalExperience()
    {
        return 0;
    }

    @Override
    public void setTotalExperience(int i)
    {

    }

    @Override
    public float getExhaustion()
    {
        return 0;
    }

    @Override
    public void setExhaustion(float v)
    {

    }

    @Override
    public float getSaturation()
    {
        return 0;
    }

    @Override
    public void setSaturation(float v)
    {

    }

    @Override
    public int getFoodLevel()
    {
        return 0;
    }

    @Override
    public void setFoodLevel(int i)
    {

    }

    @Override
    public boolean isOnline()
    {
        return false;
    }

    @Override
    public boolean isBanned()
    {
        return false;
    }

    @Override
    public boolean isWhitelisted()
    {
        return false;
    }

    @Override
    public void setWhitelisted(boolean b)
    {

    }

    @Override
    public Player getPlayer()
    {
        return null;
    }

    @Override
    public long getFirstPlayed()
    {
        return 0;
    }

    @Override
    public long getLastPlayed()
    {
        return 0;
    }

    @Override
    public boolean hasPlayedBefore()
    {
        return false;
    }

    @Override
    public Location getBedSpawnLocation()
    {
        return null;
    }

    @Override
    public long getLastLogin() {
        return 0;
    }

    @Override
    public long getLastSeen() {
        return 0;
    }

    @Override
    public void setBedSpawnLocation(Location location)
    {

    }

    @Override
    public void setBedSpawnLocation(Location location, boolean b)
    {

    }

    @Override
    public boolean sleep(Location location, boolean b) {
        return false;
    }

    @Override
    public void wakeup(boolean b) {

    }

    @Override
    public Location getBedLocation() {
        return null;
    }

    @Override
    public boolean getAllowFlight()
    {
        return false;
    }

    @Override
    public void setAllowFlight(boolean b)
    {

    }

    @Override
    public void hidePlayer(Player player)
    {

    }

    @Override
    public void hidePlayer(Plugin plugin, Player player)
    {

    }

    @Override
    public void showPlayer(Player player)
    {

    }

    @Override
    public void showPlayer(Plugin plugin, Player player)
    {

    }

    @Override
    public boolean canSee(Player player)
    {
        return false;
    }

    @Override
    public boolean isFlying()
    {
        return false;
    }

    @Override
    public void setFlying(boolean b)
    {

    }

    @Override
    public void setFlySpeed(float v) throws IllegalArgumentException
    {

    }

    @Override
    public void setWalkSpeed(float v) throws IllegalArgumentException
    {

    }

    @Override
    public float getFlySpeed()
    {
        return 0;
    }

    @Override
    public float getWalkSpeed()
    {
        return 0;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public void setTexturePack(String s)
    {

    }

    @Override
    public void setResourcePack(String s)
    {

    }

    @Override
    public void setResourcePack(String s, byte[] bytes)
    {

    }

    @Override
    public Scoreboard getScoreboard()
    {
        return null;
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException
    {

    }

    @Override
    public boolean isHealthScaled()
    {
        return false;
    }

    @Override
    public void setHealthScaled(boolean b)
    {

    }

    @Override
    public void setHealthScale(double v) throws IllegalArgumentException
    {

    }

    @Override
    public double getHealthScale()
    {
        return 0;
    }

    @Override
    public Entity getSpectatorTarget()
    {
        return null;
    }

    @Override
    public void setSpectatorTarget(Entity entity)
    {

    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public void sendTitle(String s, String s1)
    {

    }

    @Override
    public void sendTitle(String s, String s1, int i, int i1, int i2)
    {

    }

    @Override
    public void resetTitle()
    {

    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i)
    {

    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, T t)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, T t)
    {

    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2)
    {

    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, T t)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, T t)
    {

    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3)
    {

    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3, T t)
    {

    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, T t)
    {

    }

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement)
    {
        return null;
    }

    @Override
    public int getClientViewDistance()
    {
        return 0;
    }

    @Override
    public String getLocale()
    {
        return null;
    }

    @Override
    public boolean getAffectsSpawning()
    {
        return false;
    }

    @Override
    public void setAffectsSpawning(boolean b)
    {

    }

    @Override
    public void updateCommands()
    {

    }

    @Override
    public int getViewDistance()
    {
        return 0;
    }

    @Override
    public void setViewDistance(int i)
    {

    }

    @Override
    public void setResourcePack(String s, String s1)
    {

    }

    @Override
    public PlayerResourcePackStatusEvent.Status getResourcePackStatus()
    {
        return null;
    }

    @Override
    public String getResourcePackHash()
    {
        return null;
    }

    @Override
    public boolean hasResourcePack()
    {
        return false;
    }

    @Override
    public PlayerProfile getPlayerProfile()
    {
        return null;
    }

    @Override
    public void setPlayerProfile(PlayerProfile playerProfile)
    {

    }

    @Override
    public float getCooldownPeriod()
    {
        return 0;
    }

    @Override
    public float getCooledAttackStrength(float v)
    {
        return 0;
    }

    @Override
    public void resetCooldown()
    {

    }

    @Override
    public Spigot spigot()
    {
        return p.spigot();
    }

    @Override
    public Location getOrigin()
    {
        return null;
    }

    @Override
    public boolean fromMobSpawner()
    {
        return false;
    }

    @Override
    public Chunk getChunk()
    {
        return null;
    }

    @Override
    public CreatureSpawnEvent.SpawnReason getEntitySpawnReason() {
        return null;
    }

    @Override
    public Location getLocation()
    {
        return p.getLocation();
    }

    @Override
    public Location getLocation(Location location)
    {
        return p.getLocation(location);
    }

    @Override
    public void setVelocity(Vector vector)
    {

    }

    @Override
    public Vector getVelocity()
    {
        return null;
    }

    @Override
    public double getHeight()
    {
        return 0;
    }

    @Override
    public double getWidth()
    {
        return 0;
    }

    @Override
    public BoundingBox getBoundingBox()
    {
        return null;
    }

    @Override
    public boolean isOnGround()
    {
        return false;
    }

    @Override
    public World getWorld()
    {
        return p.getWorld();
    }

    @Override
    public void setRotation(float v, float v1) {

    }

    @Override
    public boolean teleport(Location location)
    {
        return false;
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause teleportCause)
    {
        return false;
    }

    @Override
    public boolean teleport(Entity entity)
    {
        return false;
    }

    @Override
    public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause teleportCause)
    {
        return false;
    }

    @Override
    public List<Entity> getNearbyEntities(double v, double v1, double v2)
    {
        return null;
    }

    @Override
    public int getEntityId()
    {
        return 0;
    }

    @Override
    public int getFireTicks()
    {
        return 0;
    }

    @Override
    public int getMaxFireTicks()
    {
        return 0;
    }

    @Override
    public void setFireTicks(int i)
    {

    }

    @Override
    public void remove()
    {

    }

    @Override
    public boolean isDead()
    {
        return false;
    }

    @Override
    public boolean isValid()
    {
        return false;
    }

    @Override
    public void sendMessage(String s)
    {

    }

    @Override
    public void sendMessage(String[] strings)
    {

    }

    @Override
    public Server getServer()
    {
        return null;
    }

    @Override
    public boolean isPersistent()
    {
        return false;
    }

    @Override
    public void setPersistent(boolean b)
    {

    }

    @Override
    public PersistentDataContainer getPersistentDataContainer()
    {
        return p.getPersistentDataContainer();
    }

    /**
     * @deprecated
     */
    @Override
    public Entity getPassenger()
    {
        return null;
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public boolean setPassenger(Entity entity)
    {
        return false;
    }

    @Override
    public List<Entity> getPassengers()
    {
        return null;
    }

    @Override
    public boolean addPassenger(Entity entity)
    {
        return false;
    }

    @Override
    public boolean removePassenger(Entity entity)
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean eject()
    {
        return false;
    }

    @Override
    public float getFallDistance()
    {
        return 0;
    }

    @Override
    public void setFallDistance(float v)
    {

    }

    @Override
    public void setLastDamageCause(EntityDamageEvent entityDamageEvent)
    {

    }

    @Override
    public EntityDamageEvent getLastDamageCause()
    {
        return null;
    }

    @Override
    public UUID getUniqueId()
    {
        return uuid;
    }

    @Override
    public int getTicksLived()
    {
        return 0;
    }

    @Override
    public void setTicksLived(int i)
    {

    }

    @Override
    public void playEffect(EntityEffect entityEffect)
    {

    }

    @Override
    public EntityType getType()
    {
        return null;
    }

    @Override
    public boolean isInsideVehicle()
    {
        return false;
    }

    @Override
    public boolean leaveVehicle()
    {
        return false;
    }

    @Override
    public Entity getVehicle()
    {
        return null;
    }

    @Override
    public void setCustomNameVisible(boolean b)
    {

    }

    @Override
    public boolean isCustomNameVisible()
    {
        return false;
    }

    @Override
    public void setGlowing(boolean b)
    {

    }

    @Override
    public boolean isGlowing()
    {
        return false;
    }

    @Override
    public void setInvulnerable(boolean b)
    {

    }

    @Override
    public boolean isInvulnerable()
    {
        return false;
    }

    @Override
    public boolean isSilent()
    {
        return false;
    }

    @Override
    public void setSilent(boolean b)
    {

    }

    @Override
    public boolean hasGravity()
    {
        return false;
    }

    @Override
    public void setGravity(boolean b)
    {

    }

    @Override
    public int getPortalCooldown()
    {
        return 0;
    }

    @Override
    public void setPortalCooldown(int i)
    {

    }

    @Override
    public Set<String> getScoreboardTags()
    {
        return null;
    }

    @Override
    public boolean addScoreboardTag(String s)
    {
        return false;
    }

    @Override
    public boolean removeScoreboardTag(String s)
    {
        return false;
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction()
    {
        return null;
    }

    @Override
    public BlockFace getFacing()
    {
        return null;
    }

    @Override
    public Map<String, Object> serialize()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public PlayerInventory getInventory()
    {
        return p.getInventory();
    }

    @Override
    public Inventory getEnderChest()
    {
        return null;
    }

    @Override
    public MainHand getMainHand()
    {
        return p.getMainHand();
    }

    @Override
    public boolean setWindowProperty(InventoryView.Property property, int i)
    {
        return false;
    }

    @Override
    public InventoryView getOpenInventory()
    {
        return p.getOpenInventory();
    }

    @Override
    public InventoryView openInventory(Inventory inventory)
    {
        return null;
    }

    @Override
    public InventoryView openWorkbench(Location location, boolean b)
    {
        return null;
    }

    @Override
    public InventoryView openEnchanting(Location location, boolean b)
    {
        return null;
    }

    @Override
    public void openInventory(InventoryView inventoryView)
    {

    }

    @Override
    public InventoryView openMerchant(Villager villager, boolean b)
    {
        return null;
    }

    @Override
    public InventoryView openMerchant(Merchant merchant, boolean b)
    {
        return null;
    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public void closeInventory(InventoryCloseEvent.Reason reason)
    {

    }

    /**
     * @deprecated
     */
    @Override
    public ItemStack getItemInHand()
    {
        return p.getItemInHand();
    }

    /**
     * @param itemStack
     * @deprecated
     */
    @Override
    public void setItemInHand(ItemStack itemStack)
    {

    }

    @Override
    public ItemStack getItemOnCursor()
    {
        return null;
    }

    @Override
    public void setItemOnCursor(ItemStack itemStack)
    {

    }

    @Override
    public boolean hasCooldown(Material material)
    {
        return false;
    }

    @Override
    public int getCooldown(Material material)
    {
        return 0;
    }

    @Override
    public void setCooldown(Material material, int i)
    {

    }

    @Override
    public boolean isSleeping()
    {
        return false;
    }

    @Override
    public int getSleepTicks()
    {
        return 0;
    }

    @Override
    public GameMode getGameMode()
    {
        return p.getGameMode();
    }

    @Override
    public void setGameMode(GameMode gameMode)
    {

    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }

    @Override
    public boolean isHandRaised()
    {
        return false;
    }

    @Override
    public int getExpToLevel()
    {
        return 0;
    }

    @Override
    public Entity releaseLeftShoulderEntity()
    {
        return null;
    }

    @Override
    public Entity releaseRightShoulderEntity()
    {
        return null;
    }

    @Override
    public boolean discoverRecipe(NamespacedKey namespacedKey)
    {
        return false;
    }

    @Override
    public int discoverRecipes(Collection<NamespacedKey> collection)
    {
        return 0;
    }

    @Override
    public boolean undiscoverRecipe(NamespacedKey namespacedKey)
    {
        return false;
    }

    @Override
    public int undiscoverRecipes(Collection<NamespacedKey> collection)
    {
        return 0;
    }

    /**
     * @deprecated
     */
    @Override
    public Entity getShoulderEntityLeft()
    {
        return null;
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public void setShoulderEntityLeft(Entity entity)
    {

    }

    /**
     * @deprecated
     */
    @Override
    public Entity getShoulderEntityRight()
    {
        return null;
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public void setShoulderEntityRight(Entity entity)
    {

    }

    @Override
    public void openSign(Sign sign)
    {

    }

    @Override
    public double getEyeHeight()
    {
        return 0;
    }

    @Override
    public double getEyeHeight(boolean b)
    {
        return 0;
    }

    @Override
    public Location getEyeLocation()
    {
        return p.getEyeLocation();
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> set, int i)
    {
        return null;
    }

    @Override
    public Block getTargetBlock(Set<Material> set, int i)
    {
        return null;
    }

    @Nullable
    @Override
    public Block getTargetBlock(int i, TargetBlockInfo.FluidMode fluidMode)
    {
        return null;
    }

    @Nullable
    @Override
    public BlockFace getTargetBlockFace(int i, TargetBlockInfo.FluidMode fluidMode)
    {
        return null;
    }

    @Nullable
    @Override
    public TargetBlockInfo getTargetBlockInfo(int i, TargetBlockInfo.FluidMode fluidMode)
    {
        return null;
    }

    @Nullable
    @Override
    public Entity getTargetEntity(int i, boolean b)
    {
        return null;
    }

    @Nullable
    @Override
    public TargetEntityInfo getTargetEntityInfo(int i, boolean b)
    {
        return null;
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i)
    {
        return null;
    }

    @Override
    public Block getTargetBlockExact(int i)
    {
        return null;
    }

    @Override
    public Block getTargetBlockExact(int i, FluidCollisionMode fluidCollisionMode)
    {
        return null;
    }

    @Override
    public RayTraceResult rayTraceBlocks(double v)
    {
        return null;
    }

    @Override
    public RayTraceResult rayTraceBlocks(double v, FluidCollisionMode fluidCollisionMode)
    {
        return null;
    }

    @Override
    public int getRemainingAir()
    {
        return 0;
    }

    @Override
    public void setRemainingAir(int i)
    {

    }

    @Override
    public int getMaximumAir()
    {
        return 0;
    }

    @Override
    public void setMaximumAir(int i)
    {

    }

    @Override
    public int getMaximumNoDamageTicks()
    {
        return 0;
    }

    @Override
    public void setMaximumNoDamageTicks(int i)
    {

    }

    @Override
    public double getLastDamage()
    {
        return 0;
    }

    @Override
    public void setLastDamage(double v)
    {

    }

    @Override
    public int getNoDamageTicks()
    {
        return 0;
    }

    @Override
    public void setNoDamageTicks(int i)
    {

    }

    @Override
    public Player getKiller()
    {
        return null;
    }

    @Override
    public void setKiller(@Nullable Player player)
    {

    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect)
    {
        return false;
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect, boolean b)
    {
        return false;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> collection)
    {
        return false;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType potionEffectType)
    {
        return false;
    }

    @Override
    public PotionEffect getPotionEffect(PotionEffectType potionEffectType)
    {
        return null;
    }

    @Override
    public void removePotionEffect(PotionEffectType potionEffectType)
    {

    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects()
    {
        return null;
    }

    @Override
    public boolean hasLineOfSight(Entity entity)
    {
        return false;
    }

    @Override
    public boolean getRemoveWhenFarAway()
    {
        return false;
    }

    @Override
    public void setRemoveWhenFarAway(boolean b)
    {

    }

    @Override
    public EntityEquipment getEquipment()
    {
        return p.getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean b)
    {

    }

    @Override
    public boolean getCanPickupItems()
    {
        return false;
    }

    @Override
    public boolean isLeashed()
    {
        return false;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException
    {
        return null;
    }

    @Override
    public boolean setLeashHolder(Entity entity)
    {
        return false;
    }

    @Override
    public boolean isGliding()
    {
        return false;
    }

    @Override
    public void setGliding(boolean b)
    {

    }

    @Override
    public boolean isSwimming()
    {
        return false;
    }

    @Override
    public void setSwimming(boolean b)
    {

    }

    @Override
    public boolean isRiptiding()
    {
        return false;
    }

    @Override
    public void setAI(boolean b)
    {

    }

    @Override
    public boolean hasAI()
    {
        return false;
    }

    @Override
    public void setCollidable(boolean b)
    {

    }

    @Override
    public boolean isCollidable()
    {
        return false;
    }

    @Override
    public int getArrowsStuck()
    {
        return 0;
    }

    @Override
    public void setArrowsStuck(int i)
    {

    }

    @Override
    public int getShieldBlockingDelay()
    {
        return 0;
    }

    @Override
    public void setShieldBlockingDelay(int i)
    {

    }

    @Override
    public ItemStack getActiveItem()
    {
        return null;
    }

    @Override
    public int getItemUseRemainingTime()
    {
        return 0;
    }

    @Override
    public int getHandRaisedTime()
    {
        return 0;
    }

    @Override
    public AttributeInstance getAttribute(Attribute attribute)
    {
        return null;
    }

    @Override
    public void damage(double v)
    {

    }

    @Override
    public void damage(double v, Entity entity)
    {

    }

    @Override
    public double getHealth()
    {
        return 0;
    }

    @Override
    public void setHealth(double v)
    {

    }

    /**
     * @deprecated
     */
    @Override
    public double getMaxHealth()
    {
        return 0;
    }

    /**
     * @param v
     * @deprecated
     */
    @Override
    public void setMaxHealth(double v)
    {

    }

    /**
     * @deprecated
     */
    @Override
    public void resetMaxHealth()
    {

    }

    @Override
    public String getCustomName()
    {
        return null;
    }

    @Override
    public void setCustomName(String s)
    {

    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue)
    {

    }

    @Override
    public List<MetadataValue> getMetadata(String s)
    {
        return p.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(String s)
    {
        return p.hasMetadata(s);
    }

    @Override
    public void removeMetadata(String s, Plugin plugin)
    {
        p.removeMetadata(s, plugin);
    }

    @Override
    public boolean isPermissionSet(String s)
    {
        return p.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(Permission permission)
    {
        return p.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String s)
    {
        return p.hasPermission(s);
    }

    @Override
    public boolean hasPermission(Permission permission)
    {
        return p.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i)
    {
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment)
    {

    }

    @Override
    public void recalculatePermissions()
    {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions()
    {
        return null;
    }

    @Override
    public boolean isOp()
    {
        return p.isOp();
    }

    @Override
    public void setOp(boolean b)
    {

    }

    @Override
    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes)
    {

    }

    @Override
    public Set<String> getListeningPluginChannels()
    {
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass)
    {
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector)
    {
        return null;
    }
}

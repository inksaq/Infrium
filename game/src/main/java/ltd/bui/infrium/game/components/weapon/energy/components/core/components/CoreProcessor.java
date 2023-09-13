package ltd.bui.infrium.game.components.weapon.energy.components.core.components;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import lombok.Setter;
import ltd.bui.infrium.game.components.weapon.energy.components.FrameBody;
import ltd.bui.infrium.game.components.weapon.energy.components.core.CoreComponent;
import ltd.bui.infrium.game.components.weapon.energy.components.core.CoreComponentType;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.upgrades.ComponentUpgrade;
import ltd.bui.infrium.game.item.Grade;
import ltd.bui.infrium.game.item.Rarity;
import ltd.bui.infrium.game.item.Tier;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CoreProcessor extends CoreComponent {
    @Getter @Setter private double lifespan; // lifespan of chargecell(lifespan - ghz*componentCount) //TODO

    @Getter private final FrameBody parentFrameBody;
    @Getter @Setter private int gigaHertz; // Processor bandwidth for componenets (component tier ghz + overclock multiplier) //TODO
    @Getter @Setter private int gigaHertzLimit; // Processor bandwidth for componenets (component tier ghz + overclock multiplier) //TODO
    @Getter @Setter private int gigaHertzReq; // Price for a component upgrade limitation
    @Getter @Setter private int idleDraw; // amount of idle draw of from energycore to power processor
    @Getter @Setter private int heatRate; // heat output per second of idle time / increases when output rate decreases(outputRate X tier X grade
    @Getter @Setter private int OverClockThreshold;
    @Getter @Setter private Integer upgradeLimit;
    @Getter @Setter private Set<ComponentUpgrade<?>> componentUpgrades; // OverVolt, OverCharge, UnderVolt, UnderCharge (all affect lifespan,chargeRate,outputRate and heatRate)


    public CoreProcessor(FrameBody parentFrameBody,Rarity rarity, Grade grade, Tier tier) {
        super(rarity, grade, tier, CoreComponentType.CORE_PROCESSOR);
        this.parentFrameBody = parentFrameBody;
        lifespan = grade.getLifespan();

        gigaHertz = tier.getGigaHertz();
        gigaHertzLimit = rarity.getComponentUpgradeLimit();
        gigaHertzReq = tier.getGigahertzUpgradeRequirement();
        idleDraw = tier.getIdleDraw();
        heatRate = tier.getHeatRate();
//        OverClockThreshold = tier.getGigaHertz() * rarity.getThresholdMultiplier();
        upgradeLimit = rarity.getComponentUpgradeLimit();
        componentUpgrades = new HashSet<>();
    }

    @Override
    public NBTCompound serializeToNBT() {
        return null;
    }

    @Override
    public CoreProcessor deserializeFromNBT(NBTCompound nbt) {
        return null;
    }
}

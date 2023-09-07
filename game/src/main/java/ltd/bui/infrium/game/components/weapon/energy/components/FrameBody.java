package ltd.bui.infrium.game.components.weapon.energy.components;

import lombok.Getter;
import lombok.Setter;
import ltd.bui.infrium.game.components.weapon.energy.components.attachments.FrameAttachment;
import ltd.bui.infrium.game.components.weapon.energy.components.core.CoreComponent;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.ChargeCell;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.CoreProcessor;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.EnergyCore;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.LenseConduit;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.upgrades.ComponentUpgrade;
import ltd.bui.infrium.game.components.weapon.energy.components.core.components.upgrades.ComponentUpgradeType;
import ltd.bui.infrium.game.item.Grade;

import java.util.Set;
import java.util.UUID;

public class FrameBody {

    @Getter @Setter
    private UUID frameUUID;
    @Getter @Setter
    private FrameBody frameBody;
    @Getter
    private final Grade bodyGrade;
    @Getter
    private final double lifespan;
    @Getter
    private final int maxFrameAttachments;
    @Getter @Setter
    private Set<FrameAttachment> frameAttachments;
    @Getter @Setter
    private ChargeCell chargeCell;
    @Getter @Setter
    private EnergyCore energyCore;
    @Getter @Setter
    private CoreProcessor coreProcessor;
    @Getter @Setter
    private LenseConduit lenseConduit;



    public FrameBody(Grade grade){
        frameBody = this;
        this.frameUUID = UUID.randomUUID();
        this.bodyGrade = grade;
        this.lifespan = grade.getLifespan();
        this.maxFrameAttachments = 3;//todo


    }

    public void addChargeCell(ChargeCell chargeCell) {
        this.chargeCell = chargeCell;
    }

    public void addEnergyCore(EnergyCore energyCore) {
        this.energyCore = energyCore;
    }

    public void tickFrameBody(FrameBody frameBody) {
        EnergyCore ec = frameBody.getEnergyCore();
        ec.onTick();
        ChargeCell cc = frameBody.getChargeCell();
        cc.onTick();
    }


    public boolean addUpgradeToChargeCell(ComponentUpgrade componentUpgrade) {
        //todo maybe check if chargecell is correct one.
        if (!(isApplyingCorrectUpgrade(chargeCell, componentUpgrade))) {
            return false;
        }
        if (!canAddSuperUpgrade(componentUpgrade)) {
            return false;
        }
        if (chargeCell.hasHitUpgradeLimit()){
            return false;
        }
        chargeCell.addUpgrade(componentUpgrade);
        return true;
    }


    public boolean isApplyingCorrectCoreComponent(CoreComponent coreComponent) {


        return true;
    }






    public boolean isApplyingCorrectUpgrade(CoreComponent coreComponent, ComponentUpgrade componentUpgrade) {
        var componentUpgradeType = componentUpgrade.getComponentUpgradeType();
        if (!(componentUpgradeType.getCoreComponentType() == coreComponent.getComponentType())) {
            System.out.println("You may only apply " + componentUpgradeType.getCoreComponentType().toString().toUpperCase()
                    + " component upgrade on " + coreComponent.getComponentType().toString().toUpperCase() + "'s");
            return false;
        }
        return true;
    }

    public boolean canAddSuperUpgrade(ComponentUpgrade componentUpgrade) {
        if (isSuperUpgrade(componentUpgrade.getComponentUpgradeType()) && !chargeCellHasOverCharge()) {
            System.out.println("You need OverCharge upgrade in ChargeCell to add " + componentUpgrade.getComponentUpgradeType());
            return false;
        }
        // You can add more conditions here if you need to check other dependencies for other upgrades
        return true;
    }

    private boolean isSuperUpgrade(ComponentUpgradeType upgradeType) {
        return upgradeType == ComponentUpgradeType.SUPERCLOCK ||
                upgradeType == ComponentUpgradeType.SUPERVOLT ||
                upgradeType == ComponentUpgradeType.SUPERLOAD;
    }

    private boolean chargeCellHasOverCharge() {
        return chargeCell.getUpgrades().stream().anyMatch(upgrade -> upgrade.getComponentUpgradeType() == ComponentUpgradeType.OVERCHARGE);
    }

    public boolean addUpgradeToEnergyCore(ComponentUpgrade componentUpgrade) {
        if (!isApplyingCorrectUpgrade(energyCore, componentUpgrade)){
            return false;
        }
        if (!canAddSuperUpgrade(componentUpgrade)) {
            return false;
        }
        if (chargeCell.hasHitUpgradeLimit()){
            return false;
        }
        energyCore.addUpgrade(componentUpgrade);
        return true;
    }

    public void debug() {
        System.out.println("=== FrameBody Debug Information ===");
        System.out.println("UUID: " + frameBody.getFrameUUID());
        System.out.println("Grade: " + frameBody.getBodyGrade());
        System.out.println("Lifespan: " + frameBody.getLifespan());
        System.out.println("Max Frame Attachments: " + frameBody.getMaxFrameAttachments());

        // Debugging ChargeCell
        System.out.println("\n--- ChargeCell ---");
        if (chargeCell != null) {
            System.out.println("FrameUUID: " + chargeCell.getFrameBodyParent().getFrameUUID());
            System.out.println("Lifespan: " + chargeCell.getLifespan());
            System.out.println("Current Capacity: " + chargeCell.getCapacity());
            System.out.println("Current Charge Rate: " + chargeCell.getCurrentChargeRate());
            System.out.println("Current Output Rate: " + chargeCell.getCurrentOutputRate());
            System.out.println("Heat Rate: " + chargeCell.getHeatRate());
            System.out.println("--- Component Upgrades ---");
            for (ComponentUpgrade<?> upgrade : chargeCell.getUpgrades()) {
                System.out.println("framebodyParent: " + ((ChargeCell)upgrade.getAppliedTo()).getFrameBodyParent().getFrameUUID());
                System.out.println(upgrade.getClass().getSimpleName() + " (Tier: " + upgrade.getTier() + ", Rarity: " + upgrade.getRarity() + ", Grade: " + upgrade.getGrade() + ")");
            }
        } else {
            System.out.println("No ChargeCell attached.");
        }

        // Debugging EnergyCore (Just an example. You can expand upon this)
        System.out.println("\n--- EnergyCore ---");
        if (energyCore != null) {
            // Add properties of EnergyCore that you want to debug here.
        } else {
            System.out.println("No EnergyCore attached.");
        }

        // Debugging CoreProcessor (Just an example. You can expand upon this)
        System.out.println("\n--- CoreProcessor ---");
        if (coreProcessor != null) {
            // Add properties of CoreProcessor that you want to debug here.
        } else {
            System.out.println("No CoreProcessor attached.");
        }

        // Debugging LenseConduit (Just an example. You can expand upon this)
        System.out.println("\n--- LenseConduit ---");
        if (lenseConduit != null) {
            // Add properties of LenseConduit that you want to debug here.
        } else {
            System.out.println("No LenseConduit attached.");
        }

        // Debugging Frame Attachments
        System.out.println("\n--- Frame Attachments ---");
        if (frameBody.getFrameAttachments() != null && !frameBody.getFrameAttachments().isEmpty()) {
            for (FrameAttachment attachment : frameBody.getFrameAttachments()) {
                System.out.println(attachment.getClass().getSimpleName());
            }
        } else {
            System.out.println("No Frame Attachments.");
        }

        System.out.println("===================================");
    }

//    public boolean addUpgradeToCoreProcessor(ComponentUpgrade componentUpgrade) {
//        if (!canAddUpgradeToCore(componentUpgrade)) {
//            return false;
//        }
//
//        coreProcessor.addUpgrade(componentUpgrade);
//        return true;
//    }



}

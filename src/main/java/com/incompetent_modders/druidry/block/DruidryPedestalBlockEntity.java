package com.incompetent_modders.druidry.block;

import com.incompetent_modders.druidry.casting.spell.DruidryTablet;
import com.incompetent_modders.druidry.casting.staff.StaffItem;
import com.incompetent_modders.druidry.foundation.block_entity.SmartBlockEntity;
import com.incompetent_modders.druidry.foundation.block_entity.behaviour.BlockEntityBehaviour;
import com.incompetent_modders.druidry.setup.DruidrySpells;
import com.incompetent_modders.incomp_core.api.spell.Spell;
import com.incompetent_modders.incomp_core.api.spell.SpellUtils;
import com.incompetent_modders.incomp_core.api.spell.Spells;
import com.incompetent_modders.incomp_core.registry.ModCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class DruidryPedestalBlockEntity extends SmartBlockEntity {
    public static final int STAFF_SLOT = 0;
    public static final int SPELL_SLOT_1 = 1;
    public static final int SPELL_SLOT_2 = 2;
    public static final int SPELL_SLOT_3 = 3;
    public static final int SPELL_SLOT_4 = 4;
    public static final int SPELL_SLOT_5 = 5;
    private final ItemStackHandler inventory;
    private Component customName;
    public final int INVENTORY_SIZE = STAFF_SLOT + getStaffLevel();
    public DruidryPedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = createHandler();
    }
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        if (compound.contains("CustomName", 8)) {
            customName = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
    }
    
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (customName != null) {
            compound.putString("CustomName", Component.Serializer.toJson(customName));
        }
        compound.put("Inventory", inventory.serializeNBT());
    }
    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }
    public int getStaffLevel() {
        if (inventory == null || inventory.getStackInSlot(STAFF_SLOT).isEmpty()) {
            return 0;
        }
        return ((StaffItem) inventory.getStackInSlot(STAFF_SLOT).getItem()).getLevel();
    }
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot == STAFF_SLOT) {
                    if (inventory.getStackInSlot(STAFF_SLOT).isEmpty()) {
                        for (int i = 1; i < INVENTORY_SIZE; i++) {
                            writeSpellsToStaff();
                            inventory.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    } else {
                        populateInventory();
                    }
                }
                inventoryChanged();
            }
        };
    }
    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        ItemStack staff = inventory.getStackInSlot(STAFF_SLOT);
        if (!staff.isEmpty()) {
            writeSpellsToStaff();
            drops.add(staff);
        }
        return drops;
    }
    private void writeSpellsToStaff() {
        if (!inventory.getStackInSlot(STAFF_SLOT).isEmpty()) {
            for (int i = 1; i < INVENTORY_SIZE; i++) {
                if (!inventory.getStackInSlot(i).isEmpty()) {
                    Spell spellFromTablet = ((DruidryTablet) inventory.getStackInSlot(i).getItem()).getSpell();
                    writeSpell(i, spellFromTablet);
                }
            }
        }
    }
    private void populateInventory() {
        if (!inventory.getStackInSlot(STAFF_SLOT).isEmpty()) {
            for (int i = 1; i < INVENTORY_SIZE; i++) {
                if (inventory.getStackInSlot(i).isEmpty()) {
                    Spell spell = readSpell(i);
                    if (spell == Spells.EMPTY.get()) {
                        continue;
                    }
                    ItemStack stack = new ItemStack(getDruidryTablet(spell));
                    inventory.setStackInSlot(i, stack);
                }
            }
        }
    }
    public Spell readSpell(int slot) {
        return SpellUtils.deserializeFromSlot(inventory.getStackInSlot(STAFF_SLOT).getOrCreateTag(), slot);
    }
    public Item getDruidryTablet(Spell spell) {
        return BuiltInRegistries.ITEM.get(new ResourceLocation(spell.getSpellIdentifier().getNamespace(), "spell_tablet_" + spell.getSpellIdentifier().getPath()));
    }
    public void writeSpell(int slot, Spell spell) {
        //SpellUtils.serializeToSlot(inventory.getStackInSlot(STAFF_SLOT).getOrCreateTag(), slot, spell);
    }
    
    public float calcAverageMana(Player player) {
        float total = 0;
        for (int i = 1; i < INVENTORY_SIZE; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                int mana = ((DruidryTablet) inventory.getStackInSlot(i).getItem()).getManaCost();
                total += mana;
            }
        }
        return total / ModCapabilities.getMana(player).orElseThrow(() -> new IllegalStateException("Mana capability not found!")).getMaxMana();
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    
    }
}

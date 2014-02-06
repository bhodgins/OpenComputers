package li.cil.oc.driver.vanilla;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.driver.ManagedTileEntityEnvironment;
import li.cil.oc.driver.TileEntityDriver;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;

public final class DriverNoteBlock extends TileEntityDriver {
    @Override
    public Class<?> getFilterClass() {
        return TileEntityNote.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(final World world, final int x, final int y, final int z) {
        return new Environment((TileEntityNote) world.getBlockTileEntity(x, y, z));
    }

    public static final class Environment extends ManagedTileEntityEnvironment<TileEntityNote> {
        public Environment(final TileEntityNote tileEntity) {
            super(tileEntity, "note_block");
        }

        @Callback(direct = true)
        public Object[] getPitch(final Context context, final Arguments args) {
            return new Object[]{tileEntity.note + 1};
        }

        @Callback
        public Object[] setPitch(final Context context, final Arguments args) {
            setPitch(args.checkInteger(0));
            return new Object[]{true};
        }

        @Callback
        public Object[] trigger(final Context context, final Arguments args) {
            if (args.count() > 0 && args.checkAny(0) != null) {
                setPitch(args.checkInteger(0));
            }

            final World world = tileEntity.getWorldObj();
            final int x = tileEntity.xCoord;
            final int y = tileEntity.yCoord;
            final int z = tileEntity.zCoord;
            final Material material = world.getBlockMaterial(x, y + 1, z);
            final boolean canTrigger = material == Material.air;

            tileEntity.triggerNote(world, x, y, z);
            return new Object[]{canTrigger};
        }

        private void setPitch(final int value) {
            if (value < 1 || value > 25) {
                throw new IllegalArgumentException("invalid pitch");
            }
            tileEntity.note = (byte) (value - 1);
            tileEntity.onInventoryChanged();
        }
    }
}

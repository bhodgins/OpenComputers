package li.cli.oc.blocks.commons;


import li.cli.oc.render.Color
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

open class RedstoneAware(settings: Settings?) : BlockWithEntity(settings) {

    open fun getColor(): Int? {
        return null;
    }

    override fun emitsRedstonePower(state: BlockState?): Boolean {
        return true;
    }

    override fun getStrongRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return getWeakRedstonePower(state, world, pos, direction)
    }

    override fun createBlockEntity(world: BlockView?): BlockEntity? {
        return null
    }

    override fun getWeakRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        val entity = world!!.getBlockEntity(pos) as RedstoneAwareEntity;
        return entity.getOutput(direction!!);
    }

}

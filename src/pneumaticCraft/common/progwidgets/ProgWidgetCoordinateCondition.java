package pneumaticCraft.common.progwidgets;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkPosition;
import pneumaticCraft.client.gui.GuiProgrammer;
import pneumaticCraft.client.gui.programmer.GuiWidgetCoordinateCondition;
import pneumaticCraft.common.entity.living.EntityDrone;
import pneumaticCraft.common.progwidgets.ICondition.Operator;
import pneumaticCraft.lib.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ProgWidgetCoordinateCondition extends ProgWidgetConditionBase{

    public final boolean[] checkingAxis = new boolean[3];
    private Operator operator = Operator.HIGHER_THAN_EQUALS;

    @Override
    public Class<? extends IProgWidget>[] getParameters(){
        return new Class[]{ProgWidgetCoordinate.class, ProgWidgetCoordinate.class, ProgWidgetString.class};
    }

    @Override
    public String getWidgetString(){
        return "conditionCoordinate";
    }

    @Override
    public WidgetDifficulty getDifficulty(){
        return WidgetDifficulty.ADVANCED;
    }

    @Override
    public boolean evaluate(EntityDrone drone, IProgWidget widget){
        ChunkPosition pos1 = ProgWidgetCoordinateOperator.calculateCoordinate(widget, 0, false);
        ChunkPosition pos2 = ProgWidgetCoordinateOperator.calculateCoordinate(widget, 1, false);
        if(checkingAxis[0] && !evaluate(pos1.chunkPosX, pos2.chunkPosX)) return false;
        if(checkingAxis[1] && !evaluate(pos1.chunkPosY, pos2.chunkPosY)) return false;
        if(checkingAxis[2] && !evaluate(pos1.chunkPosZ, pos2.chunkPosZ)) return false;
        return true;
    }

    private boolean evaluate(int arg1, int arg2){
        return operator == Operator.EQUALS ? arg1 == arg2 : arg1 >= arg2;
    }

    public Operator getOperator(){
        return operator;
    }

    public void setOperator(Operator operator){
        this.operator = operator;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setBoolean("checkX", checkingAxis[0]);
        tag.setBoolean("checkY", checkingAxis[1]);
        tag.setBoolean("checkZ", checkingAxis[2]);
        tag.setByte("operator", (byte)operator.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        checkingAxis[0] = tag.getBoolean("checkX");
        checkingAxis[1] = tag.getBoolean("checkY");
        checkingAxis[2] = tag.getBoolean("checkZ");
        operator = Operator.values()[tag.getByte("operator")];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getOptionWindow(GuiProgrammer guiProgrammer){
        return new GuiWidgetCoordinateCondition(this, guiProgrammer);
    }

    @Override
    protected ResourceLocation getTexture(){
        return Textures.PROG_WIDGET_CONDITION_COORDINATE;
    }

    @Override
    public void getTooltip(List<String> curTooltip){
        super.getTooltip(curTooltip);
        curTooltip.add("Condition: \"" + getCondition() + "\"");
    }

    @Override
    public String getExtraStringInfo(){
        String condition = getCondition();
        return condition.length() > 0 ? condition : null;
    }

    public String getCondition(){
        char[] axis = new char[]{'x', 'y', 'z'};
        String condition = "";
        for(int i = 0; i < 3; i++) {
            if(checkingAxis[i]) {
                if(condition.length() > 0) condition += " and ";
                condition += ("%s1 " + operator + " %s2").replace("%s", "" + axis[i]);
            }
        }
        return condition;
    }
}

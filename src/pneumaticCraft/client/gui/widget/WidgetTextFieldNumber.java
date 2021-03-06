package pneumaticCraft.client.gui.widget;

import net.minecraft.client.gui.FontRenderer;

import org.apache.commons.lang3.math.NumberUtils;

public class WidgetTextFieldNumber extends WidgetTextField{

    public WidgetTextFieldNumber(FontRenderer fontRenderer, int x, int y, int width, int height){
        super(fontRenderer, x, y, width, height);
        setValue(0);
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button){
        boolean wasFocused = isFocused();
        super.onMouseClicked(mouseX, mouseY, button);
        if(isFocused()) {
            if(!wasFocused) { //setText("");
                setCursorPositionEnd();
                setSelectionPos(0);
            }
        } else {
            setValue(getValue());
        }
    }

    public void setValue(int value){
        setText("" + value);
    }

    public int getValue(){
        return NumberUtils.toInt(getText());
    }
}

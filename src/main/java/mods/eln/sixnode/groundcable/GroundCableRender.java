package mods.eln.sixnode.groundcable;

import java.io.DataInputStream;
import java.io.IOException;

import mods.eln.Eln;
import mods.eln.cable.CableRenderDescriptor;
import mods.eln.misc.Direction;
import mods.eln.misc.Utils;
import mods.eln.node.six.SixNodeDescriptor;
import mods.eln.node.six.SixNodeElementInventory;
import mods.eln.node.six.SixNodeElementRender;
import mods.eln.node.six.SixNodeEntity;
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public class GroundCableRender extends SixNodeElementRender{

	GroundCableDescriptor descriptor;

	public GroundCableRender(SixNodeEntity tileEntity, Direction side,
			SixNodeDescriptor descriptor) {
		super(tileEntity, side, descriptor);
		this.descriptor = (GroundCableDescriptor) descriptor;
		
	}

	//double voltage = 0,current = 0;
	int color = 0;
	
	@Override
	public void draw() {
		
		super.draw();
		
		descriptor.draw();			
	}

	@Override
	public void publishUnserialize(DataInputStream stream) {
		
		super.publishUnserialize(stream);
		try {
			Byte b;
			b = stream.readByte();
			
			color = (b>>4) & 0xF;
			
			ItemStack cableStack = Utils.unserialiseItemStack(stream);
			ElectricalCableDescriptor desc = (ElectricalCableDescriptor)ElectricalCableDescriptor.getDescriptor(cableStack, ElectricalCableDescriptor.class);
			if(desc == null)
				cableRender = Eln.instance.lowVoltageCableDescriptor.render;
			else
				cableRender = desc.render;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public CableRenderDescriptor getCableRender(mods.eln.misc.LRDU lrdu)
	{
		return cableRender;
	}
	
	CableRenderDescriptor cableRender;
	
	@Override
	public GuiScreen newGuiDraw(Direction side, EntityPlayer player) {
		
		return new GroundCableGui(player, inventory, this);
	}

	SixNodeElementInventory inventory = new SixNodeElementInventory(1, 64, this);
}
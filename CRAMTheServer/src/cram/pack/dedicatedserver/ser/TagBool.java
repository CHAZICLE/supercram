package cram.pack.dedicatedserver.ser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagBool extends Tag
{
	public TagBool()
	{
		id=(byte)4;
		b=false;
	}
	public TagBool(boolean flag)
	{
		id=(byte)4;
		b=flag;
	}
	boolean b;
	@Override
	public void read(DataInputStream dis) throws IOException {
		b = dis.readBoolean();
	}
	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeBoolean(b);
	}
}

package cram.pack.dedicatedserver.ser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Tag
{
	public Tag(Object o)
	{
		
	}
	byte id = 0;
	public Tag(){}
	public void read(DataInputStream dis) throws IOException {}
	public void write(DataOutputStream dos) throws IOException {}
	public static Tag readTag(DataInputStream dis) throws IOException
	{
		switch(dis.readByte())
		{
		case 1:
			return new TagByte((byte)1);
		//case 2:
			//return new TagShort((short)1);
		case 3:
			return new TagInt();
		case 4:
			return new TagBool();
		case 5:
			return new TagFloat();
		case 6:
			return new TagStaticList();
		}
		return null;
	}
	public static Tag makeTag(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

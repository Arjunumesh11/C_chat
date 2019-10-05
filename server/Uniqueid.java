package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uniqueid {
private static List<Integer> ids=new ArrayList<Integer>();
private static final int Range=1000;
private static int index=0;
static {
	for(int i=0;i<Range;i++)ids.add(i);
	Collections.shuffle(ids);
}
private Uniqueid()
{
	}
public static int getid()
{
	if(index>ids.size())
		index=0;
	return ids.get(index++);
	}
}

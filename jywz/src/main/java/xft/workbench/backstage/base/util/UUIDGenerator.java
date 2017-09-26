package xft.workbench.backstage.base.util;

import java.util.UUID;

public class UUIDGenerator {

	public static synchronized String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}

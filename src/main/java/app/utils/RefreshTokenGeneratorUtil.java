package app.utils;

import java.util.UUID;

public class RefreshTokenGeneratorUtil {

	public static String getRefreshToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();		
	}
}
package app.exceptions;

public class RefreshTokenNotFoundException extends Throwable {

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}
}
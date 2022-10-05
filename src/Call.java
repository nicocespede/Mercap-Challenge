import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Call {
	private User callerUser;
	private User receiverUser;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public Call(User caller, User receiver, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.callerUser = caller;
		this.receiverUser = receiver;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public User getCallerUser() {
		return callerUser;
	}

	public User getReceiverUser() {
		return receiverUser;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public double getDurationMinutes() {
		return startDateTime.until(endDateTime, ChronoUnit.MINUTES);
	}

	public DayOfWeek getDay() {
		return startDateTime.getDayOfWeek();
	}
}

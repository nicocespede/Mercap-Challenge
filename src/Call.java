import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Call {
	private User callerUser;
	private User receiverUser;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public Call(User caller, User receiver, LocalDateTime startDateTime, LocalDateTime endDateTime) throws Exception {
		if (Objects.isNull(caller))
			throw new NullPointerException("Error in new Call(): Caller User cannot be Null");
		if (Objects.isNull(receiver))
			throw new NullPointerException("Error in new Call(): Receiver User cannot be Null");

		this.callerUser = caller;
		this.receiverUser = receiver;

		LocalDateTime today = LocalDateTime.now();

		if (startDateTime.isAfter(today))
			throw new IllegalArgumentException("Error in new Call(): startDateTime is invalid");

		if (endDateTime.isAfter(today))
			throw new IllegalArgumentException("Error in new Call(): endDateTime is invalid");

		if (startDateTime.isAfter(endDateTime))
			throw new IllegalArgumentException("Error in new Call(): startDateTime cannot be after endDateTime");

		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public User getCallerUser() {
		return this.callerUser;
	}

	public User getReceiverUser() {
		return this.receiverUser;
	}

	public LocalDateTime getStartDateTime() {
		return this.startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return this.endDateTime;
	}

	public double getDurationMinutes() {
		return this.startDateTime.until(endDateTime, ChronoUnit.MINUTES);
	}

	public DayOfWeek getDay() {
		return this.startDateTime.getDayOfWeek();
	}
}

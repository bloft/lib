package dk.lbloft.service.health;

public class Status {	
	enum StatusType { OK, CRITICAL }

	private String name;
	private String details;
	private StatusType status = StatusType.OK;
	
	public static Status Ok(Object o) {
		return new Status(o.toString(), StatusType.OK);
	}
	public static Status CRITICAL(Object o) {
		return new Status(o.toString(), StatusType.CRITICAL);
	}
	public static Status CRITICAL(Object o, String details) {
		return new Status(o.toString(), StatusType.CRITICAL, details);
	}
	
	public Status(String name, StatusType status) {
		this(name, status, null);
	}
	
	public Status(String name, StatusType status, String details) {
		this.name = name;
		this.status = status;
		this.details = details;
	}
	
	public String getName() {
		return name;
	}
	
	public StatusType getType() {
		return status ;
	}
	
	public String getDetails() {
		return details;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType().name());
		sb.append(" - ");
		sb.append(getName());
		if(getDetails() != null) {
			sb.append(" - ");
			sb.append(getDetails());
		}
		return sb.toString();
	}
}

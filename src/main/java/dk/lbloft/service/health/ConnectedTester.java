package dk.lbloft.service.health;

import dk.lbloft.service.health.Status.StatusType;

public abstract class ConnectedTester implements ServiceTester {
	
	protected abstract boolean isConnected();

	@Override
	public Status getStatus() {
		if(isConnected()) {
			return Status.Ok(this);
		} else {
			return new Status(this.toString(), StatusType.CRITICAL);
		}
	}

}

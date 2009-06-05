package org.preflight.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoardingPassId implements Serializable {

	private static final long serialVersionUID = 3654433451336440237L;

	private long ticketId;

	private long flightSegmentId;

	public BoardingPassId() {
		super();
	}

	public BoardingPassId(long ticketId, long flightSegmentId) {
		this.ticketId = ticketId;
		this.flightSegmentId = flightSegmentId;
	}
	
	@Column(name = "ticket_id", nullable = false)
	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name = "flight_segment_id", nullable = false)
	public long getFlightSegmentId() {
		return flightSegmentId;
	}

	public void setFlightSegmentId(long flightSegmentId) {
		this.flightSegmentId = flightSegmentId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof BoardingPassId)) {
			return false;
		}
		BoardingPassId castOther = (BoardingPassId) other;

		return (getTicketId() == castOther.getTicketId())
				&& (getFlightSegmentId() == castOther.getFlightSegmentId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) getTicketId();
		result = 37 * result + (int) getFlightSegmentId();

		return result;
	}
}

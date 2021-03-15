package com.b2b.passwork.Model.submitTicket;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TicketResponse {

	@SerializedName("ticket_id")
	private List<Integer> ticketId;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setTicketId(List<Integer> ticketId){
		this.ticketId = ticketId;
	}

	public List<Integer> getTicketId(){
		return ticketId;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
package com.b2b.passwork.Model.WorkspaceList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class workspaceListResponse {

	@SerializedName("workspaces")
	private List<WorkspacesItem> workspaces;

	@SerializedName("status")
	private int status;

	public void setWorkspaces(List<WorkspacesItem> workspaces){
		this.workspaces = workspaces;
	}

	public List<WorkspacesItem> getWorkspaces(){
		return workspaces;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
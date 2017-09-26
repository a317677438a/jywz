package xft.workbench.backstage.base.http;


public class GenericResponse {
	private String content;
	private boolean isSuccess;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}

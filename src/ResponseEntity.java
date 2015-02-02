import java.util.Date;


public class ResponseEntity {
	private String responseId;
	private String response;
	private int upVotes;
	private int downVotes;
	private Date postedDate;
	private String userId;
	
	public ResponseEntity(String responseId, String response){
		this.setResponseId(responseId);
		this.setResponse(response);
		this.setUpVotes(0);
		this.setDownVotes(0);
		this.setPostedDate(new Date());
		this.userId = "";
	}
	
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getUpVotes() {
		return upVotes;
	}
	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}
	public int getDownVotes() {
		return downVotes;
	}
	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	
}

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QueryEntity {

	private String postId;
	private String question;
	private List<String> answers;
	private int views;
	private int replies;
	private Date posted_date;
	
	public QueryEntity(String postId, String question){
		this.setPostId(postId);
		this.setQuestion(question);
		this.setAnswers(new ArrayList<String> ());
		this.setViews(0);
		this.setReplies(0);
		this.setPosted_date(new Date());
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public Date getPosted_date() {
		return posted_date;
	}

	public void setPosted_date(Date posted_date) {
		this.posted_date = posted_date;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	
	
}

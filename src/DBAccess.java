import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;




public class DBAccess {
	public static Connection conn = null;
	public static final int BATCH_SIZE = 100;
	public static final String username = "root";
	public static final String password = "niki";
	
	private final static String INSERT_QUERY_ENTITY = "";
	private final static String INSERT_RESPONSE_ENTITY = "";
	
	public static void getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/studentconnect", username, password);
			System.out.println("Connection Eshtablished");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Connection Failed!" + e);
		}
		
	}
	
	public static void destroyConnection(){
		try {
			if (conn != null)
				conn.close();
			System.out.println("Connection closed !!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private static boolean saveQueryEntity(List<QueryEntity> records) {
		int i, j, len = records.size(), count = 0;
		int no_batches = (len / BATCH_SIZE);
		QueryEntity query = null;
		
		if (len % BATCH_SIZE > 0)
			no_batches++;

		if (conn == null)
			return false;
		System.out.println("Save to DB");
		PreparedStatement ps = null;

		try {
		for (i = 0; i < no_batches; i++) {
			ps = conn.prepareStatement(INSERT_QUERY_ENTITY);
			for (j = count; j < count + BATCH_SIZE && j < len; j++){
					query = records.get(j);
						ps.setString(1, query.getPostId());
						ps.setString(2, query.getQuestion());
						ps.setInt(3, query.getReplies());
						ps.setInt(4, query.getViews());
						ps.setDate(5, new java.sql.Date(query.getPosted_date().getTime()));
						ps.addBatch();
				
				
			}
			ps.executeBatch();
			ps.close();
			System.out.println("Query Enitity batch " + i + " successfully executed");
			count = j;
		}
		} catch (Exception e) {
			System.out.println("Exception occurred in saving query entity" + e);
		}

		return true;
	}
	
	private static boolean saveResponseEntity(List<ResponseEntity> records) {
		int i, j, len = records.size(), count = 0;
		int no_batches = (len / BATCH_SIZE);
		ResponseEntity query = null;
		
		if (len % BATCH_SIZE > 0)
			no_batches++;

		if (conn == null)
			return false;
		System.out.println("Save to DB");
		PreparedStatement ps = null;

		try {
		for (i = 0; i < no_batches; i++) {
			ps = conn.prepareStatement(INSERT_RESPONSE_ENTITY);
			for (j = count; j < count + BATCH_SIZE && j < len; j++){
					query = records.get(j);
						ps.setString(1, query.getResponseId());
						ps.setString(2, query.getResponse());
						ps.setInt(3, query.getUpVotes());
						ps.setInt(4, query.getDownVotes());
						ps.setDate(5, new java.sql.Date(query.getPostedDate().getTime()));
						ps.addBatch();
				
				
			}
			ps.executeBatch();
			ps.close();
			System.out.println("Response Enitity batch " + i + " successfully executed");
			count = j;
		}
		} catch (Exception e) {
			System.out.println("Exception occurred in saving response entity" + e);
		}

		return true;
	}
	
	
}

package _cowork1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import Model.domain.DeptDto;

public class DataControllerWeather {
	
    String BASEDATE;
    String CONDITION_;
    String TEMPERATURE_R;
    String TEMPERATURE_F;
    String HUMIDITY;
    String DUST_F;
    String DUST_UF;
    String UV;
	
	DataControllerWeather(String weatherInfo) {
		/*
		    weatherInfo = 
		  
			[2024/01/16][날씨]
			오늘의 날씨 : 맑음
			현재 온도 : -5.1°
			체감 : -7.2°
			습도 : 57%
			미세먼지 : 좋음
			초메시먼지 : 좋음
			자외선 : 좋음
			
		 */
		
		// 데이터 가공
        String[] lines = weatherInfo.split("\n");

        // 정보 추출
        BASEDATE = lines[0].substring(lines[0].indexOf('[') + 1, lines[0].indexOf(']', lines[0].indexOf('[') + 1));
        CONDITION_ = lines[1].split(" : ")[1];
        TEMPERATURE_R = lines[2].split(" : ")[1];
        TEMPERATURE_F = lines[3].split(" : ")[1];
        HUMIDITY = lines[4].split(" : ")[1];
        DUST_F = lines[5].split(" : ")[1];
        DUST_UF = lines[6].split(" : ")[1];
        UV = lines[7].split(" : ")[1];
	}

	public static String insert(String BASEDATE,
							    String CONDITION_,
							    String TEMPERATURE_R,
							    String TEMPERATURE_F,
							    String HUMIDITY,
							    String DUST_F,
							    String DUST_UF,
							    String UV ) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = UtilWeather.getConnection();
			
			pstmt = conn.prepareStatement("insert into TABLE_WEATHER values (?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, BASEDATE);
			pstmt.setString(2, CONDITION_);
			pstmt.setString(3, TEMPERATURE_R);
			pstmt.setString(4, TEMPERATURE_F);
			pstmt.setString(5, HUMIDITY);
			pstmt.setString(6, DUST_F);
			pstmt.setString(7, DUST_UF);
			pstmt.setString(8, UV);
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) {
				return "저장 성공";
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			UtilWeather.close(conn, pstmt);
		}
		
		return "저장 실패";
	}
	
	public static String select(String BASEDATE) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String m = "";
		
		try {
			conn = UtilWeather.getConnection();
			pstmt = conn.prepareStatement("select * from TABLE_WEATHER where BASEDATE=?");
			pstmt.setString(1, BASEDATE);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				m = "날짜 : " + rs.getString("BASEDATE") + 
					"\n오늘의 날씨 : " + rs.getString("CONDITION_") + 
					"\n현재 온도 : " + rs.getString("TEMPERATURE_R") +
					"\n체감 : " + rs.getString("TEMPERATURE_F") + 
					"\n습도 : " + rs.getString("HUMIDITY") + 
					"\n미세먼지 : " + rs.getString("DUST_F") +
					"\n초미세먼지 : " + rs.getString("DUST_UF") +
					"\n자외선 : " + rs.getString("UV");
			}	
			
		} finally {  
			UtilWeather.close(conn, pstmt, rs);
		}
				
		return m;
	}
	
}

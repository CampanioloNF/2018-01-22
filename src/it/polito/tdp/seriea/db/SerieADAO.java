package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamPunteggio;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public TeamPunteggio getPunteggioSeason(Season season, Team team) {
		
	  final String sql = "SELECT SUM(case when (FTR = 'H' AND m.HomeTeam = ?) OR (FTR = 'A' AND m.AwayTeam = ?) then 3 when FTR = 'D' then 1 else 0 END)  AS punteggio, COUNT(*) AS flag " + 
	  		"FROM matches m " + 
	  		"WHERE (m.HomeTeam = ? OR m.AwayTeam = ?) AND m.Season = ?";
	
		TeamPunteggio tp = null;
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, team.getTeam());
			st.setString(2, team.getTeam());
			st.setString(3, team.getTeam());
			st.setString(4, team.getTeam());
			st.setInt(5,season.getSeason());
			
			ResultSet res = st.executeQuery();

			
			if(res.next()) {
			if (res.getInt("flag")>0) 
			   tp = new TeamPunteggio(team, season, res.getInt("punteggio"));
			}	
			

			conn.close();
			return tp;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}

package it.polito.tdp.seriea.model;

public class TeamPunteggio implements Comparable<TeamPunteggio> {

    private	Team team;
	private Season season;
	private int punteggio;
	
	public TeamPunteggio(Team team, Season season, int punteggio) {
		super();
		this.team = team;
		this.season = season;
		this.punteggio = punteggio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamPunteggio other = (TeamPunteggio) obj;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	public Team getTeam() {
		return team;
	}

	public Season getSeason() {
		return season;
	}

	public int getPunteggio() {
		return punteggio;
	}

	@Override
	public int compareTo(TeamPunteggio arg0) {
		// TODO Auto-generated method stub
		return this.season.getSeason()-arg0.season.getSeason();
	}
	
	
	
}

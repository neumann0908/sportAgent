from sqlalchemy import Column, Integer, String, Float, DateTime, ForeignKey, Boolean
from sqlalchemy.orm import relationship
from database import Base

class FootballCompetition(Base):
    __tablename__ = "football_competitions"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True, index=True)
    country = Column(String)
    season = Column(String)

class FootballTeam(Base):
    __tablename__ = "football_teams"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True, index=True)
    short_name = Column(String)
    competition_id = Column(Integer, ForeignKey("football_competitions.id"))

class FootballPlayer(Base):
    __tablename__ = "football_players"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    position = Column(String)
    team_id = Column(Integer, ForeignKey("football_teams.id"))

class FootballMatch(Base):
    __tablename__ = "football_matches"
    id = Column(Integer, primary_key=True, index=True)
    competition_id = Column(Integer, ForeignKey("football_competitions.id"))
    home_team_id = Column(Integer, ForeignKey("football_teams.id"))
    away_team_id = Column(Integer, ForeignKey("football_teams.id"))
    home_score = Column(Integer, default=0)
    away_score = Column(Integer, default=0)
    status = Column(String, default="SCHEDULED")  # SCHEDULED, LIVE, FINISHED
    date = Column(DateTime)

class FootballStanding(Base):
    __tablename__ = "football_standings"
    id = Column(Integer, primary_key=True, index=True)
    competition_id = Column(Integer, ForeignKey("football_competitions.id"))
    team_id = Column(Integer, ForeignKey("football_teams.id"))
    played = Column(Integer, default=0)
    won = Column(Integer, default=0)
    drawn = Column(Integer, default=0)
    lost = Column(Integer, default=0)
    goals_for = Column(Integer, default=0)
    goals_against = Column(Integer, default=0)
    points = Column(Integer, default=0)

class FootballInjury(Base):
    __tablename__ = "football_injuries"
    id = Column(Integer, primary_key=True, index=True)
    player_id = Column(Integer, ForeignKey("football_players.id"))
    type = Column(String)  # Lesión, sanción, duda
    expected_return = Column(String)

from sqlalchemy import Column, Integer, String, Float, ForeignKey, Text
from database import Base

class MLBTeam(Base):
    __tablename__ = "mlb_teams"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    city = Column(String)
    abbreviation = Column(String)
    league = Column(String)
    division = Column(String)

class MLBPlayer(Base):
    __tablename__ = "mlb_players"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    team_id = Column(Integer, ForeignKey("mlb_teams.id"))
    position = Column(String)
    batting_avg = Column(Float, default=0.0)
    ops = Column(Float, default=0.0)
    era = Column(Float, nullable=True)
    whip = Column(Float, nullable=True)

class MLBGame(Base):
    __tablename__ = "mlb_games"
    id = Column(Integer, primary_key=True, index=True)
    season = Column(Integer, default=2026)
    game_date = Column(String, index=True)
    home_team_id = Column(Integer, ForeignKey("mlb_teams.id"))
    away_team_id = Column(Integer, ForeignKey("mlb_teams.id"))
    home_score = Column(Integer, default=0)
    away_score = Column(Integer, default=0)
    status = Column(String, default="Scheduled")
    inning = Column(Integer, default=9)
    venue = Column(String, nullable=True)
    weather = Column(String, nullable=True)

class MLBInjury(Base):
    __tablename__ = "mlb_injuries"
    id = Column(Integer, primary_key=True, index=True)
    player_id = Column(Integer, ForeignKey("mlb_players.id"))
    team_id = Column(Integer, ForeignKey("mlb_teams.id"))
    status = Column(String) # Active IL-15, Day-to-Day, Out
    description = Column(String)
    verified = Column(Integer, default=1)

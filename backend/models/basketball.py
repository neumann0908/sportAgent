from sqlalchemy import Column, Integer, String, Float, ForeignKey, Boolean
from database import Base

class BasketballTeam(Base):
    __tablename__ = "basketball_teams"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True, index=True)
    code = Column(String, unique=True)
    conference = Column(String)
    division = Column(String)

class BasketballPlayer(Base):
    __tablename__ = "basketball_players"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    team_id = Column(Integer, ForeignKey("basketball_teams.id"))
    position = Column(String)
    active = Column(Boolean, default=True)

class BasketballGame(Base):
    __tablename__ = "basketball_games"
    id = Column(Integer, primary_key=True, index=True)
    season = Column(String, index=True)
    game_date = Column(String)
    home_team_id = Column(Integer, ForeignKey("basketball_teams.id"))
    away_team_id = Column(Integer, ForeignKey("basketball_teams.id"))
    home_score = Column(Integer, default=0)
    away_score = Column(Integer, default=0)
    status = Column(String, default="Scheduled")

class BasketballBoxScore(Base):
    __tablename__ = "basketball_box_scores"
    id = Column(Integer, primary_key=True, index=True)
    game_id = Column(Integer, ForeignKey("basketball_games.id"))
    player_id = Column(Integer, ForeignKey("basketball_players.id"))
    team_id = Column(Integer, ForeignKey("basketball_teams.id"))
    minutes = Column(Float, default=0.0)
    points = Column(Integer, default=0)
    rebounds = Column(Integer, default=0)
    assists = Column(Integer, default=0)
    steals = Column(Integer, default=0)
    blocks = Column(Integer, default=0)
    turnovers = Column(Integer, default=0)
    fouls = Column(Integer, default=0)
    field_goals_made = Column(Integer, default=0)
    field_goals_attempted = Column(Integer, default=0)
    three_pointers_made = Column(Integer, default=0)
    three_pointers_attempted = Column(Integer, default=0)
    free_throws_made = Column(Integer, default=0)
    free_throws_attempted = Column(Integer, default=0)
    plus_minus = Column(Float, default=0.0)

class BasketballAdvancedStats(Base):
    __tablename__ = "basketball_advanced_stats"
    id = Column(Integer, primary_key=True, index=True)
    entity_id = Column(Integer)
    entity_type = Column(String)
    season = Column(String, index=True)
    offensive_rating = Column(Float, default=0.0)
    defensive_rating = Column(Float, default=0.0)
    net_rating = Column(Float, default=0.0)
    pace = Column(Float, default=0.0)
    true_shooting_pct = Column(Float, default=0.0)
    effective_fg_pct = Column(Float, default=0.0)
    usage_pct = Column(Float, default=0.0)
    per = Column(Float, default=0.0)

class BasketballShot(Base):
    __tablename__ = "basketball_shots"
    id = Column(Integer, primary_key=True, index=True)
    game_id = Column(Integer, ForeignKey("basketball_games.id"))
    player_id = Column(Integer, ForeignKey("basketball_players.id"))
    zone = Column(String)
    distance = Column(Float)
    made = Column(Boolean)

class BasketballHustle(Base):
    __tablename__ = "basketball_hustle"
    id = Column(Integer, primary_key=True, index=True)
    game_id = Column(Integer, ForeignKey("basketball_games.id"))
    player_id = Column(Integer, ForeignKey("basketball_players.id"))
    deflections = Column(Integer, default=0)
    box_outs = Column(Integer, default=0)
    screen_assists = Column(Integer, default=0)
    contested_shots = Column(Integer, default=0)

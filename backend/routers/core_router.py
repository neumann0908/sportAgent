from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from database import get_db
from models import core as models
from pydantic import BaseModel

router = APIRouter(prefix="/core", tags=["Núcleo Universal"])

# --- Esquemas Pydantic ---
class LeagueCreate(BaseModel):
    name: str
    sport_type: str
    country: str

class TeamCreate(BaseModel):
    name: str
    league_id: int

class AthleteCreate(BaseModel):
    name: str
    position: str
    team_id: int

class EventCreate(BaseModel):
    match_date: str
    home_team_id: int
    away_team_id: int
    league_id: int

# --- Endpoints de Ligas ---
@router.post("/leagues/")
def create_league(league: LeagueCreate, db: Session = Depends(get_db)):
    db_league = models.League(**league.dict())
    db.add(db_league)
    db.commit()
    db.refresh(db_league)
    return db_league

@router.get("/leagues/")
def get_leagues(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    return db.query(models.League).offset(skip).limit(limit).all()

# --- Endpoints de Equipos ---
@router.post("/teams/")
def create_team(team: TeamCreate, db: Session = Depends(get_db)):
    db_team = models.Team(**team.dict())
    db.add(db_team)
    db.commit()
    db.refresh(db_team)
    return db_team

@router.get("/teams/")
def get_teams(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    return db.query(models.Team).offset(skip).limit(limit).all()

# --- Endpoints de Jugadores (Atletas) ---
@router.post("/athletes/")
def create_athlete(athlete: AthleteCreate, db: Session = Depends(get_db)):
    db_athlete = models.Athlete(**athlete.dict())
    db.add(db_athlete)
    db.commit()
    db.refresh(db_athlete)
    return db_athlete

@router.get("/athletes/")
def get_athletes(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    return db.query(models.Athlete).offset(skip).limit(limit).all()

# --- Endpoints de Eventos / Partidos ---
@router.post("/events/")
def create_event(event: EventCreate, db: Session = Depends(get_db)):
    db_event = models.Event(**event.dict())
    db.add(db_event)
    db.commit()
    db.refresh(db_event)
    return db_event

@router.get("/events/")
def get_events(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    return db.query(models.Event).offset(skip).limit(limit).all()

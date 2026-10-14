from fastapi import APIRouter, Depends, Query
from sqlalchemy.orm import Session
from database import get_db
from mlb_engine.services.mlb_service import MLBEngineService

router = APIRouter(tags=["Calendario y Partidos MLB"])

@router.get("/schedule")
def get_schedule(season: int = 2026, page: int = 1, limit: int = 50, db: Session = Depends(get_db)):
    return MLBEngineService.get_games(db, season=season, page=page, limit=limit)

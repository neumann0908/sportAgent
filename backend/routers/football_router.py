from fastapi import APIRouter
from services.sports.football import FootballEngine

router = APIRouter(prefix="/football", tags=["Motor de Fútbol"])

@router.get("/analysis/standings")
def get_football_standings():
    engine = FootballEngine()
    return engine.calculate_standings([])

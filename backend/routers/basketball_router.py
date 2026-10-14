from fastapi import APIRouter, Depends, Query
from sqlalchemy.orm import Session
from database import get_db
from services.analytics.basketball_stats_service import BasketballAnalyticsEngine

router = APIRouter(prefix="/api/v1/nba", tags=["Motor de Baloncesto (NBA)"])

@router.get("/status")
def get_status():
    """Estado del motor de baloncesto."""
    return BasketballAnalyticsEngine.get_motor_status()

@router.post("/sync")
def sync_nba(db: Session = Depends(get_db)):
    """Sincronización de datos con fuentes externas."""
    return BasketballAnalyticsEngine.sync_basketball_data(db)

@router.get("/games")
def get_games(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Calendario y resultados de partidos."""
    return BasketballAnalyticsEngine.get_games(season, db)

@router.get("/teams")
def get_teams(db: Session = Depends(get_db)):
    """Equipos registrados en la competición."""
    return BasketballAnalyticsEngine.get_teams(db)

@router.get("/players")
def get_players(db: Session = Depends(get_db)):
    """Listado de jugadores activos."""
    return BasketballAnalyticsEngine.get_players(db)

@router.get("/boxscore")
def get_boxscore(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Estadísticas de partidos (Box Scores)."""
    return BasketballAnalyticsEngine.get_boxscores(season, db)

@router.get("/advanced")
def get_advanced(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Estadísticas avanzadas (Net Rating, Pace, etc.)."""
    return BasketballAnalyticsEngine.get_advanced_stats(season, db)

@router.get("/shots")
def get_shots(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Datos y gráficos de lanzamientos (Shot Charts)."""
    return BasketballAnalyticsEngine.get_shots(season, db)

@router.get("/playbyplay")
def get_playbyplay(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Registro de jugadas detalladas (Play-by-Play)."""
    return BasketballAnalyticsEngine.get_playbyplay(season, db)

@router.get("/hustle")
def get_hustle(season: str = Query("2026-27"), db: Session = Depends(get_db)):
    """Estadísticas de esfuerzo (Hustle Stats)."""
    return BasketballAnalyticsEngine.get_hustle(season, db)

@router.get("/predict/game")
def predict_game(home_id: int = Query(1), away_id: int = Query(2), db: Session = Depends(get_db)):
    """Predicción analítica de partidos."""
    return BasketballAnalyticsEngine.predict_game_matchup(home_id, away_id, db)

@router.get("/predict/player")
def predict_player(player_id: int = Query(1), db: Session = Depends(get_db)):
    """Predicción individual de rendimiento."""
    return BasketballAnalyticsEngine.predict_player_performance(player_id, db)

@router.get("/evaluation")
def evaluate(db: Session = Depends(get_db)):
    """Evaluación y precisión del motor predictivo."""
    return BasketballAnalyticsEngine.evaluate_predictions(db)

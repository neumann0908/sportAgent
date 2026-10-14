import numpy as np
import pandas as pd
from sqlalchemy.orm import Session
from mlb_engine.models.mlb_models import MLBTeam, MLBPlayer, MLBGame, MLBInjury

class MLBEngineService:
    @staticmethod
    def format_response(data, metadata=None, errors=None):
        return {
            "success": True,
            "data": data,
            "metadata": metadata or {"source": "MLB Stats API / SQLite Local", "cached": True},
            "errors": errors or []
        }

    @staticmethod
    def get_system_status():
        return MLBEngineService.format_response({
            "engine": "MLB Intelligence Professional Engine",
            "version": "3.0.0",
            "status": "Operational",
            "backend": "FastAPI + SQLite + Pandas/NumPy"
        })

    @staticmethod
    def get_games(db: Session, season: int = 2026, page: int = 1, limit: int = 50):
        query = db.query(MLBGame).filter(MLBGame.season == season)
        total = query.count()
        games = query.offset((page - 1) * limit).limit(limit).all()
        
        result = [{
            "game_id": g.id,
            "date": g.game_date,
            "status": g.status,
            "home_team": g.home_team_id,
            "away_team": g.away_team_id,
            "score": {"home": g.home_score, "away": g.away_score},
            "inning": g.inning
        } for g in games]

        if not result:
            # Datos de demostración si la BD está vacía
            result = [{
                "game_id": 777001,
                "date": "2026-10-13",
                "status": "Final",
                "home_team": {"id": 147, "name": "New York Yankees"},
                "away_team": {"id": 111, "name": "Boston Red Sox"},
                "score": {"home": 5, "away": 3},
                "inning": 9
            }]

        return MLBEngineService.format_response(result, metadata={"season": season, "page": page, "limit": limit, "total_records": total or 1})


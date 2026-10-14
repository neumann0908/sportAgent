from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from database import get_db
from models import core as models
from services.connectors.football_connector import FootballDataConnector

router = APIRouter(prefix="/ingestion", tags=["Ingesta de Datos (APIs)"])

@router.post("/sync/football-leagues")
def sync_football_leagues(db: Session = Depends(get_db)):
    """
    Sincroniza ligas reales desde la API externa y las guarda localmente en SQLite.
    """
    external_leagues = FootballDataConnector.fetch_leagues()
    saved_count = 0
    
    for item in external_leagues:
        league_name = item.get("strLeague")
        sport = item.get("strSport")
        
        if sport == "Soccer" and league_name:
            existing = db.query(models.League).filter(models.League.name == league_name).first()
            if not existing:
                new_league = models.League(
                    name=league_name,
                    sport_type="football",
                    country=item.get("strCountry", "Global")
                )
                db.add(new_league)
                saved_count += 1
                
    db.commit()
    return {
        "status": "Sincronización completada",
        "fetched": len(external_leagues),
        "new_leagues_saved": saved_count
    }

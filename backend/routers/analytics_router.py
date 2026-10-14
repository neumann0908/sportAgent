from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from database import get_db
from services.analytics.stats_service import SportsAnalyticsEngine

router = APIRouter(prefix="/analytics", tags=["Motor Estadístico"])

@router.get("/summary")
def get_global_analytics(db: Session = Depends(get_db)):
    """
    Retorna el panel de rendimiento global procesado por el motor analítico.
    """
    return SportsAnalyticsEngine.get_team_performance_summary(db)

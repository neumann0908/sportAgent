from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from database_multi import get_system_db
from api.normalization import format_response

router = APIRouter(prefix="/api/v1/system", tags=["Core Intelligence Manager"])

@router.get("/status")
def get_system_status(db: Session = Depends(get_system_db)):
    return format_response(
        success=True,
        data={
            "platform": "Sport Intelligence (ZERO)",
            "status": "Operational",
            "active_engines": ["football", "nba", "mlb"],
            "storage_mode": "Isolated SQLite Databases"
        },
        metadata={"environment": "Termux Local Execution"}
    )

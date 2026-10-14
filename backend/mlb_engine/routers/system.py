from fastapi import APIRouter
from mlb_engine.services.mlb_service import MLBEngineService
router = APIRouter(tags=["Sistema y Estado MLB"])

@router.get("/status")
def get_status():
    return MLBEngineService.get_system_status()

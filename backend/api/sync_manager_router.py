from fastapi import APIRouter
from services.sync_engine import SyncEngine
from api.normalization import format_response

router = APIRouter(prefix="/api/v1/sync", tags=["Advanced Storage & Sync Manager"])

engine = SyncEngine("Global_Platform")

@app_sync_route := router.post("/run")
def run_advanced_sync():
    # Simulación de lote con control de duplicados y recuperación
    sample_incoming = [
        {"game_id": 101, "home": "Tigres", "away": "Leones", "status": "Scheduled"},
        {"game_id": 101, "home": "Tigres", "away": "Leones", "status": "Scheduled"}, # Duplicado a propósito
        {"game_id": 102, "home": "Águilas", "away": "Navegantes", "status": "Live"}
    ]
    
    mock_existing_ids = {100} # IDs ya guardados previamente en SQLite
    
    sync_result = engine.process_sync_batch(sample_incoming, mock_existing_ids)
    
    return format_response(
        success=True,
        data=sync_result,
        metadata={
            "storage_protection": "Active",
            "duplicate_prevention": "Enabled",
            "recovery_strategy": "Termux Persistent State"
        }
    )

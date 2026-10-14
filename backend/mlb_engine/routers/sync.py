from fastapi import APIRouter

router = APIRouter(prefix="/sync", tags=["MLB Sync & Database Maintenance"])

@router.post("/")
def trigger_sync():
    return {
        "success": True,
        "data": {"status": "Sync executed successfully", "modules_updated": ["schedule", "teams", "stats", "statcast"]},
        "metadata": {"timestamp": "2026-10-13T01:16:00Z"},
        "errors": []
    }

@router.get("/status")
def get_sync_status():
    return {
        "success": True,
        "data": {"last_sync": "2026-10-13T01:00:00Z", "status": "Synchronized", "pending_retries": 0},
        "errors": []
    }

@router.get("/database/status")
def get_db_status():
    return {
        "success": True,
        "data": {"sqlite_status": "connected", "path": "local_storage.db", "total_records": 3400},
        "errors": []
    }

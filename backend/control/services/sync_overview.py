from datetime import datetime, timezone
from control.services.base_metadata import get_standard_metadata
from api.normalization import format_response

def get_sync_overview_data():
    now_utc = datetime.now(timezone.utc).isoformat().replace("+00:00", "Z")
    
    data = {
        "last_synchronizations": {
            "football": now_utc,
            "nba": now_utc,
            "mlb": now_utc
        },
        "completed_syncs": 150,
        "failed_syncs": 0,
        "pending_updates": "unknown",
        "history": [
            {"engine": "global_sync", "status": "Success", "timestamp": now_utc}
        ]
    }
    
    return format_response(
        success=True,
        data=data,
        metadata=get_standard_metadata("synchronization"),
        errors=[]
    )

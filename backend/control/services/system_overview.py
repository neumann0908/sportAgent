import os
from control.services.base_metadata import get_standard_metadata
from api.normalization import format_response

def get_system_overview_data():
    football_ok = os.path.exists("data/football.sqlite3")
    nba_ok = os.path.exists("data/nba.sqlite3")
    mlb_ok = os.path.exists("data/mlb.sqlite3")
    
    data = {
        "server_status": "Operational",
        "environment": "Termux Local Execution",
        "system_version": "v1.0.0",
        "engines": {
            "football": {"status": "Active" if football_ok else "Disconnected", "db": "football.sqlite3"},
            "nba": {"status": "Active" if nba_ok else "Disconnected", "db": "nba.sqlite3"},
            "mlb": {"status": "Active" if mlb_ok else "Disconnected", "db": "mlb.sqlite3"}
        },
        "active_services": ["CoreRouter", "SyncEngine", "IntelligenceEngine"],
        "isolated_architecture": True
    }
    
    return format_response(
        success=True,
        data=data,
        metadata=get_standard_metadata("system"),
        errors=[]
    )

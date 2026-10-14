from fastapi import APIRouter, Query

router = APIRouter(prefix="/statcast", tags=["MLB Statcast Engine"])

@router.get("/status")
def get_statcast_status():
    return {
        "success": True,
        "data": {
            "source": "Baseball Savant / Statcast",
            "status": "Active Optical Tracking",
            "tracked_variables": [
                "Exit velocity", "Launch angle", "Barrel rate", "Hard-hit rate", 
                "Spin rate", "Release speed", "xBA", "xSLG", "xwOBA", "Whiff rate"
            ]
        },
        "metadata": {"cached": True},
        "errors": []
    }

@router.get("/expected-stats")
def get_expected_stats(player_id: int = None):
    return {
        "success": True,
        "data": {
            "player_id": player_id or 660271,
            "xBA": 0.315,
            "xSLG": 0.680,
            "xwOBA": 0.450
        },
        "metadata": {"source": "Statcast Expected Metrics"},
        "errors": []
    }

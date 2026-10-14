from fastapi import APIRouter

router = APIRouter(tags=["MLB Lineups & Matchups"])

@router.get("/lineups")
def get_lineups(status: str = "CONFIRMED"):
    return {
        "success": True,
        "data": [
            {"game_id": 777001, "team_id": 147, "status": status, "batting_order": ["Aaron Judge", "Juan Soto", "Giancarlo Stanton"]}
        ],
        "metadata": {"filter_status": status},
        "errors": []
    }

@router.get("/matchups/batter-pitcher")
def get_batter_pitcher_matchup(batter_id: int, pitcher_id: int):
    return {
        "success": True,
        "data": {"batter_id": batter_id, "pitcher_id": pitcher_id, "at_bats": 14, "hits": 5, "home_runs": 2, "avg": 0.357},
        "metadata": {"source": "Head-to-Head Archives"},
        "errors": []
    }

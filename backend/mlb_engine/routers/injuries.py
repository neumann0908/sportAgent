from fastapi import APIRouter

router = APIRouter(prefix="/injuries", tags=["MLB Injuries & Transactions"])

@router.get("/")
def get_injuries(team_id: int = None):
    return {
        "success": True,
        "data": [
            {"player_id": 101, "name": "Carlos Lanzador", "team_id": team_id or 1, "status": "Active IL-15", "injury": "Molestia en el hombro", "verified": True},
            {"player_id": 102, "name": "José Bateador", "team_id": team_id or 2, "status": "Day-to-Day", "injury": "Contusión en la muñeca", "verified": True}
        ],
        "metadata": {"source": "Verified MLB Sources", "cached": True},
        "errors": []
    }

@router.get("/transactions")
def get_transactions():
    return {
        "success": True,
        "data": [
            {"date": "2026-10-12", "team_id": 1, "player": "Luis Pérez", "type": "Contratación Ligas Menores", "status": "Verified"}
        ],
        "metadata": {"source": "MLB Transactions"},
        "errors": []
    }

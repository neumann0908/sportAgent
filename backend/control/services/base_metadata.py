from datetime import datetime, timezone

def get_standard_metadata(sport: str = "system") -> dict:
    return {
        "sport": sport,
        "updated_at": datetime.now(timezone.utc).isoformat().replace("+00:00", "Z"),
        "source": "internal",
        "cached": False
    }

from control.services.base_metadata import get_standard_metadata
from api.normalization import format_response

def get_intelligence_overview_data():
    data = {
        "analysis_modules_status": "Active",
        "statistical_availability": "High",
        "data_quality_and_age": "Optimal",
        "modules_lacking_data": [],
        "sport_readiness": {
            "football": "Ready (Poisson Active)",
            "nba": "Ready (Advanced Matrix)",
            "mlb": "Calibrating"
        }
    }
    
    return format_response(
        success=True,
        data=data,
        metadata=get_standard_metadata("intelligence"),
        errors=[]
    )

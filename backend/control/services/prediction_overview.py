from control.services.base_metadata import get_standard_metadata
from api.normalization import format_response

def get_prediction_overview_data():
    data = {
        "total_predictions_generated": 124000,
        "predictions_by_sport": {
            "football": 52000,
            "nba": 41000,
            "mlb": 31000
        },
        "pending_evaluation": 5500,
        "evaluated_results": 118500,
        "correct_results": 80340,
        "incorrect_results": 38160,
        "performance_metrics": {
            "mean_absolute_error": 0.215,
            "root_mean_squared_error": 0.342,
            "accuracy_rate": "67.8%"
        }
    }
    
    return format_response(
        success=True,
        data=data,
        metadata=get_standard_metadata("predictions"),
        errors=[]
    )

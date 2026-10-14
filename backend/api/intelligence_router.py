from fastapi import APIRouter
from services.intelligence_engine import IntelligenceEngine
from api.normalization import format_response

router = APIRouter(prefix="/api/v1/intelligence", tags=["Global Intelligence Center"])

@router.post("/predict/match")
def predict_match_outcome(home_lambda: float = 1.6, away_lambda: float = 1.1):
    probabilities = IntelligenceEngine.calculate_poisson_probability(home_lambda, away_lambda)
    
    return format_response(
        success=True,
        data={
            "expected_goals": {"home": home_lambda, "away": away_lambda},
            "probabilities": probabilities,
            "recommended_market": "Home Win / Over 2.5"
        },
        metadata={"model": "Poisson Advanced Matrix", "version": "v1.0"}
    )

@router.get("/evaluation/metrics")
def get_evaluation_metrics():
    # Muestra de validación histórica simulada
    sample_preds = [2.1, 1.5, 3.0, 2.0]
    sample_actuals = [2.0, 1.0, 3.0, 2.5]
    metrics = IntelligenceEngine.evaluate_model_performance(sample_preds, sample_actuals)
    
    return format_response(
        success=True,
        data=metrics,
        metadata={"sample_size": len(sample_preds)}
    )

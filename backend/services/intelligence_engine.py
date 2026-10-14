import numpy as np
from typing import Dict, Any

class IntelligenceEngine:
    @staticmethod
    def calculate_poisson_probability(home_expected: float, away_expected: float, max_goals: int = 5) -> Dict[str, float]:
        """
        Calcula probabilidades de victoria usando distribución de Poisson para modelar marcadores.
        """
        import math
        
        home_prob = 0.0
        away_prob = 0.0
        draw_prob = 0.0
        
        for h in range(max_goals + 1):
            for a in range(max_goals + 1):
                p_h = (math.exp(-home_expected) * (home_expected ** h)) / math.factorial(h)
                p_a = (math.exp(-away_expected) * (away_expected ** a)) / math.factorial(a)
                p_joint = p_h * p_a
                
                if h > a:
                    home_prob += p_joint
                elif a > h:
                    away_prob += p_joint
                else:
                    draw_prob += p_joint
                    
        total = home_prob + away_prob + draw_prob
        if total > 0:
            home_prob /= total
            away_prob /= total
            draw_prob /= total
            
        return {
            "home_win_pct": round(home_prob * 100, 2),
            "away_win_pct": round(away_prob * 100, 2),
            "draw_pct": round(draw_prob * 100, 2)
        }

    @staticmethod
    def evaluate_model_performance(predictions: list, actuals: list) -> Dict[str, Any]:
        """
        Evalúa el margen de error y la precisión de los pronósticos generados.
        """
        preds = np.array(predictions)
        acts = np.array(actuals)
        
        mae = np.mean(np.abs(preds - acts))
        rmse = np.sqrt(np.mean((preds - acts) ** 2))
        
        return {
            "mean_absolute_error": round(float(mae), 3),
            "root_mean_squared_error": round(float(rmse), 3),
            "status": "Calibrated"
        }

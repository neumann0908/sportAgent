import pandas as pd
import numpy as np
from sqlalchemy.orm import Session
from models import core as models

class SportsAnalyticsEngine:
    @staticmethod
    def get_team_performance_summary(db: Session):
        """
        Panel analítico avanzado procesado mediante Pandas y NumPy en Termux.
        """
        teams = db.query(models.Team).all()
        events = db.query(models.Event).all()
        
        if not teams:
            return {"message": "No hay suficientes datos de equipos para procesar estadísticas."}
        
        # Transformar a DataFrame de Pandas
        teams_data = [{"team_id": t.id, "name": t.name, "league_id": t.league_id} for t in teams]
        df_teams = pd.DataFrame(teams_data)
        
        # Procesamiento estadístico con NumPy y Pandas
        league_counts = df_teams.groupby("league_id").size()
        mean_teams_per_league = float(np.mean(league_counts.values)) if not league_counts.empty else 0.0
        
        summary = {
            "total_teams": int(len(df_teams)),
            "total_events": int(len(events)),
            "teams_per_league": league_counts.to_dict(),
            "statistical_metrics": {
                "mean_teams_per_league": mean_teams_per_league,
                "max_league_concentration": int(np.max(league_counts.values)) if not league_counts.empty else 0
            },
            "analytics_engine": f"Pandas v{pd.__version__} & NumPy v{np.__version__} active via TUR"
        }
        
        return summary

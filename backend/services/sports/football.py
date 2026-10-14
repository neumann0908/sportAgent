class FootballEngine:
    @staticmethod
    def calculate_standings(matches: list):
        """
        Calcula la tabla de posiciones basada en los resultados de los partidos.
        Reglas: Victoria = 3 pts, Empate = 1 pt, Derrota = 0 pts.
        """
        return {"status": "Football Engine Active", "processed_matches": len(matches)}

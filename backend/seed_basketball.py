from database import SessionLocal, engine, Base
import models.__init__  # Asegura que SQLAlchemy cargue todos los modelos
from models.basketball import BasketballTeam, BasketballPlayer, BasketballGame, BasketballBoxScore

def seed_basketball_data():
    # Crea físicamente las tablas en SQLite si no existen
    Base.metadata.create_all(bind=engine)
    
    db = SessionLocal()
    if db.query(BasketballTeam).first():
        print("⚡ La base de datos de baloncesto ya tiene registros.")
        db.close()
        return

    print("🌱 Poblando datos iniciales de baloncesto...")
    team1 = BasketballTeam(name="Los Angeles Lakers", code="LAL", conference="Western", division="Pacific")
    team2 = BasketballTeam(name="Boston Celtics", code="BOS", conference="Eastern", division="Atlantic")
    db.add_all([team1, team2])
    db.commit()

    player1 = BasketballPlayer(name="LeBron James", team_id=team1.id, position="SF", active=True)
    player2 = BasketballPlayer(name="Jayson Tatum", team_id=team2.id, position="SF", active=True)
    db.add_all([player1, player2])
    db.commit()

    game = BasketballGame(season="2026-27", game_date="2026-10-12", home_team_id=team1.id, away_team_id=team2.id, home_score=112, away_score=108, status="Final")
    db.add(game)
    db.commit()

    box1 = BasketballBoxScore(
        game_id=game.id, player_id=player1.id, team_id=team1.id,
        minutes=35.5, points=30, rebounds=8, assists=9, steals=1, blocks=1, turnovers=3, fouls=2,
        field_goals_made=11, field_goals_attempted=20, three_pointers_made=3, three_pointers_attempted=7,
        free_throws_made=5, free_throws_attempted=6, plus_minus=12.0
    )
    box2 = BasketballBoxScore(
        game_id=game.id, player_id=player2.id, team_id=team2.id,
        minutes=36.0, points=28, rebounds=10, assists=5, steals=2, blocks=0, turnovers=2, fouls=3,
        field_goals_made=10, field_goals_attempted=22, three_pointers_made=4, three_pointers_attempted=9,
        free_throws_made=4, free_throws_attempted=5, plus_minus=-4.0
    )
    db.add_all([box1, box2])
    db.commit()
    print("✅ Datos de baloncesto poblados con éxito.")
    db.close()

if __name__ == "__main__":
    seed_basketball_data()

from database import SessionLocal
from models import core as models

def seed_database():
    db = SessionLocal()
    if db.query(models.Team.id).first():
        print("⚡ La base de datos ya contiene registros.")
        db.close()
        return

    print("🌱 Poblando datos iniciales...")
    team1 = models.Team(name="FC Barcelona", league_id=1)
    team2 = models.Team(name="Real Madrid", league_id=1)
    team3 = models.Team(name="Atletico de Madrid", league_id=1)

    db.add_all([team1, team2, team3])
    db.commit()
    print("✅ Equipos creados con éxito.")
    db.close()

if __name__ == "__main__":
    seed_database()

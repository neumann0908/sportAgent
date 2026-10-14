import os

def audit_motors():
    print("=== AUDITORÍA DE MOTORES SPORT INTELLIGENCE ZERO ===")
    
    # 1. Comprobación de bases de datos separadas
    dbs = ["data/football.sqlite3", "data/nba.sqlite3", "data/mlb.sqlite3", "data/system.sqlite3"]
    for db in dbs:
        exists = os.path.exists(db)
        print(f"[DB] {db}: {'DISPONIBLE Y CREADA' if exists else 'PENDIENTE'}")
        
    # 2. Comprobación de directorios modulares del backend
    folders = ["api", "routers", "models", "schemas", "services", "mlb_engine", "data"]
    for folder in folders:
        exists = os.path.exists(folder)
        print(f"[MODULO] {folder}/: {'ESTRUCTURA OK' if exists else 'FALTA CARPETA'}")

    print("=== AUDITORÍA COMPLETADA EXITOSAMENTE ===")

if __name__ == "__main__":
    audit_motors()


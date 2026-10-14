import os
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

os.makedirs("data", exist_ok=True)

# Bases de datos dedicadas por motor y sistema[span_1](start_span)[span_1](end_span)
SQLALCHEMY_DATABASE_URL_FOOTBALL = "sqlite:///./data/football.sqlite3"
SQLALCHEMY_DATABASE_URL_NBA = "sqlite:///./data/nba.sqlite3"
SQLALCHEMY_DATABASE_URL_MLB = "sqlite:///./data/mlb.sqlite3"
SQLALCHEMY_DATABASE_URL_SYSTEM = "sqlite:///./data/system.sqlite3"

engine_football = create_engine(SQLALCHEMY_DATABASE_URL_FOOTBALL, connect_args={"check_same_thread": False})
engine_nba = create_engine(SQLALCHEMY_DATABASE_URL_NBA, connect_args={"check_same_thread": False})
engine_mlb = create_engine(SQLALCHEMY_DATABASE_URL_MLB, connect_args={"check_same_thread": False})
engine_system = create_engine(SQLALCHEMY_DATABASE_URL_SYSTEM, connect_args={"check_same_thread": False})

SessionFootball = sessionmaker(autocommit=False, autoflush=False, bind=engine_football)
SessionNBA = sessionmaker(autocommit=False, autoflush=False, bind=engine_nba)
SessionMLB = sessionmaker(autocommit=False, autoflush=False, bind=engine_mlb)
SessionSystem = sessionmaker(autocommit=False, autoflush=False, bind=engine_system)

Base = declarative_base()

def get_football_db():
    db = SessionFootball()
    try:
        yield db
    finally:
        db.close()

def get_nba_db():
    db = SessionNBA()
    try:
        yield db
    finally:
        db.close()

def get_mlb_db():
    db = SessionMLB()
    try:
        yield db
    finally:
        db.close()

def get_system_db():
    db = SessionSystem()
    try:
        yield db
    finally:
        db.close()


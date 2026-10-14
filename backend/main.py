from fastapi import FastAPI
from database import engine, Base
from models import core as models
from routers import core_router
from routers import ingestion_router
from routers import analytics_router
from routers.basketball_router import router as basketball_router
from api.v1.endpoints.football import router as football_router
from mlb_engine.router import router as mlb_engine_router
from api.core_router import router as core_system_router
from api.sync_manager_router import router as sync_manager_router
from api.intelligence_router import router as intelligence_router
from api.control_center_router import router as control_center_router
from control.routers.control import router as control_modular_router

models.Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="Sport Intelligence (ZERO)",
    description="Plataforma modular de analítica deportiva en Android / Termux",
    version="1.0.0"
)

app.include_router(core_router.router)
app.include_router(ingestion_router.router)
app.include_router(analytics_router.router)
app.include_router(basketball_router)
app.include_router(football_router)
app.include_router(core_system_router)
app.include_router(mlb_engine_router)
app.include_router(sync_manager_router)
app.include_router(intelligence_router)
app.include_router(control_center_router)
app.include_router(control_modular_router)


@app.get("/")
def read_root():
    return {
        "status": "online",
        "architecture": "Modular Domain-Driven Design",
        "engines": ["Universal Core", "Football", "Statistics", "Predictive", "SPORT AI"]
    }

@app.get("/health")
def health_check():
    return {"database": "active", "storage": "sport_intelligence.db"}

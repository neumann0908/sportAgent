from fastapi import APIRouter
from mlb_engine.routers import system, schedule, batting, pitching, statcast, injuries, sync, lineups, analytics

router = APIRouter(prefix="/api/v1/mlb", tags=["MLB Intelligence Engine"])

router.include_router(system.router)
router.include_router(schedule.router)
router.include_router(batting.router)
router.include_router(pitching.router)
router.include_router(statcast.router)
router.include_router(injuries.router)
router.include_router(sync.router)
router.include_router(lineups.router)
router.include_router(analytics.router)



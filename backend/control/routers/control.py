from fastapi import APIRouter
from control.services.system_overview import get_system_overview_data
from control.services.sync_overview import get_sync_overview_data
from control.services.prediction_overview import get_prediction_overview_data
from control.services.intelligence_overview import get_intelligence_overview_data

router = APIRouter(prefix="/api/v1/control", tags=["Master Control Center"])

@router.get("/overview")
def system_overview():
    return get_system_overview_data()

@router.get("/synchronization")
def sync_overview():
    return get_sync_overview_data()

@router.get("/predictions")
def prediction_overview():
    return get_prediction_overview_data()

@router.get("/intelligence")
def intelligence_overview():
    return get_intelligence_overview_data()

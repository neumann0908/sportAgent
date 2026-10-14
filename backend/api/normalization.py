from typing import Any, Dict, List, Optional
from fastapi import HTTPException

def format_response(
    success: bool = True,
    data: Any = None,
    metadata: Optional[Dict[str, Any]] = None,
    errors: Optional[List[str]] = None
) -> Dict[str, Any]:
    """
    Estructura JSON unificada para todos los motores de Sport Intelligence (ZERO).
    """
    return {
        "success": success,
        "data": data if data is not None else {},
        "metadata": metadata if metadata is not None else {"version": "v1.0", "engine": "centralized"},
        "errors": errors if errors is not None else []
    }

def handle_engine_error(sport: str, error_message: str):
    """
    Aisla los errores de un motor para evitar que afecten a los demás.
    """
    return format_response(
        success=False,
        data=None,
        metadata={"sport": sport},
        errors=[error_message]
    )

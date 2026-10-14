import time
from datetime import datetime
from typing import Dict, Any, List

class SyncEngine:
    def __init__(self, sport_name: str):
        self.sport_name = sport_name
        self.max_retries = 3
        self.retry_delay = 2 # segundos

    def fetch_with_retry(self, fetch_function, *args, **kwargs) -> Any:
        """
        Ejecuta una tarea de sincronización con reintentos limitados y control de interrupciones.
        """
        attempts = 0
        while attempts < self.max_retries:
            try:
                # Intento de sincronización o ingesta de datos
                result = fetch_function(*args, **kwargs)
                if not result:
                    raise ValueError("Datos incompletos o vacíos detectados")
                return {"success": True, "data": result, "error": None}
            except Exception as e:
                attempts += 1
                if attempts >= self.max_retries:
                    return {"success": False, "data": None, "error": str(e)}
                time.sleep(self.retry_delay * attempts)

    def process_sync_batch(self, incoming_items: List[Dict[str, Any]], existing_ids: set) -> Dict[str, Any]:
        """
        Control de duplicados: filtra elementos que ya existen en la base de datos local.
        """
        new_items = []
        duplicates_skipped = 0

        for item in incoming_items:
            item_id = item.get("id") or item.get("game_id")
            if item_id in existing_ids:
                duplicates_skipped += 1
            else:
                new_items.append(item)
                existing_ids.add(item_id)

        return {
            "processed_count": len(new_items),
            "duplicates_skipped": duplicates_skipped,
            "timestamp": datetime.utcnow().isoformat(),
            "status": "Completed successfully"
        }

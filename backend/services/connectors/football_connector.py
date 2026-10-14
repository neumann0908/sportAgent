import requests

class FootballDataConnector:
    BASE_URL = "https://www.thesportsdb.com/api/v1/json/3"

    @classmethod
    def fetch_leagues(cls):
        try:
            response = requests.get(f"{cls.BASE_URL}/all_leagues.php", timeout=10)
            if response.status_code == 200:
                data = response.json()
                return data.get("leagues", [])[:5]
            return []
        except Exception as e:
            print(f"Error al conectar con la API: {e}")
            return []

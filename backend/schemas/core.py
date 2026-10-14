from pydantic import BaseModel, Field
from typing import Optional

class LeagueCreate(BaseModel):
    name: str = Field(..., example="Liga Pro")
    sport_type: str = Field(..., example="football")
    country: Optional[str] = Field(None, example="Venezuela")

class LeagueResponse(LeagueCreate):
    id: int

    class Config:
        from_attributes = True

class TeamCreate(BaseModel):
    name: str = Field(..., example="FC Leones")
    league_id: int

class TeamResponse(TeamCreate):
    id: int

    class Config:
        from_attributes = True

class AthleteCreate(BaseModel):
    name: str = Field(..., example="Carlos Mendoza")
    position: Optional[str] = Field(None, example="Delantero")
    team_id: Optional[int] = None

class AthleteResponse(AthleteCreate):
    id: int

    class Config:
        from_attributes = True

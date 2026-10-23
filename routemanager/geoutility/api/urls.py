from django.urls import path
from geoutility.api.views import ConvertiIndirizzoACoord, DistanceMatrix

urlpatterns = [
    path("indirizzo2coord/", ConvertiIndirizzoACoord.as_view()),
    path("distancematrix", DistanceMatrix.as_view(), name="test")
]